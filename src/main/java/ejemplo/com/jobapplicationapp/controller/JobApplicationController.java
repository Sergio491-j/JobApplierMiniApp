package ejemplo.com.jobapplicationapp.controller;

import ejemplo.com.jobapplicationapp.model.JobApplication;
import ejemplo.com.jobapplicationapp.service.JobApplicationService;
import lombok.AllArgsConstructor;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:63342")
@AllArgsConstructor
public class JobApplicationController {

    private JobApplicationService service;

    @GetMapping("/listaURLs")
    public ResponseEntity<List<JobApplication>> getURL() {
        if (service.getList() != null) return ResponseEntity.ok(service.getList());
        else return ResponseEntity
                .noContent()
                .build();
    }
}
