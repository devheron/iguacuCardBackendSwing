package org.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numero;

    private String titular;
    private String tipo;
    private String status;
    private String validade;

    private BigDecimal saldo;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Plano plano;

    @ManyToOne
    private Empresa empresa;

    // Getters e Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getValidade() { return validade; }
    public void setValidade(String validade) { this.validade = validade; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Plano getPlano() { return plano; }
    public void setPlano(Plano plano) { this.plano = plano; }

    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
}