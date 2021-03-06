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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import com.darwino.commons.Platform;
import com.darwino.commons.json.JsonException;
import com.darwino.commons.util.NativeUtil;
import com.darwino.mobile.platform.DarwinoMobileApplication;
import com.darwino.platform.DarwinoManifest;
import com.darwino.swt.platform.SwtActionsCommon;
import com.darwino.swt.platform.hybrid.SwtMain;

import frostillicus.dtdl.app.shell.AppShell;

/**
 * SWT Application
 */
public class SwtMainClass extends SwtMain {

	@Override
	public String getApplicationId() {
		return "frostillicus.dtdl"; //$NON-NLS-1$
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
	public void execute() {
		onCreate();
		try {
			DarwinoManifest manifest = DarwinoMobileApplication.get().getManifest();
			Display.setAppName(manifest.getLabel());
			
			Display display = Display.getDefault();
			
			// Bind prefs to the Mac-standard preferences menu
			if(NativeUtil.isMac()) {
				Menu systemMenu = display.getSystemMenu();
				for(MenuItem systemItem : systemMenu.getItems()) {
					if(systemItem.getID() == SWT.ID_PREFERENCES) {
						systemItem.addListener(SWT.Selection, event -> SwtActionsCommon.openSettings());
					}
				}
			}
			
			
			final Shell shell = new AppShell(display);
			shell.setSize(this.getMainWindowSize());
			Point loc = this.getMainWindowLocation();
			if(loc != null) {
				shell.setLocation(loc);
			}
			
			shell.addListener(SWT.Resize, event -> {
				Point newSize = shell.getSize();
				storeMainWindowSize(newSize);
			});
			shell.addListener(SWT.Move, event -> {
				Point newLoc = shell.getLocation();
				storeMainWindowLocation(newLoc);
			});
			
			Image[] icons = getIcons();
			
			if(icons != null && icons.length > 0) {
				shell.setImages(icons);
			}
	
			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
			display.dispose();
			
			exit();
		} finally {
			onDestroy();
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
