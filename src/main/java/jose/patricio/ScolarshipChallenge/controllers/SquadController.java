package jose.patricio.ScolarshipChallenge.controllers;

import jose.patricio.ScolarshipChallenge.dtos.ClassRecord;
import jose.patricio.ScolarshipChallenge.dtos.SquadRecord;
import jose.patricio.ScolarshipChallenge.services.SquadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/squads")
public class SquadController {


    @Autowired
    private SquadService squadService;

    @GetMapping
    public ResponseEntity<List<SquadRecord>> getAllSquads() {
        List<SquadRecord> squadRecords = squadService.getAllSquads();
        return new ResponseEntity<>(squadRecords, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SquadRecord> postCar(@RequestBody SquadRecord squadRecord) {
        System.out.println(squadRecord);
        return ResponseEntity.created(null).body(squadService.createSquad(squadRecord));
    }


}
