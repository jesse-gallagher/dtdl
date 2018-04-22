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

import { Component, Prop, State } from '@stencil/core';
import { MatchResults } from '@stencil/router';

@Component({
  tag: 'app-edit-source'
})
export class AppEditSource {
  @Prop({ context: 'httpBase' }) private httpBase: string;
  @Prop() match: MatchResults;

  @State() private source: any;
  @State() private issues: Array<any>;

  componentDidLoad() {
    this.refreshSource();
  }

  refreshSource() {
    try {
      const url = new URL("$darwino-app/models/Source/" + encodeURIComponent(this.match.params.id), this.httpBase);
      fetch(url.toString(), { credentials: 'include' })
        .then(r => r.json())
        .then(json => {
          this.source = json.payload;
        })
        .catch(e => {
            console.error("Error while connecting to server", e);
        })
    } catch(e) {
        console.log(e);
    }
  }
  
  updateSource(e) {
      e.preventDefault();
      try {
          const url = new URL("$darwino-app/models/Source/" + encodeURIComponent(this.match.params.id), this.httpBase);
          fetch(url.toString(), {
              method: 'PUT',
              body: JSON.stringify(this.source),
              credentials: 'include'
          })
              .then(json => {
                  alert("Updated source");
                  
                  this.refreshSource();
              })
              .catch(e => {
                  console.error("Error while connecting to server", e);
              })
      } catch(e) {
          console.log(e);
      }
  }
  
  handleSourceFieldUpdate(prop:string, e) {
      const newObj = {};
      newObj[prop] = e.target.value;
      this.source = {
         ...this.source,
         ...newObj
      };
      console.log(this.source);
  }

  render() {
    if(this.source == null) {
        return null;
    }
    return (
        <div>
            <fieldset>
                <legend>Source</legend>
                
                <form onSubmit={(e) => this.updateSource(e)}>
                    <div class="form-group">
                        <label htmlFor="sourceTitle">Title</label>
                        <input type="text" class="form-control" id="sourceTitle" value={this.source.title}
                            onInput={(e) => this.handleSourceFieldUpdate('title', e)}
                            onChange={(e) => this.handleSourceFieldUpdate('title', e)} />
                    </div>
                    <div class="form-group">
                        <label htmlFor="sourceType">Type</label>
                        <input type="text" class="form-control" id="sourceType" value={this.source.type}
                            onInput={(e) => this.handleSourceFieldUpdate('type', e)}
                            onChange={(e) => this.handleSourceFieldUpdate('type', e)} />
                    </div>
                        
                    <button class="btn btn-primary" onClick={(e) => this.updateSource(e)}>Update</button>
                </form>
            </fieldset>
        </div>
    )
  }
}
