package jose.patricio.ScolarshipChallenge.advices;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.context.request.WebRequest;

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
