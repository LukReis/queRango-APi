package com.rasmoo.api.querango.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.api.querango.entity.Cardapio;
import com.rasmoo.api.querango.repository.CardapioRepository;
import com.rasmoo.api.querango.repository.specification.CardapioSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cardapio")
public class CardapioController {

    @Autowired
    private CardapioRepository cardapioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<Page<Cardapio>> buscarTodos(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
                                                      @RequestParam(value = "sort", required = false) Sort.Direction sort,
                                                      @RequestParam(value = "property", required = false) String property) {
        Pageable pageable = Objects.nonNull(sort)
               ? PageRequest.of(page, size, Sort.by(sort, property))
                : PageRequest.of(page, size);
        return ResponseEntity.ok(cardapioRepository.findAll(pageable));
    }

    @GetMapping("/categoria/{categoriaId}/disponivel")
    public ResponseEntity<List<Cardapio>> buscarTodos(@PathVariable("categoriaId") final Integer categoriaId,
                                                                @RequestParam("page") Integer page, @RequestParam("size") Integer size,
                                                                @RequestParam(value = "sort", required = false) Sort.Direction sort,
                                                                @RequestParam(value = "property", required = false) String property) {
        Pageable pageable = Objects.nonNull(sort)
                ? PageRequest.of(page, size, Sort.by(sort, property))
                : PageRequest.of(page, size);
        return ResponseEntity.ok(cardapioRepository.findAll(Specification
                .where(CardapioSpec.categoria(categoriaId))
                .and(CardapioSpec.disponivel(true)), pageable).getContent());
    }

    @GetMapping("/nome/{nome}/disponivel")
    public ResponseEntity<List<Cardapio>> buscarTodos(@PathVariable("nome") final String nome,
                                                         @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(cardapioRepository.findAll(Specification
                .where(CardapioSpec.nome(nome))
                .and(CardapioSpec.disponivel(true)), pageable).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cardapio> buscarPorId(@PathVariable("id") final Integer id) {
        return cardapioRepository.findById(id)
                .map(cardapio -> ResponseEntity.status(HttpStatus.OK).body(cardapio))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cardapio> criar(@RequestBody final Cardapio cardapio) throws JsonMappingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardapioRepository.save(cardapio));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> exlcuir(@PathVariable("id") final Integer id) {
        Optional<Cardapio> cardapioEncontrado = cardapioRepository.findById(id);

        if (cardapioEncontrado.isPresent()) {
            cardapioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cardapio Não Encontrado");
    }
    @PatchMapping(path = "/{id}/img", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Cardapio> salvarImg(@PathVariable("id") final Integer id, @RequestPart final MultipartFile file) throws IOException {

        Optional<Cardapio> cardapioEncontrado = cardapioRepository.findById(id);

        if (cardapioEncontrado.isPresent()) {
            Cardapio cardapio = cardapioEncontrado.get();
            cardapio.setImg(file.getBytes());
            return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.save(cardapioEncontrado.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Cardapio> atualizarPorId(@PathVariable("id") final Integer id,@RequestBody final Cardapio cardapio) throws JsonMappingException {
        Optional<Cardapio> cardapioEncontrado = cardapioRepository.findById(id);

        if (cardapioEncontrado.isPresent()) {
            objectMapper.updateValue(cardapioEncontrado.get(), cardapio);
            return ResponseEntity.status(HttpStatus.OK).body(cardapioRepository.save(cardapioEncontrado.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


}
