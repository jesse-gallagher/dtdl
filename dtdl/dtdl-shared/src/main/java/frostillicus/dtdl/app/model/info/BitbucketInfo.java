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
package frostillicus.dtdl.app.model.info;

import javax.validation.constraints.NotEmpty;

import org.jnosql.artemis.Column;
import org.jnosql.artemis.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable @Data @NoArgsConstructor
public class BitbucketInfo implements InfoHolder {
	@Column @NotEmpty
	private String username;
	
	@Column @NotEmpty
	private String password;
	
	@Column @NotEmpty
	private String repository;
}
