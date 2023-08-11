package jose.patricio.ScolarshipChallenge.dtos;

import jose.patricio.ScolarshipChallenge.entities.OrganizerRole;

import java.util.List;

public record OrganizerRecord(Long id,
                              String name,
                              String email,
                              OrganizerRole organizerRole,
                              List<ClassRecord> classRecordList

                              ) {

}
