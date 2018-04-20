package frostillicus.dtdl.app.model.services;

import java.util.Optional;

import javax.inject.Inject;

import org.jnosql.artemis.Repository;
import org.jnosql.artemis.document.DocumentEntityConverter;

import com.darwino.commons.json.JsonObject;
import com.darwino.commons.services.AbstractHttpService;
import com.darwino.commons.services.HttpServiceContext;
import com.darwino.commons.util.StringUtil;

import frostillicus.dtdl.app.WeldContext;
import frostillicus.dtdl.app.model.ModelRepository;
import frostillicus.dtdl.app.model.util.ModelUtil;
import lombok.NonNull;

public class ModelListService extends AbstractHttpService {
	private final @NonNull Class<?> modelClass;
	private final @NonNull Repository<?, ?> repository;

	private final DocumentEntityConverter documentEntityConverter;

	public ModelListService(String modelName) {
		if(StringUtil.isEmpty(modelName)) {
			throw new IllegalArgumentException("modelName cannot be empty"); //$NON-NLS-1$
		}
		
		Optional<Class<?>> modelClass = ModelUtil.getModelClass(modelName);
		if(!modelClass.isPresent()) {
			throw new IllegalArgumentException("Could not find model class for name " + modelName); //$NON-NLS-1$
		}
		this.modelClass = modelClass.get();
		
		Repository<?, ?> repository = ModelUtil.getRepository(modelClass.get());
		if(repository == null) {
			throw new NullPointerException("Could not find repository for class " + modelClass.get().getName()); //$NON-NLS-1$
		}
		this.repository = repository;
		
		this.documentEntityConverter = WeldContext.INSTANCE.getBean(DocumentEntityConverter.class);
	}

	@Override
	protected void doGet(HttpServiceContext context) throws Exception {
		if(repository instanceof ModelRepository) {
			context.emitJson(JsonObject.of("entities", ((ModelRepository<?>)repository).findAll()));	
		} else {
			throw new IllegalArgumentException("Cannot get list for non-model repository");
		}
	}
	
	/**
	 * Creates a new model object with the JSON body of the request
	 */
	@Override
	protected void doPost(HttpServiceContext context) throws Exception {
		Object newModel = context.getContentAsJson();
		
		//Object entity = documentEntityConverter.toEntity(entity)
	}
}
