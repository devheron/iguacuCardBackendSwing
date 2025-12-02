package org.example.controller;

import org.example.dto.TransacaoDTO;
import org.example.model.Transacao;
import org.example.service.TransacaoService;

import java.util.List;
import java.util.stream.Collectors;

public class TransacaoController {

    private final TransacaoService service = new TransacaoService();

    public TransacaoDTO registrar(Long cartaoId, TransacaoDTO dto) {
        Transacao t = service.registrarTransacao(cartaoId, dto.getValor(), dto.getDescricao());
        return new TransacaoDTO(t.getId(), t.getValor(), t.getDescricao(), t.getDataHora(),
                t.getCartao().getId(), t.getEmpresa() != null ? t.getEmpresa().getId() : null);
    }

    public List<TransacaoDTO> listar() {
        return service.listarTodos().stream()
                .map(t -> new TransacaoDTO(t.getId(), t.getValor(), t.getDescricao(), t.getDataHora(),
                        t.getCartao().getId(), t.getEmpresa() != null ? t.getEmpresa().getId() : null))
                .collect(Collectors.toList());
    }

    public TransacaoDTO buscar(Long id) {
        Transacao t = service.buscar(id);
        return new TransacaoDTO(t.getId(), t.getValor(), t.getDescricao(), t.getDataHora(),
                t.getCartao().getId(), t.getEmpresa() != null ? t.getEmpresa().getId() : null);
    }

    public void deletar(Long id) {
        service.deletar(id);
    }
}