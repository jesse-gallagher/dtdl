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

import { flush, render } from '@stencil/core/testing';
import { AppProfile } from './app-profile';

describe('app-profile', () => {
  it('should build', () => {
    expect(new AppProfile()).toBeTruthy();
  });

  describe('rendering', () => {
    let element;
    beforeEach(async () => {
      element = await render({
        components: [AppProfile],
        html: '<app-profile></app-profile>'
      });
    });

    it('should not render any content if there is not a match', async () => {
      await flush(element);
      expect(element.textContent).toEqual('');
    })

    it('should work with a name passed', async () => {
      element.match = {
        params: {
          name: 'stencil'
        }
      }
      
      await flush(element);
      expect(element.textContent).toEqual('Hello! My name is stencil. My name was passed in through a route param!');
    });
  });
});