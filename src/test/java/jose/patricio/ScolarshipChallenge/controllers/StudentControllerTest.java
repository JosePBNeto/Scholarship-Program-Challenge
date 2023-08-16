package jose.patricio.ScolarshipChallenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jose.patricio.ScolarshipChallenge.dtos.StudentRecord;
import jose.patricio.ScolarshipChallenge.services.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(controllers = StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;

    @Test
    void testGetAllStudents() throws Exception {
        List<StudentRecord> studentRecords = Collections.singletonList(
                new StudentRecord(
                        1L, "neto", "neto@gmail.com", null, null, null, null)
        );

        when(studentService.getAllStudents()).thenReturn(studentRecords);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/students"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(studentRecords)));
    }
    @Test
    void testInvalidRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/lalala"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testInvalidUpdateRequest() throws Exception {
        StudentRecord inputStudent = new StudentRecord(
                null, "", "invalidemail", null, null, null, null);

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputStudent)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void testGetStudentById() throws Exception {
        StudentRecord studentRecord = new StudentRecord(
                1L, "Luiz", "luiz@gmail.com", null, null, null, null);

        when(studentService.getStudentsById(1L)).thenReturn(studentRecord);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/students/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(studentRecord)));
    }

    @Test
    void testPostStudent() throws Exception {
        StudentRecord inputStudent = new StudentRecord(
                null, "Pedro", "pedro@gmail.com", null, null, null, null);
        StudentRecord createdStudent = new StudentRecord(1L, "Pedro", "pedro@gmail.com", null, null, null, null);

        when(studentService.createStudent(any())).thenReturn(createdStudent);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputStudent)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(createdStudent)));
    }


}