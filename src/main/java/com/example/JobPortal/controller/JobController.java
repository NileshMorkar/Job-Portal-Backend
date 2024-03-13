package com.example.JobPortal.controller;

import com.example.JobPortal.model.response.JobResponse;
import com.example.JobPortal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping("/all")
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        List<JobResponse> jobResponsesList = jobService.getAllJobs();
        return ResponseEntity.status(HttpStatus.OK).body(jobResponsesList);
    }

    @GetMapping("/all/{keyword}")
    public ResponseEntity<List<JobResponse>> searchJobsForUser(@PathVariable String keyword) {
        List<JobResponse> jobResponsesList = jobService.searchJobsForUser(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(jobResponsesList);
    }

}
