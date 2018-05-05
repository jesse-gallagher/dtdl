package frostillicus.dtdl.app.model.issues;

import org.jnosql.artemis.Column;
import org.jnosql.artemis.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable @Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Person {
	@Column private String name;
	@Column private String avatarUrl;
	@Column private String url;
}