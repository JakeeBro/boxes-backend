package com.jakeposhepny.boxes.controller;

import com.jakeposhepny.boxes.model.Box;
import com.jakeposhepny.boxes.repository.BoxRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/boxes")
@CrossOrigin(origins = "https://jakeebro.github.io")
public class BoxController {

    private final BoxRepository boxRepository;

    public BoxController(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    @GetMapping
    public List<Box> getAllBoxes() {
        return boxRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Box> getBoxById(@PathVariable long id) {
        Optional<Box> box = boxRepository.findById(id);
        return box.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public Box createBox(@RequestBody Box box) {
        return boxRepository.save(box);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Box> updateBox(@PathVariable long id, @RequestBody Box box) {
        return boxRepository.findById(id)
                .map(existing -> {
                    existing.setLabel(box.getLabel());
                    existing.setColor(box.getColor());
                    boxRepository.save(existing);
                    return ResponseEntity.ok(existing);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBox(@PathVariable long id) {
        if (!boxRepository.existsById(id)) return ResponseEntity.notFound().build();
        boxRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
