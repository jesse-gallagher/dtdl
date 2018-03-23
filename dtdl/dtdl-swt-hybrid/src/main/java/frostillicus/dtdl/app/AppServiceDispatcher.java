/*!COPYRIGHT HEADER! 
 *
 */

package frostillicus.dtdl.app;


import com.darwino.commons.json.JsonObject;
import com.darwino.commons.services.HttpServerContext;
import com.darwino.commons.services.HttpServiceContext;
import com.darwino.commons.services.HttpServiceFactories;
import com.darwino.mobile.hybrid.platform.NanoHttpdDarwinoHttpServer;
import com.darwino.mobile.hybrid.services.MobileDelegateRestFactory;


public class AppServiceDispatcher extends NanoHttpdDarwinoHttpServer {
	
	public AppServiceDispatcher(HttpServerContext context) {
		super(context);
	}
	
	@Override
	public void addApplicationServiceFactories(HttpServiceFactories factories) {
		// Add the debug services
		//final DebugRestFactory debug = new DebugRestFactory();  
		//factories.add(debug);
	}
}
