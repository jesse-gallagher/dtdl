package frostillicus.dtdl.app.model;

import java.util.List;

public interface ModelRepository<T> {
	List<T> findAll();
}
