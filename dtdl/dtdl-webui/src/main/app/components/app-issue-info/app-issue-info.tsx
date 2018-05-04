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

import { Component, Prop } from '@stencil/core';

@Component({
    tag: 'app-issue-info'
})
export class AppIssueInfo {
    @Prop() issue:any;

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
                <hr />
                <div innerHTML={issue.body}></div>
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
}
