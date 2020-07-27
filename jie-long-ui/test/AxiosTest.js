const axios = require('axios');

const ENDPOINT_PREFIX = 'http://localhost:8080/api/card';

function calcEndPoint(subPath) {
  return `${ENDPOINT_PREFIX}/${subPath}`;
}

function test() {
  const endpoint = calcEndPoint('/randomcard');
  axios.get(endpoint).then(function abc() {
    console.log('Recieved respose:');
  }, function error(err) {
    console.log(err);
  });
}

test();