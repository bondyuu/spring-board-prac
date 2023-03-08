<template>
  <div class="search-form">
    <b-nav-form>
      <b-form-input class="mr-sm-2" placeholder="Search"></b-form-input>
      <b-button variant="outline-success" class="my-2 my-sm-0" type="submit">Search</b-button>
      <b-button variant="outline-success" class="bt-write" href="/write-post">New Post</b-button>
    </b-nav-form>
  </div>
  <div class="postlist">
    <PostList />
  </div>
</template>

<script>
import PostList from '@/components/PostList.vue'
export default {
  name: 'PostListView',
  components: {
    PostList
  }
}
</script>

<style scoped>
.postlist {
  text-align: center;
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