package edu.rutgers.cs336;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("edu.rutgers.cs336")
public class Cs336Application {
    public static void main(String[] args) {
        SpringApplication.run(Cs336Application.class, args);
    }
}
