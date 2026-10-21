package com.ai_project.dataart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class DataartApplication {

    public static void main(String[] args) {
        // Це має бути першим рядком
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        System.setProperty("user.timezone", "UTC");
        SpringApplication.run(DataartApplication.class, args);
    }
}