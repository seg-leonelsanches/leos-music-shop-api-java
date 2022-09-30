package br.com.segment.leosmusicstoreapi.controllers;

import br.com.segment.leosmusicstoreapi.repositories.DrumkitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/drums")
public class DrumsController {

    @Autowired
    private DrumkitRepository drumkitRepository;

    @GetMapping("")
    public ResponseEntity<?> getAllDrums() {
        return new ResponseEntity<>(drumkitRepository.findAll(), HttpStatus.OK);
    }
}
