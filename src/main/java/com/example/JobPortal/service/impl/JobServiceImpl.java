package com.example.JobPortal.service.impl;

import com.example.JobPortal.entity.JobEntity;
import com.example.JobPortal.model.response.JobResponse;
import com.example.JobPortal.repository.JobRepository;
import com.example.JobPortal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public List<JobResponse> getAllJobs() {

        List<JobEntity> jobEntityList = jobRepository.findAll();

        return jobEntityList.stream().map(jobEntity -> JobResponse
                .builder()
                .description(jobEntity.getDescription())
                .id(jobEntity.getId())
                .count(jobEntity.getJobApplicationList().size())
                .exp(jobEntity.getExp())
                .profile(jobEntity.getProfile())
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<JobResponse> searchJobsForUser(String keyword) {
        List<JobEntity> jobEntityList = jobRepository.searchJobsForUser(keyword);

        return jobEntityList.stream().map(jobEntity -> JobResponse
                .builder()
                .description(jobEntity.getDescription())
                .id(jobEntity.getId())
                .count(jobEntity.getJobApplicationList().size())
                .exp(jobEntity.getExp())
                .profile(jobEntity.getProfile())
                .build()).collect(Collectors.toList());
    }
}
