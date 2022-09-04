package com.dyf;

import com.dyf.annotation.JwtAnnotation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@JwtAnnotation.PassToken
@SpringBootApplication
public class CanteenManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanteenManagementApplication.class, args);
    }

}
