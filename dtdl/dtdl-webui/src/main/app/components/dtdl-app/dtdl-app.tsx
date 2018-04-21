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

import { Component } from '@stencil/core';

@Component({
  tag: 'dtdl-app',
  styleUrl: 'dtdl-app.css'
})
export class DTDLApp {

  render() {
    return (
      <div>
        <nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
          <stencil-route-link url='/' class="navbar-brand col-sm-3 col-md-2 mr-0">Darwino To-Do List</stencil-route-link>
        </nav>

        <div class="container-fluid">
          <div class="row">
            <nav class="col-md-2 d-none d-md-block bg-light sidebar">
              <div class="sidebar-sticky">
                <app-navbar></app-navbar>
              </div>
            </nav>

            <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
              <stencil-router>
                <stencil-route url='/' component='app-home' exact={true}>
                </stencil-route>

                <stencil-route url='/sources' component='app-source-list' exact={true}/>

                <stencil-route url='/profile/:name' component='app-profile'>
                </stencil-route>
              </stencil-router>
            </main>
          </div>
        </div>
      </div>
    );
  }
}
