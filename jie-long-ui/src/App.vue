<template>
  <div id="app">
    Game Id: <input type="number" v-model="gameId"/>
    <hr/>
    <div v-for="(cards, idx) in playerCards" :key="idx">
      <PokerCard v-for="(c, i) in cards" :key="`${idx}-${i}`"
      :cardName="c.cardName"
      ></PokerCard>
    </div>

    {{players}}
    <br/>
    {{playerCards}}

    <hr/>
    <poker-card v-for="(card, idx) in jieLongHand" :key="`jielong-${idx}`" :cardName="card.cardName"></poker-card>
  
    
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
import PokerCard from './components/PokerCard.vue';
import axios from 'axios';

const ENDPOINT_PREFIX = 'http://localhost:8081/api/card';

function calcEndPoint(subPath) {
  return `${ENDPOINT_PREFIX}/${subPath}`;
}

export default {
  name: 'App',
  components: {
      PokerCard
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
  margin-top: 60px;
}

.card-group {
  margin-right: -50px;
}
</style>
