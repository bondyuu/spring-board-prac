<template>
  <div style="margin-bottom: 40px;">
    <p class="bondyuu">BONDYUU</p>

    <nav>
      <router-link to="/">Home</router-link> 
      | <router-link to="/posts">PostList</router-link>
      | <router-link to="/mypage">My Page</router-link>
      | <router-link to="/mypage">My Page</router-link>
      | <router-link to="/mypage">My Page</router-link>
      | <router-link to="/mypage">My Page</router-link>

    </nav>

    <div style="display: inline-block">
        <buttonIf v-if="store.getters.isLogin">
          <b-button variant="outline-primary" @click="logout">Logout</b-button>
        </buttonIf>
        <buttonIf v-else>
          <b-button variant="outline-primary" href="/signup">Sign Up</b-button>
          <b-button variant="outline-primary" href="/login" style="margin-left: 10px;">Login</b-button>
        </buttonIf>
    </div>
  </div> 

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
  color: #2c3e50;
}

.bondyuu {
  margin-left: 3%;
  display: inline-block; 
  font-weight: bold;
  font-size: 35px;
  width: 10%;
}

nav {
  padding: 30px;
  padding: 30px;
  display: inline-block;
  width: 70%;
  margin-left: 3%;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
  font-size: 25px;
  margin-right: 20px;
  margin-left: 20px;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
