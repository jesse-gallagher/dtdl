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
import com.darwino.jsonstore.sql.impl.sqlite.SqliteDatabaseCustomizer;
import com.darwino.mobile.platform.DarwinoMobileManifest;
import com.darwino.mobile.platform.DarwinoMobileSettings;

/**
 * Mobile Application Manifest.
 * 
 * @author Philippe Riand
 */
public class AppMobileManifest extends DarwinoMobileManifest {
	
	public AppMobileManifest(String pathInfo) {
		super(pathInfo);
	}

	@Override
	public void initDefaultSettings(DarwinoMobileSettings settings) {
		super.initDefaultSettings(settings);
		// Set the default settings here
	}
	
	@Override
	public SqliteDatabaseCustomizer findDatabaseCustomizer(String dbName) throws JsonException {
		// Return your database customizer here
		return new AppDatabaseCustomizer();
	}

	@Override
	public String[] getReplicationInstances() {
		return AppDatabaseDef.getInstances();
	}
}
