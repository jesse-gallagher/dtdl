/*!COPYRIGHT HEADER! 
 *
 */

package frostillicus.dtdl.app;

import com.darwino.commons.services.HttpServiceFactories;
import com.darwino.commons.services.debug.DebugRestFactory;
import com.darwino.j2ee.application.DarwinoJ2EEServiceDispatcherFilter;

/**
 * J2EE application.
 * 
 * @author Philippe Riand
 */
public class AppServiceDispatcher extends DarwinoJ2EEServiceDispatcherFilter {
	
	// This dispatcher can be customized!
	public AppServiceDispatcher() {
	}
	
	/**
	 * Add the application specific services. 
	 */
	@Override
	protected void addApplicationServiceFactories(HttpServiceFactories factories) {
		// Add the debug services
		final DebugRestFactory debug = new DebugRestFactory();  
		factories.add(debug);
	}
}
