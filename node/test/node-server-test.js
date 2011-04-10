var server = require('../lib/node-server'),
    net = require('net'),
    netstring = require('netstring');

exports.port = function(test) {
  test.ok(server.port() > 0);

  test.done();
};

exports.message = function(test) {
  var socket;

  test.expect(1);
  
  server.on('initialised', function onMessage(message) {
    test.strictEqual(message.type, 'initialised');
    
    test.done();
  });
  
  socket = net.createConnection(server.port());
  socket.on('connect', function connect() {
    socket.write(netstring.nsWrite(JSON.stringify({type: 'initialised'})));
  });
};
