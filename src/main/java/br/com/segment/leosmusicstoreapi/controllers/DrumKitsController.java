package br.com.segment.leosmusicstoreapi.controllers;

import br.com.segment.leosmusicstoreapi.dtos.DrumKitPostDto;
import br.com.segment.leosmusicstoreapi.models.DrumKit;
import br.com.segment.leosmusicstoreapi.repositories.DrumKitRepository;
import com.sun.istack.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/drum-kits")
@CrossOrigin
public class DrumKitsController {

    @Autowired
    private DrumKitRepository drumkitRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<?> getAllDrums() {
        return new ResponseEntity<>(drumkitRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneDrum(@PathVariable @NotNull Long id) {
        return new ResponseEntity<>(drumkitRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addDrum(@RequestBody DrumKitPostDto drumKitPostDto) {
        DrumKit drumkit = modelMapper.map(drumKitPostDto, DrumKit.class);
        drumkitRepository.save(drumkit);
        return new ResponseEntity<>(drumkit, HttpStatus.CREATED);
    }
}
