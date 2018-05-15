package com.darwino.github.issues.jstore;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.util.DateTimeISO8601;
import com.darwino.commons.util.StringUtil;
import com.darwino.github.issues.jstore.impl.DataChangeEntryGitHubIssues;
import com.darwino.jsonstore.replication.ReplicationOptions;
import com.darwino.jsonstore.replication.impl.DocumentChangeEntry;
import com.darwino.jsonstore.replication.impl.ReplicationSourceChangeCursor;

import frostillicus.dtdl.app.beans.MarkdownBean;
import frostillicus.dtdl.app.model.issues.Issue;
import frostillicus.dtdl.app.model.issues.Person;
import frostillicus.dtdl.app.model.util.ModelUtil;
import lombok.SneakyThrows;

/**
 * Change cursor for a GitHub Issues API source.
 * 
 * @author Jesse Gallagher
 * @since 0.0.1
 */
public class ReplicationSourceChangeCursorGitHubIssues extends ReplicationSourceChangeCursor {

	private final long startDate;
	private final long endDate;
	private final String repId;
	private final String issueStoreId;
	private final String commentStoreId;
	private final String instanceId;
	
	private final AtomicBoolean initialized = new AtomicBoolean(false);
	private Deque<Issue> issues;
	
	public ReplicationSourceChangeCursorGitHubIssues(ReplicationSourceGitHubIssues source, ReplicationOptions options, long startDate, long endDate, String repId, String issueStoreId, String commentStoreId, String instanceId) throws JsonException {
		super(source, options);
		
		this.startDate = startDate;
		this.endDate = endDate;
		this.repId = repId;
		this.issueStoreId = issueStoreId;
		this.commentStoreId = commentStoreId;
		this.instanceId = StringUtil.toString(instanceId);
	}

	@Override
	@SneakyThrows
	public int getEstimatedCount() throws JsonException {
		initIssues();
		return issues.size();
	}

	@Override
	public long getSourceReplicationDate() throws JsonException {
		return endDate;
	}

	@Override
	@SneakyThrows
	public boolean next() throws JsonException {
		initIssues();
		
		if(issues.isEmpty()) {
			return false;
		}
		
		return issues.pop() != null;
	}

	@Override
	@SneakyThrows
	public DocumentChangeEntry getEntry() throws JsonException {
		initIssues();
		
		Issue current = issues.peek();
		if(current != null) {
			return toChangeEntry(current);
		} else {
			return null;
		}
	}

	@Override
	public void close() throws IOException {

	}
	
	@Override
	public ReplicationSourceGitHubIssues getSource() {
		return (ReplicationSourceGitHubIssues)super.getSource();
	}

	// *******************************************************************************
	// * Implementation methods
	// *******************************************************************************
	
	private synchronized void initIssues() throws IOException {
		if(this.initialized.get()) {
			return;
		}
		
		GitHubClient client = getSource().createGitHubClient();
		IssueService service = new IssueService(client);
		RepositoryId repoId = RepositoryId.createFromId(getSource().getRepositoryId());

		// Build a filter - endDate is ignored
		Map<String, String> filterData = new HashMap<>();
		filterData.put("filter", "all"); //$NON-NLS-1$ //$NON-NLS-2$
		if(this.startDate >= 0) {
			String since = DateTimeISO8601.formatISO8601(this.startDate);
			filterData.put("since", since); //$NON-NLS-1$
		}
		// We'll want to replicate in the oldest first in case of trouble
		filterData.put("sort", "asc"); //$NON-NLS-1$ //$NON-NLS-2$
		
		this.issues = service.getIssues(repoId, filterData).stream()
			.map(this::toIssue)
			.collect(Collectors.toCollection(ArrayDeque::new));
		
		this.initialized.set(true);
	}

	private Issue toIssue(org.eclipse.egit.github.core.Issue i) {
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
		
		List<Issue.Tag> tags = i.getLabels().stream()
				.map(this::toTag)
				.collect(Collectors.toList());
		
		Milestone milestone = i.getMilestone();
		Issue.Version version = null;
		if(milestone != null) {
			Issue.Version.builder()
				.name(milestone.getTitle())
				.url(milestone.getUrl())
				.build();
		}
		
		Person assignedTo = toPerson(i.getAssignee());
		Person reportedBy = toPerson(i.getUser());
		
		String content = i.getBody();
		String html = MarkdownBean.INSTANCE.toHtml(content);
		
		return Issue.builder()
			.id(StringUtil.toString(i.getNumber()))
			.title(i.getTitle())
			.url(i.getHtmlUrl())
			.status(status)
			.tags(tags)
			.version(version)
			.assignedTo(assignedTo)
			.reportedBy(reportedBy)
			.createdAt(i.getCreatedAt())
			.updatedAt(i.getUpdatedAt())
			.body(html)
			.build();
	}
	
	private Issue.Tag toTag(Label label) {
		if(label == null) {
			return null;
		} else {
			return Issue.Tag.builder()
				.name(label.getName())
				.color(label.getColor())
				.url(label.getUrl())
				.build();
		}
	}
	
	private Person toPerson(org.eclipse.egit.github.core.User u) {
		if(u == null) {
			return null;
		} else {
			return Person.builder()
				.name(u.getLogin())
				.avatarUrl(u.getAvatarUrl())
				.url(u.getUrl())
				.build();
		}
	}
	
	@SneakyThrows
	private DocumentChangeEntry toChangeEntry(Issue issue) {
		int seqId = 1;
		long mdate = issue.getUpdatedAt().getTime();
		int updateId = generateUpdateId(mdate);
		
		ReplicationSourceDocumentGitHubIssues doc = ReplicationSourceDocumentGitHubIssues.builder()
			.instId(instanceId)
			.storeId(issueStoreId)
			.unid(issue.getUrl())
			.replicaId(getSource().getRepositoryId())
			.seqId(seqId)
			.updateId(updateId)
			.creationDate(issue.getCreatedAt().getTime())
			.creationUser(issue.getReportedBy().getName())
			.lastModificationDate(mdate)
			.lastModificationUser(issue.getReportedBy().getName())
			.changes(StringUtil.EMPTY_STRING)
			.json(ModelUtil.toJson(issue))
			.build();
		
		return new DataChangeEntryGitHubIssues(false, doc);
	}
	
	/**
	 * Generates a Darwino-style update ID based on the provided modification timestamp.
	 * 
	 * @param modificationDate the Unix-style modification timestamp
	 * @return an update ID value
	 */
	public static int generateUpdateId(long modificationDate) {
		return (int)(modificationDate&0xFFFFFFFFL);
	}
}
