<template>
  <div id="app">
    <poker-card v-for="(card, idx) in deck" :key="idx" :suit="card.suit" :rank="card.rank.toString()"></poker-card>
    <br/>
    <button v-on:click="fetchRandomDeck()">fetch</button>
    <hr/>
    <PokerCard v-bind:suit="suit" v-bind:rank="rank"/>
    <hr/>
    rank:
    <input type="text" v-model="rank"/>
    <br/>
    suit:
    <input type="text" v-model="suit"/>

    <hr/>
    <!--
    <PokerCard class="card-group" 
      v-for="(card, idx) in cards" 
      v-bind:style="{'z-index': 100-idx}"
      v-bind:key="idx" v-bind:suit="card.suit" v-bind:rank="card.rank"
    />
    -->
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
import axios from 'axios';

export default {
  name: 'App',
  components: {
      PokerCard
  },

  data() {
    return {
      rank: 'ace',
      suit: 'heart',

      deck: [
        {suit: 'spade', rank: '5'}, 
        {suit:'heart', rank: '4'}
      ],

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
