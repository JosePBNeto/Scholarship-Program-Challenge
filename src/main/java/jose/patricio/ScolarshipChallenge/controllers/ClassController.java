package jose.patricio.ScolarshipChallenge.controllers;


import jakarta.validation.Valid;
import jose.patricio.ScolarshipChallenge.dtos.ClassRecord;
import jose.patricio.ScolarshipChallenge.services.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping
    public ResponseEntity<List<ClassRecord>> getAllClasses() {
        List<ClassRecord> classRecordList = classService.getAllClasses();
       return new ResponseEntity<>(classRecordList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassRecord> getClassById(@PathVariable Long id) {
        ClassRecord classById = classService.getClassById(id);
        return new ResponseEntity<>(classById, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClassRecord> postClass(@Valid @RequestBody ClassRecord classRecord) {
        return ResponseEntity.created(null).body(classService.createClass(classRecord));
    }

    @PutMapping("/{id}")
    ResponseEntity<ClassRecord> updateClass(@PathVariable Long id, @Valid @RequestBody ClassRecord classRecord) {
        ClassRecord classRecordRecived = classService.updateClass(id, classRecord);
        return new ResponseEntity<>(classRecordRecived, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
