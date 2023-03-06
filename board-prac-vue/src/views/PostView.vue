<template>
    <div>
        <h1>Vuex Test</h1>
        {{ counter }} <br>
        {{ $store.getters.getTwoPowerCounter }}<br>
        <button @click="increase">increase</button>
    </div>
</template>

<script>
import { computed } from 'vue';
import { useStore } from 'vuex';

export default {
    setup() {
        const store = useStore();
        const counter = computed(() => store.state.counter);
        const increase = () => store.commit('setCounter', ++counter.value);

        return {counter, increase}
    }
}
</script>
