package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.entities.ClassEntity;
import jose.patricio.ScolarshipChallenge.repositories.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    @Autowired
    public ClassServiceImpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public List<ClassEntity> getAllClasses() {
        return classRepository.findAll();
    }

    public ClassEntity getClassById(Long id) {
        Optional<ClassEntity> byId = classRepository.findById(id);
        return  byId.get();
    }

    public ClassEntity createClass(ClassEntity classEntityToCreate) {
        return classRepository.save(classEntityToCreate);

    }


}
