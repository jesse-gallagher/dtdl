package frostillicus.dtdl.app.microservices;

import java.util.Arrays;

import com.darwino.commons.json.JsonArray;
import com.darwino.commons.json.JsonException;
import com.darwino.commons.json.JsonObject;
import com.darwino.commons.microservices.JsonMicroService;
import com.darwino.commons.microservices.JsonMicroServiceContext;

import frostillicus.dtdl.app.model.Source;
import lombok.val;

public class SourceTypes implements JsonMicroService {
	public static final String NAME = "SourceTypes";

	@Override
	public void execute(JsonMicroServiceContext context) throws JsonException {
		val result = new JsonArray();
		
		Arrays.stream(Source.Type.values())
			.map(t -> JsonObject.of(
				"id", t.name(),
				"name", t.getFriendlyName()
			))
			.forEach(result::add);
		
		context.setResponse(result);
	}

}
