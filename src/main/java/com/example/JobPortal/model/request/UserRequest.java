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
public class UserRequest {

    @NotEmpty(message = "Name Should Not Be Empty!!")
    String name;

    @NotEmpty(message = "Email Should Not Be Empty!!")
    String email;

    @NotEmpty(message = "Password Should Not Be Empty!!")
    private String password;
}
