package iba.proton.therapy.sensors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SensorsRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensorsRestApplication.class, args);
    }

}
