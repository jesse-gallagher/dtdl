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
package frostillicus.dtdl.app.model.services;

import static frostillicus.dtdl.app.model.services.ServiceUtil.ok;

import java.util.stream.Collectors;

import javax.enterprise.inject.spi.CDI;
import javax.validation.constraints.NotNull;

import org.darwino.jnosql.diana.driver.EntityConverter;
import org.jnosql.artemis.Repository;
import org.jnosql.artemis.document.DocumentEntityConverter;

import com.darwino.commons.json.JsonObject;
import com.darwino.commons.services.AbstractHttpService;
import com.darwino.commons.services.HttpServiceContext;

import frostillicus.dtdl.app.model.Source;
import frostillicus.dtdl.app.model.issues.Issue;
import frostillicus.dtdl.app.model.util.ModelUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IssueListService extends AbstractHttpService {
	
	private final @NotNull String sourceId;
	
	@Override
	protected void doGet(HttpServiceContext context) throws Exception {
		@SuppressWarnings("unchecked")
		Repository<Source, Object> repository = (Repository<Source, Object>)ModelUtil.getRepository(Source.class);
		if(repository == null) {
			throw new NullPointerException("Could not find repository for class " + Source.class.getName()); //$NON-NLS-1$
		}

		DocumentEntityConverter documentEntityConverter = CDI.current().select(DocumentEntityConverter.class).get();
		
		Source source = repository.findById(sourceId).orElseThrow(() -> new RuntimeException("Could not find source for ID " + sourceId)); //$NON-NLS-1$
		context.emitJson(JsonObject.of(
			"status", "success", //$NON-NLS-1$ //$NON-NLS-2$
			"payload", source.getIssues().stream() //$NON-NLS-1$
				.map(documentEntityConverter::toDocument)
				.map(EntityConverter::convert)
				.collect(Collectors.toList())
		));
	}
	
	@Override
	protected void doPost(HttpServiceContext context) throws Exception {
		@SuppressWarnings("unchecked")
		Repository<Source, Object> repository = (Repository<Source, Object>)ModelUtil.getRepository(Source.class);
		if(repository == null) {
			throw new NullPointerException("Could not find repository for class " + Source.class.getName()); //$NON-NLS-1$
		}
		
		Source source = repository.findById(sourceId).orElseThrow(() -> new RuntimeException("Could not find source for ID " + sourceId)); //$NON-NLS-1$
		
		Object newModel = context.getContentAsJson();
		Issue issue = ModelUtil.toEntity((JsonObject)newModel, Issue.class);
		source.getType().getIssueProvider().createIssue(source.getInfoHolder(), issue);
		
		ok(context, issue.toString());
	}
}
