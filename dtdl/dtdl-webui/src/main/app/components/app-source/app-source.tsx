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
  tag: 'app-source'
})
export class AppSource {
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

          this.refreshIssues();
        })
        .catch(e => {
            console.error("Error while connecting to server", e);
        })
    } catch(e) {
        console.log(e);
    }
  }

  refreshIssues() {
    try {
        const url = new URL("$darwino-app/issues/" + encodeURIComponent(this.match.params.id), this.httpBase);
        fetch(url.toString(), { credentials: 'include' })
          .then(r => r.json())
          .then(json => {
            this.issues = json.payload;
          })
          .catch(e => {
              console.error("Error while connecting to server", e);
          })
      } catch(e) {
          console.log(e);
      }
  }

  renderIssues() {
    if(this.issues == null) {
      return [];
    }
    return this.issues.map(issue =>
        <tr>
            <td>{issue.title}</td>
            <td><a href={issue.url} target="_blank">{issue.url}</a></td>
        </tr>
    );
  }

  render() {
    if(this.source == null) {
        return null;
    }
    return (
        <div>
            <h1>{this.source._id}</h1>
            <h3>{this.source.type}</h3>

            <table>
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Link</th>
                    </tr>
                </thead>
                <tbody>
                    {this.renderIssues()}
                </tbody>
            </table>
        </div>
    )
  }
}