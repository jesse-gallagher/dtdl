package frostillicus.dtdl.app.model.issues;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.darwino.commons.Platform;
import com.darwino.commons.httpclnt.HttpClient;
import com.darwino.commons.httpclnt.HttpClientService;
import com.darwino.commons.json.JsonArray;
import com.darwino.commons.json.JsonException;
import com.darwino.commons.json.JsonObject;
import com.darwino.commons.util.StringUtil;

import frostillicus.dtdl.app.WeldContext;
import frostillicus.dtdl.app.model.bitbucket.BitbucketIssue;
import frostillicus.dtdl.app.model.info.BitbucketInfo;
import frostillicus.dtdl.app.model.util.ModelUtil;
import lombok.SneakyThrows;

public class BitbucketIssueProvider implements IssueProvider<BitbucketInfo> {
	
	public static final String API_BASE = "https://api.bitbucket.org/1.0"; //$NON-NLS-1$
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
		
		List<Issue> result = new LinkedList<>();
		fetch(result, client, org, repo, 0);
		return result;
	}

	// *******************************************************************************
	// * Internal utility methods
	// *******************************************************************************
	
	private void fetch(List<Issue> result, HttpClient client, String org, String repo, int start) throws JsonException {
		Map<String, Object> params = new HashMap<>();
		params.put("limit", 50); //$NON-NLS-1$
		params.put("status", Arrays.asList("open", "new", "on hold")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		Object jsonObj = client.getAsJson(new String[] { REPOSITORIES_RESOURCE, org, repo, ISSUES_RESOURCE }, params);
		if(!(jsonObj instanceof JsonObject)) {
			throw new IllegalStateException("Received unexpected JSON response: " + jsonObj);
		}
		JsonObject json = (JsonObject)jsonObj;
		JsonArray values = json.getAsArray("issues"); //$NON-NLS-1$
		if(values == null) {
			throw new IllegalStateException("JSON response did not contain issues: " + json);
		}
		
		values.stream()
			.map(this::createIssue)
			.forEach(result::add);
		
		// If there are more, fetch the next page
		int count = json.getAsInt("count"); //$NON-NLS-1$
		int read = values.size();
		int remainder = count - read - start;
		if(remainder > 0) {
			fetch(result, client, org, repo, start+read);
		}
	}
	
	private Issue createIssue(Object obj) {
		if(!(obj instanceof JsonObject)) {
			throw new IllegalStateException("Received unexpected issue JSON: " + obj);
		}
		JsonObject json = (JsonObject)obj;
		BitbucketIssue issue = ModelUtil.toEntity(WeldContext.INSTANCE.getContainer(), json, BitbucketIssue.class);
		
		String title = issue.getTitle();
		String resourceUri = issue.getResourceUri();
		String url = "https://bitbucket.org" + resourceUri.substring("/1.0/repositories".length()); //$NON-NLS-1$ //$NON-NLS-2$
		
		Issue.Status status;
		switch(issue.getStatus()) {
		case "closed": //$NON-NLS-1$
			status = Issue.Status.CLOSED;
			break;
		default:
			status = Issue.Status.OPEN;
			break;
		}
		
		List<String> tags = Collections.emptyList();
		
		BitbucketIssue.Metadata m = issue.getMetadata();
		Issue.Version version = null;
		if(m != null) {
			String v = m.getVersion();
			if(StringUtil.isNotEmpty(v)) {
				version = Issue.Version.builder().name(v).build();
			}
		}
		
		return Issue.builder()
			.id(StringUtil.toString(issue.getLocalId()))
			.title(title)
			.url(url)
			.status(status)
			.tags(tags)
			.version(version)
			.build();
	}
	
	private HttpClient getBitbucketClient(BitbucketInfo info) {
		return Platform.getService(HttpClientService.class).createHttpClient(API_BASE, info.getUsername(), info.getPassword());
	}
}
