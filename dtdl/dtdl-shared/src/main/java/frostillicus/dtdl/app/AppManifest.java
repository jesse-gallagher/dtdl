/*!COPYRIGHT HEADER! 
 *
 */

package frostillicus.dtdl.app;

import com.darwino.commons.json.JsonException;
import com.darwino.jsonstore.Database;
import com.darwino.jsonstore.Session;
import com.darwino.jsonstore.extensions.ExtensionRegistry;
import com.darwino.jsonstore.meta.DatabaseFactory;
import com.darwino.platform.DarwinoContext;
import com.darwino.platform.DarwinoManifest;

/**
 * Application Manifest.
 * 
 * @author Philippe Riand
 */
public class AppManifest extends DarwinoManifest {
	
	public static String CONFIG_ID = "dtdl";
	
	// This is used by the mobile application to call the remote service
	public static final String MOBILE_PATHINFO	= "dtdl";
	
	public static Session getSession() throws JsonException {
		return DarwinoContext.get().getSession();
	}

	public static Database getDatabase() throws JsonException {
		return getSession().getDatabase(AppDatabaseDef.DATABASE_NAME);
	}
	

	public AppManifest(Section section) {
		super(section);
	}
	
	@Override
	public String getConfigId() {
		return CONFIG_ID;
	}
	
	@Override
	public String getLabel() {
		return "Darwino To-Do List";
	}
	
	@Override
	public String getDescription() {
		return "";
	}
	
	@Override
	public String getMainPageUrl() {
		return "index.html";
	}
	
	@Override
	public String[] getDatabases() {
		return AppDatabaseDef.DATABASES;
	}
	
	@Override
	public DatabaseFactory getDatabaseFactory() {
		return new AppDatabaseDef();
	}
	
	@Override
	public ExtensionRegistry getExtensionRegistry() {
		return new AppDBBusinessLogic();
	}
}