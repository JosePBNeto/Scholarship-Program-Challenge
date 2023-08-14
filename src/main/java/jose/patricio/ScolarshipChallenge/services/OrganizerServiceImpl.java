package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.advices.IdNotFoundException;
import jose.patricio.ScolarshipChallenge.dtos.OrganizerRecord;
import jose.patricio.ScolarshipChallenge.entities.OrganizerEntity;
import jose.patricio.ScolarshipChallenge.repositories.OrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrganizerServiceImpl implements OrganizerService{

    private static final String IDNOTFOUND = "Organizer Id not found";
    private OrganizerRepository organizerRepository;

    @Autowired
    public OrganizerServiceImpl(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }


    public List<OrganizerRecord> getAllOrganizers() {
        return organizerRepository.findAll().stream()
                .map(this::mapToOrganizerRecord)
                .toList();
    }


    public OrganizerRecord getOrganizerById(Long id) {
        return organizerRepository.findById(id)
                .map(this::mapToOrganizerRecord)
                .orElseThrow(() -> new IdNotFoundException(IDNOTFOUND));

    }


    public OrganizerRecord createOrganizer(OrganizerRecord organizerRecordToCreate) {
        OrganizerEntity organizerEntity =  mapToOrganizerEntity(organizerRecordToCreate);
        organizerEntity = organizerRepository.save(organizerEntity);

        return mapToOrganizerRecord(organizerEntity);
    }


    public OrganizerRecord updateOrganizer(Long id, OrganizerRecord organizerRecordRecord) {
        return organizerRepository.findById(id)
                .map(extistingOrganizerEntity -> updateAndSaveOrganizerEntity(extistingOrganizerEntity, organizerRecordRecord))
                .map(this::mapToOrganizerRecord)
                .orElseThrow(() -> new IdNotFoundException(IDNOTFOUND));
    }


    public void deleteOrganizer(Long id) {
        organizerRepository.findById(id)
                .ifPresentOrElse(
                        organizerEntity -> organizerRepository.delete(organizerEntity),
                        () -> {
                            throw new IdNotFoundException(IDNOTFOUND);
                        }
                );
    }

    private OrganizerRecord mapToOrganizerRecord(OrganizerEntity organizerEntity) {
        return new OrganizerRecord(
                organizerEntity.getId(),
                organizerEntity.getName(),
                organizerEntity.getEmail(),
                organizerEntity.getRole(),
                organizerEntity.getClasses()
        );
    }

    private OrganizerEntity mapToOrganizerEntity(OrganizerRecord organizerRecord) {
        return new OrganizerEntity(
                organizerRecord.id(),
                organizerRecord.name(),
                organizerRecord.email(),
                organizerRecord.organizerRole(),
                organizerRecord.classEntityList()
        );
    }

    private OrganizerEntity updateAndSaveOrganizerEntity(OrganizerEntity existingOrganizerEntity, OrganizerRecord updatedOrganizerRecord){
        existingOrganizerEntity.setName(updatedOrganizerRecord.name());
        existingOrganizerEntity.setEmail(updatedOrganizerRecord.email());
        existingOrganizerEntity.setRole(updatedOrganizerRecord.organizerRole());
        existingOrganizerEntity.setClasses(updatedOrganizerRecord.classEntityList());

        return organizerRepository.save(existingOrganizerEntity);
    }

}
