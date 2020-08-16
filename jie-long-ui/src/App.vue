<template>
  <div id="app">
    <message></message>
    <div class="table">
        <jie-long-table :cards="playerCards[0]">
        </jie-long-table>
    </div>
    <div v-for="(cards, idx) in playerCards.slice(1)" :key="`playerCard-${idx}`">
      <poker-hand :seat="idx"
                  :cards="cards"
                  :tableCards="playerCards[0]"
                  v-on:playcard="handlePlayCard"
      >
      </poker-hand>
    </div>    
    Game Id: <input type="number" v-model="gameId"/>
    <!-- {{players}}
    <br/>
    {{playerCards}} -->


    <!-- <hr/>
    <poker-card v-for="(card, idx) in jieLongHand" :key="`jielong-${idx}`" :cardName="card.cardName"></poker-card> -->
  
    <!-- <br/>
    <input type="number" v-model="gameId"/>
    <input type="number" v-model="playerId"/>
    <button v-on:click="fetchJieLong()">Jie-Long</button>
    <hr/>
    
    <poker-card v-for="(card, idx) in deck" :key="idx" :cardName="card.cardName"></poker-card>
    <br/>
    <button v-on:click="fetchRandomDeck()">fetch</button>
    <hr/>
    <poker-card v-bind:cardName="cardName"/>
    <hr/>
    Card Name:
    <input type="text" v-model="cardName"/>
    <hr/> -->
  </div>
</template>

<script>
import JieLongTable from './components/JieLongTable.vue';
import PokerHand from './components/PokerHand.vue';
import Message from './components/Message.vue';
import axios from 'axios';

import util from './components/util.js';

export default {
  name: 'App',
  components: {
    JieLongTable,
    PokerHand,
    Message
  },

  data() {
    return {
      cardName: 'HA',
      gameId: 0,
      playerId: 0,
      jieLongHand: [],
      players: [],
      playerCards: [],

      deck: [
        {cardName: 'S5'}, 
        {cardName:'H4'}
      ],

      eventMessage: null
    };
  },
  
  methods: {
    fetchRandomDeck() {
      const self = this;
      const url = util.calcCardEndPoint('/randomdeck');
      console.log('about to fetch', url);
      axios.get(url)
      .then((response) => {
        console.log('fetch Random Deck', response.data);
        self.deck = response.data;
      }).catch(err => {
        console.log('got error', err);
      });
    },
    
    fetchJieLong() {
      const self = this;
      const url = util.calcCardEndPoint('/jie-long/' + self.gameId + '/' + self.playerId);
      axios.get(url).then(response => {
        console.log('fetch Jie Long', response);    
        self.jieLongHand = response.data;
      });
    },

    fetchGame() {
      const playersEndpoint = util.calcCardEndPoint(`/jie-long/${this.gameId}/players`);
      axios.get(playersEndpoint).then(response => {
        this.players = response.data;
        const playerIds = this.players.map(p => p.playerId);
        console.log('Player ID\'s:', playerIds);

        return Promise.all(
          [-1, ...playerIds].map(id => {
            const playerHandEndpoint = util.calcCardEndPoint(`/jie-long/${this.gameId}/${id}`);
            console.log('fetching:', playerHandEndpoint);
            return axios.get(playerHandEndpoint);
          })
        );
      }).then(responses => {
        this.playerCards = responses.map(r => r.data);
        console.log('Player Cards:', this.playerCards);
      });
    },

    connectSse(retryDelay) {
      const self = this;
      const endpoint = util.calcCardEndPoint("/jie-long/emitter");
      const evtsource = new EventSource(endpoint);
      evtsource.onmessage = (msg) => {
        console.log("connectSse received:", msg);
        self.eventMessage = msg.data;
      }

      evtsource.addEventListener('error', (e) => {
        console.log("error:", e);
        evtsource.close();
        if(retryDelay >= 0) {
          setTimeout(() => {
            self.connectSse(retryDelay);
          }, retryDelay);
        } else {
          console.log('Sse disconnected');
        }
      });
      evtsource.addEventListener('event', (e) => console.log("event:", e));
    },

    handlePlayCard(message) {
      const {seat, cardName} = message;
      console.log('played!', seat, cardName);
      const endpoint = util.calcCardEndPoint(`/jie-long/${this.gameId}/${seat}/${cardName}`)
      axios.post(endpoint).then(response => {
        console.log('it worked!', response.data);
        this.fetchGame();
      }, err => {
        console.log(err.message);
      });
    }
  },

  watch: {
    gameId() {
      this.fetchGame();
    }
  },

  mounted() {
    // console.log(axios);
    this.fetchGame();
    //this.connectSse(50);
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  }

.table {
    outline: 4px black;
    background-color: darkolivegreen;
    padding-top: 10px;
    padding-bottom: 4px;
}

.card-group {
  margin-right: -50px;
}
</style>
