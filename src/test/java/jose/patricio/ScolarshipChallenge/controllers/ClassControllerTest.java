package jose.patricio.ScolarshipChallenge.controllers;

import jose.patricio.ScolarshipChallenge.dtos.ClassRecord;
import jose.patricio.ScolarshipChallenge.entities.ClassStatus;
import jose.patricio.ScolarshipChallenge.services.ClassService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClassControllerTest {

    @Mock
    private ClassService classService;

    @InjectMocks
    private ClassController classController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllClasses() {
        List<ClassRecord> classRecords = Collections.singletonList(
                new ClassRecord(1L, "Spring Boot", ClassStatus.WAITING, new Date(), new Date(), null, null, null)
        );
        when(classService.getAllClasses()).thenReturn(classRecords);

        ResponseEntity<List<ClassRecord>> responseEntity = classController.getAllClasses();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(classRecords, responseEntity.getBody());
    }

    @Test
    void testGetClassById() {
        ClassRecord classRecord = new ClassRecord(
                1L, "Spring Boot", ClassStatus.WAITING, new Date(), new Date(), null, null, null
        );
        when(classService.getClassById(1L)).thenReturn(classRecord);

        ResponseEntity<ClassRecord> responseEntity = classController.getClassById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(classRecord, responseEntity.getBody());
    }


}