package org.ktc2.cokaen.wouldyouin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WouldYouInApplication {

    public static void main(String[] args) {
        SpringApplication.run(WouldYouInApplication.class, args);
    }

}
