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
package frostillicus.dtdl.app.model.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import org.jnosql.artemis.Entity;
import org.jnosql.artemis.Repository;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.darwino.commons.util.StringUtil;

import frostillicus.dtdl.app.AppManifest;
import frostillicus.dtdl.app.WeldContext;

public enum ModelUtil {
	;
	
	private static Reflections reflections;
	private static Set<Class<?>> modelClasses;
	private static Set<Class<? extends Repository<?, ?>>> repositoryClasses;
	
	static {
		reflections = new Reflections(
				new ConfigurationBuilder()
				.addUrls(ClasspathHelper.forPackage(AppManifest.class.getPackage().getName()))
				.setScanners(new TypeAnnotationsScanner(), new SubTypesScanner())
		);
	}
	
	public static synchronized Set<Class<?>> getModelClasses() {
		if(modelClasses == null) {
			modelClasses = reflections.getTypesAnnotatedWith(Entity.class);
		}
		return modelClasses;
	}
	
	public static Optional<Class<?>> getModelClass(String modelName) {
		return getModelClasses()
			.stream()
			.filter(c -> StringUtil.equals(c.getSimpleName(), modelName))
			.findFirst();
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized <C, T extends Repository<C, ?>> T getRepository(Class<C> modelClass) {
		if(repositoryClasses == null) {
			repositoryClasses = (Set<Class<? extends Repository<?, ?>>>)(Set<?>)reflections.getSubTypesOf(Repository.class);
		}
		for(Class<? extends Repository<?, ?>> clazz : repositoryClasses) {
			// For now, assume that it directly extends an interface with the model class
			Type[] interfaces = clazz.getGenericInterfaces();
			for(Type type : interfaces) {
				if(type instanceof ParameterizedType) {
					ParameterizedType pt = (ParameterizedType)type;
					if(Arrays.asList(pt.getActualTypeArguments()).contains(modelClass)) {
						return (T) WeldContext.INSTANCE.getBean(clazz);
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Attempts to instantiate an instance of the provided class using a
	 * constructor matching the provided parameters. This is wrapped in an
	 * doPrivileged block in order to facilitate use in a low-rights XPages
	 * runtime.
	 *
	 * @param clazz
	 *            the class to instantiate
	 * @param params
	 *            the parameters to pass to the constructor
	 * @return a new object of the provided class
	 */
	@SuppressWarnings("unchecked")
	public static <T> T instantiateObject(final Class<T> clazz, final Object... params) {
		return AccessController.doPrivileged((PrivilegedAction<T>) () -> {
			for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
				Class<?>[] constParams = constructor.getParameterTypes();
				if (Modifier.isPublic(constructor.getModifiers()) && _instantiateDoClassesMatch(constParams, params)) {
					try {
						return (T) constructor.newInstance(params);
					} catch (IllegalArgumentException e1) {
						throw new RuntimeException(e1);
					} catch (InstantiationException e2) {
						throw new RuntimeException(e2);
					} catch (IllegalAccessException e3) {
						throw new RuntimeException(e3);
					} catch (InvocationTargetException e4) {
						throw new RuntimeException(e4);
					}
				}
			}
			throw new IllegalArgumentException(StringUtil.format("Could not find constructor on class {0} for params {1}", clazz, params == null ? "[]" //$NON-NLS-1$ //$NON-NLS-2$
					: Arrays.asList(params)));
		});
	}

	private static boolean _instantiateDoClassesMatch(final Class<?>[] classes, final Object[] params) {
		if (classes == null && params != null) {
			return false;
		} else if (classes == null || params == null) {
			return false;
		} else if (classes.length != params.length) {
			return false;
		} else {
			for (int i = 0; i < classes.length; i++) {
				if (!classes[i].isInstance(params[i])) {
					return false;
				}
			}
			return true;
		}
	}
}
