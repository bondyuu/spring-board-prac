<template>
    <div class="signup">
        <h1>This is an Sign-up page</h1>

        <form @submit.prevent="submitForm">
            <div>
                <label for="email">email : </label>
                <input type="test" id="email" v-model="email" />
            </div>
            <div>
                <label for="password">password : </label>
                <input type="test" id="password" v-model="password" />
            </div>
            <button>sign up</button>
        </form>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'SignupView',
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

            axios.post('http://localhost:8080/users/signup', JSON.stringify(userData),
            {
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then ((res) => {
                console.log(res);
            });
        }
    }
}
</script>