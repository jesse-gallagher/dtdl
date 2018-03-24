/*!COPYRIGHT HEADER! 
 *
 */

package frostillicus.dtdl.app;

import java.util.List;

import com.darwino.mobile.platform.commands.CommandsExtension;



/**
 * SWT Plugin for registering the services.
 * 
 */
public class AppPlugin extends AppMobilePlugin {
	
	public AppPlugin() {
		super("SWT Application"); //$NON-NLS-1$
	}

	@Override
	public void findExtensions(Class<?> serviceClass, List<Object> extensions) {
		if(serviceClass==CommandsExtension.class) {
			extensions.add(new AppHybridActions());
		}
	}
}
