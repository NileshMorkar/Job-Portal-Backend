package com.example.JobPortal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "jobs")
public class JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    private String profile;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private int exp;

    @Column(nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    private AdminEntity admin;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    List<JobApplicationEntity> jobApplicationList;

}
