package com.alurachallenge.ForoHub.controller;

import com.alurachallenge.ForoHub.topico.Topico;
import com.alurachallenge.ForoHub.topico.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@Validated
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity<?> crearTopico(@Valid @RequestBody Topico topico) {
        Optional<Topico> existingTopico = topicoRepository.findByTituloAndMensaje(topico.getTitulo(), topico.getMensaje());
        if (existingTopico.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe un tópico con el mismo título y mensaje.");
        }
        Topico nuevoTopico = topicoRepository.save(topico);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTopico);
    }

    @GetMapping
    public List<Topico> listarTopicos() {
        return topicoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topico> obtenerTopico(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            return ResponseEntity.ok(topico.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> actualizarTopico(@PathVariable Long id, @Valid @RequestBody Topico detallesTopico) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            // Actualizar los campos permitidos en la actualización
            Topico topicoExistente = topico.get();
            topicoExistente.setTitulo(detallesTopico.getTitulo());
            topicoExistente.setMensaje(detallesTopico.getMensaje());
            topicoExistente.setStatus(detallesTopico.getStatus());
            topicoExistente.setAutor(detallesTopico.getAutor());
            topicoExistente.setCurso(detallesTopico.getCurso());
            topicoRepository.save(topicoExistente);
            return ResponseEntity.ok(topicoExistente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            topicoRepository.delete(topico.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}