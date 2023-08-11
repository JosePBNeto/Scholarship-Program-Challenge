package jose.patricio.ScolarshipChallenge.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "class")
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private ClassStatus status;
    @Temporal(TemporalType.DATE)
    private Date start_date;
    @Temporal(TemporalType.DATE)
    private Date end_date;

    @ManyToMany
    @JoinTable(name = "class-organizers",
            joinColumns = @JoinColumn(name = "Class_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "Organizer_id", referencedColumnName = "id"))
    @JsonIgnoreProperties("classes")
    private List<OrganizerEntity> organizers;


    @OneToMany(mappedBy = "classEntity")
    @JsonIgnore
    private List<SquadEntity> squadEntities;


}