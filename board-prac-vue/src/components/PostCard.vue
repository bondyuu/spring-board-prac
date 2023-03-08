<template>
    <b-card >
        <b-card-title class="title">
            {{ item.title }}
            <div class="more">
                <b-dropdown id="dropdown-offset" class="m-2" no-caret>
                    <template #button-content>
                        <BIconThreeDotsVertical></BIconThreeDotsVertical>
                    </template>
                    <b-dropdown-item href="#">Edit</b-dropdown-item>
                    <b-dropdown-item href="#">Delete</b-dropdown-item>
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

export default {
    name: 'PostCard',
    props: {
        item: Object
    },
    components: {
    BIconThreeDotsVertical
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