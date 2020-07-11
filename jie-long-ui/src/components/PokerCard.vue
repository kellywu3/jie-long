<template>
    <div class="card" v-bind:style="updatePosition">
    </div>
</template>

<script>
export default {
    name: 'PokerCard',

    props: {
        cardName: {
            type: String,
            default: 'HA'
        }
    },

    data() {
        return {
            offsetX: -1,
            offsetY: -1,
            rankNum: 0,
            suitNum: 0
        };
    },

    computed: {
        updatePosition() {
            return {
                'background-position': `${this.offsetX}px ${this.offsetY}px`
            };
        }
    },

    methods: {
        updateRankNum() {
            let rank = this.rank.toLowerCase();
            switch(rank) {
                case 'a':
                case 'ace':
                    this.rankNum = 0;
                    break;
                case 't':
                case 'ten':
                    this.rankNum = 9;
                    break;
                case 'j':
                case 'jack':
                    this.rankNum = 10;
                    break;
                case 'q':
                case 'queen':
                    this.rankNum = 11;
                    break;
                case 'k':
                case 'king':
                    this.rankNum = 12;
                    break;
                default:
                    this.rankNum = rank - 1;
            }
        },

        updateSuitNum() {
            let suit = this.suit.toLowerCase();
            switch(suit) {
                case 'h':
                case 'heart':
                    this.suitNum = 0;
                    break;
                case 's':
                case 'spade':
                    this.suitNum = 1;
                    break;
                case 'd':
                case'diamond':
                    this.suitNum = 2;
                    break;
                case 'c':
                case 'clover':
                case 'club':
                    this.suitNum = 3;
                    break;
                case 'b':
                case 'back':
                    this.suitNum = 4;
                    this.rankNum = 0;
                    break;
                case 'j':
                case 'joker':
                    this.suitNum = 4;
                    this.rankNum = 1;
                    break;
                default:
                    this.suitNum = suit - 1;
            }
        },

        recalculateOffset() {
            this.offsetX = - this.rankNum * 98.46 - 1;
            this.offsetY = - this.suitNum * 136.4 - 1;
        }
    },

    watch: {
        rank() {
            console.log("rank changed", this.rank);
            this.updateRankNum();
            this.recalculateOffset();
        }

        , suit() {
            console.log("suit changed", this.suit);
            this.updateSuitNum();
            this.recalculateOffset();
        }
    },

    mounted() {
        this.updateRankNum();
        this.updateSuitNum();
        this.recalculateOffset();
    }
}
</script>

<style scoped>
.card {
    display: inline-block;
    width: 96px;
    height: 132px;
    background-image: url('/images/card-faces.png');
}
</style>