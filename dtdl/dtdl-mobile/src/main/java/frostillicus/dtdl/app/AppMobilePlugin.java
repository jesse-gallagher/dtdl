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

import com.darwino.commons.platform.impl.PluginImpl;



/**
 * Application Plugin.
 */
public abstract class AppMobilePlugin extends PluginImpl {
	
	public AppMobilePlugin(String name) {
		super(name);
	}

	@Override
	public void findExtensions(Class<?> serviceClass, List<Object> extensions) {
		AppBasePlugin.findExtensions(serviceClass, extensions);
		
		// Example showing how to bypass SSL certificates for development purposes
		// This might be useful when using self signed certificates with local development servers
/*		
		if(serviceClass==SSLCertificateExtension.class) {
			extensions.add(new SSLCertificateExtension() {
				@Override
				public boolean shouldTrust(String url) {
					// Trust local servers
					if(url.startsWith("https://192.168.")) {
						return true;
					}
					return false;
				}
			});
		}
*/		
	}
}
