package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.dtos.ClassRecord;
import jose.patricio.ScolarshipChallenge.entities.ClassEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassService {

    List<ClassRecord> getAllClasses();

    ClassRecord getClassById(Long id);

    ClassEntity createClass(ClassEntity classEntityToCreate);

}
