package skrobman.dev.springstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import skrobman.dev.springstart.config.APIConfig;

@SpringBootApplication
public class SpringStartApplication {

    public static void main(String[] args) {
        APIConfig.LoadEnv();
        SpringApplication.run(SpringStartApplication.class, args);
    }
}
