package br.com.segment.leosmusicstoreapi.controllers;

import br.com.segment.leosmusicstoreapi.components.SegmentHelper;
import br.com.segment.leosmusicstoreapi.dtos.ManufacturerPostDto;
import br.com.segment.leosmusicstoreapi.dtos.outputs.UserOutput;
import br.com.segment.leosmusicstoreapi.models.Manufacturer;
import br.com.segment.leosmusicstoreapi.repositories.ManufacturerRepository;
import com.sun.istack.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/manufacturers")
@CrossOrigin
public class ManufacturersController {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SegmentHelper segmentHelper;

    @GetMapping("")
    public ResponseEntity<?> getAllManufacturers() {
        Iterable<Manufacturer> manufacturers = manufacturerRepository.findAllWithDrumKits();
        return new ResponseEntity<>(manufacturers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneManufacturer(@PathVariable @NotNull Long id) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(id);
        if (manufacturer.isEmpty()) {
            return new ResponseEntity<>(manufacturer, HttpStatus.NOT_FOUND);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserOutput principal = (UserOutput) authentication.getPrincipal();

        Map<String, String> properties = new HashMap<>();
        properties.put("manufacturer", manufacturer.get().getName());

        segmentHelper.trackEvent("Visited Manufacturer", principal.getId().toString(), properties);
        return new ResponseEntity<>(manufacturer, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addManufacturer(@RequestBody ManufacturerPostDto manufacturerPostDto) {
        Manufacturer manufacturer = modelMapper.map(manufacturerPostDto, Manufacturer.class);
        manufacturerRepository.save(manufacturer);
        return new ResponseEntity<>(manufacturer, HttpStatus.CREATED);
    }
}
