package jose.patricio.ScolarshipChallenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jose.patricio.ScolarshipChallenge.dtos.ClassRecord;
import jose.patricio.ScolarshipChallenge.entities.ClassStatus;
import jose.patricio.ScolarshipChallenge.services.ClassService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ClassController.class)
public class ClassControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClassService classService;

    @Test
    void testGetAllClasses() throws Exception {
        List<ClassRecord> classRecords = Collections.singletonList(
                new ClassRecord(1L, "Spring Boot", ClassStatus.WAITING, new Date(), new Date(), null, null, null)
        );
        when(classService.getAllClasses()).thenReturn(classRecords);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/classes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(classRecords)));
    }

    @Test
    void testGetClassById() throws Exception {
        ClassRecord classRecord = new ClassRecord(
                1L, "Spring Boot", ClassStatus.WAITING, new Date(), new Date(), null, null, null
        );
        when(classService.getClassById(1L)).thenReturn(classRecord);

        mockMvc.perform(MockMvcRequestBuilders.get("/tv1/classes/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(classRecord)));
    }
}