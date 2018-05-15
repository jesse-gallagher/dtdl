package com.darwino.github.issues.jstore;

import org.eclipse.egit.github.core.client.GitHubClient;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.json.JsonFactory;
import com.darwino.commons.json.JsonJavaFactory;
import com.darwino.github.issues.jstore.impl.DataChangeEntryGitHubIssues;
import com.darwino.jsonstore.replication.ReplicationOptions;
import com.darwino.jsonstore.replication.impl.AbstractReplicationSource;
import com.darwino.jsonstore.replication.impl.DocumentDataChangeEntry;
import com.darwino.jsonstore.replication.impl.ReplicationSourceChangeCursor;
import com.darwino.jsonstore.replication.impl.ReplicationSourceDocument;

/**
 * The entry point for the GitHub Issues API as a Darwino replication source.
 * 
 * @author Jesse Gallagher
 * @since 0.0.1
 */
public class ReplicationSourceGitHubIssues extends AbstractReplicationSource {
	private final String token;
	private final String repositoryId;

	/**
	 * Constructs a new GitHub Issues replication source for the given repository.
	 * 
	 * @param token the OAuth2 access token to use
	 * @param repositoryId the ID of the repository, in the format "organization/repo_name"
	 */
	public ReplicationSourceGitHubIssues(String token, String repositoryId) {
		super();
		
		this.token = token;
		this.repositoryId = repositoryId;
	}

	@Override
	public JsonFactory getJsonFactory() throws JsonException {
		return JsonJavaFactory.instance;
	}

	@Override
	public String getSourceId() throws JsonException {
		return token + repositoryId;
	}

	@Override
	public ReplicationSourceDocument loadDocument(DocumentDataChangeEntry entry) throws JsonException {
		// This should have been created during processing
		return ((DataChangeEntryGitHubIssues)entry).getSourceDocument();
	}

	@Override
	public ReplicationSourceChangeCursor createChangeCursor(ReplicationOptions options, long startDate, long endDate, String repId) throws JsonException {
		return new ReplicationSourceChangeCursorGitHubIssues(this, options, startDate, endDate, repId);
	}

	/**
	 * Creates a new GitHub client with the provided OAuth token
	 * 
	 * @return a newly-constructed {@link GitHubClient}
	 */
	GitHubClient createGitHubClient() {
		GitHubClient client = new GitHubClient();
		client.setOAuth2Token(token);
		return client;
	}
	
	/**
	 * Gets the repository ID for this source.
	 * 
	 * @return the repositoryId provided during construction
	 */
	String getRepositoryId() {
		return repositoryId;
	}
}
