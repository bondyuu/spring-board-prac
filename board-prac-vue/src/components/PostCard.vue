<template :v-if="this.active === true">
    <b-card>
        <b-card-title class="title">
            <router-link :to="`/posts/${item.id}/detail`">{{ item.title }} </router-link>
            <div class="more">
                <b-dropdown id="dropdown-offset" class="m-2" no-caret>
                    <template #button-content>
                        <BIconThreeDotsVertical></BIconThreeDotsVertical>
                    </template>
                    <b-dropdown-item @click="deletePost(item.id)">Delete</b-dropdown-item>
                </b-dropdown>
            </div>
        </b-card-title>
        <b-card-text class="content">
            {{ item.content }}
        </b-card-text>
        <b-card-text class="small text-muted">{{ item.user.email }}</b-card-text>
    </b-card>
</template>
  
<script>
import { BIconThreeDotsVertical } from 'bootstrap-icons-vue';
import axios from 'axios';

export default {
    name: 'PostCard',
    props: {
        item: Object
    },
    components: {
    BIconThreeDotsVertical 
    },
    methods: {
        deletePost(id) {
            axios
            .post('http://localhost:8080/posts/'+id,'',
            {
                headers: {
                        'Authorization': this.$store.state.accessToken
                    }
            })       
            .then((res) => {
                console.log(res);
                window.location.reload();

            })
            .catch((err) => {
                console.log(err);
            })
        }
    }
}
</script>

<style scoped>
.more {
    float: right;
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