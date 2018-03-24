/*!COPYRIGHT HEADER! 
 *
 */

package frostillicus.dtdl.app;

import com.darwino.commons.microservices.StaticJsonMicroServicesFactory;

import frostillicus.dtdl.app.microservices.Issues;
import frostillicus.dtdl.app.microservices.SourceTypes;

public class AppMicroServicesFactory extends StaticJsonMicroServicesFactory {
	
	public AppMicroServicesFactory() {
		add(Issues.NAME, new Issues());
		add(SourceTypes.NAME, new SourceTypes());
	}
}
