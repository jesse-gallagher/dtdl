package com.darwino.github.issues.jstore;

import java.io.IOException;

import com.darwino.commons.json.JsonException;
import com.darwino.jsonstore.replication.ReplicationOptions;
import com.darwino.jsonstore.replication.impl.DocumentChangeEntry;
import com.darwino.jsonstore.replication.impl.ReplicationSourceChangeCursor;

public class ReplicationSourceChangeCursorGitHubIssues extends ReplicationSourceChangeCursor {

	public ReplicationSourceChangeCursorGitHubIssues(ReplicationSourceGitHubIssues source, ReplicationOptions options) throws JsonException {
		super(source, options);
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getEstimatedCount() throws JsonException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getSourceReplicationDate() throws JsonException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean next() throws JsonException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DocumentChangeEntry getEntry() throws JsonException {
		// TODO Auto-generated method stub
		return null;
	}

}
