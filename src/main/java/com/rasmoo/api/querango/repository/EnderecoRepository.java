package com.rasmoo.api.querango.repository;

import com.rasmoo.api.querango.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {


    List<Endereco> findByCep(final String cep);
}
