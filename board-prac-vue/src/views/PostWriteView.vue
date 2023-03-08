<template>
    <div class="wrapper">
        <b-card bg-variant="light">
            <b-form-group
            label-cols-lg="3"
            label="Write Post"
            label-size="lg"
            label-class="font-weight-bold pt-0"
            class="mb-0"
            >
                <b-form-group
                    label="Title :"
                    label-for="title"
                    label-cols-sm="2"
                    label-align-sm="right"
                >
                    <b-form-input id="title" v-model="title"></b-form-input>
                </b-form-group>

                <b-form-group
                    label="Content :"
                    label-for="content"
                    label-cols-sm="2"
                    label-align-sm="right"
                >
                    <b-form-input id="content" v-model="content"></b-form-input>
                </b-form-group>

            </b-form-group>

            <b-button pill variant="outline-secondary" style="margin-left: 85%;" @click="save">Save</b-button>
        </b-card>
    </div>
</template>

<script>
import axios from 'axios';
import { useStore } from 'vuex';
import { computed } from 'vue';

export default {
    name: 'PostWriteView',
    setup() {
        const store = useStore();
        const at = computed(() => store.state.accessToken);
        return { at };
    },
    data() {
        return {
            title: '',
            content: '',
        }
    },
    methods: {
        save() {

            const postData = {
                title : this.title,
                content : this.content
            }

            axios
            .post('http://localhost:8080/posts/save', JSON.stringify(postData),
            {
                headers: {
                    'Authorization' : this.at,
                    'Content-Type' : 'application/json'
                }
            })
            .then(() => {
                this.$router.push('/posts')
            })
            .catch((err) => {
                console.log(err);
                alert('fail');
            });
        }
    }

}
</script>

<style scoped>
.wrapper {
    width: 50%;
    margin-left: 25%;
    margin-top: 100px;
}
#content {
    height: 300px;
}
</style>
