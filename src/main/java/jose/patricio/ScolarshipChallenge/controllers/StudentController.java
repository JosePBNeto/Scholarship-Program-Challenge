package jose.patricio.ScolarshipChallenge.controllers;

import jakarta.validation.Valid;
import jose.patricio.ScolarshipChallenge.dtos.StudentRecord;
import jose.patricio.ScolarshipChallenge.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/students")
public class StudentController {

    StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public ResponseEntity<List<StudentRecord>> getAllStudents() {
        List<StudentRecord> studentRecordList = studentService.getAllStudents();
        return new ResponseEntity<>(studentRecordList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentRecord> getStudentById(@PathVariable Long id) {
        StudentRecord studentById = studentService.getStudentsById(id);
        return new ResponseEntity<>(studentById, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StudentRecord> postStudent(@Valid @RequestBody StudentRecord studentRecord) {
        return ResponseEntity.created(null).body(studentService.createStudent(studentRecord));
    }

    @PutMapping("/{id}")
    ResponseEntity<StudentRecord> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRecord studentRecord) {
        StudentRecord studentRecordReceived = studentService.updateStudent(id, studentRecord);
        return new ResponseEntity<>(studentRecordReceived, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
