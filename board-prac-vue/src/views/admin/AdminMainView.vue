<template>
    <h1>Admin Main</h1>

    <div class="wrapper">
        <b-card class="card-left" bg-variant="default" title="Total User">
            <b-card-text>
                {{ userNum }}
            </b-card-text>
            <b-button @click="this.$router.push('/admin/users')" variant="primary">Go List of User</b-button>
        </b-card>
        <b-card bg-variant="default" title="Total Post">
            <b-card-text>
                {{ postNum }}
            </b-card-text>
            <b-button @click="this.$router.push('/admin/posts')" variant="primary">Go List of Post</b-button>
        </b-card>
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
            postNum: ''
        }
    },
    methods: {
        getMain() {
            if (this.$store.state.role === '관리자') {
                axios
                .get('http://localhost:8080/admin/main',
                {
                    headers: {
                        'Authorization': this.$store.state.accessToken
                    }
                })
                .then((res) => {
                    console.log(res);
                    this.userNum = res.data.userNum;
                    this.postNum = res.data.postNum;
                    
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
    margin-left: 16%;
    margin-bottom: 30px;
}
.wrapper {
    text-align: center;
}
.card {
    display: inline-block;
    width: 30%;
}
.card-text {
    font-size: 20px;
}
.card-left {
    margin-right: 8%;
}
</style>