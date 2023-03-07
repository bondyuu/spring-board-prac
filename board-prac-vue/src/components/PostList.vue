<template>
    <div v-for="(item, idx) in list" :key="idx" style="margin-bottom: 20px; width: 1300px; display:inline-block">
            <b-card>
                <b-card-title>{{ item.title }}</b-card-title>
                <b-card-text>
                    {{ item.content }}
                </b-card-text>
                <b-card-text class="small text-muted">{{ item.user }}</b-card-text>
            </b-card>
    </div>
<!-- 
    <div class="mt-3">
        <b-card-group deck>
            <b-card bg-variant="light" header="Light" class="text-center">
                <b-card-text>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</b-card-text>
            </b-card>
            <b-card bg-variant="light" header="Light" class="text-center">
                <b-card-text>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</b-card-text>
            </b-card>
            <b-card bg-variant="light" header="Light" class="text-center">
                <b-card-text>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</b-card-text>
            </b-card>
        </b-card-group>
    </div> -->
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
            list: {
                id: '',
                title: '',
                content: '',
                user: {id: '', email:''}
            }
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
                console.log(res.data[0].user.id);
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
 </style>