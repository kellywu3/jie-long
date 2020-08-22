<template>
  <div class="JieLong">
    <h1>Jie Long Page</h1>
    <HelloWorld></HelloWorld>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'JieLong',
  components: {
    HelloWorld
  }
}
</script>

<style scoped>
.JieLong {
  border: 10px double blue;
}
</style>
