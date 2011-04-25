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

exports.connection = function(test){
  test.expect(1);
  
  var jdbc = new Jdbc();
  jdbc.onInitialised(function initialiseEvent() {
    jdbc.createConnection('jdbc:hsqldb:mem:test', undefined, function() {
      test.ok(true);
      
      jdbc.close();
      test.done();
    });
  });
};

//exports.connected = function(test){
//  var connection;
//
//  test.expect(0);
//  
//  connection = new Connection('jdbc:hsqldb:mem:test', function connected() {
//    test.done();
//  });
//};
//
//exports.connectError = function(test){
//  var connection;
//
//  test.expect(0);
//  
//  connection = new Connection('jdbc:bad', function connected() {
//  });
//  
//  connection.on('error', function(message) {
//    test.done();
//  });
//};
//
//exports.metadata = function(test){
//  var connection;
//
//  test.expect(2);
//  
//  connection = new Connection('jdbc:hsqldb:mem:test', function connected() {
//    connection.metadata(['driverName', 'databaseProductName'], function(data) {
//      test.strictEqual(data.driverName, 'dn');
//      test.strictEqual(data.databaseProductName, 'dpn');
//      
//      test.done();
//    });
//  });
//};
