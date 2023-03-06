import { createStore } from "vuex";

// state, getters, mutations, actions, modules
export default createStore({
    state : {
        counter : 2,
        accessToken: null,
        refreshToekn: null,
    },
    getters : {
        getTwoPowerCounter(state){
            return state.counter ** 2;
        },
        isLogin(state) {
            return state.accessToken === null ? false : true;
        }
    },
    mutations : {
        setCounter(state, value){
            state.counter = value;
        },
        setAccessToken(state, token) {
            state.accessToken = token;
        },
        setRefreshToken(state, token) {
            state.refreshToekn = token;
        }
    },
    actions: {
        setAccessToken: ({commit}, token) => {
            commit('setAccessToken', token);
        },
        setRefreshToken: ({commit}, token) => {
            commit('setRefreshToken', token);
        }
    }
});