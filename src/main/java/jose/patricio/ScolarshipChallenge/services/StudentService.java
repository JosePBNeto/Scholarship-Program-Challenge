package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.dtos.StudentRecord;

import java.util.List;

public interface StudentService {

    List<StudentRecord> getAllStudents();
    StudentRecord getStudentsById(Long id);
    StudentRecord createStudent(StudentRecord studentRecordToCreate);
    StudentRecord updateStudent(Long id, StudentRecord studentRecord);
    void deleteStudent(Long id);

}
