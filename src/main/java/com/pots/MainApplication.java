package com.pots;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.pots")
@MapperScan("com.pots.**.mapper")
public class MainApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
    
}
