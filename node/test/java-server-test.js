var server = require('../lib/java-server'),
    net = require('net'),
    netstring = require('netstring');

exports.start = function(test) {
  var nodeServer;

  nodeServer = net.createServer(function connected(socket) {
    socket.on('data', function data(data) {
      test.notStrictEqual(data.toString('utf8').indexOf('initialised'), -1);
      
      test.done();
    });
  });
  
  nodeServer.listen();
  
  server.start({port: nodeServer.address().port});
};
