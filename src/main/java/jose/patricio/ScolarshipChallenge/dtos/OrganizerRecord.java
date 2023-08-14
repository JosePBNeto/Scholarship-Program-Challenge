package jose.patricio.ScolarshipChallenge.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jose.patricio.ScolarshipChallenge.entities.ClassEntity;
import jose.patricio.ScolarshipChallenge.entities.OrganizerRole;

import java.util.List;

public record OrganizerRecord(Long id,
                              @Size(min = 2,  message = "Name must have at least 2 characters")
                              String name,
                              @Email(message = "Email must be valid")
                              String email,
                              OrganizerRole organizerRole,
                              List<ClassEntity> classEntityList

                              ) {

}
