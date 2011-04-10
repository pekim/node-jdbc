var util = require('util'),
    events = require('events'),
    nodeServer = require('./node-server'),
    javaServer = require('./java-server'),
    connection = require('./connection'),
    Jdbc,
    jdbc;

Jdbc = function () {
  var self = this;

  events.EventEmitter.call(self);
  
  javaServer.start({port: nodeServer.port()});

  nodeServer.on('initialised', function onMessage(message) {
    javaServer.port = message.port;
    self.initialised = true;

    self.emit('initialised');
  });
};

util.inherits(Jdbc, events.EventEmitter);

Jdbc.prototype.onInitialised = function(callback) {
    if (this.initialised) {
      callback();
    } else {
      this.on('initialised', callback);
    }
};

Jdbc.prototype.sendRequest = function(request, callback) {
};

Jdbc.prototype.Connection = connection.Connection;

jdbc = new Jdbc();

connection.jdbc(jdbc);
module.exports = jdbc;
