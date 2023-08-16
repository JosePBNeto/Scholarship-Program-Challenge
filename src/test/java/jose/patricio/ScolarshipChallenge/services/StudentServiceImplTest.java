package jose.patricio.ScolarshipChallenge.services;

import jose.patricio.ScolarshipChallenge.dtos.StudentRecord;
import jose.patricio.ScolarshipChallenge.entities.ClassEntity;
import jose.patricio.ScolarshipChallenge.entities.ClassStatus;
import jose.patricio.ScolarshipChallenge.entities.StudentEntity;
import jose.patricio.ScolarshipChallenge.exceptions.IdNotFoundException;
import jose.patricio.ScolarshipChallenge.repositories.ClassRepository;
import jose.patricio.ScolarshipChallenge.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private ClassRepository classRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllStudents() {
        List<StudentEntity> studentEntities = List.of(
                new StudentEntity(1L, "neto", "neto@gmail.com", "4899988848", null, null, null),
                new StudentEntity(2L, "Bob", "bob@gmail.com", "88847478", null, null, null)
        );
        when(studentRepository.findAll()).thenReturn(studentEntities);


        List<StudentRecord> studentRecords = studentService.getAllStudents();


        assertEquals(2, studentRecords.size());

        assertEquals(1L, studentRecords.get(0).id());
        assertEquals("neto", studentRecords.get(0).name());
        assertEquals("4899988848", studentRecords.get(0).number());

        assertEquals(2L, studentRecords.get(1).id());
        assertEquals("bob@gmail.com", studentRecords.get(1).email());

    }

    @Test
    void testGetStudentsById() {
        StudentEntity studentEntity = new StudentEntity(1L, "Alice", "alice@gmail.com", "8895958689", null, null, null);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(studentEntity));


        StudentRecord studentRecord = studentService.getStudentsById(1L);


        assertEquals("Alice", studentRecord.name());
        assertEquals("alice@gmail.com", studentRecord.email());
    }


    @Test
    void testCreateStudent() {
        ClassEntity classEntity = new ClassEntity(null, "A", ClassStatus.WAITING, null, null, null, null, null);
        StudentRecord studentRecordToCreate = new StudentRecord(1L, "Eve", "eve@gmail.com", "54321", null, null, null);
        StudentEntity createdStudentEntity = new StudentEntity(2L, "Eve", "eve@gmail.com", "54321", null, null, null);

         when(classRepository.findById(1L)).thenReturn(Optional.of(classEntity));
         when(studentRepository.save(any(StudentEntity.class))).thenReturn(createdStudentEntity);


        StudentRecord createdStudentRecord = studentService.createStudent(studentRecordToCreate);


        assertEquals("Eve", createdStudentRecord.name());
        assertEquals("eve@gmail.com", createdStudentRecord.email());




    }


    @Test
    void testDeleteStudent() {
        StudentEntity studentEntityToDelete = new StudentEntity(1L, "Alice", "alice@gmail.com", "12345", null, null, null);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(studentEntityToDelete));


        assertDoesNotThrow(() -> studentService.deleteStudent(1L));

        verify(studentRepository, times(1)).delete(studentEntityToDelete);
    }

    @Test
    void testDeleteStudentNotFound() {
        Long nonExistentStudentId = 999L;
        when(studentRepository.findById(nonExistentStudentId)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> studentService.deleteStudent(nonExistentStudentId));

        verify(studentRepository, never()).delete(any());
    }

}