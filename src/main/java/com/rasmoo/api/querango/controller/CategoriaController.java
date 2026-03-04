package com.rasmoo.api.querango.controller;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.querango.entity.Categoria;
import com.rasmoo.api.querango.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<Categoria>> consultarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> consultarPorId(@PathVariable("id") final Integer id) {
        return categoriaRepository.findById(id)
                .map(categoria -> ResponseEntity.status(HttpStatus.OK).body(categoria))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping
    public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Integer id){
        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(id);

        if (categoriaEncontrada.isPresent()){
            categoriaRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não Encontrada");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Categoria> atualizarParcial(@PathVariable("id")final Integer id, @RequestBody Categoria categoria) throws JsonMappingException {
        Optional<Categoria> categoriaEncontrada = categoriaRepository.findById(id);

        if (categoriaEncontrada.isPresent()){
            objectMapper.updateValue(categoriaEncontrada.get(),categoria);
            return ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.save(categoriaEncontrada.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
