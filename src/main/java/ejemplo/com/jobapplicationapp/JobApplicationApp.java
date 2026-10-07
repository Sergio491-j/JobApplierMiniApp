package ejemplo.com.jobapplicationapp;

import ejemplo.com.jobapplicationapp.service.JobApplicationService;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class JobApplicationApp implements CommandLineRunner {

    // TODO : Inyectamos el servicio donde está alojado el bot.
    private final JobApplicationService jobService;

    public static void main(String[] args) {
        SpringApplication.run(JobApplicationApp.class, args);
    }

    @Override
    public void run(String @NonNull ... args) { jobService.botMenu(); }
}
