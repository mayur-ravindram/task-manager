<script setup>
import { ref } from 'vue';
import LoginForm from './components/LoginForm.vue';
import TaskManager from './components/TaskManager.vue';
import { logoutAPI } from './services/authservice';

const isLoggedIn = ref(false);

const handleLogin = () => {
  isLoggedIn.value = true;
};


const logout = async () => {
  isLoggedIn.value = false;
  try {
    await logoutAPI();
    isLoggedIn.value = false;
  } catch (error) {
    console.error('Error logging out:', error);
  }
};
</script>

<template>
  <div v-if="isLoggedIn">
    <div class="bg-gray-800 text-white p-4 flex justify-between items-center">
      <div class="flex items-center">
        
        <button @click="logout" class="text-transparent">
          Logout
        </button>
      </div>
    </div>
    <TaskManager />
  </div>
  <LoginForm v-else @login-success="handleLogin" />
</template>
