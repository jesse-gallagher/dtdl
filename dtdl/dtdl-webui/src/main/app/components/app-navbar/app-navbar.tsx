import { Component } from '@stencil/core';

@Component({
  tag: 'app-navbar'
})
export class AppNavBar {
  render() {
    return (
        <ul class="nav flex-column">
            <li class="nav-item">
                <stencil-route-link url='/' class="nav-link" activeClass="active">Home</stencil-route-link>
            </li>
            <li class="nav-item">
                <stencil-route-link url='/sources' class="nav-link" activeClass="active">Sources</stencil-route-link>
            </li>
        </ul>
    );
  }
}