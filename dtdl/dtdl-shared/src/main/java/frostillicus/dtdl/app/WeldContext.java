package frostillicus.dtdl.app;

import java.lang.annotation.Annotation;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class WeldContext {

    public static final WeldContext INSTANCE = new WeldContext();

    private final Weld weld;
    private final WeldContainer container;

    private WeldContext() {
	    	try {
	        this.weld = new Weld();
	        this.container = weld.initialize();
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    		throw e;
	    	}
    }

    public <T> T getBean(Class<T> type) {
        return container.select(type).get();
    }
    
    public <T, Q> T getBean(Class<T> type, Annotation... qualifiers) {
    		return container.select(type, qualifiers).get();
    }
}