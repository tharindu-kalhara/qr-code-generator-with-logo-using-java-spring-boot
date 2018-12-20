package com.example.qr;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
/**
 * @author kalhara
 * @version 1.0
 * @since 2018-12-21
 */
@Component
public class DeleteFile {
    @Async
    public void delete(String file) {
        try {
            Thread.sleep(5000);
            new File(file).delete();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
