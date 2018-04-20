package frostillicus.dtdl.app.microservices;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.json.JsonObject;
import com.darwino.commons.microservices.JsonMicroService;
import com.darwino.commons.microservices.JsonMicroServiceContext;

import frostillicus.dtdl.app.model.Source;

public class SourceTypes implements JsonMicroService {
	public static final String NAME = "SourceTypes"; //$NON-NLS-1$

	@Override
	public void execute(JsonMicroServiceContext context) throws JsonException {
		context.setResponse(
			Arrays.stream(Source.Type.values())
				.map(t -> JsonObject.of(
					"id", t.name(), //$NON-NLS-1$
					"name", t.getFriendlyName() //$NON-NLS-1$
				))
				.collect(Collectors.toList())
		);
	}

}
