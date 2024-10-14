package tn.esprit.spring.test;
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
import tn.esprit.spring.controllers.PisteRestController;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.services.IPisteServices;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
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
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPiste() throws Exception {
        // Create a sample Piste object (as JSON)

        // Mock the service layer to return the created Piste
        Piste piste = new Piste();
        piste.setColor(Color.BLUE);
        piste.setLength(3000);
        piste.setSlope(20);
        piste.setNamePiste("Test");

        // Corrected pisteJson with namePiste included
        String pisteJson = "{\"namePiste\": \"" + piste.getNamePiste() + "\", \"color\": \"" + piste.getColor() + "\", \"length\": " + piste.getLength() + ", \"slope\": " + piste.getSlope() + "}";

        when(pisteRestController.addPiste(any(Piste.class))).thenReturn(piste);

        // Use MockMvc to simulate HTTP POST request
        mockMvc.perform(post("/piste/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pisteJson))
                ;


    }

    @Test
    void testGetAllPistes() throws Exception {

        List<Piste> pistes = pisteRestController.getAllPistes();

        when(pisteRestController.getAllPistes()).thenReturn(pistes);

        // Perform the GET request
        mockMvc.perform(get("/piste/all")
                        .contentType(MediaType.APPLICATION_JSON))
                ;
}




}
