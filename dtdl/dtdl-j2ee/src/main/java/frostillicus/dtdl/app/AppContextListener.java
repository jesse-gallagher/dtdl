/**
 * Copyright © 2018 Jesse Gallagher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package frostillicus.dtdl.app;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.inject.spi.CDI;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.darwino.commons.Platform;
import com.darwino.commons.json.JsonException;
import com.darwino.commons.security.acl.UserContextFactory;
import com.darwino.commons.security.acl.impl.UserImpl;
import com.darwino.commons.tasks.Task;
import com.darwino.commons.tasks.TaskProgress;
import com.darwino.commons.tasks.scheduler.TaskScheduler;
import com.darwino.commons.tasks.scheduler.schedulers.IntervalScheduler;
import com.darwino.commons.tasks.scheduler.schedulers.Scheduler;
import com.darwino.commons.tasks.scheduler.schedulers.TimeoutScheduler;
import com.darwino.github.issues.jstore.ReplicationProfileGitHubIssues;
import com.darwino.github.issues.jstore.ReplicationSourceGitHubIssues;
import com.darwino.github.issues.jstore.ReplicatorGitHubIssuesFromLocal;
import com.darwino.j2ee.application.AbstractDarwinoContextListener;
import com.darwino.j2ee.application.BackgroundServletSynchronizationExecutor;
import com.darwino.j2ee.application.DarwinoJ2EEApplication;
import com.darwino.j2ee.application.DarwinoJ2EEContext;
import com.darwino.j2ee.application.DarwinoJ2EEContextFactory;
import com.darwino.jre.application.DarwinoJreApplication;
import com.darwino.jsonstore.Database;
import com.darwino.jsonstore.replication.ReplicationOptions;
import com.darwino.jsonstore.replication.ReplicationProfile;
import com.darwino.jsonstore.replication.impl.ReplicationSource;
import com.darwino.jsonstore.replication.impl.ReplicationTarget;
import com.darwino.jsonstore.sql.impl.full.DatabaseImpl;
import com.darwino.jsonstore.sql.impl.full.replication.ReplicationTargetLocal;
import com.darwino.platform.DarwinoContext;
import com.darwino.platform.DarwinoContextFactory;
import com.darwino.platform.events.builder.EventBuilderFactory;
import com.darwino.platform.events.builder.StaticEventBuilder;
import com.darwino.platform.persistence.JsonStorePersistenceService;

import frostillicus.dtdl.app.model.SourceRepository;
import frostillicus.dtdl.app.model.info.GitHubInfo;
import frostillicus.dtdl.app.model.Source.Type;
import frostillicus.dtdl.app.model.Source;

/**
 * Servlet listener for initializing the application.
 */
public class AppContextListener extends AbstractDarwinoContextListener {

	private BackgroundServletSynchronizationExecutor syncExecutor; 
	private EventBuilderFactory triggers;
	
	public AppContextListener() {
	}
	
	@Override
	protected DarwinoJ2EEApplication createDarwinoApplication(ServletContext context) throws JsonException {
		return AppJ2EEApplication.create(context);
	}
	
	@Override
	protected void initAsync(ServletContext servletContext, TaskProgress progress) throws JsonException {
		super.initAsync(servletContext, progress);
		
		// Initialize the replication asynchronously so the database is properly deployed before it starts
		initReplication(servletContext, progress);
		// Enable triggers for notifications
		initTriggers(servletContext, progress);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if(syncExecutor!=null) {
			syncExecutor.stop();
			syncExecutor = null;
		}
		if(triggers!=null) {
			triggers.uninstall();
			triggers = null;
		}

		super.contextDestroyed(sce);
	}

	protected void initReplication(ServletContext servletContext, TaskProgress progress) throws JsonException {
 		// Define these to enable the background replication with another server 
		syncExecutor = new BackgroundServletSynchronizationExecutor(getApplication(),servletContext);
		syncExecutor.putPropertyValue("dwo-sync-database",AppDatabaseDef.DATABASE_NAME); //$NON-NLS-1$
		syncExecutor.start();
		
		Scheduler sched = new TimeoutScheduler("1s"); //$NON-NLS-1$
		Task<Void> task = Task.from(() -> {
			// Init and tear down a temporary Darwino context
			
			DarwinoJ2EEContextFactory fac = (DarwinoJ2EEContextFactory)Platform.getService(DarwinoContextFactory.class);
			try(DarwinoJ2EEContext context = new DarwinoJ2EEContext(DarwinoJreApplication.get(), null, null, new UserImpl(), new UserContextFactory(), null, DarwinoJreApplication.get().getLocalJsonDBServer().createSystemSession(null))) {
				fac.push(context);
				
				SourceRepository repo = CDI.current().select(SourceRepository.class).get();
				repo.findAll().stream()
					.filter(s -> s.getType() == Type.GITHUB)
					.forEach(s -> {
						Platform.log("Want to rep with " + s);
						
						try {
							Database database = DarwinoContext.get().getSession().getDatabase(AppDatabaseDef.DATABASE_NAME);
							GitHubInfo info = (GitHubInfo)s.getInfoHolder();
							ReplicatorGitHubIssuesFromLocal replicator = new ReplicatorGitHubIssuesFromLocal(database, info.getToken(), info.getRepository(), AppDatabaseDef.STORE_ISSUES, AppDatabaseDef.STORE_COMMENTS, null);
							
							ReplicationProfile profile = new ReplicationProfileGitHubIssues();
							ReplicationOptions opts = new ReplicationOptions(profile);
							
							replicator.pull(opts);
							
						} catch (JsonException e) {
							throw new RuntimeException(e);
						}
					});
			} catch (JsonException e1) {
				throw new RuntimeException(e1);
			} finally {
				fac.pop();
			}
			
			
		});
		Platform.getService(TaskScheduler.class).scheduleTask(task, sched);
	}


	protected void initTriggers(ServletContext servletContext, TaskProgress progress) throws JsonException {
		// Install the triggers
		StaticEventBuilder triggerList = new StaticEventBuilder();
		
		// Use a persistence service for the dates
		JsonStorePersistenceService svc = new JsonStorePersistenceService()
				.database(AppDatabaseDef.DATABASE_NAME)
				.category("trigger"); //$NON-NLS-1$
		triggers = new EventBuilderFactory(triggerList,svc);
		triggers.install();
	}
}
