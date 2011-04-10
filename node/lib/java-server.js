var spawn = require('child_process').spawn,
    util = require('util'),
    events = require('events'),

    jar = '../target/node-jdbc-0.0.1-SNAPSHOT-jar-with-dependencies.jar';

var Server = function () {
  var self = this;
  
  events.EventEmitter.call(self);
};

util.inherits(Server, events.EventEmitter);

Server.prototype.start = function (nodeConfiguration) {
  var javaProcess,
      configuration = {
        node: nodeConfiguration,
        jdbc: {}
      };

  javaProcess = spawn('java', ['-jar', jar, JSON.stringify(configuration)]);
  logOutput(javaProcess);
  logEvents(javaProcess);
};

function logOutput(javaProcess) {
  javaProcess.stdout.on('data', function (data) {
    process.stdout.write(data);
  });

  javaProcess.stderr.on('data', function (data) {
    process.stderr.write(data);
  });
}

function logEvents(javaProcess) {
  javaProcess.on('exit', function (code) {
    console.log('exited : ' + code);
  });
}

module.exports = new Server();
