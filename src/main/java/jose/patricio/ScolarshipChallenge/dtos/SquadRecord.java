package jose.patricio.ScolarshipChallenge.dtos;

import jose.patricio.ScolarshipChallenge.entities.ClassEntity;

public record SquadRecord(Long id, String name, ClassEntity classEntity) {
}
