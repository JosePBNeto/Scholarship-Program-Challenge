package jose.patricio.ScolarshipChallenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jose.patricio.ScolarshipChallenge.dtos.OrganizerRecord;
import jose.patricio.ScolarshipChallenge.services.OrganizerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = OrganizerController.class)
public class OrganizerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrganizerService organizerService;


    @Test
    void testGetOrganizerById() throws Exception {
        OrganizerRecord organizerRecord = new OrganizerRecord(
                1L, "max", "max@gmail.com", null, null);

        when(organizerService.getOrganizerById(1L)).thenReturn(organizerRecord);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/organizers/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(organizerRecord)));
    }

    @Test
    void testGetNonExistentOrganizer() throws Exception {
        when(organizerService.getOrganizerById(99L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/organizer/99"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    void testUpdateOrganizer() throws Exception {
        OrganizerRecord inputOrganizer = new OrganizerRecord(
                null, "nameTrocado", "emailtrocado@gmail.com", null, null);
        OrganizerRecord updatedOrganizer = new OrganizerRecord(
                1L, "nameTrocado", "emailtrocado@gmail.com", null, null);

        when(organizerService.updateOrganizer(eq(1L), any())).thenReturn(updatedOrganizer);

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/organizers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputOrganizer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(updatedOrganizer)));
    }

    @Test
    void testDeleteOrganizer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/organizers/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(organizerService, times(1)).deleteOrganizer(1L);
    }
}