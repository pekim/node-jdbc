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

exports.metadataSimple = function(test){
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

exports.metadataResultSet = function(test){
  test.expect(4);
  
  var jdbc = new Jdbc(),
      connection;

  jdbc.onInitialised(function initialiseEvent() {
    connection = jdbc.createConnection('jdbc:hsqldb:mem:test', undefined, function(err, connection) {
      connection.metadata(['catalogs'], function(err, metadata) {
        test.strictEqual(Object.keys(metadata.catalogs.columns).length, 1);
        test.strictEqual(metadata.catalogs.columns.CATALOG_NAME.type, 'VARCHAR');

        test.strictEqual(metadata.catalogs.rows.length, 1);
        test.strictEqual(metadata.catalogs.rows[0].CATALOG_NAME, 'PUBLIC');

        jdbc.close();
        test.done();
      })
    });
  });
};
