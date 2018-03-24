import React from "react";

import { Route, Switch } from 'react-router-dom';

import Home from "./Home";
import Header from "./Header";
import Footer from "./Footer";
import Navigator from "./Navigator";
import {AdminConsole} from '@darwino/darwino-react-bootstrap';


import Source from "./app/Source";
import Sources from "./app/Sources";

export default class Layout extends React.Component {
  render() {
    const { location, renderingOptions } = this.props;
    return (
      <div>
        <Header inverse={renderingOptions.headerInverted}/>
        <div className="container-fluid" id="body-container">
          <div className="row">
            <div className="col-sm-3 col-lg-2 sidebar">
              <Navigator location={location} inverse={renderingOptions.leftnavInverted}/>
            </div>
            <div className="col-sm-9 col-lg-10 main" id="content">
              <Switch>
                <Route exact path="/" component={Home}></Route>

                <Route exact path="/app/sources" component={Sources}></Route>
                <Route exact path="/app/source/" component={Source}></Route>
                <Route exact path="/app/source/:unid" component={Source}></Route>

                <Route exact path="/admin/console" component={AdminConsole}></Route>
              </Switch>
            </div>
          </div>
        </div>
        <Footer inverse={renderingOptions.footerInverted}/>
      </div>
    );
  }
}
