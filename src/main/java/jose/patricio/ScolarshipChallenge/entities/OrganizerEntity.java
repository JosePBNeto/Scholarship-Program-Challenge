package jose.patricio.ScolarshipChallenge.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organizer")
public class OrganizerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Enumerated(value = EnumType.STRING)
    private OrganizerRole role;

    @ManyToMany(mappedBy = "organizers")
    @JsonIgnoreProperties("organizers")
    private List<ClassEntity> classes;

}
