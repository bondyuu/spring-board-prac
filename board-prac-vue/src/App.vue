<template>
  <nav>
    <router-link to="/">Home</router-link> |
    <router-link v-if="store.getters.isLogin" to="/posts">PostList</router-link>
  </nav>
  <b-button variant="outline-primary" href="/signup" v-if="!store.getters.isLogin">Sign Up</b-button>
  <b-button variant="outline-primary" href="/login" v-if="!store.getters.isLogin">Login</b-button>
  <b-button variant="outline-primary" @click="logout" v-if="store.getters.isLogin">Logout</b-button>
  <router-view/>
  
</template>

<script>
import { useStore } from 'vuex';

export default {
  setup() {
    const store = useStore();
    const setToken = (accessToken, refreshToken) => {
            store.commit('setAccessToken', accessToken),
            store.commit('setRefreshToken', refreshToken)
        };
    return { store, setToken };
  },
  methods: {
    logout() {
      this.setToken(null, null);
    }
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
