package tn.esprit.spring;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import tn.esprit.spring.controllers.PisteRestController;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.services.IPisteServices;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {GestionStationSkiApplication.class})
public class PisteTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IPisteServices pisteServices;

    @InjectMocks
    private PisteRestController pisteRestController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAddPiste() throws Exception {
        Piste piste = new Piste(); // Set properties as needed
        when(pisteServices.addPiste(any(Piste.class))).thenReturn(piste);

        mockMvc.perform(post("/piste/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(piste)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(piste.getNumPiste())); // Adjust based on your Piste properties
    }

    

    @Test
    void testGetById() throws Exception {
        Long id = 1L; // Example ID
        Piste piste = new Piste(); // Set properties as needed
        when(pisteServices.retrievePiste(id)).thenReturn(piste);

        mockMvc.perform(get("/piste/get/{id-piste}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(piste.getNumPiste())); // Adjust based on your Piste properties
    }

    @Test
    void testDeleteById() throws Exception {
        Long id = 1L; // Example ID
        doNothing().when(pisteServices).removePiste(id);

        mockMvc.perform(delete("/piste/delete/{id-piste}", id))
                .andExpect(status().isOk());

        verify(pisteServices, times(1)).removePiste(id); // Verify the service method was called
    }
}
