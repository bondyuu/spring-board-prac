<template>
    <div class="login">
        <h1>This is an Login page</h1>

        <form @submit.prevent="submitForm">
            <div>
                <label for="email">email : </label>
                <input type="test" id="email" v-model="email" />
            </div>
            <div>
                <label for="password">password : </label>
                <input type="test" id="password" v-model="password" />
            </div>
            <button>login</button>
        </form>

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

        return { at, setToken };
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
                this.setToken(res.data.grantType + res.data.accessToken, res.data.refreshToken);
                this.$router.push('/posts')
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