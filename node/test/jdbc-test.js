var Jdbc = require('../lib/jdbc');

exports.initialised = function(test){
  test.expect(1);
  
  var jdbc = new Jdbc();
  jdbc.onInitialised(function initialiseEvent() {
    test.ok(true);
    
    jdbc.close();
    test.done();
  });
};
