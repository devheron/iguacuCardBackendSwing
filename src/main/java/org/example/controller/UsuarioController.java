package org.example.controller;

import org.example.dto.UsuarioDTO;
import org.example.model.Usuario;
import org.example.service.UsuarioService;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioController {
    private final UsuarioService service = new UsuarioService();

    public UsuarioDTO salvar(UsuarioDTO dto) {
        Usuario u = new Usuario();
        u.setNome(dto.getNome());
        u.setLogin(dto.getLogin());
        u.setRole(dto.getRole());
        Usuario salvo = service.salvar(u);
        return new UsuarioDTO(salvo.getId(), salvo.getNome(), salvo.getLogin(), salvo.getRole());
    }

    public UsuarioDTO buscar(Long id) {
        Usuario u = service.buscar(id);
        return new UsuarioDTO(u.getId(), u.getNome(), u.getLogin(), u.getRole());
    }

    public List<UsuarioDTO> listar() {
        return service.listar().stream()
                .map(u -> new UsuarioDTO(u.getId(), u.getNome(), u.getLogin(), u.getRole()))
                .collect(Collectors.toList());
    }

    public UsuarioDTO atualizar(Long id, UsuarioDTO dto) {
        Usuario u = service.buscar(id);
        u.setNome(dto.getNome());
        u.setLogin(dto.getLogin());
        u.setRole(dto.getRole());
        Usuario atualizado = service.atualizar(u);
        return new UsuarioDTO(atualizado.getId(), atualizado.getNome(), atualizado.getLogin(), atualizado.getRole());
    }

    public void deletar(Long id) {
        service.deletar(id);
    }
}