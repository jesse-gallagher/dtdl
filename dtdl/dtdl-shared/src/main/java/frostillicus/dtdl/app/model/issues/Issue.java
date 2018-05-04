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

import java.util.List;

import org.jnosql.artemis.Column;
import org.jnosql.artemis.Embeddable;
import org.jnosql.artemis.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Issue {
	public enum Status {
		NEW, OPEN, CLOSED, ON_HOLD
	}
	
	@Embeddable @Data @Builder
	@NoArgsConstructor @AllArgsConstructor
	public static class Version {
		@Column private String name;
		@Column private String url;
	}
	
	@Column String id;
	@Column
	private String title;
	@Column
	private String url;
	@Column
	private Status status;
	@Column
	private List<String> tags;
	@Column
	private Version version;
	@Column
	private String body;
}
