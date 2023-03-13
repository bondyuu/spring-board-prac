<template>
    <h1>Detail of User</h1>
    <div class="wrapper">
        <b-tabs content-class="mt-3">
            <b-tab title="Infomation" active>
                <p>Email  : {{ this.user.email }}</p>
                <p>Role   : {{ this.user.role }}</p>
                <p>Status : {{ this.user.status }}</p>
            </b-tab>
            <b-tab title="Posts that this user wrote">
                <div v-for="(obj, idx) in postList" :key="idx">
                    <b-card >
                        <b-card-title class="title">
                            {{ obj.title }}
                        </b-card-title>
                        <b-card-text class="content">
                            {{ obj.content }}
                        </b-card-text>
                        <b-card-text class="small text-muted">{{ obj.heartNum }}</b-card-text>
                    </b-card>
                </div>
            </b-tab>
            <b-tab title="Posts that this user likes" >
                <div v-for="(obj, idx) in likeList" :key="idx">
                    <b-card >
                        <b-card-title class="title">
                            {{ obj.title }}
                        </b-card-title>
                        <b-card-text class="content">
                            {{ obj.content }}
                        </b-card-text>
                        <b-card-text class="small text-muted">{{ obj.heartNum }}</b-card-text>
                    </b-card>
                </div>
            </b-tab>
        </b-tabs>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'AdminUserDetail',
    mounted() {
        this.getUserDetail(this.id)
    },
    data() {
        return {
            user: '',
            postList: [],
            likeList: [],
            id: this.$route.params.id
        }
    },
    methods: {
        getUserDetail(id) {
            if (this.$store.state.role === '관리자') {
                axios
                .get('http://localhost:8080/admin/users/'+ id,
                {
                    headers: {
                        'Authorization': this.$store.state.accessToken
                    }
                })
                .then((res) => {
                    console.log(res);
                    this.user = res.data.user;
                    this.postList = res.data.postList;
                    this.likeList = res.data.likeList;
                })
            } else {
                this.$router.push('/');
            }
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
