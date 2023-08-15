package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.entities.ClassEntity;
import jose.patricio.ScolarshipChallenge.entities.ClassStatus;
import jose.patricio.ScolarshipChallenge.exceptions.ClassArgumentException;
import jose.patricio.ScolarshipChallenge.exceptions.IdNotFoundException;
import jose.patricio.ScolarshipChallenge.dtos.StudentRecord;
import jose.patricio.ScolarshipChallenge.entities.StudentEntity;
import jose.patricio.ScolarshipChallenge.repositories.ClassRepository;
import jose.patricio.ScolarshipChallenge.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private static final String IDNOTFOUND = "Student Id not found";
    private StudentRepository studentRepository;
    private ClassRepository classRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, ClassRepository classRepository) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
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
                .orElseThrow(() -> new IdNotFoundException(IDNOTFOUND));
    }

    @Override
    public StudentRecord createStudent(StudentRecord studentRecordToCreate) {

        ClassEntity classEntity1;

        if (studentRecordToCreate.classEntity() != null) {
             classEntity1 = classRepository.findById(studentRecordToCreate.classEntity().getId())
                    .orElseThrow(() -> new IdNotFoundException("Class Id not found"));
            if (!classEntity1.getStatus().equals(ClassStatus.WAITING)) {
                throw new ClassArgumentException("Students can only be added to a class in the 'waiting' status.");
            }
        }



        StudentEntity studentEntity = mapToStudentEntity(studentRecordToCreate);
        studentEntity = studentRepository.save(studentEntity);

        return mapToStudentRecord(studentEntity);
    }

    @Override
    public StudentRecord updateStudent(Long id, StudentRecord updatedStudentRecord) {
        return studentRepository.findById(id)
                .map(existingStudentEntity -> updateAndSaveStudentEntity(existingStudentEntity, updatedStudentRecord))
                .map(this::mapToStudentRecord)
                .orElseThrow(() -> new IdNotFoundException(IDNOTFOUND));
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.findById(id)
                .ifPresentOrElse(studentEntity -> studentRepository.delete(studentEntity),
                        () -> {throw new IdNotFoundException(IDNOTFOUND);
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
