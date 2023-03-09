<template>
    <h1>Admin Posts</h1>
    총 회원 수 : {{ userNum }}<br>
    총 게시글 수 : {{ postNum }}
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
