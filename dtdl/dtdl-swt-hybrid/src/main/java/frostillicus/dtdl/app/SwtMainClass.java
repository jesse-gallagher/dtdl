/*!COPYRIGHT HEADER! 
 *
 * (c) Copyright Darwino Inc. 2014-2016.
 *
 * Licensed under The MIT License (https://opensource.org/licenses/MIT)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
 * and associated documentation files (the "Software"), to deal in the Software without restriction, 
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial 
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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
		return "com.darwino.dominodisc";
	}
	
	@Override
	public final void onCreate() {
		super.onCreate();

		// Create the Darwino Application
		try {
			AppHybridApplication.create(this);
		} catch(JsonException ex) {
			throw new RuntimeException("Unable to create Darwino application", ex);
		}
	}
	
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
		try(InputStream is = getClass().getResourceAsStream("/application-x-executable.png")) {
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
