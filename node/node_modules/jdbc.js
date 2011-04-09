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
    netstring = require('netstring'),
    server;

server = net.createServer(function(socket) {
  //socket.setEncoding('utf8');
  var buffer;

  socket.on('data', function(data) {
    var newBuffer,
        netstringLength,
        remainingLength,
        payload;
    
    console.log('data : ' + data);
    
    if (buffer) {
      newBuffer = new Buffer(buffer.length + data.length);
      buffer.copy(newBuffer, 0, 0);
      data.copy(newBuffer, buffer.length, 0);
      buffer = newBuffer;
    } else {
      buffer = data;
    }
    
    netstringLength = netstring.nsLength(buffer);
    while (netstringLength > 0 && buffer.length >= netstringLength) {
      payload = netstring.nsPayload(buffer).toString('utf8');
      console.log('payload : ' + payload);

      remainingLength = buffer.length - netstringLength;
      newBuffer = new Buffer(remainingLength);
      buffer.copy(newBuffer, 0, netstringLength, netstringLength + remainingLength);
      buffer = newBuffer;
      
      netstringLength = netstring.nsLength(buffer);
    }
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
