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
                <ul class="nav flex-column">
                  <li class="nav-item">
                    <stencil-route-link url='/' class="nav-link">Home</stencil-route-link>
                  </li>
                  <li class="nav-item">
                    <stencil-route-link url='/profile/foo' class="nav-link">Profile</stencil-route-link>
                  </li>
                </ul>
              </div>
            </nav>
          </div>

          <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
            <stencil-router>
              <stencil-route url='/' component='app-home' exact={true}>
              </stencil-route>

              <stencil-route url='/profile/:name' component='app-profile'>
              </stencil-route>
            </stencil-router>
          </main>
        </div>
      </div>
    );
  }
}
