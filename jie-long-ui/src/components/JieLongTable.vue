<template>
    <div>
        <div class="cards" v-for="(cards, i) in sortedDeck" :key="`table-${i}`">
            <PokerCard v-for="(card, j) in cards" :key="`suit-${i}-${j}`" :cardName="card.cardName"></PokerCard>
        </div>
    </div>   
</template>

<script>
import PokerCard from './PokerCard.vue';
export default {
    name: 'JieLongTable',

    components: {
        PokerCard
    },

    props: {
        cards: {
            type: Array,
            default: () => {
                return [];
            }
        }
    },

    computed: {
        sortedDeck() {
            // const suits = ['H', 'S', 'D', 'C'];
            // const results = [];
            // for(let i = 0; i<suits.length; i++) {
            //     const s = suits[i];
            //     const cardsOfSuit = [];
            //     results.push(cardOfSuit);
            //     for (card in this.cards) {
            //         if (card.cardName.charAt[0] === s) {
            //             cardsOfSuit.push(card);
            //         }
            //     }
            // }
            // return results;

            return ['H', 'S', 'D', 'C'].map(suit => {
                // console.log('for each suit', suit, this.cards);
                return this.cards.filter(card => card.cardName.charAt(0) === suit);
            });
        }
    },

    methods: {
    }
}
</script>

<style scoped>
.cards {
    padding-bottom: 0px;
    margin-bottom: 0px;
}
</style>
