package frostillicus.dtdl.app.model.info;

import javax.validation.constraints.NotEmpty;

import org.jnosql.artemis.Column;
import org.jnosql.artemis.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class GitHubInfo implements InfoHolder {
	@Column @Getter @Setter @NotEmpty
	private String token;
	
	@Column @Getter @Setter @NotEmpty
	private String repository;
}