package com.bc.xx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * MainApplication
 *
 * @author xiaobc
 * @date 17/5/30
 */
@SpringBootApplication
@ComponentScan
public class MainApplication {




    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
