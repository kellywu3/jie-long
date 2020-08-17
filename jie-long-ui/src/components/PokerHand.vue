<template>
    <div class="hand">
      <span v-for="(card, idx) in cards" :key="`${seat}-${idx}`"
        v-on:click="handleClick(card.cardName)"
      >
        <PokerCard :cardName="card.cardName"
        :playable="isPlayable(card.cardName)"
        :bugger="`${seat}-${idx}`"
        ></PokerCard>
      </span>
    </div>
</template>

<script>
import PokerCard from './PokerCard.vue';
import util from './util.js';

export default {
    name: 'PokerHand',
    
    components: {
        PokerCard
        },

    props: {
        seat: {
            type: Number
        },
        cards: {
            type: Array,
            default: () => []
        },
        tableCards: {
            type: Array,
            default: () => []
        }
    },

    methods: {
        isPlayable(cardName) {
            const rank = cardName.charAt(1);
            if(rank === '7') {
                return true;
            }

            let num = util.rankToNum(rank);
            if(num > 6) {
                num--;
            } else if(num < 6) {
                num++;
            }

            const suit = cardName.charAt(0);
            const cardCheck = `${suit}${util.numToRank(num)}`;
            const result = this.tableCards.some((rec) => rec.cardName === cardCheck);
            return result;
        },

        handleClick(cardName) {
            if(this.isPlayable(cardName)) {
                console.log("Emitting event");
                this.$emit('playcard', {seat: this.seat, cardName});
            } else {
                console.log("You can't play that :(");
            }
        }
    }
}
</script>

<style scoped>
.hand {
  outline: 1px;
  padding: 5px;
}
</style>
