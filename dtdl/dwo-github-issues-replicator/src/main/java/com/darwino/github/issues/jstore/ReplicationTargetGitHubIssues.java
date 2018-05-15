package com.darwino.github.issues.jstore;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.util.io.Content;
import com.darwino.jsonstore.Attachment;
import com.darwino.jsonstore.extensions.ReplicationEvents;
import com.darwino.jsonstore.replication.ReplicationOptions;
import com.darwino.jsonstore.replication.impl.AbstractReplicationTarget;
import com.darwino.jsonstore.replication.impl.DocumentDataChangeEntry;
import com.darwino.jsonstore.replication.impl.DocumentUserChangeEntry;
import com.darwino.jsonstore.replication.impl.ReplicationSourceDocument;
import com.darwino.jsonstore.replication.impl.ReplicationTargetChangeCursor;
import com.darwino.jsonstore.replication.impl.ReplicationTargetDocument;
import com.darwino.jsonstore.replication.impl._AttachmentData;
import com.darwino.jsonstore.replication.impl._DocumentData;

/**
 * The entry point for the GitHub Issues API as a Darwino replication target.
 * 
 * @author Jesse Gallagher
 * @since 0.0.1
 */
public class ReplicationTargetGitHubIssues extends AbstractReplicationTarget {

	@Override
	public void startTransaction() throws JsonException {
		// TODO Auto-generated method stub

	}

	@Override
	public void commitTransaction() throws JsonException {
		// TODO Auto-generated method stub

	}

	@Override
	public void endTransaction() throws JsonException {
		// TODO Auto-generated method stub

	}

	@Override
	public void abortTransaction() throws JsonException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getReplicationId() throws JsonException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getReplicationDate(String sourceId, String profile) throws JsonException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setReplicationDate(String sourceId, String profile, long date) throws JsonException {
		// TODO Auto-generated method stub

	}

	@Override
	public ReplicationTargetChangeCursor createChangeCursor(ReplicationOptions options) throws JsonException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReplicationTargetDocument newDocument(ReplicationSourceDocument sourceDocument, DocumentDataChangeEntry entry) throws JsonException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReplicationTargetDocument loadDocument(ReplicationSourceDocument sourceDocument, DocumentDataChangeEntry entry) throws JsonException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveDocument(ReplicationTargetDocument document, _DocumentData data) throws JsonException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteDocument(String instId, String storeId, String unid, _DocumentData data) throws JsonException {
		// TODO Auto-generated method stub

	}

	@Override
	public void eraseDocument(String instId, String storeId, String unid) throws JsonException {
		// TODO Auto-generated method stub

	}

	@Override
	public Attachment newDocumentAttachment(ReplicationTargetDocument document, String name, Content content, _AttachmentData data) throws JsonException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateDocumentAttachment(Attachment attachment, Content content, _AttachmentData data) throws JsonException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteDocumentAttachment(Attachment attachment) throws JsonException {
		// TODO Auto-generated method stub

	}

	@Override
	public void eraseAllDocuments(String[] stores, long before) throws JsonException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUserData(DocumentUserChangeEntry entry) throws JsonException {
		// TODO Auto-generated method stub

	}

	@Override
	public ReplicationEvents findEventHandler(String storeId) throws JsonException {
		// TODO Auto-generated method stub
		return null;
	}

}
