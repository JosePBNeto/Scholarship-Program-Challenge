package jose.patricio.ScolarshipChallenge.controllers;

import jose.patricio.ScolarshipChallenge.dtos.OrganizerRecord;
import jose.patricio.ScolarshipChallenge.services.OrganizerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrganizerControllerTest {

    @Mock
    private OrganizerService organizerService;

    @InjectMocks
    private OrganizerController organizerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllOrganizers() {
        List<OrganizerRecord> organizersRecordList = Collections.singletonList(
                new OrganizerRecord(1L, "Joao", "jao@gmail.com", null, null)
        );
        when(organizerService.getAllOrganizers()).thenReturn(organizersRecordList);

        ResponseEntity<List<OrganizerRecord>> responseEntity = organizerController.getAllOrganizers();


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(organizersRecordList, responseEntity.getBody());
    }

    @Test
    void testGetOrganizerById() {
        OrganizerRecord organizerRecord = new OrganizerRecord(1L, "pedro", "peu@gmail.com", null, null);
        when(organizerService.getOrganizerById(1L)).thenReturn(organizerRecord);

        ResponseEntity<OrganizerRecord> responseEntity = organizerController.getOrganizerById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(organizerRecord, responseEntity.getBody());
    }

    @Test
    void testPostOrganizer() {
        OrganizerRecord organizerRecordToCreate = new OrganizerRecord(null, "jose", "ze@gmail.com", null, null);
        OrganizerRecord createdOrganizerRecord = new OrganizerRecord(2L, "lula", "luiz@@gmail.com", null, null);
        when(organizerService.createOrganizer(organizerRecordToCreate)).thenReturn(createdOrganizerRecord);

        ResponseEntity<OrganizerRecord> responseEntity = organizerController.postOrganizer(organizerRecordToCreate);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdOrganizerRecord, responseEntity.getBody());
    }

    @Test
    void testUpdateOrganizer() {
        Long existingOrganizerId = 1L;
        OrganizerRecord updatedOrganizerRecord = new OrganizerRecord(existingOrganizerId, "Updated Jao", "updated@gmail.com", null, null);
        when(organizerService.updateOrganizer(existingOrganizerId, updatedOrganizerRecord)).thenReturn(updatedOrganizerRecord);

        ResponseEntity<OrganizerRecord> responseEntity = organizerController.updateOrganizer(existingOrganizerId, updatedOrganizerRecord);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedOrganizerRecord, responseEntity.getBody());
    }

    @Test
    void testDeleteOrganizer() {
        ResponseEntity<Void> responseEntity = organizerController.deleteOrganizer(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(organizerService, times(1)).deleteOrganizer(1L);
    }

}
