const config = require("@vue/cli-service/webpack.config.js");

const redundantPlugins = {
  ForkTsCheckerWebpackPlugin: true,
  PreloadPlugin: true,
  HtmlWebpackPlugin: true,
  CopyPlugin: true,
  CaseSensitivePathsPlugin: true,
  FriendlyErrorsWebpackPlugin: true,
};

config.plugins = config.plugins.filter(
  (plugin) => !(plugin.constructor && redundantPlugins[plugin.constructor.name])
);

// vue files are split into multiple webpack modules,
// we need to choose and map script webpack resource (i.e. module) to one absolute file path
const mapResourceToPath = (context) => {
  const resource = context.resource;

  if (!resource) return resource;

  if (context.version) {
    // loading phase
    if (resource.endsWith("vue")) {
      return `${resource}?ignore`;
    }

    return resource.indexOf("type=script") > 0
      ? context.resourcePath
      : resource;
  }

  // processing phase
  return resource.indexOf("type=script") > 0 && context.wallaby
    ? resource.substring(0, resource.indexOf("?"))
    : resource.endsWith("vue")
    ? `${resource}?ignore`
    : resource;
};

module.exports = function(wallaby) {
  return {
    files: [{ pattern: "src/**/*.*", load: false }],

    tests: [{ pattern: "tests/**/*.*", load: false }],

    postprocessor: wallaby.postprocessors.webpack(config, {
      setupFiles: [require.resolve("@vue/cli-plugin-unit-mocha/setup")],
      mapResourceToPath: mapResourceToPath,
    }),

    env: {
      kind: "chrome",
    },

    setup: function() {
      // required to trigger test loading
      window.__moduleBundler.loadTests();
    }
  };
};
