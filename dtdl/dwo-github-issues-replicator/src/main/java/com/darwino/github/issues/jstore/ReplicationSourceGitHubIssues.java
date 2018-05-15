package com.darwino.github.issues.jstore;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.json.JsonFactory;
import com.darwino.jsonstore.replication.ReplicationOptions;
import com.darwino.jsonstore.replication.impl.AbstractReplicationSource;
import com.darwino.jsonstore.replication.impl.DocumentDataChangeEntry;
import com.darwino.jsonstore.replication.impl.ReplicationSourceChangeCursor;
import com.darwino.jsonstore.replication.impl.ReplicationSourceDocument;

public class ReplicationSourceGitHubIssues extends AbstractReplicationSource {

	@Override
	public JsonFactory getJsonFactory() throws JsonException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSourceId() throws JsonException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReplicationSourceDocument loadDocument(DocumentDataChangeEntry entry) throws JsonException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReplicationSourceChangeCursor createChangeCursor(ReplicationOptions options, long startDate, long endDate,
			String repId) throws JsonException {
		// TODO Auto-generated method stub
		return null;
	}

}
