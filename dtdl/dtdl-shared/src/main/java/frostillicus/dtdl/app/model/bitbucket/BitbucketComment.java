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
import org.jnosql.artemis.Entity;

import lombok.Data;

/**
 * Represents a comment in the Bitbucket Cloud REST API v1.
 * 
 * @author Jesse Gallagher
 * @since 1.0.0
 */
@Entity @Data
public class BitbucketComment {
	@Column private String content;
	@Column("author_info") private UserInfo author;
	@Column("comment_id") private long commentId;
	@Column("convert_markup") private boolean convertMarkup;
	@Column("utc_updated_on") private String updatedUtc;
	@Column("utc_created_on") private String createdUtc;
	@Column("is_span") private boolean spam;
}
