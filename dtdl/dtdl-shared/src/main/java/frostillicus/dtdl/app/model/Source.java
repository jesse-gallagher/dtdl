package frostillicus.dtdl.app.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.jnosql.artemis.Column;
import org.jnosql.artemis.Embeddable;
import org.jnosql.artemis.Entity;
import org.jnosql.artemis.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity @ToString
public class Source {
	@AllArgsConstructor
	public enum Type {
		GITHUB("GitHub");
		
		@Getter private final String friendlyName;
	}
	
	@Embeddable
	public static class GitHubInfo {
		@Column @Getter @Setter @NotEmpty
		private String token;
	}
	
	@Id @Getter @Setter @NotEmpty
	private String id;
	
	@Column @Getter @Setter @NotNull
	private Type type;
	
	@Column @Getter @Setter
	private GitHubInfo github;
}