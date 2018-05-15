package com.darwino.github.issues.jstore;

import com.darwino.jsonstore.Attachment;
import com.darwino.jsonstore.replication.impl.ReplicationSourceDocument;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Source document wrapper for the GitHub Issues API.
 * 
 * @author Jesse Gallagher
 * @since 0.0.1
 */
@NoArgsConstructor @AllArgsConstructor @Builder
public class ReplicationSourceDocumentGitHubIssues extends ReplicationSourceDocument {
	private @Getter String instId;
	private @Getter String storeId;
	private @Getter String unid;
	private @Getter String replicaId;
	private @Getter int seqId;
	private @Getter int updateId;
	private @Getter long creationDate;
	private @Getter String creationUser;
	private @Getter long lastModificationDate;
	private @Getter String lastModificationUser;
	private @Getter String changes;
	private @Getter Object json;
	private final @Getter Attachment[] attachments = new Attachment[0];
}
