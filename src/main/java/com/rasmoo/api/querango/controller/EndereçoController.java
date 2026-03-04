package com.rasmoo.api.querango.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.querango.entity.Endereco;
import com.rasmoo.api.querango.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/endereco")
public class EndereçoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<Endereco>> consultarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.findAll());
    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<List<Endereco>> consultarTodosPorCep(@PathVariable("cep") final String cep) {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.findByCep(cep));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> consultarPorId(@PathVariable("id")final Integer id) {
        return enderecoRepository.findById(id)
                .map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Endereco> atualizar(@PathVariable("id") final Integer id, @RequestBody final Endereco endereco) throws JsonMappingException {
        Optional<Endereco> enderecoEncontrado = enderecoRepository.findById(id);

        if (enderecoEncontrado.isPresent()){
            objectMapper.updateValue(enderecoEncontrado.get(), endereco);
            return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.save(enderecoEncontrado.get()));
        }

        return ResponseEntity.notFound().build();
    }
}
