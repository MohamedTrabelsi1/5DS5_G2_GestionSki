package tn.esprit.spring.test;

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
        // Create a sample Piste object
        Piste piste = new Piste();
        piste.setNumPiste(Long.valueOf(3)); // Set an example ID or other properties
        piste.setColor(Color.BLUE);
        piste.setLength(20);
        piste.setSlope(20);

        // Mock the service layer to return this object when called
        when(pisteServices.addPiste(any(Piste.class))).thenReturn(piste);

        // Perform the POST request (without using JSON)
        mockMvc.perform(post("/piste/add")
                        .param("name", "Piste Example") // Example parameters, adjust based on your controller's method
                        .param("length", "3000"))
                .andExpect(status().isOk())
                .andExpect(content().string("Piste added successfully")); // Expect a success message or proper handling
    }

    @Test
    void testGetById() throws Exception {
        Long id = Long.valueOf(2); // Example ID

        // Create a sample Piste object to be returned when retrieved
        Piste piste = new Piste();
        piste.setNumPiste(id);
        piste.setNamePiste("Test Piste");

        // Mock the service layer
        when(pisteServices.retrievePiste(id)).thenReturn(piste);

        // Perform the GET request (without using JSON path)
        mockMvc.perform(get("/piste/get/{id-piste}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Piste ID: 1, Name: Test Piste")); // Adjust to match your expected output
    }

    @Test
    void testDeleteById() throws Exception {
        Long id = Long.valueOf(3);// Example ID

        // Mock the service to do nothing when delete is called
        doNothing().when(pisteServices).removePiste(id);

        // Perform the DELETE request
        mockMvc.perform(delete("/piste/delete/{id-piste}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Piste deleted successfully")); // Expect success message or similar output

        // Verify the service method was called
        verify(pisteServices, times(1)).removePiste(id);
    }
}
