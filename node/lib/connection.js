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
      function(err, response) {
        if (!err) {
          self.connectionIdentifier = response.connectionIdentifier;
        }
        
        callback(err, self);
      }
  );
};

util.inherits(Connection, events.EventEmitter);

Connection.prototype.close = function (callback) {
  this.java.sendRequest('uk.co.pekim.nodejdbc.connection.close.CloseConnectionHandler',
      {connectionIdentifier: this.connectionIdentifier},
      function(err, response) {
         callback(err, this);
      }
  );
};

Connection.prototype.metadata = function (dataNames, callback) {
  this.java.sendRequest('uk.co.pekim.nodejdbc.metadata.MetadataHandler',
      {
        connectionIdentifier: this.connectionIdentifier,
        dataNames: dataNames
      },
      function(err, response) {
         callback(err, response.data);
      }
  );
};

exports.jdbc = function(value) {
  jdbc = value;
}

exports.Connection = Connection;
