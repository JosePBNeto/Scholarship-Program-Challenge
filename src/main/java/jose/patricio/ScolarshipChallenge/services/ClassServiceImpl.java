package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.exceptions.ClassArgumentException;
import jose.patricio.ScolarshipChallenge.exceptions.IdNotFoundException;
import jose.patricio.ScolarshipChallenge.exceptions.InvalidEnumValueException;
import jose.patricio.ScolarshipChallenge.dtos.ClassRecord;
import jose.patricio.ScolarshipChallenge.entities.ClassEntity;
import jose.patricio.ScolarshipChallenge.entities.ClassStatus;
import jose.patricio.ScolarshipChallenge.repositories.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    private static final String IDNOTFOUND = "Class Id not found";

    private final ClassRepository classRepository;
    @Autowired
    public ClassServiceImpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public List<ClassRecord> getAllClasses() {
        return classRepository.findAll().stream()
                .map(this::mapToClassRecord)
                .toList();
    }

    public ClassRecord getClassById(Long id) {
        return classRepository.findById(id)
                .map(this::mapToClassRecord)
                .orElseThrow(() -> new IdNotFoundException(IDNOTFOUND));
    }

    public ClassRecord createClass(ClassRecord classRecordToCreate) {

        if(!classRecordToCreate.status().equals(ClassStatus.WAITING)){
            throw new InvalidEnumValueException("Class must be in " + ClassStatus.WAITING + " status before created");
        }

        ClassEntity classEntity = mapToClassEntity(classRecordToCreate);
        classEntity = classRepository.save(classEntity);

        return mapToClassRecord(classEntity);
    }


    public ClassRecord updateClass(Long id, ClassRecord updatedClassRecord) {
        return classRepository.findById(id)
                .map(existingClassEntity -> updateAndSaveClassEntity(existingClassEntity, updatedClassRecord))
                .map(this::mapToClassRecord)
                .orElseThrow(() -> new IdNotFoundException(IDNOTFOUND));


    }

    public void deleteClass(Long id) {
        classRepository.findById(id)
                .ifPresentOrElse(
                        classEntity -> classRepository.delete(classEntity),
                        () -> {
                            throw new IdNotFoundException(IDNOTFOUND);
                        }
                );
    }

    @Override
    public ClassRecord startClass(Long id) {
        ClassEntity existingClassEntity = classRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(IDNOTFOUND));

        if (existingClassEntity.getStatus().equals(ClassStatus.STARTED) || existingClassEntity.getStatus().equals(ClassStatus.FINISHED)) {
            throw new ClassArgumentException("Class has already "+ existingClassEntity.getStatus());
        }

        // Validate the number of students before starting the class
        if (existingClassEntity.getStudentEntities().size() < 15 || existingClassEntity.getStudentEntities().size() > 30) {
            throw new ClassArgumentException("Number of students must be between 15 and 30 to start the class.");
        }

        existingClassEntity.setStatus(ClassStatus.STARTED);
        return mapToClassRecord(classRepository.save(existingClassEntity));
    }

    @Override
    public ClassRecord finishClass(Long id) {
        ClassEntity existingClassEntity = classRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(IDNOTFOUND));

        existingClassEntity.setStatus(ClassStatus.FINISHED);

        return mapToClassRecord(classRepository.save(existingClassEntity));

    }

    private ClassRecord mapToClassRecord(ClassEntity classEntity) {
        return new ClassRecord(
                classEntity.getId(),
                classEntity.getName(),
                classEntity.getStatus(),
                classEntity.getStart_date(),
                classEntity.getEnd_date(),
                classEntity.getOrganizers(),
                classEntity.getSquadEntities(),
                classEntity.getStudentEntities()
        );
    }
    private ClassEntity mapToClassEntity(ClassRecord classRecord) {
        return new ClassEntity(
                classRecord.id(),
                classRecord.name(),
                classRecord.status(),
                classRecord.start_date(),
                classRecord.end_date(),
                classRecord.organizers(),
                classRecord.squadEntities(),
                classRecord.studentEntities()

        );
    }
    private ClassEntity updateAndSaveClassEntity(ClassEntity existingClassEntity, ClassRecord updatedClassRecord) {
        existingClassEntity.setName(updatedClassRecord.name());
        existingClassEntity.setStatus(updatedClassRecord.status());
        existingClassEntity.setStart_date(updatedClassRecord.start_date());
        existingClassEntity.setEnd_date(updatedClassRecord.end_date());
        existingClassEntity.setOrganizers(updatedClassRecord.organizers());
        existingClassEntity.setSquadEntities(updatedClassRecord.squadEntities());
        existingClassEntity.setStudentEntities(updatedClassRecord.studentEntities());

        return classRepository.save(existingClassEntity);
    }

}
