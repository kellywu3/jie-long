<template>
    <div class="card" v-bind:class="{playable}" v-bind:style="dynamicPosition">
        
    </div>
</template>

<script>
export default {
    name: 'PokerCard',

    props: {
        cardName: {
            type: String,
            default: 'HA'
        },

        playable: {
            type: Boolean,
            default: false
        },

        bugger: {
            type: String,
            default: 'bug'

        }
    },

    data() {
        return {
            
        };
    },

    computed: {
        dynamicPosition() {
            const offsetX = - this.rankNum * 98.46 - 1;
            const offsetY = - this.suitNum * 136.4 - 1;
            return {
                'background-position': `${offsetX}px ${offsetY}px`
            };
        },

        rankNum() {
            return this.charToRankNum(this.cardName.charAt(1));
        },

        suitNum() {
            return this.charToSuitNum(this.cardName.charAt(0));
        }
    },

    methods: {
        charToRankNum(c = 'a') {
            let rank = c.toLowerCase();
            switch(rank) {
                case 'a':
                case 'ace':
                    return 0;
                case 't':
                    return 9;
                case 'j':
                    return 10;
                case 'q':
                    return 11;
                case 'k':
                    return 12;
                default:
                    return rank - 1;
            }
        },

        charToSuitNum(c = 'h') {
            let suit = c.toLowerCase();
            switch(suit) {
                case 'h':
                    return 0;
                case 's':
                    return 1;
                case 'd':
                    return 2;
                case 'c':
                    return 3;
                case 'z':
                    return 4;
                default:
                    return suit - 1;
            }
        }

    }
}
</script>

<style scoped>
.card {
    display: inline-block;
    width: 96px;
    height: 132px;
    background-image: url('/images/card-faces.png');
    border: 1px solid transparent;
    padding: 2px;
}

.card:hover {
    border: 1px solid transparent;
    padding: 2px;
    background-color: red;
}

.playable:hover {
    border: 1px solid transparent;
    padding: 2px;
    background-color: limegreen;
}
</style>