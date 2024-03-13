package com.example.JobPortal.service;

import com.example.JobPortal.exception.GlobalException;
import com.example.JobPortal.model.request.UserRequest;
import com.example.JobPortal.model.response.ApiResponseMessage;
import com.example.JobPortal.model.response.JobResponse;
import com.example.JobPortal.model.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    ApiResponseMessage createNewUser(UserRequest userRequest);

    UserResponse updateUserNamePassword(UserRequest userRequest) throws GlobalException;

    ApiResponseMessage addJobToJobList(Long userId, Long jobId) throws GlobalException;

    List<JobResponse> getJobList(Long userId) throws GlobalException;

    ApiResponseMessage removeJobFromJobList(Long userId, Long jobId) throws GlobalException;

    List<JobResponse> searchJobsInJobList(Long userId, String keyword) throws GlobalException;

    ApiResponseMessage deleteUser(Long userId);

}
