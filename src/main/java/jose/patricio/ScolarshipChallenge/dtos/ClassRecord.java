package jose.patricio.ScolarshipChallenge.dtos;

import jose.patricio.ScolarshipChallenge.entities.ClassStatus;
import jose.patricio.ScolarshipChallenge.entities.OrganizerEntity;
import jose.patricio.ScolarshipChallenge.entities.SquadEntity;
import jose.patricio.ScolarshipChallenge.entities.StudentEntity;

import java.util.Date;
import java.util.List;

public record ClassRecord(Long id, String name,
                          ClassStatus status,
                          Date start_date,
                          Date end_date,
                          List<OrganizerEntity> organizers,
                          List<SquadEntity> squadEntities,
                          List<StudentEntity> studentEntities)

{
}
