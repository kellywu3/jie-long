<template>
  <div id="app">
    <poker-card v-for="(card, idx) in deck" :key="idx" :cardName="card.cardName"></poker-card>
    <br/>
    <button v-on:click="fetchRandomDeck()">fetch</button>
    <hr/>
    <poker-card v-bind:cardName="cardName"/>
    <hr/>
    Card Name:
    <input type="text" v-model="cardName"/>
    <hr/>
  </div>
</template>

<script>
import PokerCard from './components/PokerCard.vue';
import axios from 'axios';

export default {
  name: 'App',
  components: {
      PokerCard
  },

  data() {
    return {
      cardName: 'HA',

      deck: [
        {cardName: 'S5'}, 
        {cardName:'H4'}
      ]
    };
  },

  methods: {
    fetchRandomDeck() {
      const self = this;
      const url = 'http://localhost:8081/api/card/randomdeck';
      console.log('about to fetch', url);
      axios.get(url).then((response) => {
        console.log('it works!', response.data);
        self.deck = response.data;
      }).catch(err => {
        console.log('got error', err);
      });
    }
  },

  mounted() {
    // console.log(axios);
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

.card-group-two {

}
</style>
