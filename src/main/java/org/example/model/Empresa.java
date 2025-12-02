package org.example.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
//@Table(name = "empresas")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true)
    private String cnpj;

    private String telefone;

    private String status = "ATIVO";

    @OneToMany(mappedBy = "empresa")
    private List<Plano> planos;

    @OneToMany(mappedBy = "empresa")
    private List<Cartao> cartoes;
    @Override
    public String toString() {
        return nome; // ou return nome + " - " + cnpj;
    }

    // Getters e Setters

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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}