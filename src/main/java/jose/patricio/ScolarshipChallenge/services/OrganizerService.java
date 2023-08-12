package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.dtos.OrganizerRecord;

import java.util.List;

public interface OrganizerService {
    List<OrganizerRecord> getAllOrganizers();

    OrganizerRecord getOrganizerById(Long id);

    OrganizerRecord createOrganizer(OrganizerRecord organizerToCreate);

    OrganizerRecord updateOrganizer(Long id, OrganizerRecord organizerToUpdate);
    void deleteOrganizer(Long id);
}
