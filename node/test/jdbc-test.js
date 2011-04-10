var jdbc = require('../lib/jdbc.js'),
    Connection = jdbc.Connection;

exports.initialised = function(test){
  test.expect(1);
  
  jdbc.onInitialised(function initialiseEvent() {
    test.ok(true);
    test.done();
  });
};

//exports.initialised2 = function(test){
//  test.expect(1);
//  
//  jdbc.onInitialised(function initialiseEvent() {
//    test.ok(true);
//    test.done();
//  });
//};

//exports.connected = function(test){
//  test.expect(0);
//  
//  console.log(Connection);
////  var connection = new Connection(function connected() {
//    test.done();
////  });
//};
