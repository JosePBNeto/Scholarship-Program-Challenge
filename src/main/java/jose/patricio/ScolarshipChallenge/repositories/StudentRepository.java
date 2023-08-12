package jose.patricio.ScolarshipChallenge.repositories;

import jose.patricio.ScolarshipChallenge.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
