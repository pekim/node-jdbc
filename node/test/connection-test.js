var Jdbc = require('../lib/jdbc');

exports.createConnection = function(test){
  var jdbc = new Jdbc(),
      connection;
  
  test.expect(1);
  
  jdbc.onInitialised(function initialiseEvent() {
    connection = jdbc.createConnection('jdbc:hsqldb:mem:test', undefined, function(err, connection) {
      test.ok(!err);
      
      jdbc.close();
      test.done();
    });
  });
};

exports.closeConnection = function(test){
  test.expect(2);
  
  var jdbc = new Jdbc(),
      connection;

  jdbc.onInitialised(function initialiseEvent() {
    connection = jdbc.createConnection('jdbc:hsqldb:mem:test', undefined, function(err, connection) {
      test.ok(!err);

      connection.close(function(err, connection) {
        test.ok(!err);
        jdbc.close();
        test.done();
      });
    });
  });
};

exports.metadata = function(test){
  test.expect(2);
  
  var jdbc = new Jdbc(),
      connection;

  jdbc.onInitialised(function initialiseEvent() {
    connection = jdbc.createConnection('jdbc:hsqldb:mem:test', undefined, function(err, connection) {
      connection.metadata(['allProceduresAreCallable', 'userName'], function(err, metadata) {
        test.strictEqual(metadata.allProceduresAreCallable, true);
        test.strictEqual(metadata.userName, 'SA');
        
        jdbc.close();
        test.done();
      })
    });
  });
};
