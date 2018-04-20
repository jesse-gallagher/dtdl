package frostillicus.dtdl.app.model;

import java.util.List;

import org.darwino.jnosql.artemis.extension.DarwinoRepository;

public interface SourceRepository extends DarwinoRepository<Source, String>, ModelRepository<Source> {
	@Override
	List<Source> findAll();
}
