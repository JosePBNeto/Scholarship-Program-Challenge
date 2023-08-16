package jose.patricio.ScolarshipChallenge.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Size;
import jose.patricio.ScolarshipChallenge.entities.ClassEntity;
import jose.patricio.ScolarshipChallenge.entities.StudentEntity;

import java.util.List;

public record SquadRecord(Long id,
                          @Size(min = 2,  message = "Squad name must have at least 2 characters")
                          String name,
                          ClassEntity classEntity,
                          @JsonIgnoreProperties("squadEntity")
                          List<StudentEntity> studentEntities) {
}
