package jose.patricio.ScolarshipChallenge.dtos;

import jose.patricio.ScolarshipChallenge.entities.ClassEntity;
import jose.patricio.ScolarshipChallenge.entities.SquadEntity;

import java.util.Date;

public record StudantRecord(Long id, String name, String email, String number,
                            Date dateOfBirth, SquadEntity squadEntity, ClassEntity classEntity) {
}
