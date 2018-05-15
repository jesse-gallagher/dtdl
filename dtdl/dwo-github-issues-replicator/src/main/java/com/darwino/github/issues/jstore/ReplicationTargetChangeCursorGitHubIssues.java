package com.darwino.github.issues.jstore;

import java.io.IOException;

import com.darwino.commons.json.JsonException;
import com.darwino.jsonstore.replication.ReplicationOptions;
import com.darwino.jsonstore.replication.impl.DocumentDataChangeEntry;
import com.darwino.jsonstore.replication.impl.DocumentUserChangeEntry;
import com.darwino.jsonstore.replication.impl.ReplicationTargetChangeCursor;

public class ReplicationTargetChangeCursorGitHubIssues extends ReplicationTargetChangeCursor {

	public ReplicationTargetChangeCursorGitHubIssues(ReplicationOptions options) {
		super(options);
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public DocumentDataChangeEntry loadDocumentDataEntry(String instId, String storeId, String unid)
			throws JsonException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentUserChangeEntry loadDocumentUserEntry(String instId, String storeId, String unid, String userName)
			throws JsonException {
		// TODO Auto-generated method stub
		return null;
	}

}
