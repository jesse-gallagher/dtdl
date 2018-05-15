package frostillicus.dtdl.app.util;

import com.darwino.commons.security.acl.UserContextFactory;
import com.darwino.commons.security.acl.impl.UserImpl;
import com.darwino.j2ee.application.DarwinoJ2EEContext;
import com.darwino.j2ee.application.DarwinoJ2EEContextFactory;
import com.darwino.jre.application.DarwinoJreApplication;

import lombok.SneakyThrows;

/**
 * This subclass of {@link DarwinoJ2EEContextFactory} provides a server-level context
 * if the current thread does not have a user-specific one initialized, such as in
 * a scheduled task.
 * 
 * @author Jesse Gallagher
 *
 */
public class ServerContextFactory extends DarwinoJ2EEContextFactory {
	private final ThreadLocal<DarwinoJ2EEContext> fallback = new ThreadLocal<>();
	
	@Override
	@SneakyThrows
	public DarwinoJ2EEContext find() {
		DarwinoJ2EEContext sup = super.find();
		if(sup != null) {
			return sup;
		} else {
			if(fallback.get() == null) {
				fallback.set(new DarwinoJ2EEContext(DarwinoJreApplication.get(), null, null, new UserImpl(), new UserContextFactory(), null, DarwinoJreApplication.get().getLocalJsonDBServer().createSystemSession(null)));	
			}
			return fallback.get();
		}
	}
}
