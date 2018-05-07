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

import { Component, Prop, State, Watch } from '@stencil/core';
import { MatchResults } from '@stencil/router';

@Component({
    tag: 'app-source-issues',
    styleUrl: 'app-source-issues.css'
})
export class AppSourceIssues {
    @Prop({ context: 'httpBase' }) private httpBase: string;
    @Prop() match: MatchResults;

    @State() private source: any;
    @State() private issues: Array<any>;
    @State() private updating: boolean;
    
    @State() private activeIssue: any;

    componentDidLoad() {
        this.refreshSource();
    }
    
    @Watch("match")
    matchHandler(newValue: MatchResults, oldValue: MatchResults) {
        this.activeIssue = null;
        this.refreshSource();
    }

    refreshSource() {
        try {
            const url = new URL("$darwino-app/models/Source/" + encodeURIComponent(this.match.params.sourceId), this.httpBase);
            fetch(url.toString(), { credentials: 'include' })
                .then(r => r.json())
                .then(json => {
                    this.source = json.payload;

                    this.refreshIssues();
                })
                .catch(e => {
                    console.error("Error while connecting to server", e);
                })
        } catch (e) {
            console.log(e);
        }
    }

    refreshIssues() {
        try {
            this.updating = true;
            const url = new URL("$darwino-app/sources/" + encodeURIComponent(this.match.params.sourceId) + "/issues", this.httpBase);
            fetch(url.toString(), { credentials: 'include' })
                .then(r => r.json())
                .then(json => {
                    this.updating = false;
                    this.issues = json.payload;
                })
                .catch(e => {
                    console.error("Error while connecting to server", e);
                })
        } catch (e) {
            console.log(e);
        }
    }

    renderIssues() {
        if(this.updating) {
            return <tr><td colSpan={3} style={{textAlign: 'center'}}><div class="loader" style={{margin: '0 auto'}}></div></td></tr>
        }
        if (this.issues == null) {
            return [];
        }
        return this.issues.map(issue => {
            const tags = issue.tags instanceof Array ? issue.tags : [];
            const version = issue.version ?
                    issue.version.name :
                    null;
            
            return (<div class={this.activeIssue == issue ? 'issue active' : 'issue'} onClick={() => this.selectIssue(issue)}>
                <span class='issue-id'>{issue.id}</span>
                <span class='issue-title'>{issue.title}</span>
                <span class='issue-status'>{issue.status}</span>
                <span class='issue-reporter'><app-user user={issue.reportedBy} /></span>
                <span class='issue-date'><app-date value={issue.createdAt}/></span>
            </div>)
        });
    }
    
    selectIssue(issue:any) {
        this.activeIssue = issue
    }
    
    renderIssueInfo() {
        if(this.activeIssue) {
            return <app-issue-info issue={this.activeIssue} sourceId={this.match.params.sourceId}></app-issue-info>;
        } else {
            return null;
        }
    }

    render() {
        if (this.source == null) {
            return null;
        }
        return (
            <div>
                <h2>{this.source.title}</h2>
                
                <div class="row">
                    <div class="col-md-5 col-sm-12 issue-list">
                        {this.renderIssues()}
                    </div>
                    <div class="col-md-7 col-sm-12">
                         {this.renderIssueInfo()}
                    </div>
                </div>
            </div>
        )
    }
}
