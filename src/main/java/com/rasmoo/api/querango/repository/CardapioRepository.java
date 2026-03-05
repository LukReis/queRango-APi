package com.rasmoo.api.querango.repository;

import com.rasmoo.api.querango.dto.CardapioDto;
import com.rasmoo.api.querango.entity.Cardapio;
import com.rasmoo.api.querango.repository.projection.CardapioProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository
public interface CardapioRepository extends PagingAndSortingRepository<Cardapio, Integer>, JpaSpecificationExecutor<Cardapio> {

    @Query("select new com.rasmoo.api.querango.dto.CardapioDto(c.nome, c.descricao, c.valorDeRegistro ,c.categoria.nome)" +
            " FROM Cardapio c WHERE c.nome LIKE %:nome% AND c.disponivel = true")
    Page<CardapioDto> findAllByNome(final String nome, final Pageable pageable);

    @Query(value = "SELECT" +
            "    c.nome as nome," +
            "    c.descricao as descricao," +
            "    c.valor as valor," +
            "    cat.nome as nomeCategoria" +
            "    FROM cardapio c" +
            "    INNER JOIN categorias cat on  c.categoria_id = cat.id" +
            "    WHERE c.categoria_id = :categoria AND c.disponivel = true",
            nativeQuery = true,
            countQuery = "SELECT count(*) from cardapio")
    Page<CardapioProjection> findAllByCategoria(final Integer categoria, final Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Cardapio c set c.disponivel = " +
            "case c.disponivel " +
            "when true then false " +
            "else true end " +
            "where c.id = :id")
    Integer updateDisponibilidade(final Integer id);
}
