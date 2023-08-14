package jose.patricio.ScolarshipChallenge.controllers;

import jose.patricio.ScolarshipChallenge.entities.OrganizerEntity;
import jose.patricio.ScolarshipChallenge.entities.OrganizerRole;
import jose.patricio.ScolarshipChallenge.entities.StudentEntity;
import jose.patricio.ScolarshipChallenge.repositories.OrganizerRepository;
import jose.patricio.ScolarshipChallenge.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping("/v1/populate")
public class PopulateDatabaseController {

    private OrganizerRepository organizerRepository;

    private StudentRepository studentRepository;

    @Autowired
    public PopulateDatabaseController(OrganizerRepository organizerRepository, StudentRepository studentRepository) {
        this.organizerRepository = organizerRepository;
        this.studentRepository = studentRepository;
    }

    @PostMapping
    public ResponseEntity<String> populateDatabase() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = dateFormat.parse("1995-05-10");

            for (int i = 1; i <= 3; i++) {
                OrganizerEntity coordinator = new OrganizerEntity();

                coordinator.setName("Coordinator " + i);
                coordinator.setEmail("coordinator" +i+ "@compass.com");
                coordinator.setRole(OrganizerRole.COORDINATOR);
                coordinator.setClasses(null);

                organizerRepository.save(coordinator);
            }

            for (int i = 1; i <= 3; i++) {
                OrganizerEntity sm = new OrganizerEntity();

                sm.setName("Scrum Master " + i);
                sm.setEmail("scrummaster" +i+ "@compass.com");
                sm.setRole(OrganizerRole.SCRUMMASTER);
                sm.setClasses(null);

                organizerRepository.save(sm);
            }

            for (int i = 1; i <= 3; i++) {
                OrganizerEntity instructor = new OrganizerEntity();

                instructor.setName("Instructor " + i);
                instructor.setEmail("instructor" +i+ "@compass.com");
                instructor.setRole(OrganizerRole.INSTRUCTOR);
                instructor.setClasses(null);

                organizerRepository.save(instructor);
            }


            for (int i = 1; i <= 14; i++) {
                StudentEntity studentEntity = new StudentEntity();

                studentEntity.setName("Student "+ i);
                studentEntity.setEmail("student"+i+"@compass.com");
                studentEntity.setNumber("489998848"+i);
                studentEntity.setClassEntity(null);
                studentEntity.setSquadEntity(null);
                studentEntity.setDateOfBirth(birthDate);

                studentRepository.save(studentEntity);
            }


            return ResponseEntity.ok("Database populated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error populating database: " + e.getMessage());
        }
    }


}
