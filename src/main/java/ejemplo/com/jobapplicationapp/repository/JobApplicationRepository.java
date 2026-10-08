package ejemplo.com.jobapplicationapp.repository;

import ejemplo.com.jobapplicationapp.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {

    // boolean seeApplicationExistenceByUrl(String url);

}
