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

import java.io.InputStream;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.darwino.commons.Platform;
import com.darwino.commons.json.JsonException;
import com.darwino.swt.platform.hybrid.SwtMain;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

/**
 * SWT Application
 */
public class SwtMainClass extends SwtMain {

	@Override
	public String getApplicationId() {
		return "frostillicus.dtdl"; //$NON-NLS-1$
	}
	
	@Override
	public void execute() {
		try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
			super.execute();
		}
	}
	
	@Override
	public final void onCreate() {
		super.onCreate();

		// Create the Darwino Application
		try {
			AppHybridApplication.create(this);
		} catch(JsonException ex) {
			throw new RuntimeException("Unable to create Darwino application", ex); //$NON-NLS-1$
		}
	}
	
	@Override
	public Image[] getIcons() {
		try(InputStream is = getClass().getResourceAsStream("/application-x-executable.png")) { //$NON-NLS-1$
			Image icon = new Image(Display.getDefault(), is);
			return new Image[] { icon };
		} catch (Throwable t) {
			Platform.log(t);
		}
		return super.getIcons();
	}
	
	
	public static void main(String[] args) {
		SwtMainClass main = new SwtMainClass();
		main.execute();
	}
}
