// src/services/taskService.js
const API_BASE_URL = 'http://localhost:8080/tasks';

export const fetchTasksAPI = async () => {
  const response = await fetch(API_BASE_URL);
  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }
  return response.json();
};

export const addTaskAPI = async (taskData) => {
  const response = await fetch(API_BASE_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(taskData),
  });
  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }
  return response.json();
};

export const updateTaskAPI = async (task) => {
  const response = await fetch(`${API_BASE_URL}/${task.id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(task),
  });
  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }
  // Assuming PUT might not return the full object or could return 204
  // For consistency, you might want your API to return the updated task
  return task; // Or await response.json() if your API returns it
};

export const deleteTaskAPI = async (taskId) => {
  const response = await fetch(`${API_BASE_URL}/${taskId}`, {
    method: 'DELETE',
  });
  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }
  // DELETE often returns 204 No Content
};
