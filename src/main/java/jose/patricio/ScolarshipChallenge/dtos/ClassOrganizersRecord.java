package jose.patricio.ScolarshipChallenge.dtos;

import jose.patricio.ScolarshipChallenge.entities.ClassEntity;
import jose.patricio.ScolarshipChallenge.entities.OrganizerEntity;

public record ClassOrganizersRecord(ClassEntity classEntity, OrganizerEntity organizerEntity) {
}
