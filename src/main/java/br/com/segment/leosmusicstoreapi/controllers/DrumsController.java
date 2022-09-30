package br.com.segment.leosmusicstoreapi.controllers;

import br.com.segment.leosmusicstoreapi.dtos.DrumPostDto;
import br.com.segment.leosmusicstoreapi.models.Drumkit;
import br.com.segment.leosmusicstoreapi.repositories.DrumkitRepository;
import com.sun.istack.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/drums")
public class DrumsController {

    @Autowired
    private DrumkitRepository drumkitRepository;

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
    public ResponseEntity<?> addDrum(@RequestBody DrumPostDto drumPostDto) {
        Drumkit drumkit = modelMapper.map(drumPostDto, Drumkit.class);
        drumkitRepository.save(drumkit);
        return new ResponseEntity<>(drumkit, HttpStatus.CREATED);
    }
}
