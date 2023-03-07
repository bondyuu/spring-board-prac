<template>
  <nav>
    <router-link to="/">Home</router-link> |
    <router-link to="/signup">SignUp</router-link> |
    <router-link to="/login">Login</router-link> |
    <router-link v-if="store.getters.isLogin" to="/posts">PostList</router-link>
  </nav>
  <router-view/>
   
</template>

<script>
import { useStore } from 'vuex';

export default {
  setup() {
    const store = useStore();
    
    return { store };
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
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
