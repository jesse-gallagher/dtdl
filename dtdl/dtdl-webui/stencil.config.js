exports.config = {
  srcDir: 'src/main/app',
  outputTargets: [
    {
      type: 'www',
      dir: 'src/main/resources/DARWINO-INF/resources',
      serviceWorker: {
    	  swSrc: 'sw.js'
      }
    }
  ],
  globalScript: 'src/main/app/global/context.ts',
  enableCache: false
};

exports.devServer = {
  root: 'src/main/resources/DARWINO-INF/resources',
  watchGlob: '**/**'
};
