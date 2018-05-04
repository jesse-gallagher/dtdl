package frostillicus.dtdl.app.model.bitbucket;

import org.jnosql.artemis.Column;
import org.jnosql.artemis.Embeddable;
import org.jnosql.artemis.Entity;

import lombok.Data;

/**
 * Represents an issue in the Bitbucket Cloud REST API v1.
 * 
 * @author Jesse Gallagher
 * @since 1.0.0
 */
@Entity @Data
public class BitbucketIssue {
	
	@Embeddable @Data
	public static class UserInfo {
		@Column private String username;
		@Column("first_name") private String firstName;
		@Column("last_name") private String lastName;
		@Column("display_name") private String displayName;
		@Column("is_staff") private boolean staff;
		@Column private String avatar;
		@Column("resource_uri") private String resourceUri;
		@Column("is_team") private boolean team;
	}
	@Embeddable @Data
	public static class Metadata {
		@Column private String kind;
		@Column private String version;
		@Column private String component;
		@Column private String milestone;
	}
	
	@Column private String priority;
	@Column("local_id") private int localId;
	@Column private UserInfo responsible;
	@Column private String title;
	@Column private String status;
	@Column("reported_by") private UserInfo reportedBy;
	@Column("comment_count") private int commentCount;
	@Column("follower_count") private int followerCount;
	@Column("resource_uri") private String resourceUri;
	@Column("is_spam") private boolean spam;
	@Column private Metadata metadata;
}
