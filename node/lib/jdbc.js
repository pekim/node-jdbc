var spawn = require('child_process').spawn,
    jar = '../target/node-jdbc-0.0.1-SNAPSHOT-jar-with-dependencies.jar',
    configuration = {
      node: {
        port: 0
      },
      jdbc: {
        
      }
    },
    net = require('net'),
    NetstringBuffer = require('../lib/netstring-buffer'),
    server,
    serverPort;

server = net.createServer(function connected(socket) {
  var buffer = new NetstringBuffer();

  buffer.on('payload', function payload(payload) {
    var message = JSON.parse(payload);
    
    switch (message.type) {
    case 'initialised':
      serverInitialised(message);
      break;
    default:
      console.error('Unrecognised message type from server : ' + message.type);
    }
  });

  socket.on('data', function data(data) {
    buffer.put(data);
  });
});
server.listen();

configuration.node.port = server.address().port;

javaProcess = spawn('java', ['-jar', jar, JSON.stringify(configuration)]);

javaProcess.stdout.on('data', function (data) {
  process.stdout.write(data);
});

javaProcess.stderr.on('data', function (data) {
  process.stderr.write(data);
});

javaProcess.on('exit', function (code) {
  console.log('exited : ' + code);
});

function serverInitialised(message) {
  serverPort = message.port;

  console.log('Server initialised on port ' + serverPort);
}

