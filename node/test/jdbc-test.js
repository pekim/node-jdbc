var jdbc = require('../lib/jdbc'),
    Connection = jdbc.Connection;

exports.initialised = function(test){
  test.expect(1);
  
  jdbc.onInitialised(function initialiseEvent() {
    test.ok(true);
    test.done();
  });
};

exports.connected = function(test){
  var connection;

  test.expect(0);
  
  connection = new Connection('jdbc:hsqldb:mem:', function connected() {
    test.done();
  });
};
