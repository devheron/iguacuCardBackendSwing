package org.example.controller;

import org.example.dto.PlanoDTO;
import org.example.model.Plano;
import org.example.model.Usuario;
import org.example.model.Cartao;
import org.example.service.PlanoService;

import java.util.List;
import java.util.stream.Collectors;

public class PlanoController {

    private final PlanoService service = new PlanoService();

    public PlanoDTO salvar(PlanoDTO dto) {
        Plano p = new Plano();
        p.setNome(dto.getNome());
        p.setDescricao(dto.getDescricao());
        p.setPreco(dto.getPreco());
        Plano salvo = service.salvar(p);
        return new PlanoDTO(salvo.getId(), salvo.getNome(), salvo.getDescricao(), salvo.getPreco());
    }

    public PlanoDTO buscar(Long id) {
        Plano p = service.buscar(id);
        return new PlanoDTO(p.getId(), p.getNome(), p.getDescricao(), p.getPreco());
    }

    public List<PlanoDTO> listar() {
        return service.listar().stream()
                .map(p -> new PlanoDTO(p.getId(), p.getNome(), p.getDescricao(), p.getPreco()))
                .collect(Collectors.toList());
    }

    public PlanoDTO atualizar(Long id, PlanoDTO dto) {
        Plano p = service.buscar(id);
        p.setNome(dto.getNome());
        p.setDescricao(dto.getDescricao());
        p.setPreco(dto.getPreco());
        Plano atualizado = service.atualizar(p);
        return new PlanoDTO(atualizado.getId(), atualizado.getNome(), atualizado.getDescricao(), atualizado.getPreco());
    }

    public void deletar(Long id) {
        service.deletar(id);
    }

    // ✅ Vínculo com usuário
    public Cartao vincularPlano(Long planoId, Usuario usuario) {
        return service.vincularPlanoAoUsuario(planoId, usuario);
    }
}