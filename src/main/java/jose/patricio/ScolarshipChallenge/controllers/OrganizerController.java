package jose.patricio.ScolarshipChallenge.controllers;

import jakarta.validation.Valid;
import jose.patricio.ScolarshipChallenge.dtos.OrganizerRecord;
import jose.patricio.ScolarshipChallenge.services.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/organizers")
public class OrganizerController {

    OrganizerService organizerService;

    @Autowired
    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @GetMapping
    public ResponseEntity<List<OrganizerRecord>> getAllOrganizers() {
        List<OrganizerRecord> organizersRecordList = organizerService.getAllOrganizers();
        return new ResponseEntity<>(organizersRecordList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizerRecord> getOrganizerById(@PathVariable Long id) {
        OrganizerRecord OrganizerById = organizerService.getOrganizerById(id);
        return new ResponseEntity<>(OrganizerById, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrganizerRecord> postOrganizer(@Valid @RequestBody OrganizerRecord organizerRecord) {
        return ResponseEntity.created(null).body(organizerService.createOrganizer(organizerRecord));
    }
    @PutMapping("/{id}")
    ResponseEntity<OrganizerRecord> updateOrganizer(@PathVariable Long id, @Valid @RequestBody OrganizerRecord organizerRecord) {
        OrganizerRecord organizerRecordReceived = organizerService.updateOrganizer(id, organizerRecord);
        return new ResponseEntity<>(organizerRecordReceived, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizer(@PathVariable Long id) {
        organizerService.deleteOrganizer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
