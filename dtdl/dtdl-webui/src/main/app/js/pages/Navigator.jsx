/* 
 * (c) Copyright Darwino Inc. 2014-2017.
 */

import React from "react";
import { Navbar, Nav } from 'react-bootstrap';

import {NavLink, NavGroup} from '@darwino/darwino-react-bootstrap';

export default class Navigator extends React.Component {
    render() {
        return (
            <Navbar inverse={this.props.inverse} collapseOnSelect className="navbar-fixed-side">
                <Navbar.Header>
                    <Navbar.Toggle />
                </Navbar.Header>
                <Navbar.Collapse>
                    <Nav>
                        <NavLink to="/" exact={true}>Home</NavLink>

                        <NavGroup title="Sources" collapsible={true} defaultExpanded={true}>
                            <NavLink to="/app/sources">Sources</NavLink>
                        </NavGroup>

                        <NavGroup title="Developers">
                            <NavLink to="/admin/console">Console</NavLink>
                        </NavGroup>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}