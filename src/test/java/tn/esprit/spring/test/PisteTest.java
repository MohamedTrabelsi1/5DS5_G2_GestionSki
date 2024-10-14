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


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


    }


    @Test
    void testGetById() throws Exception {
        // Mock the service layer to return a specific Piste
        Piste piste = new Piste();
        piste.setNumPiste(1L);


        when(pisteRestController.getById(piste.getNumPiste())).thenReturn(piste);

        // Perform the GET request with path variable
        mockMvc.perform(get("/piste/get/"+piste.getNumPiste())
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }
    @Test
    void testDeleteById() throws Exception {
        Piste p ;
        List<Piste> pistes = pisteServices.retrieveAllPistes();
        System.out.println(pistes);
        p=pistes.get(0);

        // Perform the DELETE request with path variable
        mockMvc.perform(delete("/piste/delete/"+ p.getNumPiste())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // Verify that the service method was called

    }

}
