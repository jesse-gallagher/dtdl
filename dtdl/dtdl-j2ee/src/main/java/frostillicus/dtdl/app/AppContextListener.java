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

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.tasks.TaskProgress;
import com.darwino.j2ee.application.AbstractDarwinoContextListener;
import com.darwino.j2ee.application.BackgroundServletSynchronizationExecutor;
import com.darwino.j2ee.application.DarwinoJ2EEApplication;
import com.darwino.platform.events.builder.EventBuilderFactory;
import com.darwino.platform.events.builder.StaticEventBuilder;
import com.darwino.platform.persistence.JsonStorePersistenceService;

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
