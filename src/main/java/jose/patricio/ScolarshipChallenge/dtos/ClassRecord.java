package jose.patricio.ScolarshipChallenge.dtos;

import jose.patricio.ScolarshipChallenge.entities.ClassStatus;

import java.util.Date;

public record ClassRecord(Long id, String name, ClassStatus status, Date start_date, Date end_date) {
}
