import { render } from '@stencil/core/testing';
import { DTDLApp } from './dtdl-app';

describe('dtdl-app', () => {
  it('should build', () => {
    expect(new DTDLApp()).toBeTruthy();
  });

  describe('rendering', () => {
    beforeEach(async () => {
      await render({
        components: [DTDLApp],
        html: '<dtdl-app></dtdl-app>'
      });
    });
  });
});