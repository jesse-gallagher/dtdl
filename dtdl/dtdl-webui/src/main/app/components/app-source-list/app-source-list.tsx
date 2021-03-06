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


@Component({
    tag: 'app-source-list'
})
export class AppSourceList {
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
        } catch (e) {
            console.log(e);
        }
    }

    renderSources() {
        if (this.sources == null) {
            return [];
        }
        return this.sources.map(s =>
            <tr>
                <td>{s.title}</td>
                <td>{s.type}</td>
                <td>
                    <stencil-route-link url={`/sources/${s._id}`} class="btn btn-info">Show</stencil-route-link>
                    <button class="btn btn-danger" onClick={() => this.deleteSource(s)}>Delete</button>
                </td>
            </tr>
        );
    }

    deleteSource(source) {
        if (confirm("Delete this source?")) {
            const url = new URL("$darwino-app/models/Source/" + encodeURIComponent(source._id), this.httpBase);
            fetch(url.toString(), { method: 'DELETE', credentials: 'include' })
                .then(r => this.refreshSources())
                .catch(e => {
                    console.error("Error while connecting to server", e);
                })
        }
    }

    render() {
        return (
            <div>
                <table class="table table-striped table-sm table-bordered">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Type</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.renderSources()}
                    </tbody>
                </table>
                <stencil-route-link url="/sources/new" class="btn btn-success">New</stencil-route-link>
            </div>
        )
    }
}
