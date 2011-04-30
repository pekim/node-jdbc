var util = require('util'),
    events = require('events'),
    Java = require('java'),
    connection = require('./connection'),
    classpath = __dirname + '/../../target/node-jdbc-0.0.1-SNAPSHOT-jar-with-dependencies.jar',
    Jdbc,
    jdbc;

Jdbc = function () {
  var self = this;

  events.EventEmitter.call(self);
  
  self.java = new Java({classpath: classpath});
  self.java.onInitialised(function initialiseEvent() {
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

Jdbc.prototype.close = function(callback) {
  this.java.shutdown();
};

Jdbc.prototype.createConnection = function(url, driverClassname, callback) {
  return new connection.Connection(this.java, url, driverClassname, callback);
}

//jdbc = new Jdbc();

//connection.jdbc(jdbc);
module.exports = Jdbc;
