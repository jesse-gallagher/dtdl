package frostillicus.dtdl.app.model.issues;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;

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
		
		return service.getIssues(repoId, Collections.emptyMap()).stream()
			.map(i -> new Issue(i.getTitle(), i.getHtmlUrl()))
			.collect(Collectors.toList());
	}
}
