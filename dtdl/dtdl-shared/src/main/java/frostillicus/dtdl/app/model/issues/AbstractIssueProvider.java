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
package frostillicus.dtdl.app.model.issues;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.map.PassiveExpiringMap;

import frostillicus.dtdl.app.model.info.InfoHolder;
import frostillicus.dtdl.app.model.util.ModelUtil;

public abstract class AbstractIssueProvider<T extends InfoHolder> {
	private Map<T, List<Issue>> CACHE = Collections.synchronizedMap(new PassiveExpiringMap<>(30, TimeUnit.SECONDS));
	
	public final List<Issue> getIssues(T info) {
		return ModelUtil.cache(CACHE, info, () -> _getIssues(info));
	}
	protected abstract List<Issue> _getIssues(T info);
}
