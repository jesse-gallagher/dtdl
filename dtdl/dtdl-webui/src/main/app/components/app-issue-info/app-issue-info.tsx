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

@Component({
    tag: 'app-issue-info',
    styleUrl: 'app-issue-info.css'
})
export class AppIssueInfo {
    @Prop({ context: 'httpBase' }) private httpBase: string;
    @Prop() issue:any;
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
    
    refreshComments() {
        if(!this.issue) {
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
        if(!this.issue) {
            return null;
        }
        const issue = this.issue;
        
        return (
            <div class="issue">
                <h2>#{issue.id} - {issue.title}</h2>
                <h3>{issue.status}</h3>
                <h4><a href={issue.url} target="_blank">{issue.url}</a></h4>
                {this.renderIssueTags(issue)}
                {this.renderName(issue.assignedTo)}
                <hr />
                <div innerHTML={issue.body}></div>
                <hr />
                {this.renderComments()}
            </div>
        );
    }
    
    renderIssueTags(issue:any):any {
        if(!(issue.tags instanceof Array)) {
            return null;
        } else {
            return <p>
                {issue.tags.map(tag => {
                    const color = tag.color ? ('#'+tag.color) : '';
                    return <span class='badge badge-primary' style={{backgroundColor: color}}>{tag.name}</span>
                    })
                }
            </p>
        }
    }
    
    renderName(user:any):any {
        if(!user) {
            return null;
        } else {
            return <a href={user.url}><img class='inline-avatar' src={user.avatarUrl} />{user.name}</a>
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
                    <div class="comment">
                        <p>{this.renderName(comment.postedBy)}</p>
                        <div innerHTML={comment.body}></div>
                    </div>
                )}
            </div>
        )
    }
}
