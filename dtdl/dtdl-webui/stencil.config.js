exports.config = {
  srcDir: 'src/main/app',
  outputTargets: [
    {
      type: 'www',
      dir: 'src/main/resources/DARWINO-INF/resources'
    }
  ]
};

exports.devServer = {
  root: 'src/main/resources/DARWINO-INF/resources',
  watchGlob: '**/**'
};
