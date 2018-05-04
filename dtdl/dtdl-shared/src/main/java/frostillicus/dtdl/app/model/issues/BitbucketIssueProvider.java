package frostillicus.dtdl.app.model.issues;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.darwino.commons.Platform;
import com.darwino.commons.httpclnt.HttpClient;
import com.darwino.commons.httpclnt.HttpClientService;
import com.darwino.commons.json.JsonArray;
import com.darwino.commons.json.JsonObject;
import com.darwino.commons.util.StringUtil;

import frostillicus.dtdl.app.WeldContext;
import frostillicus.dtdl.app.model.bitbucket.BitbucketIssue;
import frostillicus.dtdl.app.model.info.BitbucketInfo;
import frostillicus.dtdl.app.model.util.ModelUtil;
import lombok.SneakyThrows;

public class BitbucketIssueProvider implements IssueProvider<BitbucketInfo> {
	
	public static final String API_BASE = "https://api.bitbucket.org/2.0"; //$NON-NLS-1$
	public static final String REPOSITORIES_RESOURCE = "repositories"; //$NON-NLS-1$
	public static final String ISSUES_RESOURCE = "issues"; //$NON-NLS-1$

	@Override
	@SneakyThrows
	public List<Issue> getIssues(BitbucketInfo info) {
		HttpClient client = getBitbucketClient(info);
		
		String repository = StringUtil.toString(info.getRepository());
		int slashIndex = repository.indexOf('/');
		if(slashIndex < 1 || slashIndex == repository.length()-1) {
			throw new IllegalArgumentException("Repository must contain an organization and a repository: " + repository);
		}
		String org = repository.substring(0, slashIndex);
		String repo = repository.substring(slashIndex+1);
		
		Object json = client.getAsJson(new String[] { REPOSITORIES_RESOURCE, org, repo, ISSUES_RESOURCE });
		if(!(json instanceof JsonObject)) {
			throw new IllegalStateException("Received unexpected JSON response: " + json);
		}
		JsonArray values = ((JsonObject)json).getAsArray("values"); //$NON-NLS-1$
		if(values == null) {
			throw new IllegalStateException("JSON response did not contain values");
		}
		
		return values.stream()
			.map(this::createIssue)
			.collect(Collectors.toList());
	}

	// *******************************************************************************
	// * Internal utility methods
	// *******************************************************************************
	
	private Issue createIssue(Object obj) {
		if(!(obj instanceof JsonObject)) {
			throw new IllegalStateException("Received unexpected issue JSON: " + obj);
		}
		JsonObject json = (JsonObject)obj;
		BitbucketIssue issue = ModelUtil.toEntity(WeldContext.INSTANCE.getContainer(), json, BitbucketIssue.class);
		
		String title = issue.getTitle();
		String url = issue.getLinks().getHtml().getHref();
		Issue.Status status;
		switch(issue.getState()) {
		case "closed": //$NON-NLS-1$
			status = Issue.Status.CLOSED;
			break;
		default:
			status = Issue.Status.OPEN;
			break;
		}
		
		List<String> tags = Collections.emptyList();
		
		return new Issue(title, url, status, tags);
	}
	
	private HttpClient getBitbucketClient(BitbucketInfo info) {
		return Platform.getService(HttpClientService.class).createHttpClient(API_BASE, info.getUsername(), info.getPassword());
	}
}
