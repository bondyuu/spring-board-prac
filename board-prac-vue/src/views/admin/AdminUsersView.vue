<template>
    <h1>List of Users</h1>
    <div class="search-form">
        <b-nav-form>
            <b-form-input class="mr-sm-2" placeholder="Search" v-model="email"></b-form-input>
            <b-button variant="outline-success" class="my-2 my-sm-0" @click="getUsers">Search</b-button>
        </b-nav-form>
    </div>
    <div class="tbl-wrapper">
        <b-table :fields="fields" :items="list">
            <template #cell(key)="data">
                {{ data.index + 1 }}
            </template>
            <template #cell(email)="data">
                <a href="#"> {{ data.value }} </a>
            </template>
            <template #cell(action)="data">
                <b-button v-if="data.item.status==='활성회원'" variant="outline-success" class="btn-del" @:click="deleteUser(data.item.id)">Delete</b-button>
                <!-- deleteUser(data.item.id) -->
            </template>
        </b-table>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'AdminMainView',
    mounted() {
        this.getUsers()
    },
    data() {
        return {
            fields: [
                'key',
                'email',
                'role',
                'status',
                'action'
            ],
            email: '',
            list: [],
        }
    },
    methods: {
        getUsers() {
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
        },
        deleteUser(id) {
            const userId = id;
            console.log(userId);
            console.log(this.$store.state.accessToken);

            axios
            .post('http://localhost:8080/users/' + userId + '/delete','',
            {
                headers: {
                    'Authorization' : this.$store.state.accessToken,
                }
            })
            .then((res) => {
                console.log(res);
                const item = this.list.find((item) => item.id === userId);
                item.status = res.data;
            })
            .catch((err) => {
                console.log(err);
            })
        },
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
.btn-del {
    height: 25px;
    font-size: 10px;
}
</style>
