package jose.patricio.ScolarshipChallenge.dtos;

import jose.patricio.ScolarshipChallenge.entities.OrganizerRole;

public record OrganizerRecord(Long id, String name, String email, OrganizerRole organizerRole) {
}
