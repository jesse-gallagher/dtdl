package frostillicus.dtdl.app.model.bitbucket;

import org.jnosql.artemis.Column;
import org.jnosql.artemis.Embeddable;
import org.jnosql.artemis.Entity;

import lombok.Data;

@Entity @Data
public class BitbucketIssue {
	@Embeddable @Data
	public static class Link {
		@Column private String href;
	}
	
	@Embeddable @Data
	public static class Links {
		@Column private Link attachments;
		@Column private Link self;
		@Column private Link watch;
		@Column private Link comments;
		@Column private Link html;
		@Column private Link vote;
	}
	
	@Column private String priority;
	@Column private String kind;
	@Column private int id;
	@Column private String type;
	@Column private String assignee;
	@Column private String title;
	@Column private Links links;
	@Column private String state;
}
