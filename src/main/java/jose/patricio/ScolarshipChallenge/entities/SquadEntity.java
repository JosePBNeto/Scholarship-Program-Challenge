package jose.patricio.ScolarshipChallenge.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Squad")
public class SquadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "Class_id")
    @JsonIgnore
    private ClassEntity classEntity;

    @OneToMany(mappedBy = "squadEntity")
    @JsonIgnore
    private List<StudentEntity> studentEntities;

}
