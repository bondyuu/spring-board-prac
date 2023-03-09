<template>
    <h1>List of Users</h1>
    <div class="search-form">
        <b-nav-form>
            <b-form-input class="mr-sm-2" placeholder="Search" v-model="email"></b-form-input>
            <b-button variant="outline-success" class="my-2 my-sm-0" @click="getMain">Search</b-button>
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
