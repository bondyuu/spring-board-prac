<template>
    <h1>List of Posts</h1>
    <div class="search-form">
        <b-nav-form>
            <b-form-input class="mr-sm-2" placeholder="Search" v-model="title"></b-form-input>
            <b-button variant="outline-success" class="my-2 my-sm-0" @click="getPosts">Search</b-button>
        </b-nav-form>
    </div>
    <div class="tbl-wrapper">
    <b-table striped hover :items="list"></b-table>
  </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'AdminMainView',
    mounted() {
        this.getPosts()
    },
    data() {
        return {
            title: '',
            list: []
        }
    },
    methods: {
        getPosts() {
            if (this.$store.state.role === '관리자') {
                axios
                .get('http://localhost:8080/admin/posts?title='+this.title,
                {
                    headers: {
                        'Authorization': this.$store.state.accessToken
                    }
                })
                .then((res) => {
                    console.log(res);
                    this.list = res.data.content;
                })
            } else {
                this.$router.push('/');
            }
        }
    },
}
</script>

<style scoped>
h1 {
    margin-left: 20%;
    margin-bottom: 15px;
}
.search-form {
    width: 60%;
    margin-left: 20%;
    margin-bottom: 15px;
}
.mr-sm-2 {
    width: 30%;
    margin-right: 2%;

}
.tbl-wrapper {
    width: 60%;
    margin-left: 20%;
}
</style>
