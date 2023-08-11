package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.dtos.StudentRecord;
import jose.patricio.ScolarshipChallenge.entities.ClassEntity;
import jose.patricio.ScolarshipChallenge.entities.StudentEntity;
import jose.patricio.ScolarshipChallenge.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

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

    private StudentRecord mapToStudentRecord(StudentEntity studentEntity) {
        return new StudentRecord(
                studentEntity.getId(),
                studentEntity.getName(),
                studentEntity.getEmail(),
                studentEntity.getNumber(),
                studentEntity.getDateOfBirth(),
                studentEntity.getSquadEntity(),
                studentEntity.getClassEntity()
        );
    }

    private StudentEntity mapToStudentEntity(StudentRecord studentRecord) {
        return new StudentEntity(
                studentRecord.id(),
                studentRecord.name(),
                studentRecord.email(),
                studentRecord.number(),
                studentRecord.dateOfBirth(),
                studentRecord.squadEntity(),
                studentRecord.classEntity()
        );
    }

    private StudentEntity updateAndSaveStudentEntity(StudentEntity existingStudentEntity, StudentRecord updatedStudentRecord){
        existingStudentEntity.setName(updatedStudentRecord.name());
        existingStudentEntity.setEmail(updatedStudentRecord.email());
        existingStudentEntity.setNumber(updatedStudentRecord.number());
        existingStudentEntity.setDateOfBirth(updatedStudentRecord.dateOfBirth());
        existingStudentEntity.setSquadEntity(existingStudentEntity.getSquadEntity());
        existingStudentEntity.setClassEntity(existingStudentEntity.getClassEntity());

        return studentRepository.save(existingStudentEntity);

    }

}
