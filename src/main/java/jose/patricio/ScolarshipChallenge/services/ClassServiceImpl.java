package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.entities.*;
import jose.patricio.ScolarshipChallenge.exceptions.ClassArgumentException;
import jose.patricio.ScolarshipChallenge.exceptions.IdNotFoundException;
import jose.patricio.ScolarshipChallenge.exceptions.InvalidEnumValueException;
import jose.patricio.ScolarshipChallenge.dtos.ClassRecord;
import jose.patricio.ScolarshipChallenge.repositories.ClassRepository;
import jose.patricio.ScolarshipChallenge.repositories.OrganizerRepository;
import jose.patricio.ScolarshipChallenge.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {

    private static final String IDNOTFOUND = "Class Id not found";

    private final ClassRepository classRepository;

    private final OrganizerRepository organizerRepository;

    private final StudentRepository studentRepository;

    @Autowired
    public ClassServiceImpl(ClassRepository classRepository, OrganizerRepository organizerRepository, StudentRepository studentRepository) {
        this.classRepository = classRepository;
        this.organizerRepository = organizerRepository;
        this.studentRepository = studentRepository;
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

        validateOrganizerIdIfNotNull(classRecordToCreate);

        if(!classRecordToCreate.status().equals(ClassStatus.WAITING)){
            throw new InvalidEnumValueException("Class must be in " + ClassStatus.WAITING + " status before created");
        }

        ClassEntity classEntity = mapToClassEntity(classRecordToCreate);
        classEntity = classRepository.save(classEntity);

        return mapToClassRecord(classEntity);
    }


    public ClassRecord updateClass(Long id, ClassRecord updatedClassRecord) {

        validateOrganizerIdIfNotNull(updatedClassRecord);

        return classRepository.findById(id)
                .map(existingClassEntity -> updateAndSaveClassEntity(existingClassEntity, updatedClassRecord))
                .map(this::mapToClassRecord)
                .orElseThrow(() -> new IdNotFoundException(IDNOTFOUND));


    }

    public void deleteClass(Long id) {
        classRepository.findById(id)
                .ifPresentOrElse(
                        classEntity -> {

                            List<StudentEntity> studentsToUpdate = studentRepository.findByClassEntity(classEntity);

                            studentsToUpdate.forEach(student -> {
                                student.setClassEntity(null);

                                studentRepository.save(student);

                            });

                            classRepository.delete(classEntity);
                        },
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

        if (existingClassEntity.getStudentEntities().size() < 15 || existingClassEntity.getStudentEntities().size() > 30) {
            throw new ClassArgumentException("Number of students must be between 15 and 30 to start the class.");
        }

        validateIfHasAllOrganizersNeeded(existingClassEntity.getOrganizers());

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


    public void validateIfHasAllOrganizersNeeded(List<OrganizerEntity> organizers) {
        Map<OrganizerRole, Long> roleCounts = organizers.stream()
                .collect(Collectors.groupingBy(
                        OrganizerEntity::getRole,
                        Collectors.counting()
                ));

        long coordinatorCount = roleCounts.getOrDefault(OrganizerRole.COORDINATOR, 0L);
        long smCount = roleCounts.getOrDefault(OrganizerRole.SCRUMMASTER, 0L);
        long instructorCount = roleCounts.getOrDefault(OrganizerRole.INSTRUCTOR, 0L);

        if (coordinatorCount < 1 || smCount < 1 || instructorCount < 3) {
            throw new ClassArgumentException("To start the class you need 1 coordinator, 1 scrum master and 3 instructors.");
        }
    }

    public void validateOrganizerIdIfNotNull(ClassRecord classRecord) {
        Optional.ofNullable(classRecord.organizers())
                .ifPresent(organizerEntities -> organizerEntities.stream()
                        .map(OrganizerEntity::getId)
                        .forEach(organizerId -> organizerRepository.findById(organizerId)
                                .orElseThrow(() -> new IdNotFoundException("Organizer Id not found"))));
    }


}
