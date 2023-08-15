package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.dtos.OrganizerRecord;
import jose.patricio.ScolarshipChallenge.entities.OrganizerEntity;
import jose.patricio.ScolarshipChallenge.entities.OrganizerRole;
import jose.patricio.ScolarshipChallenge.exceptions.IdNotFoundException;
import jose.patricio.ScolarshipChallenge.repositories.ClassRepository;
import jose.patricio.ScolarshipChallenge.repositories.OrganizerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrganizerServiceImplTest {

    @Mock
    private OrganizerRepository organizerRepository;
    @Mock
    private ClassRepository classRepository;
    @InjectMocks
    private OrganizerServiceImpl organizerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllOrganizers() {
        List<OrganizerEntity> organizerEntities = List.of(
                new OrganizerEntity(1L, "Pedro", "pedro@gmail.com", OrganizerRole.COORDINATOR, null),
                new OrganizerEntity(2L, "Bob", "bob@gmail.com", OrganizerRole.INSTRUCTOR, null)
        );
        when(organizerRepository.findAll()).thenReturn(organizerEntities);

        List<OrganizerRecord> organizerRecords = organizerService.getAllOrganizers();

        assertEquals(2, organizerRecords.size());
    }

    @Test
    void testGetOrganizerById() {
        OrganizerEntity organizerEntity = new OrganizerEntity(
                1L, "Neto", "neto@gmail.com", OrganizerRole.SCRUMMASTER, new ArrayList<>()
        );
        when(organizerRepository.findById(1L)).thenReturn(Optional.of(organizerEntity));

        OrganizerRecord organizerRecord = organizerService.getOrganizerById(1L);

        assertEquals("Neto", organizerRecord.name());
        assertEquals("neto@gmail.com", organizerRecord.email());
    }


    @Test
    void testDeleteOrganizer() {
        OrganizerEntity organizerEntityToDelete = new OrganizerEntity(
                1L, "Alice", "alice@gmail.com", OrganizerRole.SCRUMMASTER, null
        );
        when(organizerRepository.findById(1L)).thenReturn(Optional.of(organizerEntityToDelete));

        assertDoesNotThrow(() -> organizerService.deleteOrganizer(1L));

        verify(organizerRepository, times(1)).delete(organizerEntityToDelete);
    }
    @Test
    void testGetOrganizerByIdNotFound() {
        Long nonExistentOrganizerId = 100L;
        when(organizerRepository.findById(nonExistentOrganizerId)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> organizerService.getOrganizerById(nonExistentOrganizerId));
    }
    @Test
    void testUpdateOrganizer() {
        Long existingOrganizerId = 1L;
        OrganizerEntity existingOrganizerEntity = new OrganizerEntity(existingOrganizerId, "Alice", "alice@gmail.com",null,null);
        OrganizerRecord updatedOrganizerRecord = new OrganizerRecord(existingOrganizerId, "Updated Alice", "updated@gmail.com", null, null);

        when(organizerRepository.findById(existingOrganizerId)).thenReturn(Optional.of(existingOrganizerEntity));
        when(organizerRepository.save(any())).thenReturn(existingOrganizerEntity);

        OrganizerRecord updatedRecord = organizerService.updateOrganizer(existingOrganizerId, updatedOrganizerRecord);


        assertEquals(updatedOrganizerRecord.name(), updatedRecord.name());
        assertEquals(updatedOrganizerRecord.email(), updatedRecord.email());

        verify(organizerRepository, times(1)).save(existingOrganizerEntity);
    }
}

