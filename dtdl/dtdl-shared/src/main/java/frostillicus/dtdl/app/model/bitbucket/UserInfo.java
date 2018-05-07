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