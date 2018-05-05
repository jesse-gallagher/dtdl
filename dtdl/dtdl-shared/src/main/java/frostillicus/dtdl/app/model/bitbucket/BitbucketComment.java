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
	@Column("utc_updated_on") private String utcUpdatedOn;
	@Column("utc_created_on") private String utcCreatedOn;
	@Column("is_span") private boolean spam;
}
