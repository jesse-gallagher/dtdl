package com.darwino.github.issues.jstore;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.darwino.commons.json.JsonException;
import com.darwino.jsonstore.replication.ReplicationOptions;
import com.darwino.jsonstore.replication.impl.DocumentChangeEntry;
import com.darwino.jsonstore.replication.impl.ReplicationSourceChangeCursor;

/**
 * Change cursor for a GitHub Issues API source.
 * 
 * @author Jesse Gallagher
 * @since 0.0.1
 */
public class ReplicationSourceChangeCursorGitHubIssues extends ReplicationSourceChangeCursor {

	private final long startDate;
	private final long endDate;
	private final String repId;
	
	private final AtomicBoolean initialized = new AtomicBoolean(false);
	
	public ReplicationSourceChangeCursorGitHubIssues(ReplicationSourceGitHubIssues source, ReplicationOptions options, long startDate, long endDate, String repId) throws JsonException {
		super(source, options);
		
		this.startDate = startDate;
		this.endDate = endDate;
		this.repId = repId;
	}

	@Override
	public int getEstimatedCount() throws JsonException {
		initIssues();
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getSourceReplicationDate() throws JsonException {
		initIssues();
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean next() throws JsonException {
		initIssues();
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DocumentChangeEntry getEntry() throws JsonException {
		initIssues();
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws IOException {

	}
	
	@Override
	public ReplicationSourceGitHubIssues getSource() {
		return (ReplicationSourceGitHubIssues)super.getSource();
	}

	// *******************************************************************************
	// * Implementation methods
	// *******************************************************************************
	
	private synchronized void initIssues() {
		if(this.initialized.get()) {
			return;
		}
		
		
		this.initialized.set(true);
	}

}
