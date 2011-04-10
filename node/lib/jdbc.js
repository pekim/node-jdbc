var util = require('util'),
    events = require('events'),
    nodeServer = require('./node-server'),
    javaServer = require('./java-server'),
    Connection = require('./connection'),
    Jdbc;

Jdbc = function () {
  var self = this;

  events.EventEmitter.call(self);
  
  javaServer.start({port: nodeServer.port()});

  nodeServer.on('initialised', function onMessage(message) {
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

Jdbc.prototype.Connection = Connection;

module.exports = new Jdbc();
