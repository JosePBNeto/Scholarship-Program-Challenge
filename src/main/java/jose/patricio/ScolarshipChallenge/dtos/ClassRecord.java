package jose.patricio.ScolarshipChallenge.dtos;

import jose.patricio.ScolarshipChallenge.entities.ClassStatus;
import jose.patricio.ScolarshipChallenge.entities.OrganizerEntity;

import java.util.Date;
import java.util.List;

public record ClassRecord(Long id, String name, ClassStatus status,
                          Date start_date, Date end_date, List<OrganizerEntity> organizers) {
}
