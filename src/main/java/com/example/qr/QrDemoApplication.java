package com.example.qr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author kalhara
 * @version 1.0
 * @since 2018-12-21
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
public class QrDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(QrDemoApplication.class, args);
    }

}

