package jose.patricio.ScolarshipChallenge.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Size;
import jose.patricio.ScolarshipChallenge.entities.ClassStatus;
import jose.patricio.ScolarshipChallenge.entities.OrganizerEntity;
import jose.patricio.ScolarshipChallenge.entities.SquadEntity;
import jose.patricio.ScolarshipChallenge.entities.StudentEntity;

import java.util.Date;
import java.util.List;

public record ClassRecord(Long id,
                          @Size(min = 2,  message = "Name must have at least 2 characters")
                          String name,
                          ClassStatus status,

                          Date start_date,
                          Date end_date,
                          List<OrganizerEntity> organizers,
                          @JsonIgnore
                          List<SquadEntity> squadEntities,
                          @Size(max = 30, message = "The class must have at least 15 students and at most 30 students")
                          List<StudentEntity> studentEntities)

{
}
