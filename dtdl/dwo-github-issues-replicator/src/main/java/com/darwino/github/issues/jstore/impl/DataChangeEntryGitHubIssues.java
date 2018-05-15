package com.darwino.github.issues.jstore.impl;

import com.darwino.github.issues.jstore.ReplicationSourceDocumentGitHubIssues;
import com.darwino.jsonstore.replication.impl.DocumentDataChangeEntry;

public class DataChangeEntryGitHubIssues extends DocumentDataChangeEntry {

	// This is used to temporarily store the document after processing, for efficiency
	public ReplicationSourceDocumentGitHubIssues sourceDocument;

	public DataChangeEntryGitHubIssues(boolean deleted, boolean unreachable, String instId, String storeId, String unid,
			String replicaId, int seqId, int updateId, long synchronizationDate, long creationDate, String creationUser,
			long lastModificationDate, String lastModificationUser, String changes) {
		super(deleted, unreachable, instId, storeId, unid, replicaId, seqId, updateId, synchronizationDate, creationDate,
				creationUser, lastModificationDate, lastModificationUser, changes);
	}
	
	public void setSourceDocument(ReplicationSourceDocumentGitHubIssues sourceDocument) {
		this.sourceDocument = sourceDocument;
	}

	public ReplicationSourceDocumentGitHubIssues getSourceDocument() {
		return sourceDocument;
	}
}
