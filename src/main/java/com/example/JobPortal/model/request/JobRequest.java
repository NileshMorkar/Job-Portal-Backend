package com.example.JobPortal.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobRequest {

    @NotEmpty(message = "Profile Should Not Be Empty!!")
    private String profile;

    @NotEmpty(message = "Description Should Not Be Empty!!")
    private String description;

    //    @Size(min = 0, max = 20, message = "Experience Must Be In The Range 0 - 20")
    private Integer exp;

}
