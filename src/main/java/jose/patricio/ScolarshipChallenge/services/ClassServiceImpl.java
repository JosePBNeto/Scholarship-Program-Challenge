package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.dtos.ClassRecord;
import jose.patricio.ScolarshipChallenge.entities.ClassEntity;
import jose.patricio.ScolarshipChallenge.repositories.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    @Autowired
    public ClassServiceImpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public List<ClassRecord> getAllClasses() {
        List<ClassRecord> classRecordList = new ArrayList<>();
        List<ClassEntity> classEntityList = classRepository.findAll();
        for (ClassEntity classEntity : classEntityList) {
            classRecordList.add(new ClassRecord(classEntity.getId(), classEntity.getName(), classEntity.getStatus(),
                    classEntity.getStart_date(), classEntity.getEnd_date()));
        }
        return classRecordList;
    }

    public ClassRecord getClassById(Long id) {
        Optional<ClassEntity> byId = classRepository.findById(id);
        if (byId.isPresent()){
            ClassEntity classEntity = byId.get();
            return new ClassRecord(classEntity.getId(), classEntity.getName(), classEntity.getStatus(),
                                classEntity.getStart_date(), classEntity.getEnd_date());
        }
       else {
           throw new RuntimeException("TODO");
        }
    }

    public ClassEntity createClass(ClassEntity classEntityToCreate) {
        return classRepository.save(classEntityToCreate);

    }


}
