package SKT.seletctshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling
public class SeletctshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeletctshopApplication.class, args);
	}

}
