package com.rasmoo.api.querango.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cardapio")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String descricao;
    private boolean disponivel;

    @Column(name = "valor_de_registro")
    private BigDecimal valorDeRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    private Categoria categoria;


    @Column(name = "data_de_registro")
    private LocalDateTime dataDeRegistro = LocalDateTime.now();

    @Lob
    private byte[] img;

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public Cardapio() {
    }

    public Cardapio(Integer id, String nome, String descricao, boolean disponivel, BigDecimal valorDeRegistro, Categoria categoria, LocalDateTime dataDeRegistro) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.disponivel = disponivel;
        this.valorDeRegistro = valorDeRegistro;
        this.categoria = categoria;
        this.dataDeRegistro = dataDeRegistro;
    }

    public Cardapio(String nome, String descricao, boolean disponivel, BigDecimal valorDeRegistro, Categoria categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.disponivel = disponivel;
        this.valorDeRegistro = valorDeRegistro;
        this.categoria = categoria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public BigDecimal getValor() {
        return valorDeRegistro;
    }

    public void setValor(BigDecimal valor) {
        this.valorDeRegistro = valor;
    }

    public LocalDateTime getDataDeRegistro() {
        return dataDeRegistro;
    }

    public void setDataDeRegistro(LocalDateTime dataDeRegistro) {
        this.dataDeRegistro = dataDeRegistro;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Prato{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", disponivel=" + disponivel +
                ", valor=" + valorDeRegistro +
                ", dataDeRegistro=" + dataDeRegistro +
                '}';
    }
}
