package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.advices.IdNotFoundException;
import jose.patricio.ScolarshipChallenge.dtos.StudentRecord;
import jose.patricio.ScolarshipChallenge.entities.ClassEntity;
import jose.patricio.ScolarshipChallenge.entities.StudentEntity;
import jose.patricio.ScolarshipChallenge.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{

    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentRecord> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::mapToStudentRecord)
                .toList();
    }

    @Override
    public StudentRecord getStudentsById(Long id) {
        return studentRepository.findById(id)
                .map(this::mapToStudentRecord)
                .orElseThrow(() -> new IdNotFoundException("Student Id not found"));
    }

    @Override
    public StudentRecord createStudent(StudentRecord studentRecordToCreate) {
        StudentEntity studentEntity = mapToStudentEntity(studentRecordToCreate);
        studentEntity = studentRepository.save(studentEntity);

        return mapToStudentRecord(studentEntity);
    }

    @Override
    public StudentRecord updateStudent(Long id, StudentRecord updatedStudentRecord) {
        return studentRepository.findById(id)
                .map(existingStudentEntity -> updateAndSaveStudentEntity(existingStudentEntity, updatedStudentRecord))
                .map(this::mapToStudentRecord)
                .orElseThrow(() -> new IdNotFoundException("Student Id not found"));
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.findById(id)
                .ifPresentOrElse(studentEntity -> studentRepository.delete(studentEntity),
                        () -> {throw new IdNotFoundException("Student Id not found");
                });
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
        existingStudentEntity.setSquadEntity(updatedStudentRecord.squadEntity());
        existingStudentEntity.setClassEntity(updatedStudentRecord.classEntity());

        return studentRepository.save(existingStudentEntity);

    }

}