package br.com.segment.leosmusicstoreapi.controllers;

import br.com.segment.leosmusicstoreapi.dtos.DrumKitPostDto;
import br.com.segment.leosmusicstoreapi.dtos.outputs.DrumKitOutput;
import br.com.segment.leosmusicstoreapi.models.DrumKit;
import br.com.segment.leosmusicstoreapi.repositories.DrumKitRepository;
import com.sun.istack.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<DrumKit> drumKits = drumkitRepository.findAllWithManufacturer();
        return new ResponseEntity<>(
                drumKits.stream()
                        .map(d -> new DrumKitOutput(
                                d.getId(),
                                d.getModel(),
                                d.getPrice(),
                                d.getMainImage(),
                                d.getManufacturer().getName()
                        )),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneDrum(@PathVariable @NotNull Long id) {
        DrumKit drumKit = drumkitRepository.findByIdWithManufacturer(id);
        return new ResponseEntity<>(
                new DrumKitOutput(drumKit.getId(),
                        drumKit.getModel(),
                        drumKit.getPrice(),
                        drumKit.getMainImage(),
                        drumKit.getManufacturer().getName()),
                HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addDrum(@RequestBody DrumKitPostDto drumKitPostDto) {
        DrumKit drumkit = modelMapper.map(drumKitPostDto, DrumKit.class);
        drumkitRepository.save(drumkit);
        return new ResponseEntity<>(drumkit, HttpStatus.CREATED);
    }
}
