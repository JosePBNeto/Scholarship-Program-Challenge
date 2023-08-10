package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.dtos.SquadRecord;
import jose.patricio.ScolarshipChallenge.entities.SquadEntity;
import jose.patricio.ScolarshipChallenge.repositories.SquadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SquadServiceImpl implements  SquadService {

    private final SquadRepository squadRepository;

    @Autowired
    public SquadServiceImpl(SquadRepository squadRepository) {
        this.squadRepository = squadRepository;
    }

    public List<SquadRecord> getAllSquads() {
        return squadRepository.findAll().stream()
                .map(this::mapToSquadRecord)
                .collect(Collectors.toList());
    }


    public SquadRecord createSquad(SquadRecord squadRecordToCreate) {
        SquadEntity squadEntity = mapToSquadEntity(squadRecordToCreate);
        squadEntity = squadRepository.save(squadEntity);

        return mapToSquadRecord(squadEntity);

    }

    @Override
    public SquadRecord getSquadById(Long id) {
        return null;
    }

    private SquadEntity mapToSquadEntity(SquadRecord squadRecord) {
        return new SquadEntity(
             squadRecord.id(),
             squadRecord.name(),
             squadRecord.classEntity()
        );
    }
    private SquadRecord mapToSquadRecord(SquadEntity squadEntity){
        return new SquadRecord(
                squadEntity.getId(),
                squadEntity.getName(),
                squadEntity.getClassEntity()
        );
    }


}
