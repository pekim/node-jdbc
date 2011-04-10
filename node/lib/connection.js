var util = require('util'),
    events = require('events'),
    javaServer = require('./java-server'),
    net = require('net'),
    netstring = require('netstring'),
    Connection,
    jdbc;

Connection = function (url, connectedCallback) {
  var self = this;
  
  events.EventEmitter.call(self);
  
  jdbc.onInitialised(function() {
    var socket = net.createConnection(javaServer.port);

    socket.on('connect', function connect() {
      send({url: url});
    });

    socket.on('data', function data(data) {
      connectedCallback();
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
