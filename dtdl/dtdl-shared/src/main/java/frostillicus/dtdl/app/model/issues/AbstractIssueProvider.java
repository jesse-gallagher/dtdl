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
package frostillicus.dtdl.app.model.issues;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.map.PassiveExpiringMap;

import frostillicus.dtdl.app.model.info.InfoHolder;

public abstract class AbstractIssueProvider<T extends InfoHolder> {
	private Map<T, List<Issue>> ISSUE_CACHE = Collections.synchronizedMap(new PassiveExpiringMap<>(30, TimeUnit.SECONDS));
	private Map<String, List<Comment>> COMMENT_CACHE = Collections.synchronizedMap(new PassiveExpiringMap<>(30, TimeUnit.SECONDS));
	
	public List<Issue> getIssues(T info) {
		return ISSUE_CACHE.computeIfAbsent(info, this::doGetIssues);
	}
	protected abstract List<Issue> doGetIssues(T info);
	
	public List<Comment> getComments(T info, String issueId) {
		return COMMENT_CACHE.computeIfAbsent(info+issueId, (key) -> this.doGetComments(info, issueId));
	}
	protected abstract List<Comment> doGetComments(T info, String issueId);
	
	public abstract void createIssue(T info, Issue issue);
}
