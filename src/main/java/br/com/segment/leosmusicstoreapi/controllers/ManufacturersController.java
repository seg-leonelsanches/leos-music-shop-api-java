package br.com.segment.leosmusicstoreapi.controllers;

import br.com.segment.leosmusicstoreapi.dtos.ManufacturerPostDto;
import br.com.segment.leosmusicstoreapi.models.Manufacturer;
import br.com.segment.leosmusicstoreapi.repositories.ManufacturerRepository;
import com.sun.istack.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/manufacturers")
public class ManufacturersController {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<?> getAllManufacturers() {
        return new ResponseEntity<>(manufacturerRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneManufacturer(@PathVariable @NotNull Long id) {
        return new ResponseEntity<>(manufacturerRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addManufacturer(@RequestBody ManufacturerPostDto manufacturerPostDto) {
        Manufacturer manufacturer = modelMapper.map(manufacturerPostDto, Manufacturer.class);
        manufacturerRepository.save(manufacturer);
        return new ResponseEntity<>(manufacturer, HttpStatus.CREATED);
    }
}
