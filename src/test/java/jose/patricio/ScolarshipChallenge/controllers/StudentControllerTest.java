package jose.patricio.ScolarshipChallenge.controllers;

import jose.patricio.ScolarshipChallenge.dtos.StudentRecord;
import jose.patricio.ScolarshipChallenge.services.StudentService;
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

public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllStudents() {
        List<StudentRecord> studentRecords = Collections.singletonList(
                new StudentRecord(1L, "neto", "neto@gmail.com", "345345345", new Date(), null, null)
        );
        when(studentService.getAllStudents()).thenReturn(studentRecords);

        ResponseEntity<List<StudentRecord>> responseEntity = studentController.getAllStudents();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(studentRecords, responseEntity.getBody());
    }

    @Test
    void testGetStudentById() {
        StudentRecord studentRecord = new StudentRecord(
                1L, "Luiz", "luiz@gmai.com", "23423423", new Date(), null, null
        );
        when(studentService.getStudentsById(1L)).thenReturn(studentRecord);

        ResponseEntity<StudentRecord> responseEntity = studentController.getStudentById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(studentRecord, responseEntity.getBody());
    }

    @Test
    void testPostStudent() {
        StudentRecord studentRecordToCreate = new StudentRecord(
                null, "Bob", "bob@gmail.com", "67890", new Date(), null, null
        );
        StudentRecord createdStudentRecord = new StudentRecord(
                2L, "Bob", "bob@gmail.com", "67890", new Date(), null, null
        );
        when(studentService.createStudent(studentRecordToCreate)).thenReturn(createdStudentRecord);


        ResponseEntity<StudentRecord> responseEntity = studentController.postStudent(studentRecordToCreate);


        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdStudentRecord, responseEntity.getBody());
    }

    @Test
    void testUpdateStudent() {

        Long existingStudentId = 1L;
        StudentRecord updatedStudentRecord = new StudentRecord(
                existingStudentId, "Updated Alice", "updated@gmail.com", "4899948487", new Date(), null, null
        );
        when(studentService.updateStudent(existingStudentId, updatedStudentRecord)).thenReturn(updatedStudentRecord);


        ResponseEntity<StudentRecord> responseEntity = studentController.updateStudent(existingStudentId, updatedStudentRecord);


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedStudentRecord, responseEntity.getBody());
    }

    @Test
    void testDeleteStudent() {
        ResponseEntity<Void> responseEntity = studentController.deleteStudent(1L);


        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(studentService, times(1)).deleteStudent(1L);
    }


}