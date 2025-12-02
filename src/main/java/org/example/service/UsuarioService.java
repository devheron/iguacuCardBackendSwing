package org.example.service;

import org.example.DAO.UsuarioDAO;
import org.example.model.Usuario;

import java.util.List;

public class UsuarioService {
    private final UsuarioDAO dao = new UsuarioDAO();

    public Usuario salvar(Usuario u) {
        return dao.save(u);
    }

    public Usuario atualizar(Usuario u) {
        return dao.update(u);
    }

    public Usuario buscar(Long id) {
        return dao.findById(id);
    }

    public List<Usuario> listar() {
        return dao.findAll();
    }

    public void deletar(Long id) {
        dao.delete(id);
    }
}