<script setup>
import { ref, onMounted } from 'vue';
import { FlagIcon, CheckIcon, TrashIcon, PencilIcon } from '@heroicons/vue/24/solid';
import { addTaskAPI, deleteTaskAPI, fetchTasksAPI, updateTaskAPI } from '../services/taskService';

const newTask = ref({
  title: '',
  description: ''
});
const tasks = ref([]);
const editingTask = ref(null);

const fetchTasks = async () => {
  try {
    tasks.value = await fetchTasksAPI();
  } catch (error) {
    console.error('Error fetching tasks:', error);
    // Handle error (e.g., show a message to the user)
  }
};

onMounted(fetchTasks);

const addTask = async () => {
  if (!newTask.value.title || !newTask.value.description) {
    alert('Please enter both title and description');
    return;
  }
  try {
    await addTaskAPI(newTask.value);
    newTask.value.title = '';
    newTask.value.description = '';
    await fetchTasks();
  } catch (error) {
    console.error('Error adding task:', error);
  }
};

const startEditing = (task) => {
  editingTask.value = { ...task };
};

const cancelEdit = () => {
  editingTask.value = null;
};

const saveTask = async (taskToSave) => {
  try {
    await updateTaskAPI(taskToSave);
    editingTask.value = null;
    await fetchTasks();
  } catch (error) {
    console.error('Error updating task:', error);
  }
};

const toggleStatus = async (task) => {
  try {
    const updatedTask = { ...task, done: !task.done };
    await updateTaskAPI(updatedTask);
    await fetchTasks();
  } catch (error) {
    console.error('Error toggling task status:', error);
  }
};

const deleteTask = async (task) => {
  try {
    await deleteTaskAPI(task.id);
    await fetchTasks();
  } catch (error) {
    console.error('Error deleting task:', error);
  }
};

// This function was named updateTask but its action is to start editing.
// Renamed for clarity if desired, or keep as updateTask if it's the entry point for the UI edit action.
const initiateUpdateTask = (task) => {
  startEditing(task);
};
</script>

<template>
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
        <table class="table-auto border-collapse border border-gray-200">
          <thead>
            <tr class="bg-gray-100">
              <th class=" px-4 py-2 text-left text-gray-600 font-bold">Title</th>
              <th class=" px-4 py-2 text-left text-gray-600 font-bold">Description</th>
              <th class=" px-4 py-2 text-left text-gray-600 font-bold">Status</th>
              <th class=" px-4 py-2 text-center text-gray-600 font-bold">Action</th>
            </tr>
          </thead>
          <tbody>

            <!-- make this template when updateTask is called else otherwise -->
            <template v-if="editingTask">
              <tr>
                <td class=" px-4 py-2">
                  <input type="text" v-model="editingTask.title" class="">
                </td>
                <td class=" px-4 py-2">
                  <textarea v-model="editingTask.description" class=""></textarea>
                </td>
                <td class=" px-4 py-2">
                  <input type="checkbox" v-model="editingTask.done">
                </td>
                <td class=" p-2 text-center flex justify-between">
                  <a href="#" @click="saveTask(editingTask)" class="p-2">Save</a>
                </td>
              </tr>
            </template>
            <template v-else>
              <tr v-for="task in tasks" :key="task.id" class="hover:bg-gray-50">
                <td class=" px-4 py-2">{{ task.title }}</td>
                <td class=" px-4 py-2">{{ task.description }}</td>
                <td class=" px-4 py-2">{{ task.done ? 'Done' : 'Ongoing' }}</td>
                <td class=" px-4 py-2 text-center">
                  <a href="#" class="m-2 p-2" @click="toggleStatus(task)">
                    {{ task.done ? '' : '' }}
                    <component :is="task.done ? FlagIcon : CheckIcon" class="h-5 w-5 inline-block mr-1" />
                  </a>
                  <a href="#" class="m-2 p-2" @click="deleteTask(task)">
                    <TrashIcon class="h-5 w-5 inline-block mr-1 text-red-500" />
                  </a>
                  <a href="#" class="m-2 p-2" @click="updateTask(task)">
                    <PencilIcon class="h-5 w-5 inline-block mr-1 text-blue-500" />
                  </a>
                </td>
              </tr>
            </template>
          </tbody>
        </table>
      </div>

    </header>
  </div>
</template>
