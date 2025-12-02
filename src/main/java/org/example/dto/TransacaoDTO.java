package org.example.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoDTO {
    private Long id;
    private BigDecimal valor;
    private String descricao;
    private LocalDateTime dataHora;
    private Long cartaoId;
    private Long empresaId;

    public TransacaoDTO() {}

    public TransacaoDTO(Long id, BigDecimal valor, String descricao, LocalDateTime dataHora, Long cartaoId, Long empresaId) {
        this.id = id;
        this.valor = valor;
        this.descricao = descricao;
        this.dataHora = dataHora;
        this.cartaoId = cartaoId;
        this.empresaId = empresaId;
    }

    // Getters e Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public Long getCartaoId() { return cartaoId; }
    public void setCartaoId(Long cartaoId) { this.cartaoId = cartaoId; }

    public Long getEmpresaId() { return empresaId; }
    public void setEmpresaId(Long empresaId) { this.empresaId = empresaId; }
}