package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.dtos.SquadRecord;

import java.util.List;


public interface SquadService {
    List<SquadRecord> getAllSquads();

    SquadRecord getSquadById(Long id);

    SquadRecord createSquad(SquadRecord squadRecord);
}
