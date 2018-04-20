/**
 * Copyright © 2018 Jesse Gallagher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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