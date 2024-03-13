package com.example.JobPortal.controller;

import com.example.JobPortal.exception.GlobalException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class HomeController {


    @RequestMapping("/")
    public void homePage(HttpServletResponse httpServletResponse) throws GlobalException {
        try {
            httpServletResponse.sendRedirect("/swagger-ui.html");
        } catch (Exception exception) {
            throw new GlobalException("Page Not Found Exception!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "OK !!!";
    }

}
