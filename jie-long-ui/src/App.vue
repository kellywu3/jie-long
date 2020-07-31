<template>
  <div id="app">
    <div class="table">
        <jie-long-table :cards="playerCards[0]">
        </jie-long-table>
    </div>
    <div v-for="(cards, idx) in playerCards.slice(1)" :key="`playerCard-${idx}`">
      <poker-hand :seat="idx" :cards="cards" v-on:playcard="handlePlayCard">
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
import axios from 'axios';

const ENDPOINT_PREFIX = 'http://localhost:8081/api/card';

function calcEndPoint(subPath) {
  return `${ENDPOINT_PREFIX}/${subPath}`;
}

export default {
  name: 'App',
  components: {
    JieLongTable,
    PokerHand
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
      ]
    };
  },
  
  methods: {
    fetchRandomDeck() {
      const self = this;
      const url = calcEndPoint('/randomdeck');
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
      const url = calcEndPoint('/jie-long/' + self.gameId + '/' + self.playerId);
      axios.get(url).then(response => {
        console.log('fetch Jie Long', response);    
        self.jieLongHand = response.data;
      });
    },

    fetchGame() {
      const playersEndpoint = calcEndPoint(`/jie-long/${this.gameId}/players`);
      axios.get(playersEndpoint).then(response => {
        this.players = response.data;
        const playerIds = this.players.map(p => p.playerId);
        console.log('Player ID\'s:', playerIds);

        return Promise.all(
          [-1, ...playerIds].map(id => {
            const playerHandEndpoint = calcEndPoint(`/jie-long/${this.gameId}/${id}`);
            console.log('fetching:', playerHandEndpoint);
            return axios.get(playerHandEndpoint);
          })
        );
      }).then(responses => {
        this.playerCards = responses.map(r => r.data);
        console.log('Player Cards:', this.playerCards);
      });
    },

    handlePlayCard(message) {
      const {seat, cardName} = message;
      console.log('played!', seat, cardName);
      const endpoint = calcEndPoint(`/jie-long/${this.gameId}/${seat}/${cardName}`)
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
