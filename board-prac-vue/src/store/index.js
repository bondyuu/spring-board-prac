import { createStore } from "vuex";
import createPersistedState from 'vuex-persistedstate';

// state, getters, mutations, actions, modules
export default createStore({
    plugins: [
        createPersistedState()
    ],
    state : {
        counter : 2,
        accessToken: null,
        refreshToekn: null,
        role: null,
    },
    getters : {
        getTwoPowerCounter(state){
            return state.counter ** 2;
        },
        isLogin(state) {
            return state.accessToken === null ? false : true;
        },
        isAdmin(state) {
            return state.role === '관리자' ? true : false;
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
        },
        setRole(state, value) {
            state.role = value;
        }
    },
    actions: {
        setAccessToken: ({commit}, token) => {
            commit('setAccessToken', token);
        },
        setRefreshToken: ({commit}, token) => {
            commit('setRefreshToken', token);
        },
        setRole: ({commit}, value) => {
            commit('setRole', value);
        }
    }
});