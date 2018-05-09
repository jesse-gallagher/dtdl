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
    tag: 'app-issue-info',
    styleUrl: 'app-issue-info.css',
    shadow: true
})
export class AppIssueInfo {
    @Prop({ context: 'httpBase' }) private httpBase: string;
    @Prop() match: MatchResults;
    
    @Prop() issue:any = null;
    @Prop() sourceId:any;
    
    @State() private comments:Array<any>;
    @State() private updating: boolean;
    
    componentDidLoad() {
        this.refreshComments();
    }
    
    @Watch("issue")
    matchHandler(newValue:any, oldValue:any) {
        this.refreshComments();
    }
    
    isNew():boolean {
        return this.issue != null && !this.issue.id;
    }
    
    refreshComments() {
        if(!this.issue || this.isNew()) {
            return;
        }
        try {
            this.updating = true;
            const url = new URL("$darwino-app/sources/" + encodeURIComponent(this.sourceId) + "/issues/" + encodeURIComponent(this.issue.id) + "/comments", this.httpBase);
            fetch(url.toString(), { credentials: 'include' })
                .then(r => r.json())
                .then(json => {
                    this.updating = false;
                    this.comments = json.payload;
                })
                .catch(e => {
                    console.error("Error while connecting to server", e);
                })
        } catch (e) {
            console.log(e);
        }
    }

    render() {
        if(this.issue === null) {
            return null;
        }
        const issue = this.issue;
        
        return (
            <div class="issue-info">
                <div class='issue-box'>
                    <span class='issue-title'>{issue.title}</span>
                    <span class='issue-id'>{issue.id}</span>
                    <span class='issue-status'>{issue.status}</span>
                    <span class='issue-tags'>{this.renderIssueTags(issue)}</span>
                    <span class='issue-assignee'><app-user user={issue.assignedTo} /></span>
                    <span class='issue-url'><a href={issue.url} target='_blank'>Open</a></span>
                </div>
                <div class='issue-box'>
                    <div class='box-header'>
                        <span class='issue-reporter'><app-user user={issue.reportedBy} /></span>
                        <span class='issue-date'>Filed <app-date value={issue.createdAt}/></span>
                    </div>
                    <div innerHTML={issue.body}></div>
                </div>
                {this.renderComments()}
            </div>
        );
    }
    
    renderIssueTags(issue:any):any {
        if(!(issue.tags instanceof Array)) {
            return null;
        } else {
            return <span class='tags'>
                {issue.tags.map(tag => {
                    const color = tag.color ? ('#'+tag.color) : '';
                    return <span class='badge badge-primary' style={{backgroundColor: color}}>{tag.name}</span>
                    })
                }
            </span>
        }
    }
    
    renderComments():any {
        if(!this.issue) {
            return null;
        }
        if(!this.comments) {
            return null;
        }
        
        return (
            <div class="comments">
                {this.comments.map(comment =>
                    <div class="comment issue-box">
                        <span class='comment-poster'><app-user user={comment.postedBy}/></span>
                        <span class='comment-date'><app-datetime value={comment.createdAt}/></span>
                        <div innerHTML={comment.body}></div>
                    </div>
                )}
            </div>
        )
    }
}
