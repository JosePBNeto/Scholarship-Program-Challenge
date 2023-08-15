package jose.patricio.ScolarshipChallenge.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import jose.patricio.ScolarshipChallenge.exceptions.InvalidEnumValueException;

public enum OrganizerRole {
    SCRUMMASTER, COORDINATOR, INSTRUCTOR;




    @JsonCreator
    public static OrganizerRole fromString(String value) {
        try{
            return OrganizerRole.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidEnumValueException(
                    "Invalid Organzier Role: " + value + ". Please select: SCRUMMASTER, COORDINATOR, INSTRUCTOR");
        }
    }

}
