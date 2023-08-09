package jose.patricio.ScolarshipChallenge.repositories;

import jose.patricio.ScolarshipChallenge.entities.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
}
