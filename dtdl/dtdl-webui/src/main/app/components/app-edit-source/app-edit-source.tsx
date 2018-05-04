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
    @State() private id: string;

    componentDidLoad() {
        this.id = this.match.params.id;
        console.log(this.match);
        this.refreshSource();
    }
    
    getSourceTypes():Array<any> {
        return [
            { id: "GITHUB", label: "GitHub" },
            { id: "BITBUCKET", label: "Bitbucket" }
        ];
    }

    refreshSource() {
        try {
            if (!this.id || this.id === 'new') {
                this.source = {
                    title: '',
                    type: '',
                    github: { },
                    bitbucket: { }
                };
                return;
            }

            const url = new URL("$darwino-app/models/Source/" + encodeURIComponent(this.id), this.httpBase);
            fetch(url.toString(), { credentials: 'include' })
                .then(r => r.json())
                .then(json => {
                    this.source = json.payload;
                })
                .catch(e => {
                    console.error("Error while connecting to server", e);
                })
        } catch (e) {
            console.log(e);
        }
    }

    updateSource(e) {
        e.preventDefault();
        try {
            const id = this.match.params.id;
            let req;
            if (id === 'new') {
                const url = new URL("$darwino-app/models/Source", this.httpBase)
                req = new Request(url.toString(), {
                    method: 'POST',
                    body: JSON.stringify(this.source),
                    credentials: 'include'
                })
            } else {
                const url = new URL("$darwino-app/models/Source/" + encodeURIComponent(this.id), this.httpBase);
                req = new Request(url.toString(), {
                    method: 'PUT',
                    body: JSON.stringify(this.source),
                    credentials: 'include'
                })
            }
            fetch(req)
                .then(r => r.json())
                .then(json => {
                    this.id = json._id;
                    alert("Updated source");

                    this.refreshSource();
                })
                .catch(e => {
                    console.error("Error while connecting to server", e);
                })
        } catch (e) {
            console.log(e);
        }
    }

    handleSourceFieldUpdate(prop: string, e) {
        const newObj = {};
        newObj[prop] = e.target.value;
        this.source = {
            ...this.source,
            ...newObj
        };
    }
    
    // TODO refactor these into a common mechanism
    handleGitHubFieldUpdate(prop: string, e) {
        const newObj = {};
        newObj[prop] = e.target.value;
        const github = {
            ...this.source.github,
            ...newObj
        }
        this.source = {
            ...this.source,
            github
        };
    }
    handleBitbucketFieldUpdate(prop: string, e) {
        const newObj = {};
        newObj[prop] = e.target.value;
        const bitbucket = {
            ...this.source.bitbucket,
            ...newObj
        }
        this.source = {
            ...this.source,
            bitbucket
        };
    }
    
    renderSourceInfo():any {
        if(this.source == null) {
            return null;
        }
        
        switch(this.source.type) {
        case "GITHUB":
            return (
                <fieldset>
                    <legend>GitHub Info</legend>
                    
                    <div class="form-group">
                        <label htmlFor="gitHubToken">Token</label>
                        <input type="text" class="form-control" id="gitHubToken" value={this.source.github.token}
                            onInput={(e) => this.handleGitHubFieldUpdate('token', e)}
                            onChange={(e) => this.handleGitHubFieldUpdate('token', e)} />
                    </div>
                    <div class="form-group">
                        <label htmlFor="gitHubRepository">Repository</label>
                        <input type="text" class="form-control" id="gitHubRepository" value={this.source.github.repository}
                            onInput={(e) => this.handleGitHubFieldUpdate('repository', e)}
                            onChange={(e) => this.handleGitHubFieldUpdate('repository', e)} />
                    </div>
                </fieldset>
            );
        case "BITBUCKET":
            return (
                <fieldset>
                    <legend>Bitbucket Info</legend>
                    
                    <div class="form-group">
                        <label htmlFor="bitbucketUsername">Username</label>
                        <input type="text" class="form-control" id="bitbucketUsername" value={this.source.bitbucket.username}
                            onInput={(e) => this.handleBitbucketFieldUpdate('username', e)}
                            onChange={(e) => this.handleBitbucketFieldUpdate('username', e)} />
                    </div>
                    <div class="form-group">
                        <label htmlFor="bitbucketPassword">App-Specific Password</label>
                        <input type="text" class="form-control" id="bitbucketPassword" value={this.source.bitbucket.password}
                            onInput={(e) => this.handleBitbucketFieldUpdate('password', e)}
                            onChange={(e) => this.handleBitbucketFieldUpdate('password', e)} />
                    </div>
                    <div class="form-group">
                        <label htmlFor="bitbucketRepository">Repository</label>
                        <input type="text" class="form-control" id="bitbucketRepository" value={this.source.bitbucket.repository}
                            onInput={(e) => this.handleBitbucketFieldUpdate('repository', e)}
                            onChange={(e) => this.handleBitbucketFieldUpdate('repository', e)} />
                    </div>
                </fieldset>
            );
        default:
            return null;
        }
    }

    render() {
        if (this.source == null) {
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
                            <select class="form-control" id="sourceType"
                                onChange={(e) => this.handleSourceFieldUpdate('type', e)}>
                                <option value="">- Select One -</option>
                                {this.getSourceTypes().map(type =>
                                    <option value={type.id} selected={this.source.type==type.id}>{type.label}</option>
                                )}
                            </select>
                        </div>
                        
                        {this.renderSourceInfo()}

                        <button class="btn btn-primary" onClick={(e) => this.updateSource(e)}>Update</button>
                    </form>
                </fieldset>
            </div>
        )
    }
}
