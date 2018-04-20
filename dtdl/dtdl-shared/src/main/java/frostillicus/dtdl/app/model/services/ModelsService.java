package frostillicus.dtdl.app.model.services;

import java.util.Set;
import java.util.stream.Collectors;

import com.darwino.commons.json.JsonObject;
import com.darwino.commons.services.AbstractHttpService;
import com.darwino.commons.services.HttpServiceContext;

import frostillicus.dtdl.app.model.util.ModelUtil;

public class ModelsService extends AbstractHttpService {

	@Override
	protected void doGet(HttpServiceContext context) throws Exception {
		Set<Class<?>> entities = ModelUtil.getModelClasses();
		JsonObject result = new JsonObject();
		
		result.put("entities", //$NON-NLS-1$
			entities.stream()
				.map(c -> c.getSimpleName())
				.collect(Collectors.toList())
		);
		
		context.emitJson(result);
	}

}
