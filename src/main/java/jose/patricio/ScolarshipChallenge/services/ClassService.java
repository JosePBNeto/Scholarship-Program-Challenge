package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.dtos.ClassRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassService {

    List<ClassRecord> getAllClasses();

    ClassRecord getClassById(Long id);

    ClassRecord createClass(ClassRecord classRecordToCreate);

    ClassRecord updateClass(Long id, ClassRecord classRecord);
    void deleteClass(Long id);
}
