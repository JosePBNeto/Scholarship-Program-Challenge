package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.dtos.SquadRecord;
import jose.patricio.ScolarshipChallenge.entities.SquadEntity;
import jose.patricio.ScolarshipChallenge.repositories.SquadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SquadServiceImpl implements  SquadService {

    private final SquadRepository squadRepository;

    @Autowired
    public SquadServiceImpl(SquadRepository squadRepository) {
        this.squadRepository = squadRepository;
    }

    public List<SquadRecord> getAllSquads() {
        List<SquadRecord> squadRecordList = new ArrayList<>();
        List<SquadEntity> squadEntityList = squadRepository.findAll();
        for (SquadEntity squadEntity : squadEntityList) {
            squadRecordList.add(new SquadRecord(squadEntity.getId(), squadEntity.getName(), squadEntity.getClassEntity()));
        }
        return squadRecordList;
    }


    public SquadRecord createSquad(SquadRecord squadRecord) {
        SquadEntity squadEntity = new SquadEntity(squadRecord.id(), squadRecord.name(), squadRecord.classEntity());
        squadRepository.save(squadEntity);

        return new SquadRecord(squadEntity.getId(), squadEntity.getName(), squadEntity.getClassEntity());

    }

    @Override
    public SquadRecord getSquadById(Long id) {
        return null;
    }

}
