package frostillicus.dtdl.app.model.issues;

import org.jnosql.artemis.Column;
import org.jnosql.artemis.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data
@AllArgsConstructor @NoArgsConstructor
public class Issue {
	@Column
	private String title;
	@Column
	private String url;
}
