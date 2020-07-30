<template>
    <div class="hand">
      <span v-for="(card, idx) in cards" :key="`${seat}-${idx}`"
        v-on:click="$emit('playcard', {seat, cardName:card.cardName})"
      >
        <PokerCard :cardName="card.cardName"></PokerCard>
      </span>
    </div>
</template>

<script>
import PokerCard from './PokerCard.vue';

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
            type: Array
        }
    }
}
</script>

<style scoped>
.hand {
  outline: 1px dotted blue;
  padding: 5px;
}
</style>
