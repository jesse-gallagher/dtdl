package frostillicus.dtdl.app.model.issues;

import java.util.Date;

import org.jnosql.artemis.Column;
import org.jnosql.artemis.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Comment {
	@Column private String id;
	@Column private String issueId;
	@Column private Person postedBy;
	@Column private String body;
	@Column private Date createdAt;
	@Column private Date updatedAt;
}
