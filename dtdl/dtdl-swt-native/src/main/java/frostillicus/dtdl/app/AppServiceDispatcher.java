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


import com.darwino.commons.services.HttpServerContext;
import com.darwino.commons.services.HttpServiceFactories;
import com.darwino.mobile.hybrid.platform.NanoHttpdDarwinoHttpServer;


public class AppServiceDispatcher extends NanoHttpdDarwinoHttpServer {
	
	public AppServiceDispatcher(HttpServerContext context) {
		super(context);
	}
	
	@Override
	public void addApplicationServiceFactories(HttpServiceFactories factories) {
	}
}
