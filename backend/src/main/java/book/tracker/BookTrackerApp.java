package book.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("book.tracker.entity")
@EnableJpaRepositories("book.tracker.repository")
public class BookTrackerApp {

    public static void main(String[] args) {
        SpringApplication.run(BookTrackerApp.class,args);
    }

}
