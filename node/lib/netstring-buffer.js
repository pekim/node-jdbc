var util = require('util'),
    events = require('events'),
    netstring = require('netstring');

var NetstringBuffer = function () {
  events.EventEmitter.call(this);
  
  this.buffer = undefined;
};

util.inherits(NetstringBuffer, events.EventEmitter);

NetstringBuffer.prototype.put = function (newData) {
  var newBuffer,
  netstringLength,
  remainingLength,
  payload;

  if (this.buffer) {
    newBuffer = new Buffer(this.buffer.length + newData.length);
    this.buffer.copy(newBuffer, 0, 0);
    newData.copy(newBuffer, this.buffer.length, 0);
    this.buffer = newBuffer;
  } else {
    this.buffer = newData;
  }
  
  netstringLength = netstring.nsLength(this.buffer);
  while (netstringLength > 0 && this.buffer.length >= netstringLength) {
    payload = netstring.nsPayload(this.buffer).toString('utf8');
    this.emit('payload', payload);

    remainingLength = this.buffer.length - netstringLength;
    newBuffer = new Buffer(remainingLength);
    this.buffer.copy(newBuffer, 0, netstringLength, netstringLength + remainingLength);
    this.buffer = newBuffer;
    
    netstringLength = netstring.nsLength(this.buffer);
  }
}

module.exports = NetstringBuffer;
