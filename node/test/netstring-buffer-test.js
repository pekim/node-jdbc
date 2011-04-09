var NetstringBuffer = require('../lib/netstring-buffer');

exports.oneString = function(test){
  var buffer = new NetstringBuffer();
  
  buffer.on('payload', function(payload) {
    test.strictEqual(payload, 'abc');
    test.done();
  });
  
  test.expect(1);
    
  buffer.put(new Buffer('3:abc,'));
};
