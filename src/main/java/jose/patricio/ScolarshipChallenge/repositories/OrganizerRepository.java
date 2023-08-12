package jose.patricio.ScolarshipChallenge.repositories;

import jose.patricio.ScolarshipChallenge.entities.OrganizerEntity;
import jose.patricio.ScolarshipChallenge.entities.SquadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizerRepository extends JpaRepository<OrganizerEntity, Long> {
}
