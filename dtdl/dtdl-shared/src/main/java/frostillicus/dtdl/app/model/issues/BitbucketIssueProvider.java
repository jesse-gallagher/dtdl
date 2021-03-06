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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.darwino.commons.Platform;
import com.darwino.commons.httpclnt.HttpClient;
import com.darwino.commons.httpclnt.HttpClientService;
import com.darwino.commons.json.JsonArray;
import com.darwino.commons.json.JsonObject;
import com.darwino.commons.util.NotImplementedException;
import com.darwino.commons.util.StringUtil;

import frostillicus.dtdl.app.beans.MarkdownBean;
import frostillicus.dtdl.app.model.bitbucket.BitbucketComment;
import frostillicus.dtdl.app.model.bitbucket.BitbucketIssue;
import frostillicus.dtdl.app.model.bitbucket.UserInfo;
import frostillicus.dtdl.app.model.info.BitbucketInfo;
import frostillicus.dtdl.app.model.util.ModelUtil;
import lombok.SneakyThrows;

@ApplicationScoped
public class BitbucketIssueProvider extends AbstractIssueProvider<BitbucketInfo> {
	
	private static final ThreadLocal<DateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd hh:mm:ssX")); //$NON-NLS-1$
	
	public static final String API_BASE = "https://api.bitbucket.org/1.0"; //$NON-NLS-1$
	public static final String REPOSITORIES_RESOURCE = "repositories"; //$NON-NLS-1$
	public static final String ISSUES_RESOURCE = "issues"; //$NON-NLS-1$
	public static final String COMMENTS_RESOURCE = "comments"; //$NON-NLS-1$
	
	@Inject
	private MarkdownBean markdown;

	@Override
	protected List<Issue> doGetIssues(BitbucketInfo info) {
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
	
	@Override
	protected List<Comment> doGetComments(BitbucketInfo info, String issueId) {
		HttpClient client = getBitbucketClient(info);
		
		String repository = StringUtil.toString(info.getRepository());
		int slashIndex = repository.indexOf('/');
		if(slashIndex < 1 || slashIndex == repository.length()-1) {
			throw new IllegalArgumentException("Repository must contain an organization and a repository: " + repository);
		}
		String org = repository.substring(0, slashIndex);
		String repo = repository.substring(slashIndex+1);
		
		List<Comment> result = new LinkedList<>();
		fetchComments(result, client, org, repo, issueId);
		return result;
	}
	
	@Override
	public void createIssue(BitbucketInfo info, Issue issue) {
		throw new NotImplementedException();
	}

	// *******************************************************************************
	// * Internal utility methods
	// *******************************************************************************
	@SneakyThrows
	private void fetch(List<Issue> result, HttpClient client, String org, String repo, int start) {
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
			.map(this::toIssue)
			.forEach(result::add);
		
		// If there are more, fetch the next page
		int count = json.getAsInt("count"); //$NON-NLS-1$
		int read = values.size();
		int remainder = count - read - start;
		if(remainder > 0) {
			fetch(result, client, org, repo, start+read);
		}
	}
	
	@SneakyThrows
	private Issue toIssue(Object obj) {
		if(!(obj instanceof JsonObject)) {
			throw new IllegalStateException("Received unexpected issue JSON: " + obj);
		}
		JsonObject json = (JsonObject)obj;
		BitbucketIssue issue = ModelUtil.toEntity(json, BitbucketIssue.class);
		
		String title = issue.getTitle();
		String resourceUri = issue.getResourceUri();
		String url = "https://bitbucket.org" + resourceUri.substring("/1.0/repositories".length()); //$NON-NLS-1$ //$NON-NLS-2$
		
		Issue.Status status;
		switch(issue.getStatus()) {
		case "closed": //$NON-NLS-1$
			status = Issue.Status.CLOSED;
			break;
		case "on hold": //$NON-NLS-1$
			status = Issue.Status.ON_HOLD;
			break;
		case "new": //$NON-NLS-1$
			status = Issue.Status.NEW;
			break;
		default:
			status = Issue.Status.OPEN;
			break;
		}
		
		List<Issue.Tag> tags = Collections.emptyList();
		
		BitbucketIssue.Metadata m = issue.getMetadata();
		Issue.Version version = null;
		if(m != null) {
			String v = m.getVersion();
			if(StringUtil.isNotEmpty(v)) {
				version = Issue.Version.builder().name(v).build();
			}
		}
		
		String content = issue.getContent();
		String html = markdown.toHtml(content);

		Person assignee = toPerson(issue.getResponsible());
		Person reportedBy = toPerson(issue.getReportedBy());
		
		Date updated = toDate(issue.getLastUpdatedUtc());
		Date created = toDate(issue.getCreatedUtc());
		
		return Issue.builder()
			.id(StringUtil.toString(issue.getLocalId()))
			.title(title)
			.url(url)
			.status(status)
			.tags(tags)
			.version(version)
			.body(html)
			.assignedTo(assignee)
			.createdAt(created)
			.updatedAt(updated)
			.reportedBy(reportedBy)
			.build();
	}
	
	@SneakyThrows
	private void fetchComments(List<Comment> result, HttpClient client, String org, String repo, String issueId) {
		Object jsonObj = client.getAsJson(new String[] { REPOSITORIES_RESOURCE, org, repo, ISSUES_RESOURCE, issueId, COMMENTS_RESOURCE });
		if(!(jsonObj instanceof JsonArray)) {
			throw new IllegalStateException("Received unexpected JSON response: " + jsonObj);
		}
		JsonArray comments = (JsonArray)jsonObj;
		
		comments.stream()
			.map(this::toComment)
			.forEach(result::add);
	}
	
	private Comment toComment(Object obj) {
		if(!(obj instanceof JsonObject)) {
			throw new IllegalStateException("Received unexpected comment JSON: " + obj);
		}
		JsonObject json = (JsonObject)obj;
		BitbucketComment comment = ModelUtil.toEntity(json, BitbucketComment.class);
		
		Person postedBy = toPerson(comment.getAuthor());
		Date created = toDate(comment.getCreatedUtc());
		Date updated = toDate(comment.getUpdatedUtc());
		
		String content = comment.getContent();
		String html = markdown.toHtml(content);
		
		return Comment.builder()
			.id(StringUtil.toString(comment.getCommentId()))
			.postedBy(postedBy)
			.createdAt(created)
			.updatedAt(updated)
			.body(html)
			.build();
	}
	
	private Person toPerson(UserInfo u) {
		if(u != null) {
			String name = u.getDisplayName();
			if(StringUtil.isNotEmpty(name)) {
				String avatarUrl = u.getAvatar();
				String responsibleUri = u.getResourceUri();
				String assigneeUrl = null;
				if(StringUtil.isNotEmpty(responsibleUri)) {
					assigneeUrl = "https://bitbucket.org" + responsibleUri.substring("/1.0".length()); //$NON-NLS-1$ //$NON-NLS-2$
				}
				return Person.builder()
						.name(name)
						.avatarUrl(avatarUrl)
						.url(assigneeUrl)
						.build();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	@SneakyThrows
	private Date toDate(String d) {
		if(StringUtil.isNotEmpty(d)) {
			return DATE_FORMAT.get().parse(d);
		}
		return null;
	}
	
	private HttpClient getBitbucketClient(BitbucketInfo info) {
		return Platform.getService(HttpClientService.class).createHttpClient(API_BASE, info.getUsername(), info.getPassword());
	}
}
