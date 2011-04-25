var util = require('util'),
    events = require('events'),
    Connection;

Connection = function (java, url, driverClassname, callback) {
  var self = this;
  
  events.EventEmitter.call(self);

  java.sendRequest('uk.co.pekim.nodejdbc.connection.create.CreateConnectionHandler',
      {
        url: url,
        driverClassname: driverClassname
      },
      function(response) {
        callback(response);
      }
  );
};

util.inherits(Connection, events.EventEmitter);

Connection.prototype.metadata = function (requiredMetadata, callback) {
  this.send({
    type: 'metadata',
    requiredMetadata: requiredMetadata
  });
};

exports.jdbc = function(value) {
  jdbc = value;
}

exports.Connection = Connection;
