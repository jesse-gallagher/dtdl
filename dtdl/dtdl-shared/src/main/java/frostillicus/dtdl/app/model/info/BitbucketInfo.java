package frostillicus.dtdl.app.model.info;

import javax.validation.constraints.NotEmpty;

import org.jnosql.artemis.Column;
import org.jnosql.artemis.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class BitbucketInfo implements InfoHolder {
	@Column @Getter @Setter @NotEmpty
	private String username;
	
	@Column @Getter @Setter @NotEmpty
	private String password;
	
	@Column @Getter @Setter @NotEmpty
	private String repository;
}
