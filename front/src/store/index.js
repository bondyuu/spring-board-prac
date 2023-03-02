import { createStore } from "vuex";

// state, getters, mutations, actions, modules
export default createStore({
    state : {
        counter : 2
    },
    getters : {
        getTwoPowerCounter(state){
            return state.counter ** 2;
        }
    },
    mutations : {
        setCounter(state, value){
            state.counter = value;
        }
    },
});