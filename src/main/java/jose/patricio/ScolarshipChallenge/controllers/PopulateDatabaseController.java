package jose.patricio.ScolarshipChallenge.controllers;

import jose.patricio.ScolarshipChallenge.entities.ClassEntity;
import jose.patricio.ScolarshipChallenge.entities.OrganizerEntity;
import jose.patricio.ScolarshipChallenge.entities.OrganizerRole;
import jose.patricio.ScolarshipChallenge.entities.StudentEntity;
import jose.patricio.ScolarshipChallenge.exceptions.ClassArgumentException;
import jose.patricio.ScolarshipChallenge.exceptions.IdNotFoundException;
import jose.patricio.ScolarshipChallenge.repositories.ClassRepository;
import jose.patricio.ScolarshipChallenge.repositories.OrganizerRepository;
import jose.patricio.ScolarshipChallenge.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


@RestController
@RequestMapping("/v1/populate")
public class PopulateDatabaseController {

    private OrganizerRepository organizerRepository;

    private ClassRepository classRepository;

    private StudentRepository studentRepository;

    Random random = new Random();

    @Autowired
    public PopulateDatabaseController(OrganizerRepository organizerRepository, ClassRepository classRepository, StudentRepository studentRepository) {
        this.organizerRepository = organizerRepository;
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
    }

    @PostMapping
    public ResponseEntity<String> populateDatabase() {
        return populateDatabaseWith14StudendsAndOrganizers();
    }

    @PostMapping("/class/{id}/numberOfStudents/{numberOfStudents}")
    public ResponseEntity<String> populateClassWithNumberOfStudents(@PathVariable Long id, @PathVariable Long numberOfStudents ) {
        return populateClassWithStudents(id, numberOfStudents);
    }




















    private ResponseEntity<String> populateClassWithStudents(Long id, Long number) {

        ClassEntity existingClassEntity = classRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Class Id not Found"));

        if (existingClassEntity.getStudentEntities().size() + number > 30 && number > 0) {
            throw new ClassArgumentException("Max of 30 students in a class");
        }

        try {

            for (int i = 0; i < number; i++){

                int randomInt = random.nextInt(10000);

                StudentEntity studentEntity = new StudentEntity();


                studentEntity.setName("Student " + randomInt);
                studentEntity.setEmail("student"+randomInt+"@gmail.com");
                studentEntity.setNumber(randomInt+ "232234");
                studentEntity.setSquadEntity(null);
                studentEntity.setClassEntity(existingClassEntity);


                studentRepository.save(studentEntity);

            }
            return ResponseEntity.ok("Database populated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error populating database: " + e.getMessage());
        }
    }


    private ResponseEntity<String> populateDatabaseWith14StudendsAndOrganizers() {
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
