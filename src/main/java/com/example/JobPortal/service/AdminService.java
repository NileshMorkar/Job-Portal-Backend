package com.example.JobPortal.service;


import com.example.JobPortal.exception.GlobalException;
import com.example.JobPortal.model.request.AdminRequest;
import com.example.JobPortal.model.request.JobRequest;
import com.example.JobPortal.model.response.AdminResponse;
import com.example.JobPortal.model.response.ApiResponseMessage;
import com.example.JobPortal.model.response.JobResponse;
import com.example.JobPortal.model.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {

    ApiResponseMessage createAdmin(AdminRequest adminRequest);

    AdminResponse updateAdminNamePassword(Long adminId, AdminRequest adminRequest) throws GlobalException;

    List<JobResponse> getAllJobs(Long adminId) throws GlobalException;

    ApiResponseMessage deleteJobById(Long adminId, Long jobId) throws GlobalException;

    ApiResponseMessage createNewJob(Long adminId, JobRequest jobRequest) throws GlobalException;

    List<UserResponse> getAllCandidatesOfJobById(Long adminId, Long jobId) throws GlobalException;

    List<JobResponse> searchJobsForAdmin(Long adminId, String keyword) throws GlobalException;

//    ApiResponseMessage deleteAdmin(Long adminId) throws GlobalException;
}
