package com.mayur.portfolio.task.manager.controller;

import com.mayur.portfolio.task.manager.model.Task;
import com.mayur.portfolio.task.manager.repo.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class ViewController {

    // constructor injection of TaskRepository
    private final TaskRepository taskRepository;

    public ViewController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    // Add this method to TaskController
    @GetMapping("/view")
    public String viewAllTasks(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "tasks"; // This refers to tasks.html in src/main/resources/templates
    }

    @PostMapping("/add")
    public String addTask(@RequestParam String title, @RequestParam String description) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDone(false);
        taskRepository.save(task);
        return "redirect:/tasks/view";
    }

    @PostMapping("update/{id}")
    public String patchTask(@PathVariable Long id, @ModelAttribute Task partialTask, Model model) {
        return taskRepository.findById(id)
                .map(task -> {
                    if (partialTask.getTitle() != null) {
                        task.setTitle(partialTask.getTitle());
                    }
                    if (partialTask.getDescription() != null) {
                        task.setDescription(partialTask.getDescription());
                    }
                    if (partialTask.isDone() != task.isDone()) {
                        task.setDone(partialTask.isDone());
                    }
                    taskRepository.save(task);
                    model.addAttribute("tasks", taskRepository.findAll());
                    return "tasks";
                })
                .orElse("error"); // You can customize the error view as needed
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/tasks/view"; // Redirect to the view page after deletion
    }

}
