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


import com.darwino.commons.json.JsonException;
import com.darwino.commons.log.Logger;
import com.darwino.commons.services.HttpServerContext;
import com.darwino.mobile.hybrid.platform.DarwinoMobileHttpServer;
import com.darwino.mobile.platform.DarwinoMobileApplication;
import com.darwino.mobile.platform.MobileLogger;
import com.darwino.platform.DarwinoManifest;
import com.darwino.swt.Application;
import com.darwino.swt.platform.hybrid.DarwinoSwtHybridApplication;


public class AppHybridApplication extends DarwinoSwtHybridApplication {
	
	public static AppHybridApplication create(Application application) throws JsonException {
		if(!DarwinoMobileApplication.isInitialized()) {
			AppHybridApplication app = new AppHybridApplication(
					new AppManifest(new AppMobileManifest(AppManifest.MOBILE_PATHINFO) {
						// Disable encryption as it is not available on SWT yet
						@Override
						public boolean isDataEncryptedByDefault() {
							return false;
						}
					}), 
					application);
			app.init();
		}
		return (AppHybridApplication)AppHybridApplication.get();
	}
	
	protected AppHybridApplication(DarwinoManifest manifest, Application application) {
		super(manifest, application);

		// Enable some debug trace
		//MobileLogger.HYBRID_HTTPD.setLogLevel(LogMgr.LOG_INFO_LEVEL);
		MobileLogger.DBSYNC.setLogLevel(Logger.LOG_DEBUG_LEVEL);
		//SocialLogger.DBCACHE.setLogLevel(LogMgr.LOG_DEBUG_LEVEL);
	}

	@Override
	protected DarwinoMobileHttpServer createHttpServer(HttpServerContext context) {
    	return new AppServiceDispatcher(context);
	}
}
