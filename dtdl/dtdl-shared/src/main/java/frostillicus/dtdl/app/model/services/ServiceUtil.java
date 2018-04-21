package frostillicus.dtdl.app.model.services;

import com.darwino.commons.json.JsonObject;
import com.darwino.commons.services.HttpServiceContext;

public enum ServiceUtil {
	;
	
	public static void ok(HttpServiceContext context, Object payload) {
		context.emitJson(JsonObject.of(
			"status", "success", //$NON-NLS-1$ //$NON-NLS-2$
			"payload", payload //$NON-NLS-1$
		));
	}
}
