/**
 * This is an autogenerated file created by the Stencil build process.
 * It contains typing information for all components that exist in this project
 * and imports for stencil collections that might be configured in your stencil.config.js file
 */

import '@stencil/core';

declare global {
  namespace JSX {
    interface Element {}
    export interface IntrinsicElements {}
  }
  namespace JSXElements {}

  interface HTMLStencilElement extends HTMLElement {
    componentOnReady(): Promise<this>;
    componentOnReady(done: (ele?: this) => void): void;

    forceUpdate(): void;
  }

  interface HTMLAttributes {}
}

import '@stencil/router';

import {
  MatchResults,
} from '@stencil/router';

declare global {

  namespace StencilComponents {
    interface AppEditSource {
      'match': MatchResults;
    }
  }

  interface HTMLAppEditSourceElement extends StencilComponents.AppEditSource, HTMLStencilElement {}

  var HTMLAppEditSourceElement: {
    prototype: HTMLAppEditSourceElement;
    new (): HTMLAppEditSourceElement;
  };
  interface HTMLElementTagNameMap {
    'app-edit-source': HTMLAppEditSourceElement;
  }
  interface ElementTagNameMap {
    'app-edit-source': HTMLAppEditSourceElement;
  }
  namespace JSX {
    interface IntrinsicElements {
      'app-edit-source': JSXElements.AppEditSourceAttributes;
    }
  }
  namespace JSXElements {
    export interface AppEditSourceAttributes extends HTMLAttributes {
      'match'?: MatchResults;
    }
  }
}


declare global {

  namespace StencilComponents {
    interface AppHome {

    }
  }

  interface HTMLAppHomeElement extends StencilComponents.AppHome, HTMLStencilElement {}

  var HTMLAppHomeElement: {
    prototype: HTMLAppHomeElement;
    new (): HTMLAppHomeElement;
  };
  interface HTMLElementTagNameMap {
    'app-home': HTMLAppHomeElement;
  }
  interface ElementTagNameMap {
    'app-home': HTMLAppHomeElement;
  }
  namespace JSX {
    interface IntrinsicElements {
      'app-home': JSXElements.AppHomeAttributes;
    }
  }
  namespace JSXElements {
    export interface AppHomeAttributes extends HTMLAttributes {

    }
  }
}


declare global {

  namespace StencilComponents {
    interface AppNavbar {

    }
  }

  interface HTMLAppNavbarElement extends StencilComponents.AppNavbar, HTMLStencilElement {}

  var HTMLAppNavbarElement: {
    prototype: HTMLAppNavbarElement;
    new (): HTMLAppNavbarElement;
  };
  interface HTMLElementTagNameMap {
    'app-navbar': HTMLAppNavbarElement;
  }
  interface ElementTagNameMap {
    'app-navbar': HTMLAppNavbarElement;
  }
  namespace JSX {
    interface IntrinsicElements {
      'app-navbar': JSXElements.AppNavbarAttributes;
    }
  }
  namespace JSXElements {
    export interface AppNavbarAttributes extends HTMLAttributes {

    }
  }
}


declare global {

  namespace StencilComponents {
    interface AppSourceIssues {
      'match': MatchResults;
    }
  }

  interface HTMLAppSourceIssuesElement extends StencilComponents.AppSourceIssues, HTMLStencilElement {}

  var HTMLAppSourceIssuesElement: {
    prototype: HTMLAppSourceIssuesElement;
    new (): HTMLAppSourceIssuesElement;
  };
  interface HTMLElementTagNameMap {
    'app-source-issues': HTMLAppSourceIssuesElement;
  }
  interface ElementTagNameMap {
    'app-source-issues': HTMLAppSourceIssuesElement;
  }
  namespace JSX {
    interface IntrinsicElements {
      'app-source-issues': JSXElements.AppSourceIssuesAttributes;
    }
  }
  namespace JSXElements {
    export interface AppSourceIssuesAttributes extends HTMLAttributes {
      'match'?: MatchResults;
    }
  }
}


declare global {

  namespace StencilComponents {
    interface AppSourceList {

    }
  }

  interface HTMLAppSourceListElement extends StencilComponents.AppSourceList, HTMLStencilElement {}

  var HTMLAppSourceListElement: {
    prototype: HTMLAppSourceListElement;
    new (): HTMLAppSourceListElement;
  };
  interface HTMLElementTagNameMap {
    'app-source-list': HTMLAppSourceListElement;
  }
  interface ElementTagNameMap {
    'app-source-list': HTMLAppSourceListElement;
  }
  namespace JSX {
    interface IntrinsicElements {
      'app-source-list': JSXElements.AppSourceListAttributes;
    }
  }
  namespace JSXElements {
    export interface AppSourceListAttributes extends HTMLAttributes {

    }
  }
}


declare global {

  namespace StencilComponents {
    interface DtdlApp {

    }
  }

  interface HTMLDtdlAppElement extends StencilComponents.DtdlApp, HTMLStencilElement {}

  var HTMLDtdlAppElement: {
    prototype: HTMLDtdlAppElement;
    new (): HTMLDtdlAppElement;
  };
  interface HTMLElementTagNameMap {
    'dtdl-app': HTMLDtdlAppElement;
  }
  interface ElementTagNameMap {
    'dtdl-app': HTMLDtdlAppElement;
  }
  namespace JSX {
    interface IntrinsicElements {
      'dtdl-app': JSXElements.DtdlAppAttributes;
    }
  }
  namespace JSXElements {
    export interface DtdlAppAttributes extends HTMLAttributes {

    }
  }
}

declare global { namespace JSX { interface StencilJSX {} } }
