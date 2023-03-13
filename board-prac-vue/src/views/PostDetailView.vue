<template>
    <h1>Detail of Post</h1>
    <div class="wrapper">
        <p>Title     : {{ this.post.title }}</p>
        <p>Author     : {{ this.email }}</p>
        <p>Content   : {{ this.post.content }}</p>
        <p>Heart Num : {{ this.post.heartNum }}</p>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'AdminUserDetail',
    mounted() {
        this.getPostDetail(this.id)
    },
    data() {
        return {
            id: this.$route.params.id,
            post: '',
            email: ''
        }
    },
    methods: {
        getPostDetail(id) {
            axios
            .get('http://localhost:8080/posts/'+ id +'/detail',
            {
                headers: {
                    'Authorization': this.$store.state.accessToken
                }
            })
            .then((res) => {
                console.log(res);
                this.post = res.data;
                this.email = res.data.user.email;
            })
        }
    }
}
</script>

<style scoped>
h1 {
    margin-left: 20%;
    margin-bottom: 15px;
}
.wrapper {
    width: 60%;
    margin-left: 20%;
}
.card {
    margin-bottom: 20px;
}
</style>
