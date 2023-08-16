package jose.patricio.ScolarshipChallenge.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "class_organizers")
public class ClassOrganizersEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "Class_id")
    private ClassEntity classEntity;

    @Id
    @ManyToOne
    @JoinColumn(name = "Organizer_id")
    private OrganizerEntity organizerEntity;

}
