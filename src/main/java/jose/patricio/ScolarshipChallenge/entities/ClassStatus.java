package jose.patricio.ScolarshipChallenge.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import jose.patricio.ScolarshipChallenge.exceptions.InvalidEnumValueException;

public enum ClassStatus {
    WAITING, STARTED, FINISHED;





    @JsonCreator
    public static ClassStatus fromString(String value) {
        try {
            return ClassStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidEnumValueException(
                    "Invalid Class Status: " + value + ". Please select: WAITING, STARTED, FINISHED");
        }
    }

}
