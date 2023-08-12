package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.dtos.ClassRecord;
import jose.patricio.ScolarshipChallenge.entities.ClassEntity;
import jose.patricio.ScolarshipChallenge.repositories.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    @Autowired
    public ClassServiceImpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public List<ClassRecord> getAllClasses() {
        return classRepository.findAll().stream()
                .map(this::mapToClassRecord)
                .collect(Collectors.toList());
    }

    public ClassRecord getClassById(Long id) {
        return classRepository.findById(id)
                .map(this::mapToClassRecord)
                .orElseThrow(() -> new RuntimeException("TODO")); // Replace "TODO" with a more appropriate exception
    }

    public ClassRecord createClass(ClassRecord classRecordToCreate) {
        ClassEntity classEntity = mapToClassEntity(classRecordToCreate);
        classEntity = classRepository.save(classEntity);

        return mapToClassRecord(classEntity);
    }


    public ClassRecord updateClass(Long id, ClassRecord updatedClassRecord) {
        return classRepository.findById(id)
                .map(existingClassEntity -> updateAndSaveClassEntity(existingClassEntity, updatedClassRecord))
                .map(this::mapToClassRecord)
                .orElseThrow(() -> new RuntimeException("Class not found")); // TODO: Replace with a more appropriate exception
    }

    public void deleteClass(Long id) {
        classRepository.findById(id)
                .ifPresentOrElse(
                        classEntity -> classRepository.delete(classEntity),
                        () -> {
                            throw new RuntimeException("Class not found"); //TODO: Replace with a more appropriate exception
                        }
                );
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
