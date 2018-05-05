package frostillicus.dtdl.app.model.bitbucket;

import org.jnosql.artemis.Column;
import org.jnosql.artemis.Embeddable;

import lombok.Data;

@Embeddable @Data
public class UserInfo {
	@Column private String username;
	@Column("first_name") private String firstName;
	@Column("last_name") private String lastName;
	@Column("display_name") private String displayName;
	@Column("is_staff") private boolean staff;
	@Column private String avatar;
	@Column("resource_uri") private String resourceUri;
	@Column("is_team") private boolean team;
}