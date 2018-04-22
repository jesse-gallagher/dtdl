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
package frostillicus.dtdl.app;

import java.util.List;

import com.darwino.commons.services.HttpService;
import com.darwino.commons.services.HttpServiceContext;
import com.darwino.commons.services.rest.RestServiceBinder;
import com.darwino.commons.services.rest.RestServiceFactory;
import com.darwino.platform.DarwinoHttpConstants;

import frostillicus.dtdl.app.model.services.IssueListService;
import frostillicus.dtdl.app.model.services.ModelListService;
import frostillicus.dtdl.app.model.services.ModelObjectService;
import frostillicus.dtdl.app.model.services.ModelsService;


/**
 * Application REST Services Factory.
 */
public class AppRestServiceFactory extends RestServiceFactory {
	
	public AppRestServiceFactory() {
		super(DarwinoHttpConstants.APPSERVICES_PATH);
	}
	
	@Override
	protected void createServicesBinders(List<RestServiceBinder> binders) {
		binders.add(new RestServiceBinder("models") { //$NON-NLS-1$
			@Override public HttpService createService(HttpServiceContext context, String[] parts) {
				return new ModelsService();
			}
		});
		binders.add(new RestServiceBinder("models", null) { //$NON-NLS-1$
			@Override public HttpService createService(HttpServiceContext context, String[] parts) {
				String modelName = parts[1];
				return new ModelListService(WeldContext.INSTANCE.getContainer(), modelName);
			}
		});
		binders.add(new RestServiceBinder("models", null, null) { //$NON-NLS-1$
			@Override
			public HttpService createService(HttpServiceContext context, String[] parts) {
				String modelName = parts[1];
				String id = parts[2];
				return new ModelObjectService(WeldContext.INSTANCE.getContainer(), modelName, id);
			}
		});
		
		binders.add(new RestServiceBinder("issues", null) { //$NON-NLS-1$
			@Override
			public HttpService createService(HttpServiceContext context, String[] parts) {
				String sourceId = parts[1];
				return new IssueListService(WeldContext.INSTANCE.getContainer(), sourceId);
			}
		});
	}	
}
