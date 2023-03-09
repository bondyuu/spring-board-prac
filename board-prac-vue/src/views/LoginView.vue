<template>
    <div class="login">
        <h1 style="margin-bottom: 50px; margin-top: 100px;">Login</h1>
        <b-form  @submit.stop.prevent>
            <div style="margin-bottom: 50px;">
                <label for="email" style="margin-bottom: 10px; font-size: 20px;">email</label>
                <b-form-input v-model="email" id="email" style="width: 30%; margin-left: 35%;"></b-form-input>   
            </div>
            <div style="margin-bottom: 50px;">
                <label for="password" style="margin-bottom: 10px; font-size: 20px;">password</label>
                <b-form-input v-model="password" id="password" style="width: 30%; margin-left: 35%;"></b-form-input>
            </div>
        </b-form>
        <b-button variant="outline-primary" @click="submitForm">Login</b-button>
    </div>

</template>

<script>
import axios from 'axios';
import { useStore } from 'vuex';
import { computed } from 'vue';

export default {
    name: 'LoginView',
    setup() {
        const store = useStore();
        const at = computed(() => store.state.accessToken);
        const setToken = (accessToken, refreshToken) => {
            store.commit('setAccessToken', accessToken),
            store.commit('setRefreshToken', refreshToken)
        };
        const setRole = (role) => {
            store.commit('setRole', role)
        };
        return { at, setToken, setRole };
    },
    data() {
        return {
            email: '',
            password: '',
        };
    },
    methods: {
        submitForm() {
            const userData = {
                email: this.email,
                password: this.password
            };

            axios.post('http://localhost:8080/users/login', JSON.stringify(userData),
            {
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then ((res) => {
                console.log(res);
                this.setToken(res.data.token.grantType + res.data.token.accessToken, res.data.token.refreshToken);
                this.setRole(res.data.role);
                this.$router.push('/')
                console.log(this.$store.state.isLogin);
            })
            .catch((err) => {
                alert('로그인에 실패했습니다.');
                console.log(err);
            });
        }
    }
}
</script>

<style scoped>
.login {
    text-align: center;
}
</style>