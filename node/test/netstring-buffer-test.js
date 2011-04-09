var NetstringBuffer = require('../lib/netstring-buffer');

exports.emptyString = function(test){
  var buffer = new NetstringBuffer();
  
  buffer.on('payload', function(payload) {
    test.strictEqual(payload, '');
    test.done();
  });
  
  test.expect(1);
    
  buffer.put(new Buffer('0:,'));
};

exports.partialLength = function(test){
  var buffer = new NetstringBuffer();
  
  buffer.on('payload', function(payload) {
    throw Error('not expected');
  });
  
  test.expect(0);
    
  buffer.put(new Buffer('1'));
  test.done();
};

exports.incompleteString = function(test){
  var buffer = new NetstringBuffer();
  
  buffer.on('payload', function(payload) {
    throw Error('not expected');
  });
  
  test.expect(0);
    
  buffer.put(new Buffer('2:a'));
  test.done();
};

exports.oneString = function(test){
  var buffer = new NetstringBuffer();
  
  buffer.on('payload', function(payload) {
    test.strictEqual(payload, 'abc');
    test.done();
  });
  
  test.expect(1);
    
  buffer.put(new Buffer('3:abc,'));
};

exports.twoStrings = function(test){
  var buffer = new NetstringBuffer(),
      expected = ['abc', 'xyz'],
      payloads = 0;
  
  buffer.on('payload', function(payload) {
    test.strictEqual(payload, expected[payloads++]);
    
    if (payloads == expected.length) {
      test.done();
    }
  });
  
  test.expect(2);
    
  buffer.put(new Buffer('3:abc,3:xyz,'));
};

exports.oneStringSplitAcrossTwoPuts = function(test){
  var buffer = new NetstringBuffer();
  
  buffer.on('payload', function(payload) {
    test.strictEqual(payload, 'abc');
    test.done();
  });
  
  test.expect(1);
    
  buffer.put(new Buffer('3:a'));
  buffer.put(new Buffer('bc,'));
};

exports.twoStringsSplitAcrossThreePuts = function(test){
  var buffer = new NetstringBuffer(),
  expected = ['abc', 'xyz'],
  payloads = 0;

  buffer.on('payload', function(payload) {
  test.strictEqual(payload, expected[payloads++]);
  
  if (payloads == expected.length) {
    test.done();
  }
  });
  
  test.expect(2);
  
  buffer.put(new Buffer('3:a'));
  buffer.put(new Buffer('bc,3:xyz'));
  buffer.put(new Buffer(','));
};
