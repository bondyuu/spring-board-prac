<template>
    <b-card >
        <b-card-title class="title">{{ item.title }}</b-card-title>
        <b-card-text class="content">
            {{ item.content }}
        </b-card-text>
        <b-card-text class="small text-muted">{{ item.user.email }}</b-card-text>
    </b-card>
</template>
  
<script>

export default {
    name: 'PostCard',
    props: {
        item: Object
    }
}
</script>

<style scoped>
/* .wrapper {
    margin-bottom: 20px; 
    margin-right: 3%;
    width: 32%;
    display: inline-block;
} */
.title, .content {
    text-align: left;
    margin-left: 2%;
    margin-top: 2%;
}

.small {
    text-align: right;
}
</style>