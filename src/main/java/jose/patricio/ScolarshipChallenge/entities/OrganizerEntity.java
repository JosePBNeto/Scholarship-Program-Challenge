package jose.patricio.ScolarshipChallenge.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;

    @Enumerated(value = EnumType.STRING)
    private OrganizerRole role;

    @ManyToMany(mappedBy = "organizers")
    @JsonIgnoreProperties("organizers")
    @JsonIgnore
    private List<ClassEntity> classes;

}
