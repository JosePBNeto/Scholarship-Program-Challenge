package jose.patricio.ScolarshipChallenge.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorDetails {
    private LocalDateTime timeStamp;
    private String message;
    private String details;
}
