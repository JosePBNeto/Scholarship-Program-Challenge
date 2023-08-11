package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.dtos.StudentRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
    @Override
    public List<StudentRecord> getAllStudents() {
        return null;
    }

    @Override
    public StudentRecord getStudentsById(Long id) {
        return null;
    }

    @Override
    public StudentRecord createStudent(StudentRecord studentRecordToCreate) {
        return null;
    }

    @Override
    public StudentRecord updateStudent(Long id, StudentRecord studentRecord) {
        return null;
    }

    @Override
    public void deleteStudent(Long id) {

    }
}
