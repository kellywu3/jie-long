<template>
  <div id="app">
    <PokerCard v-bind:suit="suit" v-bind:rank="rank"/>
    <hr/>
    rank:
    <input type="text" v-model="rank"/>
    <br/>
    suit:
    <input type="text" v-model="suit"/>

    <hr/>
    <PokerCard class="card-group" 
      v-for="(card, idx) in cards" 
      v-bind:style="{'z-index': 100-idx}"
      v-bind:key="idx" v-bind:suit="card.suit" v-bind:rank="card.rank"
    />

    <hr/>
        <ul>
          <li v-for="(card, idx) in cards" v-bind:key="idx">
            <PokerCard v-bind:suit="card.suit" v-bind:rank="card.rank"/>
            <br/>
             <!-- {{ card.suit }} - {{ card.rank }} -->
          </li>
        </ul>
  </div>
</template>

<script>
import PokerCard from './components/PokerCard.vue';
// import axios from 'axios';

export default {
  name: 'App',
  components: {
      PokerCard
  },

  data() {
    return {
      rank: 'ace',
      suit: 'heart',

      cards: [
        {suit: 'spade', rank: '5'},
        {suit: 'heart', rank: '4'},
        {suit: 'diamond', rank: '9'},
        {suit: 'joker'},
        {suit: 'back'}
      ]
    };
  },

  methods: {
    fetchPlayerHands() {
      const url = 'https://cdnjs.cloudflare.com/ajax/libs/axios/0.19.2/axios.min.js';
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
