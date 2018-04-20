/**
 * Copyright © 2018 Jesse Gallagher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
