/**
 * Copyright © 2018 Jesse Gallagher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
	@Column private String content;
}
