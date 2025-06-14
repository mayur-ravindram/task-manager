<script setup>
import { ref } from 'vue';
import { loginAPI } from '../services/authservice';

const username = ref('');
const password = ref('');
const loginError = ref('');

const emit = defineEmits(['login-success']);

const handleLogin = async () => {
    loginError.value = '';
    try {
        await loginAPI(username.value, password.value);
        emit('login-success');
        username.value = ''; // Clear fields on success
        password.value = '';
    } catch (error) {
        loginError.value = error.message || 'Failed to login.';
    }
};
</script>

<template>
    <div class="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
        <div class="sm:mx-auto sm:w-full sm:max-w-sm">
            <img class="mx-auto h-10 w-auto"
                src="https://tailwindcss.com/plus-assets/img/logos/mark.svg?color=indigo&shade=600"
                alt="Your Company" />
            <h2 class="mt-10 text-center text-2xl/9 font-bold tracking-tight text-gray-900">Sign in to your account</h2>
        </div>

        <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
            <form class="space-y-6" @submit.prevent="handleLogin">
                <div>
                    <label for="username" class="text-left block text-sm/6 font-medium text-gray-900">Username</label>
                    <div class="mt-2">
                        <input type="text" name="username" id="username" v-model="username" required
                            class="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm/6" />
                    </div>
                </div>
                <div>
                    <div class="flex items-center justify-between">
                        <label for="password" class="block text-sm/6 font-medium text-gray-900">Password</label>
                        <div class="text-sm">
                            <a href="#" class="font-semibold text-indigo-600 hover:text-indigo-500">Forgot password?</a>
                        </div>
                    </div>
                    <div class="mt-2">
                        <input type="password" name="password" id="password" autocomplete="current-password"
                            v-model="password" required
                            class="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm/6" />
                    </div>
                </div>
                <div v-if="loginError" class="my-3 p-3 bg-red-100 border border-red-400 text-red-700 rounded-md">
                    <p role="alert">
                        <span class="font-medium">Login Failed:</span> {{ loginError }}
                    </p>
                </div>
                <div>
                    <button type="submit" class="p-2 m-2 hover:bg-indigo-600 hover:text-white">Sign in</button>
                </div>
            </form>

            <p class="mt-10 text-center text-sm/6 text-gray-500">
                Not Registered?
                <a href="#" class="font-semibold text-indigo-600 hover:text-indigo-500">Sign-up Here!</a>
            </p>
        </div>
    </div>

</template>
