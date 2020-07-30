const axios = require('axios');

const ENDPOINT_PREFIX = 'http://localhost:8080/api/card';
const gameId = 1;

function calcEndPoint(subPath) {
  return `${ENDPOINT_PREFIX}/${subPath}`;
}

// function test() {
//   const endpoint = calcEndPoint('/randomcard');
//   axios.get(endpoint).then(function good(response) {
//     console.log('Recieved respose:', response.data, response.header, response.config);
//   }, function error(err) {
//     console.log(err.message, err.data, err.config);
//   });
// }

// function test2() {
//   const ids = [-1, 0, 1, 2, 3];
//   const promises = ids.map(v => calcEndPoint(`/jie-long/${gameId}/${v}`))
//     .map(v => axios.get(v));
//   console.log(promises);
//   const allPromises = Promise.all(promises);
//   console.log('All promises:', allPromises);
//   return allPromises;
// }

// function test3() {
//   const ids = [-1, 0, 1, 2, 3];
//   const promises = ids.map(v => calcEndPoint(`/jie-long/${gameId}/${v}`))
//   .map(v => axios.get(v));
//   const allPromises = Promise.all(promises);
//   console.log('Promises:', promises);
//   console.log('All promises:', allPromises);
//   promises.map(v => {
//     console.log('Promises:', v);
//   });
// }

function test4() {
  const playersEndpoint = calcEndPoint(`/jie-long/${gameId}/players`);
  console.log('Players endpoint:', playersEndpoint);
  axios.get(playersEndpoint
  ).then(response => {
    this.players = response.data;
    const playerIds = this.players.map(p => p.playerId);
    console.log('Player ID\'s:', playerIds);
    const x = [-1, ...playerIds].map(id => {
      const handpoint = calcEndPoint(`/jie-long/${gameId}/${id}`);
      return axios.get(handpoint);
    });
    console.log('X:', x);
    return x;
  // }).then(ids => {
  //   return ids.map(id => {
  //     const handpoint = calcEndPoint(`/jie-long/${gameId}/${id}`);
  //     return axios.get(handpoint);
  //   });
  }).then(promises => Promise.all(promises)
  ).then(responses => responses.map(r => r.data) 
  ).then(hands => {
    console.log('Hands:', hands);
  }).catch(err => {
    console.log(err.message, err.config);
  });

    //     return Promise.all(
    //       [-1, ...playerIds].map(id => {
    //         const playerHandEndpoint = calcEndPoint(`/jie-long/${this.gameId}/${id}`);
    //         console.log('fetching:', playerHandEndpoint);
    //         return axios.get(playerHandEndpoint);
    //       })
    //     );
    //   }).then(responses => {
    //     this.playerCards = responses.map(r => r.data);
    //     console.log('Player Cards:', this.playerCards);
    //   });
    // }
}

// test();
// const results = test2().then(responses => {
//   console.log('Responses:', responses.map(r => r.data));
// });
// test3();
test4();