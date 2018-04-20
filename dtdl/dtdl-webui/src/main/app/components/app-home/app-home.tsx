import { Component } from '@stencil/core';


@Component({
  tag: 'app-home',
  styleUrl: 'app-home.css'
})
export class AppHome {

  render() {
    return (
      <div class='app-home'>
        <p>
          Welcome to the Darwino To-Do List app
        </p>

        <stencil-route-link url='/profile/stencil'>
          <button>
            Profile page
          </button>
        </stencil-route-link>
      </div>
    );
  }
}
