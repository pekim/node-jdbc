var net = require('net'),
    NetstringBuffer = require('./netstring-buffer'),
    util = require('util'),
    events = require('events');

var NodeServer = function () {
  var self = this;
  
  events.EventEmitter.call(self);
  
  self.server = net.createServer(function connected(socket) {
    var buffer = new NetstringBuffer();

    buffer.on('payload', function payload(payload) {
      var message = JSON.parse(payload);
      
      self.emit(message.type, message);
    });

    socket.on('data', function data(data) {
      buffer.put(data);
    });
  });
  
  self.server.listen();
  
  console.log('Node server initialised on port ' + self.port());
};

util.inherits(NodeServer, events.EventEmitter);

NodeServer.prototype.port = function () {
  return this.server.address().port;
};

module.exports = new NodeServer();
