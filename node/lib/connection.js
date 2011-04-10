var util = require('util'),
    events = require('events'),
    javaServer = require('./java-server'),
    NetstringBuffer = require('./netstring-buffer'),
    net = require('net'),
    netstring = require('netstring'),
    Connection,
    jdbc;

Connection = function (url, connectedCallback) {
  var self = this;
  
  events.EventEmitter.call(self);
  
  jdbc.onInitialised(function() {
    var socket = net.createConnection(javaServer.port),
        buffer = new NetstringBuffer();

    socket.on('connect', function connect() {
      send({
        type: 'connect',
        url: url
      });
    });

    buffer.on('payload', function payload(payload) {
      var message = JSON.parse(payload);

      switch (message.type) {
      case 'connect':
        connectedCallback();
        break;
      default:
        self.emit('error', message);
        break;
      }
    });

    socket.on('data', function data(data) {
      buffer.put(data);
    });
    
    function send(message) {
      socket.write(netstring.nsWrite(JSON.stringify(message)));
    }
  });
};

util.inherits(Connection, events.EventEmitter);

exports.jdbc = function(value) {
  jdbc = value;
}

exports.Connection = Connection;
