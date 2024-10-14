package tn.esprit.spring.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.GestionStationSkiApplication;
import tn.esprit.spring.controllers.PisteRestController;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.services.IPisteServices;

@SpringBootTest(classes = GestionStationSkiApplication.class)
@AutoConfigureMockMvc
public class PisteTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IPisteServices pisteServices;

    @InjectMocks
    private PisteRestController pisteRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPiste() throws Exception {
        // Create a sample Piste object (as JSON)
        String pisteJson = "{\"numPiste\": 3, \"color\": \"BLUE\", \"length\": 3000, \"slope\": 20}";

        // Mock the service layer to return the created Piste
        Piste piste = new Piste();
        piste.setNumPiste(3L);
        piste.setColor(Color.BLUE);
        piste.setLength(3000);
        piste.setSlope(20);

        when(pisteServices.addPiste(any(Piste.class))).thenReturn(piste);

        // Use MockMvc to simulate HTTP POST request
        mockMvc.perform(post("/piste/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pisteJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numPiste").value(3))
                .andExpect(jsonPath("$.color").value("BLUE"))
                .andExpect(jsonPath("$.length").value(3000))
                .andExpect(jsonPath("$.slope").value(20));

        // Verify that the service method was called
        verify(pisteServices, times(1)).addPiste(any(Piste.class));
    }




}
