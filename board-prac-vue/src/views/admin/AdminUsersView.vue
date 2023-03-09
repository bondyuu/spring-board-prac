<template>
    <h1>List of Users</h1>
    <div class="tbl-wrapper">
    <b-table striped hover :items="list"></b-table>
  </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'AdminMainView',
    mounted() {
        this.getMain()
    },
    data() {
        return {
            userNum: '',
            postNum: '',
            email: '',
            list: []
        }
    },
    methods: {
        getMain() {
            if (this.$store.state.role === '관리자') {
                axios
                .get('http://localhost:8080/admin/users?email='+this.email,
                {
                    headers: {
                        'Authorization': this.$store.state.accessToken
                    }
                })
                .then((res) => {
                    console.log(res);
                    this.list = res.data;
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
}
.tbl-wrapper {
    width: 60%;
    margin-left: 20%;
}
</style>
