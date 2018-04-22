///
/// Copyright © 2018 Jesse Gallagher
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.
///

import { Component, State, Prop } from '@stencil/core';

@Component({
  tag: 'app-navbar'
})
export class AppNavBar {
    // TODO consolidate into a parent class
    @Prop({ context: 'httpBase' }) private httpBase: string;

    @State() private sources: Array<any>;

    componentDidLoad() {
      this.refreshSources();
    }

    refreshSources() {
      try {
        const url = new URL("$darwino-app/models/Source", this.httpBase);
        fetch(url.toString(), { credentials: 'include' })
          .then(r => r.json())
          .then(json => {
            this.sources = json.payload;
          })
          .catch(e => {
              console.error("Error while connecting to server", e);
          })
      } catch(e) {
          console.log(e);
      }
    }
    
    renderSources() {
        if(this.sources == null) {
            return [];
        }
        return this.sources.map((source) =>
            <li class="nav-item">
                <stencil-route-link url={`/issues/${source._id}`} class="nav-link" activeClass="active">{source.title}</stencil-route-link>
            </li>
        )
    }

    render() {
        return (
            <ul class="nav flex-column">
                <li class="nav-item">
                    <stencil-route-link url='/' class="nav-link" activeClass="active">Home</stencil-route-link>
                </li>
                <li class="nav-item">
                    <hr />
                </li>
                {this.renderSources()}
                <li class="nav-item">
                    <hr />
                </li>
                <li class="nav-item">
                    <stencil-route-link url='/sources' class="nav-link" activeClass="active">Sources</stencil-route-link>
                </li>
            </ul>
        );
    }
}