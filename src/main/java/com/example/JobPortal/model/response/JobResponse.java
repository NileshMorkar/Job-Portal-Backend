package com.example.JobPortal.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobResponse {

    private Long id;
    private String profile;

    private String description;
    private int exp;

    private int count;

    private Date date;

//    private AdminResponse admin;
//    private List<UserResponse> users;
}
