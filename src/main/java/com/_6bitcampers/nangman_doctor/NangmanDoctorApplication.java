package com._6bitcampers.nangman_doctor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NangmanDoctorApplication {

    public static void main(String[] args) {
        SpringApplication.run(NangmanDoctorApplication.class, args);
    }
}
