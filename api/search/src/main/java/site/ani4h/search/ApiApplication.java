package site.ani4h.search;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }, scanBasePackages = {"site.ani4h.search", "site.ani4h.shared"})
public class ApiApplication {

    public static void main(String[] args) { SpringApplication.run(ApiApplication.class, args); }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        System.out.println();
        return args -> {
            System.out.println("Let's inspect the beans provided by Spring Boot:");
        };
    }
}
