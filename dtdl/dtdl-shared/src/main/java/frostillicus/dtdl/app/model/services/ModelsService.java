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
