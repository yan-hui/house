package lone.wolf.house;


import lone1.wolf.house.autoconfig.EnableHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableHttpClient
@EnableAsync
public class HouseWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseWebApplication.class, args);
    }
}
