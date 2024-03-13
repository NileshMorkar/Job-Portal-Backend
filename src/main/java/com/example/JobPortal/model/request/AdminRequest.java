package com.example.JobPortal.model.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminRequest {

    @NotEmpty(message = "Name Should Not Be Empty!!")
    private String name;

    @NotEmpty(message = "Email Should Not Be Empty!!")
    private String email;


    @Size(min = 6, message = "Password Must Be of Minimum 6 Characters")
    private String password;

}
