package bluetix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BluetixApplication {

	public static void main(String[] args) {
		SpringApplication.run(BluetixApplication.class, args);
	}

}
