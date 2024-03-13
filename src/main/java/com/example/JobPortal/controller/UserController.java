package com.example.JobPortal.controller;

import com.example.JobPortal.exception.GlobalException;
import com.example.JobPortal.model.request.UserRequest;
import com.example.JobPortal.model.response.ApiResponseMessage;
import com.example.JobPortal.model.response.JobResponse;
import com.example.JobPortal.model.response.UserResponse;
import com.example.JobPortal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<ApiResponseMessage> createUser(@Valid @RequestBody UserRequest userRequest) {
        ApiResponseMessage message = userService.createNewUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PutMapping("/update-user")
    public ResponseEntity<UserResponse> updateUserNamePassord(@Valid @RequestBody UserRequest userRequest) throws GlobalException {
        UserResponse userResponse = userService.updateUserNamePassword(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PutMapping("/{userId}/add-job/{jobId}")
    public ResponseEntity<ApiResponseMessage> addJobToJobList(@PathVariable Long userId, @PathVariable Long jobId) throws GlobalException {
        ApiResponseMessage message = userService.addJobToJobList(userId, jobId);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @DeleteMapping("/{userId}/remove-job/{jobId}")
    public ResponseEntity<ApiResponseMessage> removeJobFromJobList(@PathVariable Long userId, @PathVariable Long jobId) throws GlobalException {
        ApiResponseMessage message = userService.removeJobFromJobList(userId, jobId);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/{userId}/all-job")
    public ResponseEntity<List<JobResponse>> getJobList(@PathVariable Long userId) throws GlobalException {
        List<JobResponse> jobResponseList = userService.getJobList(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobResponseList);
    }

    @DeleteMapping("/{userId}/delete-user")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable Long userId) throws GlobalException {
        ApiResponseMessage message = userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/{userId}/all-job/{keyword}")
    public ResponseEntity<List<JobResponse>> searchJobsInJobList(@PathVariable Long userId, @PathVariable String keyword) throws GlobalException {
        List<JobResponse> jobResponseList = userService.searchJobsInJobList(userId, keyword);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobResponseList);
    }
    
}
