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
  
  connection = new Connection('jdbc:hsqldb:mem:test', function connected() {
    test.done();
  });
};

exports.connectError = function(test){
  var connection;

  test.expect(0);
  
  connection = new Connection('jdbc:bad', function connected() {
  });
  
  connection.on('error', function(message) {
    test.done();
  });
};

exports.metadata = function(test){
  var connection;

  test.expect(2);
  
  connection = new Connection('jdbc:hsqldb:mem:test', function connected() {
    connection.metadata(['driverName', 'databaseProductName'], function(data) {
      test.strictEqual(data.driverName, 'dn');
      test.strictEqual(data.databaseProductName, 'dpn');
      
      test.done();
    });
  });
};
