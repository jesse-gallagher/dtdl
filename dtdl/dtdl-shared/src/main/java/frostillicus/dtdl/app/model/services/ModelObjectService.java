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

import java.util.Optional;

import org.jnosql.artemis.Repository;

import com.darwino.commons.services.AbstractHttpService;
import com.darwino.commons.services.HttpServiceContext;
import com.darwino.commons.util.StringUtil;

import frostillicus.dtdl.app.model.util.ModelUtil;
import lombok.NonNull;

import static frostillicus.dtdl.app.model.services.ServiceUtil.ok;

public class ModelObjectService extends AbstractHttpService {
	private final @NonNull Class<?> modelClass;
	private final @NonNull Repository<Object, Object> repository;
	private final @NonNull String id;

	public ModelObjectService(String modelName, String id) {
		if(StringUtil.isEmpty(modelName)) {
			throw new IllegalArgumentException("modelName cannot be empty"); //$NON-NLS-1$
		}
		if(StringUtil.isEmpty(id)) {
			throw new IllegalArgumentException("id cannot be empty"); //$NON-NLS-1$
		}
		
		Optional<Class<?>> modelClass = ModelUtil.getModelClass(modelName);
		if(!modelClass.isPresent()) {
			throw new IllegalArgumentException("Could not find model class for name " + modelName); //$NON-NLS-1$
		}
		this.modelClass = modelClass.get();
		
		@SuppressWarnings("unchecked")
		Repository<Object, Object> repository = (Repository<Object, Object>)ModelUtil.getRepository(modelClass.get());
		if(repository == null) {
			throw new NullPointerException("Could not find repository for class " + modelClass.get().getName()); //$NON-NLS-1$
		}
		this.repository = repository;
		
		this.id = id;
	}
	
	@Override
	protected void doGet(HttpServiceContext context) throws Exception {
		Object entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Could not find " + modelClass.getSimpleName() + " for ID " + id)); //$NON-NLS-1$ //$NON-NLS-2$
		ok(context, ModelUtil.toJson(entity));
	}
	
	@Override
	protected void doDelete(HttpServiceContext context) throws Exception {
		repository.deleteById(id);
		ok(context, id);
	}
}
