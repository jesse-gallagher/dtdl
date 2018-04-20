import { Component } from '@stencil/core';

@Component({
  tag: 'app-navbar'
})
export class AppNavBar {
  render() {
    return (
        <ul class="nav flex-column">
            <li class="nav-item">
            <stencil-route-link url='/' class="nav-link">Home</stencil-route-link>
            </li>
            <li class="nav-item">
            <stencil-route-link url='/profile/foo' class="nav-link">Profile</stencil-route-link>
            </li>
        </ul>
    );
  }
}