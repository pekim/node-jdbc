var util = require('util'),
    events = require('events'),
    Connection;

Connection = function (java, url, driverClassname, callback) {
  var self = this;
  
  events.EventEmitter.call(self);
  
  this.java = java;

  java.sendRequest('uk.co.pekim.nodejdbc.connection.create.CreateConnectionHandler',
      {
        url: url,
        driverClassname: driverClassname
      },
      function(response) {
        if (response.error) {
          callback(response, self);
        } else {
          self.connectionIdentifier = response.connectionIdentifier;
          callback(undefined, self);
        }
      }
  );
};

util.inherits(Connection, events.EventEmitter);

Connection.prototype.close = function (callback) {
  this.java.sendRequest('uk.co.pekim.nodejdbc.connection.close.CloseConnectionHandler',
      {connectionIdentifier: this.connectionIdentifier},
      function(response) {
        if (response.error) {
          callback(response, this);
        } else {
          self.connectionIdentifier = response.connectionIdentifier;
          callback(undefined, this);
        }
      }
  );
};

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
