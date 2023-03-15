<template>
    <h1>List of Posts</h1>
    <div class="search-form">
        <b-nav-form>
            <b-form-input class="mr-sm-2" placeholder="Search" v-model="title"></b-form-input>
            <b-button variant="outline-success" class="my-2 my-sm-0" @click="getPosts">Search</b-button>
        </b-nav-form>
    </div>
    <div class="tbl-wrapper">
      <b-table striped hover :fields="fields" :items="list">
          <template #cell(key)="data">
              {{ data.index + 1 }}
          </template>
          <template #cell(user)="data">
              {{ data.item.user.email }}
          </template>
          <template #cell(action)="data">
              <b-button v-if="data.item.status==='활성게시글'" variant="outline-success" class="btn-del" @:click="deletePost(data.item.id)">Delete</b-button>
              <!-- deleteUser(data.item.id) -->
          </template>
      </b-table>
      <b-pagination
          v-model="currentPage"
          :total-rows="rows"
          :per-page="1"
          aria-controls="my-table"
          @click="getPosts"
      ></b-pagination>
  </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'AdminMainView',
    mounted() {
        this.getPosts()
    },
    data() {
        return {
            rows: '',
            currentPage: 1,
            title: '',
            list: [],
            fields: [
                'key',
                'title',
                'content',
                'user',
                'heartNum',
                'status',
                'action'
            ],
        }
    },
    methods: {
        getPosts() {
            if (this.$store.state.role === '관리자') {
                axios
                .get('http://localhost:8080/admin/posts?title='+this.title+'&page='+(this.currentPage-1),
                {
                    headers: {
                        'Authorization': this.$store.state.accessToken
                    }
                })
                .then((res) => {
                    console.log(res);
                    this.list = res.data.content;
                    this.rows = res.data.totalPages;
                })
            } else {
                this.$router.push('/');
            }
        },
        deletePost(id) {
            axios
            .post('http://localhost:8080/posts/'+id,'',
            {
                headers: {
                        'Authorization': this.$store.state.accessToken
                    }
            })       
            .then((res) => {
                const item = this.list.find((item) => item.id === id);
                item.status = res.data;
            })
            .catch((err) => {
                console.log(err);
            })
        }
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
</style>
