package jose.patricio.ScolarshipChallenge.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jose.patricio.ScolarshipChallenge.entities.ClassEntity;
import jose.patricio.ScolarshipChallenge.entities.SquadEntity;

import java.util.Date;

public record StudentRecord(Long id,
                            @Size(min = 2,  message = "Name must have at least 2 characters")
                            String name,
                            @Email(message = "Email must be valid")
                            String email,
                            @Pattern(regexp = "^\\d+$", message = "Number must have only digits")
                            String number,
                            @Past(message = "Birth date must be in the past")
                            Date dateOfBirth,
                            SquadEntity squadEntity,
                            ClassEntity classEntity) {
}
