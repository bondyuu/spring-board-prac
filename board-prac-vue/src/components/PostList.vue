<template>
    <div v-for="(item, idx) in list" :key="idx" class="wrapper">
            <b-card >
                <b-card-title class="title">{{ item.title }}</b-card-title>
                <b-card-text class="content">
                    {{ item.content }}
                </b-card-text>
                <b-card-text class="small text-muted">{{ item.user.email }}</b-card-text>
            </b-card>
    </div>

</template>
  
<script>
import axios from 'axios';
import { computed } from 'vue';
import { useStore } from 'vuex';

export default {
    name: 'PostList',
    setup() {
        const store = useStore();
        const at = computed(() => store.state.accessToken);
        
        return { at };
    },
    data() {
        return {
            list: []
        };
    },
    mounted() {
        this.getBoards()
    },
    methods: {
        getBoards() {
            axios
            .get('http://localhost:8080/posts',
            {
                headers: {
                    Authorization: this.at
                }
            })
            .then((res) => {
                this.list = res.data;
            })
            .catch((err) => {
                console.log(err);
            });
        }
    }
}
</script>

<style scoped>
.wrapper {
    margin-bottom: 20px; 
    margin-right: 3%;
    width: 32%;
    display: inline-block;
}
.title, .content {
    text-align: left;
    margin-left: 2%;
    margin-top: 2%;
}

.small {
    text-align: right;
}
</style>