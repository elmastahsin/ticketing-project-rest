package com.company.controller;

import com.company.dto.ResponseWrapper;
import com.company.dto.TaskDTO;
import com.company.enums.Status;
import com.company.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@Tag(name = "Task Controller", description = "Task API")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //getAllTasks
    @GetMapping
    @RolesAllowed({"Manager"})
    @Operation(summary = "Get All Tasks")
    public ResponseEntity<ResponseWrapper> getAllTasks(){
        List<TaskDTO> tasksDTOList = taskService.listAllTasks();
        return ResponseEntity.ok(new ResponseWrapper("tasks successfully retrieved", tasksDTOList, HttpStatus.OK));
    }
    //getTaskById
    @GetMapping("/{id}")
    @RolesAllowed({"Manager"})
    @Operation(summary = "Get Task By Id")
    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable("id") Long id){
        TaskDTO taskDTO = taskService.findById(id);
        return ResponseEntity.ok(new ResponseWrapper("task successfully retrieved", taskDTO, HttpStatus.OK));
    }
    //createTask
    @PostMapping
    @RolesAllowed({"Manager"})
    @Operation(summary = "Create Task")
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO taskDTO){
        taskService.save(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("task successfully created", HttpStatus.CREATED));
    }
    //updateTask
    @PutMapping
    @RolesAllowed({"Manager"})
    @Operation(summary = "Update Task")
    public ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO taskDTO){
        taskService.update(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("task successfully updated", HttpStatus.CREATED));
    }
    //deleteTask
    @DeleteMapping("/{id}")
    @RolesAllowed({"Manager"})
    @Operation(summary = "Delete Task")
    public ResponseEntity<ResponseWrapper> deleteTask(@PathVariable("id") Long id){
        taskService.delete(id);
        return ResponseEntity
                .ok(new ResponseWrapper("task successfully deleted", HttpStatus.OK));
    }
    //employeePendingTasks
    @GetMapping("/employee/pending-tasks")
    @RolesAllowed({"Employee"})
    @Operation(summary = "Get All Pending Tasks")
    public ResponseEntity<ResponseWrapper> employeePendingTasks(){
        List<TaskDTO> tasksDTOList = taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("tasks successfully retrieved", tasksDTOList, HttpStatus.OK));
    }
    //EmployeeUpdateTasks
    @PutMapping("/employee/update")
    @RolesAllowed({"Employee"})
    @Operation(summary = "Update Task")
    public ResponseEntity<ResponseWrapper> employeeUpdateTasks(@RequestBody TaskDTO taskDTO){
        taskService.update(taskDTO);
        return ResponseEntity.ok(new ResponseWrapper("task successfully updated", HttpStatus.CREATED));
    }

    //EmployeeArchiveTasks
    @GetMapping("/employee/archive")
    @RolesAllowed({"Employee"})
    @Operation(summary = "Get All Archive Tasks")
    public ResponseEntity<ResponseWrapper> employeeArchiveTasks(){
        List<TaskDTO> taskDTOList= taskService.listAllTasksByStatus(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("task successfully retrieved",taskDTOList, HttpStatus.OK));
    }


}
