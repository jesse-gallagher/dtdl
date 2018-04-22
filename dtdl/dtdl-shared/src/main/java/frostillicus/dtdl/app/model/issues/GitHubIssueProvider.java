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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;

import com.darwino.commons.util.StringUtil;

import frostillicus.dtdl.app.model.info.GitHubInfo;
import lombok.SneakyThrows;

public class GitHubIssueProvider implements IssueProvider<GitHubInfo> {
	
	@Override
	@SneakyThrows
	public List<Issue> getIssues(GitHubInfo info) {
		GitHubClient client = new GitHubClient();
		client.setOAuth2Token(info.getToken());
		
		IssueService service = new IssueService(client);
		RepositoryId repoId = RepositoryId.createFromId(info.getRepository());
		
		Map<String, String> filterData = new HashMap<>();
		filterData.put("filter", "all"); //$NON-NLS-1$ //$NON-NLS-2$
		return service.getIssues(repoId, filterData).stream()
			.map(this::createIssue)
			.collect(Collectors.toList());
	}
	
	private Issue createIssue(org.eclipse.egit.github.core.Issue i) {
		String state = StringUtil.toString(i.getState());
		Issue.Status status;
		switch(state) {
		case "closed": //$NON-NLS-1$
			status = Issue.Status.CLOSED;
			break;
		case "open": //$NON-NLS-1$
		default:
			status = Issue.Status.OPEN;
			break;
		}
		return new Issue(i.getTitle(), i.getHtmlUrl(), status);
	}
}
