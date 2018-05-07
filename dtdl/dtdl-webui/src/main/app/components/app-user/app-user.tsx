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
    tag: 'app-user',
    styleUrl: 'app-user.css'
})
export class AppUser {
    @Prop() private user: any;
    @Prop() private link = false;

    render() {
        if (!this.user) {
            return null;
        }
        const user = this.user;

        if (this.link) {
            return <a href={user.url} class='username'><img class='inline-avatar' src={user.avatarUrl} />{user.name}</a>;
        } else {
            return <span class='username'><img class='inline-avatar' src={user.avatarUrl} />{user.name}</span>;
        }
    }
}