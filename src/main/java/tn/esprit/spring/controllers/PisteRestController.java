package tn.esprit.spring.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.services.IPisteServices;

import java.util.List;

@Tag(name = "\uD83C\uDFBF Piste Management")
@RestController
@RequestMapping("/piste")
@RequiredArgsConstructor
public class PisteRestController {


    private final IPisteServices pisteServices;
    private static final Logger logger = LogManager.getLogger(Piste.class);

    @Operation(description = "Add Piste")
    @PostMapping("/add")
    public Piste addPiste(@RequestBody Piste piste){
        logger.info("Piste Added !");
        return  pisteServices.addPiste(piste);
    }
    @Operation(description = "Retrieve all Pistes")
    @GetMapping("/all")
    public List<Piste> getAllPistes(){

        logger.info("Pistes Retrieved ! ");
        return pisteServices.retrieveAllPistes();
    }

    @Operation(description = "Retrieve Piste by Id")
    @GetMapping("/get/{id-piste}")
    public Piste getById(@PathVariable("id-piste") Long numPiste){
        logger.info("Piste Retrieved !");
        return pisteServices.retrievePiste(numPiste);
    }

    @Operation(description = "Delete Piste by Id")
    @DeleteMapping("/delete/{id-piste}")
    public void deleteById(@PathVariable("id-piste") Long numPiste){
        logger.info("Piste Deleted !");
        pisteServices.removePiste(numPiste);
    }
    

}
