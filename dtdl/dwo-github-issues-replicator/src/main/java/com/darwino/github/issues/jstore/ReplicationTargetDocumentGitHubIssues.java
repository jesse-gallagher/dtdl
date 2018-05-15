package com.darwino.github.issues.jstore;

import com.darwino.commons.json.JsonException;
import com.darwino.jsonstore.Attachment;
import com.darwino.jsonstore.replication.impl.ReplicationSourceDocument;
import com.darwino.jsonstore.replication.impl.ReplicationTargetDocument;

public class ReplicationTargetDocumentGitHubIssues extends ReplicationTargetDocument {

	@Override
	public int getSeqId() throws JsonException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getUpdateId() throws JsonException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLastModificationDate() throws JsonException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setJson(ReplicationSourceDocument sourceDocument) throws JsonException {
		// TODO Auto-generated method stub

	}

	@Override
	public Attachment[] getAttachments() throws JsonException {
		// TODO Auto-generated method stub
		return null;
	}

}
