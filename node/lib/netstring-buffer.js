var util = require('util'),
    events = require('events');

var NetstringBuffer = function () {
  events.EventEmitter.call(this);
};

util.inherits(NetstringBuffer, events.EventEmitter);

NetstringBuffer.prototype.put = function (buffer) {
  this.emit('payload', 'abc');
}

module.exports = NetstringBuffer;
