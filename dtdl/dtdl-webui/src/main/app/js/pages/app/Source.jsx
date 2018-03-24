/* 
 * (c) Copyright Darwino Inc. 2014-2017.
 */

import React, { Component } from "react";
import { withRouter } from 'react-router'
import { connect } from 'react-redux'
import { Field, reduxForm } from 'redux-form';
import { Link, Prompt } from "react-router-dom";
import { Button } from 'react-bootstrap';

import {JsonDebug} from "@darwino/darwino-react";
import { DocumentForm, ComputedField,
         renderField, renderRadioGroup, renderCheckbox, renderSelect, renderRichText, renderDatePicker } from '@darwino/darwino-react-bootstrap';

import Constants from "./Constants";

import TypeGitHub from "./subform/TypeGitHub";

const DATABASE = Constants.DATABASE;
const STORE = "_default";

const FORM_NAME = "Source";

export class Source extends DocumentForm {

    // Default values of the properties
    static defaultProps  = {
        databaseId: DATABASE,
        storeId: STORE
    };

    constructor(props) {
        super(props)
    }

    // Transform the generic attachment links to physical ones
    prepareForDisplay(values) {
        //if(values.richText) values.richText = richTextToDisplayFormat(this.state,values.richText)
    }

    // Transform the physical attachment links back to generic ones
    prepareForSave(values) {
        //if(values.richText) values.richText = richTextToStorageFormat(this.state,values.richText)
    }

    // Default values when a new document is created
    defaultValues(values) {
        //values.form = "MyForm"
    }

    // Values computed once when the document is loaded
    calculateOnLoad(values) {
        //values.title = "My Document"
    }

    // Values computed every time the document is changed
    calculateOnChange(values) {
        //values.fullnameUpper = this.getFieldValue("fullname","").toUpperCase()
    }

    // Validation
    validate(values) {
        const errors = {};
        // Add the validation rules here!
        return errors;
    }

    getTypeOptions() {
        return [{value: '', label: '- Select One -'}, ...Constants.SOURCE_TYPES.map(type => { return { value: type.id, label: type.name } })];
    }

    getTypeSubform() {
        switch(this.getFieldValue("type")) {
            case "github":
                return (<TypeGitHub {...this.props} name=""/>);
            case "":
            case null:
                return null;
        }
        return null;
    }

    render() {
        const { newDoc, doc } = this.state;
        const { handleSubmit, dirty, reset, invalid, submitting, type } = this.props;
        const readOnly = this.isReadOnly();
        const disabled = this.isDisabled();
        return (
            <div>
                <form onSubmit={handleSubmit(this.handleUpdateDocument)}>
                    {this.createActionBar()}
                    <Prompt
                        when={dirty||newDoc}
                        message={location => (
                            `The document is modified and not saved yet.\nDo you want to leave the current page without saving it?`
                        )}
                    />                    
                    <fieldset>
                        <legend>Source</legend>

                        <div className="col-md-12 col-sm-12">
                            <Field name="name" type="text" component={renderField} label="Source Name" disabled={disabled} readOnly={readOnly}/>
                        </div>
                        <div className="col-md-12 col-sm-12">
                            <Field name="type" component={renderSelect} label="Type" disabled={disabled} readOnly={readOnly} options={this.getTypeOptions()}/>
                        </div>
                        {this.getTypeSubform()}

                        <div>
                            <span style={(disabled||readOnly) ? {display: 'none'} : {}}>
                                <div className="pull-right">
                                    <Button onClick={this.handleDeleteDocument} bsStyle="danger" style={newDoc ? {display: 'none'} : {}}>Delete</Button>
                                </div>
                                <Button bsStyle="primary" type="submit" disabled={invalid||submitting}>Submit</Button>
                            </span>
                            <Button bsStyle="link" onClick={this.handleCancel}>Cancel</Button>
                        </div>
                        
                        {/*Uncomment to display the current JSON content*/}
                        <JsonDebug form={this.props.form}/>
                    </fieldset>
                </form>
            </div>
        );
  }
}

const form = reduxForm({
    form: FORM_NAME,
    validate: DocumentForm.validateForm,
    onChange: DocumentForm.onChange
});

export default withRouter(
    connect(null,DocumentForm.mapDispatchToProps)
        (form(Source))
)
