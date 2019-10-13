package me.skrib.messages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication
@ActiveProfiles("test")
public class SkribMessagesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkribMessagesApplication.class, args);
    }

}
