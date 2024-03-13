package com.example.JobPortal.service.impl;

import com.example.JobPortal.entity.JobApplicationEntity;
import com.example.JobPortal.entity.JobEntity;
import com.example.JobPortal.entity.UserEntity;
import com.example.JobPortal.exception.GlobalException;
import com.example.JobPortal.model.request.UserRequest;
import com.example.JobPortal.model.response.ApiResponseMessage;
import com.example.JobPortal.model.response.JobResponse;
import com.example.JobPortal.model.response.UserResponse;
import com.example.JobPortal.repository.JobApplicationRepository;
import com.example.JobPortal.repository.JobRepository;
import com.example.JobPortal.repository.UserRepository;
import com.example.JobPortal.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Override
    public ApiResponseMessage createNewUser(UserRequest userRequest) {

        UserEntity user = userRepository.findByEmail(userRequest.getEmail()).orElse(null);

        if (user != null) {
            return ApiResponseMessage
                    .builder()
                    .message("User Is Already Present!")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }

        user = UserEntity
                .builder()
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .jobApplicationList(new ArrayList<>())
                .password(userRequest.getPassword())
                .build();
        userRepository.save(user);

        return ApiResponseMessage
                .builder()
                .message("User Is Created Successfully!")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    //    ********* check by adding job
    @Override
    public UserResponse updateUserNamePassword(UserRequest userRequest) throws GlobalException {

        UserEntity user = userRepository.findByEmail(userRequest.getEmail()).orElseThrow(() -> new GlobalException("User Not Found!", HttpStatus.NOT_FOUND));

        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword());

        userRepository.save(user);

        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public ApiResponseMessage addJobToJobList(Long userId, Long jobId) throws GlobalException {

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new GlobalException("User Not Found!", HttpStatus.NOT_FOUND));

        JobEntity job = jobRepository.findById(jobId).orElseThrow(() -> new GlobalException("Job Not Found!", HttpStatus.NOT_FOUND));

        List<JobApplicationEntity> jobApplicationEntityList = user.getJobApplicationList();

        AtomicBoolean flag = new AtomicBoolean(false);

        jobApplicationEntityList.forEach(jobApplicationEntity -> {
            if (jobApplicationEntity.getJob().getId().equals(job.getId())) {
                flag.set(true);
            }
        });

        if (flag.get()) {
            return ApiResponseMessage
                    .builder()
                    .message("Job Is Already Present!")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }

        JobApplicationEntity jobApplicationEntity = JobApplicationEntity
                .builder()
                .date(new Date())
                .job(job)
                .user(user)
                .build();

        jobApplicationEntityList.add(jobApplicationEntity);
        userRepository.save(user);

        return ApiResponseMessage
                .builder()
                .message("Job Is Added Successfully!")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public List<JobResponse> getJobList(Long userId) throws GlobalException {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new GlobalException("User Not Found!", HttpStatus.NOT_FOUND));

        List<JobApplicationEntity> jobApplicationEntityList = user.getJobApplicationList();

        return jobApplicationEntityList.stream().map(jobApplicationEntity -> JobResponse
                        .builder()
                        .id(jobApplicationEntity.getJob().getId())
                        .exp(jobApplicationEntity.getJob().getExp())
                        .count(jobApplicationEntity.getJob().getJobApplicationList().size())
                        .profile(jobApplicationEntity.getJob().getProfile())
                        .description(jobApplicationEntity.getJob().getDescription())
                        .date(jobApplicationEntity.getJob().getDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ApiResponseMessage removeJobFromJobList(Long userId, Long jobId) throws GlobalException {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new GlobalException("User Not Found!", HttpStatus.NOT_FOUND));

        JobEntity job = jobRepository.findById(jobId).orElseThrow(() -> new GlobalException("Job Not Found!", HttpStatus.NOT_FOUND));

        List<JobApplicationEntity> jobApplicationEntityList = user.getJobApplicationList();

        AtomicBoolean flag = new AtomicBoolean(true);

        jobApplicationEntityList.removeIf(jobApplicationEntity -> {
            if (jobApplicationEntity.getJob().getId().equals(job.getId())) {
                flag.set(false);
                return true;
            }
            return false;
        });

        if (flag.get()) {
            return ApiResponseMessage
                    .builder()
                    .message("Job Not Found!")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }

        userRepository.save(user);

        return ApiResponseMessage
                .builder()
                .message("Job Is Removed Successfully!")
                .httpStatus(HttpStatus.OK)
                .build();
    }


    @Override
    public ApiResponseMessage deleteUser(Long adminId) {

        UserEntity user = userRepository.findById(adminId).orElse(null);

        if (user == null) {
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("User Not Found!")
                    .build();
        }

        userRepository.deleteById(adminId);

        return ApiResponseMessage
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("User Deleted Successfully!")
                .build();
    }


    @Override
    public List<JobResponse> searchJobsInJobList(Long userId, String keyword) throws GlobalException {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new GlobalException("User Not Found!", HttpStatus.NOT_FOUND));

        List<JobApplicationEntity> jobApplicationEntityList = user.getJobApplicationList();
        List<JobResponse> jobResponseList = new ArrayList<>();

        String finalKeyword = keyword.toLowerCase();

        jobApplicationEntityList.forEach(jobEntity -> {
            if (jobEntity.getJob().getProfile().toLowerCase().contains(finalKeyword) || jobEntity.getJob().getDescription().toLowerCase().contains(finalKeyword)) {
                JobResponse jobResponse = JobResponse
                        .builder()
                        .id(jobEntity.getJob().getId())
                        .profile(jobEntity.getJob().getProfile())
                        .description(jobEntity.getJob().getDescription())
                        .count(jobEntity.getJob().getJobApplicationList().size())
                        .exp(jobEntity.getJob().getExp())
                        .date(jobEntity.getJob().getDate())
                        .build();

                jobResponseList.add(jobResponse);
            }
        });

        return jobResponseList;
    }
}
