<template>
    <div class="about">
      <h1>This is an post list page</h1>
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
  