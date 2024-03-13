package com.example.JobPortal.controller;


import com.example.JobPortal.exception.GlobalException;
import com.example.JobPortal.model.request.AdminRequest;
import com.example.JobPortal.model.request.JobRequest;
import com.example.JobPortal.model.response.AdminResponse;
import com.example.JobPortal.model.response.ApiResponseMessage;
import com.example.JobPortal.model.response.JobResponse;
import com.example.JobPortal.model.response.UserResponse;
import com.example.JobPortal.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    public AdminService adminService;

    @PostMapping("/create-admin")
    public ResponseEntity<ApiResponseMessage> createAdmin(@Valid @RequestBody AdminRequest adminRequest) {
        ApiResponseMessage message = adminService.createAdmin(adminRequest);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PutMapping("/{adminId}/update")
    public ResponseEntity<AdminResponse> updateAdminNamePassword(@PathVariable Long adminId, @Valid @RequestBody AdminRequest adminRequest) throws GlobalException {
        AdminResponse adminResponse = adminService.updateAdminNamePassword(adminId, adminRequest);
        return ResponseEntity.status(HttpStatus.OK).body(adminResponse);
    }

    @GetMapping("/{adminId}/get-all-jobs")
    public ResponseEntity<List<JobResponse>> getAllJobs(@PathVariable Long adminId) throws GlobalException {
        List<JobResponse> jobResponsesList = adminService.getAllJobs(adminId);
        return ResponseEntity.status(HttpStatus.OK).body(jobResponsesList);
    }


    @DeleteMapping("/{adminId}/delete-job/{jobId}")
    public ResponseEntity<ApiResponseMessage> deleteJobById(@PathVariable Long adminId, @Valid @PathVariable Long jobId) throws GlobalException {
        ApiResponseMessage message = adminService.deleteJobById(adminId, jobId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PostMapping("/{adminId}/create-job")
    public ResponseEntity<ApiResponseMessage> createNewJob(@PathVariable Long adminId, @Valid @RequestBody JobRequest jobRequest) throws GlobalException {
        ApiResponseMessage message = adminService.createNewJob(adminId, jobRequest);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

//    @DeleteMapping("/{adminId}/delete-admin")
//    public ResponseEntity<ApiResponseMessage> deleteAdmin(@PathVariable Long adminId) throws GlobalException {
//        ApiResponseMessage message = adminService.deleteAdmin(adminId);
//        return ResponseEntity.status(HttpStatus.OK).body(message);
//    }

    @GetMapping("/{adminId}/get-all-jobs/{keyword}")
    public ResponseEntity<List<JobResponse>> searchJobByKeywordForAdmin(@PathVariable Long adminId, @PathVariable String keyword) throws GlobalException {
        List<JobResponse> jobResponsesList = adminService.searchJobsForAdmin(adminId, keyword);
        return ResponseEntity.status(HttpStatus.OK).body(jobResponsesList);
    }


    @GetMapping("{adminId}/get-all-candidates/{jobId}")
    public ResponseEntity<List<UserResponse>> getAllCandidatesOfJobById(@PathVariable Long adminId, @PathVariable Long jobId) throws GlobalException {
        List<UserResponse> userResponseList = adminService.getAllCandidatesOfJobById(adminId, jobId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseList);
    }

}
