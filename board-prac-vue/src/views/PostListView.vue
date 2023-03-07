<template>
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
</style>