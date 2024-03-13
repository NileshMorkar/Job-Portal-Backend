package com.example.JobPortal.service.impl;


import com.example.JobPortal.entity.AdminEntity;
import com.example.JobPortal.entity.JobApplicationEntity;
import com.example.JobPortal.entity.JobEntity;
import com.example.JobPortal.entity.UserEntity;
import com.example.JobPortal.exception.GlobalException;
import com.example.JobPortal.model.request.AdminRequest;
import com.example.JobPortal.model.request.JobRequest;
import com.example.JobPortal.model.response.AdminResponse;
import com.example.JobPortal.model.response.ApiResponseMessage;
import com.example.JobPortal.model.response.JobResponse;
import com.example.JobPortal.model.response.UserResponse;
import com.example.JobPortal.repository.AdminRepository;
import com.example.JobPortal.repository.JobRepository;
import com.example.JobPortal.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    JobRepository jobRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ApiResponseMessage createAdmin(AdminRequest adminRequest) {

        AdminEntity admin = adminRepository.findByEmail(adminRequest.getEmail()).orElse(null);

        if (admin != null) {
            return ApiResponseMessage
                    .builder()
                    .message("Admin Is Already Present!")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }

        admin = AdminEntity
                .builder()
                .email(adminRequest.getEmail())
                .name(adminRequest.getName())
                .password(adminRequest.getPassword())
                .jobs(new ArrayList<>())
                .build();
        adminRepository.save(admin);

        return ApiResponseMessage
                .builder()
                .message("Admin Is Created Successfully!")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public AdminResponse updateAdminNamePassword(Long adminId, AdminRequest adminRequest) throws GlobalException {

        AdminEntity admin = adminRepository.findById(adminId).orElseThrow(() -> new GlobalException("Admin Not Found!", HttpStatus.NOT_FOUND));

        admin.setPassword(adminRequest.getPassword());
        admin.setName(adminRequest.getName());

        adminRepository.save(admin);
        return modelMapper.map(admin, AdminResponse.class);
    }

    @Override
    public List<JobResponse> getAllJobs(Long adminId) throws GlobalException {

        try {
            AdminEntity admin = adminRepository.findById(adminId).orElseThrow(() -> new GlobalException("Admin Not Found!", HttpStatus.NOT_FOUND));

            List<JobEntity> jobEntityList = admin.getJobs();

            return jobEntityList.stream().map(jobEntity -> JobResponse
                    .builder()
                    .profile(jobEntity.getProfile())
                    .exp(jobEntity.getExp())
                    .description(jobEntity.getDescription())
                    .count(jobEntity.getJobApplicationList().size())
                    .id(jobEntity.getId())
                    .build()).toList();

        } catch (Exception exception) {
            throw new GlobalException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponseMessage deleteJobById(Long adminId, Long jobId) throws GlobalException {

        AdminEntity admin = adminRepository.findById(adminId).orElseThrow(() -> new GlobalException("Admin Not Found!", HttpStatus.NOT_FOUND));

        JobEntity jobEntity = jobRepository.findById(jobId).orElseThrow(() -> new GlobalException("Job Not Found!", HttpStatus.NOT_FOUND));

        if (jobEntity.getAdmin().getId().equals(adminId)) {
            admin.getJobs().remove(jobEntity);
            adminRepository.save(admin);
            return ApiResponseMessage
                    .builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Job Deleted Successfully!")
                    .build();
        }

        return ApiResponseMessage
                .builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message("Job Not Found!")
                .build();
    }

    @Override
    public ApiResponseMessage createNewJob(Long adminId, JobRequest jobRequest) throws GlobalException {

        AdminEntity admin = adminRepository.findById(adminId).orElseThrow(() -> new GlobalException("Admin Not Found!", HttpStatus.NOT_FOUND));

        JobEntity jobEntity = JobEntity
                .builder()
                .exp(jobRequest.getExp())
                .profile(jobRequest.getProfile())
                .jobApplicationList(new ArrayList<>())
                .date(new Date())
                .description(jobRequest.getDescription())
                .admin(admin)
                .build();

        jobRepository.save(jobEntity);

        return ApiResponseMessage
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Job Created Successfully!")
                .build();
    }


    //************************
    @Override
    public List<UserResponse> getAllCandidatesOfJobById(Long adminId, Long jobId) throws GlobalException {

        AdminEntity admin = adminRepository.findById(adminId).orElseThrow(() -> new GlobalException("Admin Not Found!", HttpStatus.NOT_FOUND));

        JobEntity jobEntity = jobRepository.findById(jobId).orElseThrow(() -> new GlobalException("Job Not Found!", HttpStatus.NOT_FOUND));

        if (jobEntity.getAdmin().getId().equals(adminId)) {
            List<UserEntity> userEntityList = jobEntity.getJobApplicationList().stream().map(JobApplicationEntity::getUser).toList();
            return userEntityList.stream().map(userEntity -> UserResponse
                            .builder()
                            .id(userEntity.getId())
                            .name(userEntity.getName())
                            .email(userEntity.getEmail())
                            .build())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<JobResponse> searchJobsForAdmin(Long adminId, String keyword) throws GlobalException {

        AdminEntity admin = adminRepository.findById(adminId).orElseThrow(() -> new GlobalException("Admin Not Found!", HttpStatus.NOT_FOUND));

        List<JobEntity> jobEntityList = jobRepository.searchJobsForAdmin(admin.getId(), keyword);

        return jobEntityList.stream().map(jobEntity -> JobResponse
                .builder()
                .profile(jobEntity.getProfile())
                .exp(jobEntity.getExp())
                .description(jobEntity.getDescription())
                .count(jobEntity.getJobApplicationList().size())
                .id(jobEntity.getId())
                .build()).toList();
    }


//    @Override
//    public ApiResponseMessage deleteAdmin(Long adminId) throws GlobalException {
//
//        AdminEntity admin = adminRepository.findById(adminId).orElse(null);
//
//        if (admin == null) {
//            return ApiResponseMessage
//                    .builder()
//                    .httpStatus(HttpStatus.NOT_FOUND)
//                    .message("Admin Not Found!")
//                    .build();
//        }
//
//        List<Long> jobIds = admin.getJobs().stream().map(JobEntity::getId).toList();
//        for (Long jobId : jobIds) {
//            deleteJobById(admin.getId(), jobId);
//        }
//

//        adminRepository.deleteById(adminId);
//
//        return ApiResponseMessage
//                .builder()
//                .httpStatus(HttpStatus.OK)
//                .message("Admin Deleted Successfully!")
//                .build();
//    }

}
