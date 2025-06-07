<script setup>
import { ref, onMounted } from 'vue';

const newTask = ref({
  title: '',
  description: ''
});

const API_BASE_URL = 'http://localhost:8080/tasks';

// add code to fetch tasks from backend api
const tasks = ref([]);

const fetchTasks = async () => {
  try {
    const response = await fetch(API_BASE_URL);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    tasks.value = await response.json();
  } catch (error) {
    console.error('Error fetching tasks:', error);
    // Consider providing user feedback for the error
  }
};

// Fetch tasks when the component is mounted
onMounted(fetchTasks);

const addTask = async () => {
  try {
    const response = await fetch(API_BASE_URL, { // Assumes POST to /tasks creates a task
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(newTask.value)
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const addedTaskResponse = await response.json();
    console.log('Task added successfully:', addedTaskResponse);

    // Reset form and refresh tasks on successful addition
    newTask.value.title = '';
    newTask.value.description = '';
    await fetchTasks(); // Refresh the list to show the new task
  } catch (error) {
    console.error('Error adding task:', error);
    // Consider providing user feedback for the error
  }
};

const toggleStatus = async (task) => {
  try {
    const response = await fetch(`${API_BASE_URL}/${task.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ ...task, done: !task.done })
    });
  }
  catch (error) { }
  await fetchTasks();
}

const deleteTask = async (task) => {
  try {
    const response = await fetch(`${API_BASE_URL}/${task.id}`, {
      method: 'DELETE'
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    console.log('Task deleted successfully:', task.id);
    await fetchTasks(); // Refresh the list after deletion
  } catch (error) {
    console.error('Error deleting task:', error);
    // Consider providing user feedback for the error
  }
};


</script>

<template>
  <!-- add nice tailwindcss style to this template -->
  <div class="container mx-auto p-4">
    <header class="mb-8">
      <div class="flex bg-green-300 w-10 justify-center rounded-full">
        <span class="">v1.0</span>
      </div>
      <h1 class="text-3xl font-bold text-center mb-4">Task Manager</h1>
      <form @submit.prevent="addTask" class="border-4 rounded-lg px-8 pt-6 pb-8 mb-4">
        <div class="mb-4">
          <label class="block text-gray-700 text-sm font-bold mb-2 text-left" for="title">
            Task Title
          </label>
          <input
            class="border-b-2 rounded-none w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="title" type="text" v-model="newTask.title" placeholder="Enter task title">
        </div>
        <div class="mb-6">
          <label class="block text-gray-700 text-sm font-bold mb-2 text-left" for="description">
            Task Description
          </label>
          <textarea
            class="border-2 rounded-lg w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="description" v-model="newTask.description" placeholder="Enter task description" rows="3"></textarea>
        </div>
        <div class="flex items-center justify-between">
          <button class="border-2" type="submit">
            Add Task
          </button>
        </div>
      </form>

      <h2 class="text-2xl font-bold text-center mb-4">Tasks</h2>

      <div class="overflow-x-auto">
        <table class="min-w-full table-auto border-collapse border border-gray-200">
          <thead>
            <tr class="bg-gray-100">
              <th class=" px-4 py-2 text-left text-gray-600 font-bold">Title</th>
              <th class=" px-4 py-2 text-left text-gray-600 font-bold">Description</th>
              <th class=" px-4 py-2 text-left text-gray-600 font-bold">Status</th>
              <th class=" px-4 py-2 text-left text-gray-600 font-bold">Action</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="task in tasks" :key="task.id" class="hover:bg-gray-50">
              <td class=" px-4 py-2">{{ task.title }}</td>
              <td class=" px-4 py-2">{{ task.description }}</td>
              <!-- on hover of this cell, make the status toggle -->

              <!-- add a button to toggle the status -->
              <td class=" px-4 py-2">{{ task.done ? 'Done' : 'Ongoing' }}</td>
              <td class=" px-4 py-2">
                <button @click="toggleStatus(task)" class="">
                  <!-- instead of text add HeroIcon as button label -->
                  {{ task.done ? 'Mark Ongoing' : 'Mark Done' }}
                  <component :is="task.done ? FlagIcon : CheckIcon" class="h-5 w-5 inline-block mr-1" />
                </button>
              </td>
              <td class=" px-4 py-2">
                <button @click="deleteTask(task)" class="">
                  <TrashIcon class="h-5 w-5 inline-block mr-1" />
                </button>
              </td>

            </tr>
          </tbody>
        </table>
      </div>

    </header>
  </div>
</template>

<script>
import { FlagIcon, CheckIcon, TrashIcon } from '@heroicons/vue/24/solid'
</script>