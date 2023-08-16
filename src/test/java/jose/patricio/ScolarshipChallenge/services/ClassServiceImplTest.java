package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.dtos.ClassRecord;
import jose.patricio.ScolarshipChallenge.entities.ClassEntity;
import jose.patricio.ScolarshipChallenge.entities.ClassStatus;
import jose.patricio.ScolarshipChallenge.exceptions.IdNotFoundException;
import jose.patricio.ScolarshipChallenge.repositories.ClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClassServiceImplTest {

    @Mock
    private ClassRepository classRepository;

    @InjectMocks
    private ClassServiceImpl classService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllClasses() {
        List<ClassEntity> classEntities = List.of(
                new ClassEntity(1L, "Spring Boot", ClassStatus.WAITING, new Date(), new Date(), null, null, null),
                new ClassEntity(2L, "React", ClassStatus.STARTED, new Date(), new Date(), null, null, null)
        );
        when(classRepository.findAll()).thenReturn(classEntities);

        List<ClassRecord> classRecords = classService.getAllClasses();

        assertEquals(2, classRecords.size());
    }

    @Test
    void testGetClassById() {
        ClassEntity classEntity = new ClassEntity(
                1L, "Spring Boot", ClassStatus.WAITING, new Date(), new Date(), null, null, null
        );
        when(classRepository.findById(1L)).thenReturn(Optional.of(classEntity));

        ClassRecord classRecord = classService.getClassById(1L);

        assertEquals("Spring Boot", classRecord.name());
        assertEquals(ClassStatus.WAITING, classRecord.status());
    }


    @Test
    void testCreateClass() {
        ClassRecord classRecordToCreate = new ClassRecord(
                null, "Spring Boot", ClassStatus.WAITING, new Date(), new Date(), null, null, null
        );
        ClassEntity createdClassEntity = new ClassEntity(
                1L, "Spring Boot", ClassStatus.WAITING, new Date(), new Date(), null, null, null
        );
        when(classRepository.save(any(ClassEntity.class))).thenReturn(createdClassEntity);

        ClassRecord createdClassRecord = classService.createClass(classRecordToCreate);


        assertEquals("Spring Boot", createdClassRecord.name());
        assertEquals(ClassStatus.WAITING, createdClassRecord.status());
    }
    @Test
    public void testDeleteClassWithIdNotFound() {
        when(classRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> classService.deleteClass(123L));

        verify(classRepository).findById(123L);
        verify(classRepository, never()).delete(any());
    }



}