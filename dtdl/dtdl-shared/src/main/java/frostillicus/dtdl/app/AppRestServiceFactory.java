/*!COPYRIGHT HEADER! 
 *
 */

package frostillicus.dtdl.app;

import java.util.List;

import com.darwino.commons.services.rest.RestServiceBinder;
import com.darwino.commons.services.rest.RestServiceFactory;
import com.darwino.platform.DarwinoHttpConstants;


/**
 * Application REST Services Factory.
 * 
 * This is the place where to define custom application services.
 * 
 * @author Philippe Riand
 */
public class AppRestServiceFactory extends RestServiceFactory {
	
	public AppRestServiceFactory() {
		super(DarwinoHttpConstants.APPSERVICES_PATH);
	}
	
	@Override
	protected void createServicesBinders(List<RestServiceBinder> binders) {
	}	
}
