package frostillicus.dtdl.app.microservices;

import com.darwino.commons.json.JsonArray;
import com.darwino.commons.json.JsonException;
import com.darwino.commons.microservices.JsonMicroService;
import com.darwino.commons.microservices.JsonMicroServiceContext;

public class Issues implements JsonMicroService {
	public static final String NAME = "Issues"; //$NON-NLS-1$

	@Override
	public void execute(JsonMicroServiceContext context) throws JsonException {
		JsonArray result = new JsonArray();
		
		
		
		context.setResponse(result);
	}

}
