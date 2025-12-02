package org.example.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
//@Table(name = "planos")
public class Plano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String status = "ATIVO";
    //private Double valor;

    @ManyToOne
    private Empresa empresa;

    @OneToMany(mappedBy = "plano")
    private List<Cartao> cartoes;

    @Override
    public String toString() {
        return nome; // ou return nome
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
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

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
// Getters e Setters
}
