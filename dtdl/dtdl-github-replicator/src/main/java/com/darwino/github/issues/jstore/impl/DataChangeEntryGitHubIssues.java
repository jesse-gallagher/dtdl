package com.darwino.github.issues.jstore.impl;

import com.darwino.commons.json.JsonException;
import com.darwino.github.issues.jstore.ReplicationSourceDocumentGitHubIssues;
import com.darwino.jsonstore.replication.impl.DocumentDataChangeEntry;

public class DataChangeEntryGitHubIssues extends DocumentDataChangeEntry {

	// This is used to temporarily store the document after processing, for efficiency
	public ReplicationSourceDocumentGitHubIssues sourceDocument;

	public DataChangeEntryGitHubIssues(boolean deleted, ReplicationSourceDocumentGitHubIssues sourceDocument) throws JsonException {
		super(
			deleted,
			false, // boolean unreachable
			sourceDocument.getInstId(),
			sourceDocument.getStoreId(),
			sourceDocument.getUnid(),
			sourceDocument.getReplicaId(),
			sourceDocument.getSeqId(),
			sourceDocument.getUpdateId(),
			sourceDocument.getLastModificationDate(), // long synchronizationDate
			sourceDocument.getCreationDate(),
			sourceDocument.getCreationUser(),
			sourceDocument.getLastModificationDate(),
			sourceDocument.getLastModificationUser(),
			sourceDocument.getChanges()
		);
		this.sourceDocument = sourceDocument;
	}
	
	public void setSourceDocument(ReplicationSourceDocumentGitHubIssues sourceDocument) {
		this.sourceDocument = sourceDocument;
	}

	public ReplicationSourceDocumentGitHubIssues getSourceDocument() {
		return sourceDocument;
	}
}
