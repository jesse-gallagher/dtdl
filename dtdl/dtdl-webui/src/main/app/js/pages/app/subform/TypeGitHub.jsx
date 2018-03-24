 import React, { Component } from "react";
 import { Field } from 'redux-form';
 import { Button, DropdownButton, MenuItem } from 'react-bootstrap';
 
 import {  _t } from '@darwino/darwino';
 import { DocumentSubform, renderText, renderSelect } from '@darwino/darwino-react-bootstrap';
 
 export default class TypeGitHub extends DocumentSubform {
 
	 constructor(props,context) {
		 super(props,context)
	 }
 
	 defaultValues(values) {
	 }
 
	 validate(values) {
		 const errors = {};
		 // Add the validation rules here!
		 return errors;
	 }
 
	 render() {
		 const readOnly = this.getForm().isReadOnly();
		 const disabled = this.getForm().isDisabled();
		 return (
			 <div>
				 <div className="col-md-12 col-sm-12">
					 <Field name="github.token" type="text" component={renderText} label="Token" disabled={disabled} readOnly={readOnly}/>
				 </div>
			 </div>
		 );
	 }
 }
 