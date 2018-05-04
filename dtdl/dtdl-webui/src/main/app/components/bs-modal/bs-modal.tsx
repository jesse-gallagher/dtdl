///
/// Copyright © 2018 Jesse Gallagher
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.
///

import { Component, Prop } from '@stencil/core';
import jQuery from "jquery";

@Component({
    tag: 'bs-modal',
    styleUrl: 'bs-modal.css'
})
export class BootstrapModal {
    @Prop() modalTitle:string;
    @Prop() modalHTML:string;
    @Prop() id:string;

    render() {
        return (
            <div class="modal fade" id={this.id+'_modal'} tabindex="-1" role="dialog" aria-labelledby={this.id+'_title'} aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id={this.id+'_title'}>{this.modalTitle}</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body" innerHTML={this.modalHTML}></div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
