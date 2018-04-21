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
package frostillicus.dtdl.app.model;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import org.jnosql.artemis.Column;
import org.jnosql.artemis.Entity;
import org.jnosql.artemis.Id;

import frostillicus.dtdl.app.model.info.GitHubInfo;
import frostillicus.dtdl.app.model.info.InfoHolder;
import frostillicus.dtdl.app.model.issues.IssueProvider;
import frostillicus.dtdl.app.model.util.ModelUtil;
import frostillicus.dtdl.app.model.issues.GitHubIssueProvider;
import frostillicus.dtdl.app.model.issues.Issue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity @ToString
public class Source {
	@AllArgsConstructor
	public enum Type {
		GITHUB(
			Messages.getString("Source.type.github"), //$NON-NLS-1$
			GitHubInfo.class,
			GitHubIssueProvider.class
		);
		
		@Getter private final String friendlyName;
		@Getter private final Class<? extends InfoHolder> infoClass;
		@Getter private final Class<? extends IssueProvider<?>> issueProviderClass;
		
		@SuppressWarnings("unchecked")
		public <Q extends InfoHolder, T extends IssueProvider<Q>> T getIssueProvider() {
			return (T)ModelUtil.instantiateObject(issueProviderClass);
		}
	}
	
	@Id @Getter @Setter @NotEmpty
	private String id = UUID.randomUUID().toString();
	
	@Column @Getter @Setter
	private String title;
	
	@Column @Getter @Setter
	private Type type;
	
	@Column @Getter @Setter
	private GitHubInfo github;
	
	public List<Issue> getIssues() {
		if(type == null) {
			return Collections.emptyList();
		}
		return type.getIssueProvider().getIssues(github);
	}
}
