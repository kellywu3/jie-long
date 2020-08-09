<template>
    <div>
        <h1>Message {{msg}}</h1>
    </div>
</template>

<script>
import util from './util.js';

export default {
    name: 'Message',

    data() {
        return {
            msg: null
        };
    },

    methods: {
        connectSse() {
            const source = util.establishSse();
            source.onmessage = msg => {
                console.log("Handle Sse message:", msg);
                this.msg = msg.data;
            };

            source.addEventListener('error', (e) => {
                console.log("Sse error: ", e);
                source.close();
            });
        }
    },

    mounted() {
        this.connectSse();
    }
}
</script>

<style scoped>

</style>