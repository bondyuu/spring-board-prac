<template>
  <div class="search-form">
    <b-nav-form>
      <b-form-input class="mr-sm-2" placeholder="Search" v-model="searchTitle"></b-form-input>
      <b-button variant="outline-success" class="my-2 my-sm-0" @click="getBoards">Search</b-button>
      <b-button variant="outline-success" class="bt-write" href="/write-post">New Post</b-button>
    </b-nav-form>
  </div>
  <div class="postlist">
    <div class="wrapper" v-for="(obj, idx) in list" :key="idx">
      <PostCard :item="obj"/>
    </div>
  </div>
</template>

<script>
import PostCard from '@/components/PostCard.vue'
import axios from 'axios'

export default {
  name: 'PostListView',
  components: {
    PostCard
  },
  data() {
    return {
        list: [],
        searchTitle: '',
    };
  },
  mounted() {
      this.getBoards()
  },
  methods: {
    getBoards() {
      console.log(this.searchTitle);
      axios
      .get('http://localhost:8080/posts?title='+this.searchTitle,
      {
        headers: {
            'Authorization': this.$store.state.accessToken
        }
      })
      .then((res) => {
        console.log(res);
        this.list = res.data.content;
      })
      .catch((err) => {
        console.log(err);
      });
    },
  }
}
</script>

<style scoped>
.postlist{
  text-align: center;
}
.wrapper {
  margin-bottom: 20px; 
  margin-right: 3%;
  width: 32%;
  display: inline-block;
}
.mr-sm-2 {
  width: 30%;
  margin-left: 15%;
  margin-right: 2%;
  margin-bottom: 3%;
}
.my-sm-0 {
  height: 36px;
  margin-right: 24%;
}
.bt-write {
  height: 36px;
}
</style>