package jose.patricio.ScolarshipChallenge.controllers;


import jose.patricio.ScolarshipChallenge.dtos.ClassRecord;
import jose.patricio.ScolarshipChallenge.entities.ClassEntity;
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
    public ClassRecord getClassById(@PathVariable Long id) {
        return classService.getClassById(id);
    }

    @PostMapping
    public ResponseEntity<ClassRecord> postCar(@RequestBody ClassRecord classRecord) {
        return ResponseEntity.created(null).body(classService.createClass(classRecord));
    }



}
