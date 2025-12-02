package org.example.controller;

import org.example.dto.CartaoDTO;
import org.example.model.Cartao;
import org.example.model.Plano;
import org.example.model.Usuario;
import org.example.service.CartaoService;

import java.util.List;
import java.util.stream.Collectors;

public class CartaoController {

    private final CartaoService service = new CartaoService();

    public CartaoDTO salvar(CartaoDTO dto) {
        Cartao c = new Cartao();
        c.setNumero(dto.getNumero());
        c.setTitular(dto.getTitular());
        c.setTipo(dto.getTipo());
        c.setStatus(dto.getStatus());
        c.setValidade(dto.getValidade());
        c.setSaldo(dto.getSaldo());
        Cartao salvo = service.salvar(c);
        return new CartaoDTO(salvo.getId(), salvo.getNumero(), salvo.getTitular(), salvo.getTipo(), salvo.getStatus(), salvo.getValidade(), salvo.getSaldo());
    }

    public CartaoDTO buscar(Long id) {
        Cartao c = service.buscar(id);
        return new CartaoDTO(c.getId(), c.getNumero(), c.getTitular(), c.getTipo(), c.getStatus(), c.getValidade(), c.getSaldo());
    }

    public List<CartaoDTO> listar() {
        return service.listarTodos().stream()
                .map(c -> new CartaoDTO(c.getId(), c.getNumero(), c.getTitular(), c.getTipo(), c.getStatus(), c.getValidade(), c.getSaldo()))
                .collect(Collectors.toList());
    }

    public CartaoDTO atualizar(Long id, CartaoDTO dto) {
        Cartao c = service.buscar(id);
        c.setNumero(dto.getNumero());
        c.setTitular(dto.getTitular());
        c.setTipo(dto.getTipo());
        c.setStatus(dto.getStatus());
        c.setValidade(dto.getValidade());
        c.setSaldo(dto.getSaldo());
        Cartao atualizado = service.atualizar(c);
        return new CartaoDTO(atualizado.getId(), atualizado.getNumero(), atualizado.getTitular(),
                atualizado.getTipo(), atualizado.getStatus(), atualizado.getValidade(), atualizado.getSaldo());
    }

    public void deletar(Long id) {
        service.deletar(id);
    }

    public Cartao emitirCartaoSocial(Usuario usuario, Plano plano) {
        return service.emitirCartaoSocial(usuario, plano);
    }
}