package jose.patricio.ScolarshipChallenge.dtos;

import jose.patricio.ScolarshipChallenge.entities.ClassEntity;
import jose.patricio.ScolarshipChallenge.entities.StudentEntity;

import java.util.List;

public record SquadRecord(Long id,
                          String name,
                          ClassEntity classEntity,
                          List<StudentEntity> studentEntities) {
}
