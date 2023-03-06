<template>
    <div class="list">
        <button @click="getBoards">dd</button>
    </div>
</template>
  
<script>
import axios from 'axios';

export default {
    name: 'PostList',
    data() {
    return {
        
    };
},
methods: {
    getBoards() {
        axios
        .get('http://localhost:8080/posts',
        {
            headers: {
                Authorization: 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTY3ODA5MTExM30.LOjK6QWvaj05qfBovO7a-oNjKm6BsIhWmRH_u-O382Q'
            }
        })
        .then((result) => {
            console.log(result);
        });
    }
}
}
</script>

  <style scoped>
  </style>