exports.config = {
  srcDir: 'src/main/app',
  outputTargets: [
    {
      type: 'www',
      dir: 'src/main/resources/DARWINO-INF/resources'
    }
  ],
  globalScript: 'src/main/app/global/context.ts'
};

exports.devServer = {
  root: 'src/main/resources/DARWINO-INF/resources',
  watchGlob: '**/**'
};
