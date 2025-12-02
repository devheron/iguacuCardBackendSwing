package org.example.dto;

import java.math.BigDecimal;

public class CartaoDTO {
    private Long id;
    private String numero;
    private String titular;
    private String tipo;
    private String status;
    private String validade;
    private BigDecimal saldo;

    public CartaoDTO() {}

    public CartaoDTO(Long id, String numero, String titular, String tipo, String status, String validade, BigDecimal saldo) {
        this.id = id;
        this.numero = numero;
        this.titular = titular;
        this.tipo = tipo;
        this.status = status;
        this.validade = validade;
        this.saldo = saldo;
    }

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
}