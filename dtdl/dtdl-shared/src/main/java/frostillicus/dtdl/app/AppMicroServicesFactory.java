/*!COPYRIGHT HEADER! 
 *
 */

package frostillicus.dtdl.app;

import com.darwino.commons.microservices.StaticJsonMicroServicesFactory;

import frostillicus.dtdl.app.microservices.HelloWorld;


/**
 * Application Micro Services Factory.
 * 
 * This is the place where to define custom application micro services.
 * 
 * @author Philippe Riand
 */
public class AppMicroServicesFactory extends StaticJsonMicroServicesFactory {
	
	public AppMicroServicesFactory() {
		add(HelloWorld.NAME, new HelloWorld());
	}
}
