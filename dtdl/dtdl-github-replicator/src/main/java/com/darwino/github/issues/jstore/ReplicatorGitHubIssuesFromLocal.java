package com.darwino.github.issues.jstore;

import com.darwino.commons.json.JsonException;
import com.darwino.jsonstore.Database;
import com.darwino.jsonstore.replication.BaseReplicator;
import com.darwino.jsonstore.replication.impl.ReplicationSource;
import com.darwino.jsonstore.replication.impl.ReplicationTarget;

import lombok.AllArgsConstructor;

public class ReplicatorGitHubIssuesFromLocal extends BaseReplicator {

	/**
	 * 
	 * @param darwinoDatabase the Darwino database to replicate with
	 * @param token the OAuth 2 token to use to connect to GitHub
	 * @param repositoryId the GitHub repository ID, in the format "organization/repo_name"
	 * @param issueStoreId the store ID for issues in the local database
	 * @param commentStoreId the store ID for comments in the local database
	 * @param instanceId the local database instance ID for replicated-in documents
	 */
	public ReplicatorGitHubIssuesFromLocal(Database darwinoDatabase, String token, String repositoryId, String issueStoreId, String commentStoreId, String instanceId) {
		super(
			new ReplicatorLocalDatabaseFactory(darwinoDatabase),
			new ReplicatorGitHubIssuesFactory(token, repositoryId, issueStoreId, commentStoreId, instanceId)
		);
	}

	@AllArgsConstructor
	private static class ReplicatorGitHubIssuesFactory extends ReplicatorLocalFactory {
		private final String token;
		private final String repositoryId;
		private final String issueStoreId;
		private final String commentStoreId;
		private final String instanceId;
		
		@Override
		public ReplicationSource createReplicationSource() throws JsonException {
			return new ReplicationSourceGitHubIssues(token, repositoryId, issueStoreId, commentStoreId, instanceId);
		}

		@Override
		public ReplicationTarget createReplicationTarget() throws JsonException {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
