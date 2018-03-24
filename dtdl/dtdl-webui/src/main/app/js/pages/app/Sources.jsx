import React from "react";
import { Link } from "react-router-dom";
import { Button } from "react-bootstrap";
import { CursorPage, CursorGrid} from '@darwino/darwino-react-bootstrap'

import Constants from "./Constants";

export class SourcesGrid extends CursorGrid {

    // Default values of the properties
    static defaultProps  = {
        databaseId: Constants.DATABASE,
        params: {
            name: "Sources"
        },
        ftSearch:true,
        grid: {
            columns:[
                {name: "Name", key: "name"},
                {name: "Creation Date", key: "cdate"}
            ]
        },
        baseRoute: "/app/source"
    }

    contributeActionBar() {
        return (
            <div key="main">
                <Link to={`${this.props.baseRoute}`} className="btn btn-primary">Create New Source</Link>
            </div>
        );
    }
}


//
// Main frame that displays the grid in a page, with an action bar
//
export default class SourcesView extends CursorPage {

    constructor(props,context) {
        super(props,context)
    }
    
    render() {
        return (
            <div>
                <h4>All Documents</h4>
                {this.createActionBar()}
                <div>
                    <SourcesGrid height={this.state.gridHeight}/>
                </div>
            </div>
        )
    }
}

