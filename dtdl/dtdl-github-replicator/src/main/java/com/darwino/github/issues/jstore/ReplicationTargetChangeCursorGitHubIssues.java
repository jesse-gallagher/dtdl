package com.darwino.github.issues.jstore;

import java.io.IOException;

import com.darwino.commons.json.JsonException;
import com.darwino.jsonstore.replication.ReplicationOptions;
import com.darwino.jsonstore.replication.impl.DocumentDataChangeEntry;
import com.darwino.jsonstore.replication.impl.DocumentUserChangeEntry;
import com.darwino.jsonstore.replication.impl.ReplicationTargetChangeCursor;

/**
 * Change cursor for the GitHub issues API as a replication target.
 * 
 * @author Jesse Gallagher
 * @since 0.0.1
 */
public class ReplicationTargetChangeCursorGitHubIssues extends ReplicationTargetChangeCursor {

	public ReplicationTargetChangeCursorGitHubIssues(ReplicationOptions options) {
		super(options);
	}

	@Override
	public DocumentDataChangeEntry loadDocumentDataEntry(String instId, String storeId, String unid) throws JsonException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentUserChangeEntry loadDocumentUserEntry(String instId, String storeId, String unid, String userName) throws JsonException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void close() throws IOException {

	}
}
