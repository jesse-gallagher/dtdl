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

import com.darwino.commons.microservices.JsonMicroServiceFactory;
import com.darwino.commons.services.HttpServiceFactory;



/**
 * Main plugin class.
 * 
 * This class is used to register the common plugin services and is meant to be overloaded
 * by an actual implementation (J2EE, Mobile...).
 */
public class AppBasePlugin {
	
	public static void findExtensions(Class<?> serviceClass, List<Object> extensions) {
		if(serviceClass==HttpServiceFactory.class) {
			extensions.add(new AppRestServiceFactory());
		} else if(serviceClass==JsonMicroServiceFactory.class) {
			extensions.add(new AppMicroServicesFactory());
		}
	}
}
