package org.example.service;

import org.example.DAO.UsuarioDAO;
import org.example.model.Usuario;
import org.example.security.PasswordUtil;
import org.example.session.SessionContext;

public class AuthService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario autenticar(String login, String senha) {
        Usuario u = usuarioDAO.findByLogin(login);
        if (u == null || !PasswordUtil.matches(senha, u.getSenha())) return null;
        //if (!"ATIVO".equalsIgnoreCase(u.getStatus())) return null;

        SessionContext.login(u);
        return u;
    }

    public Usuario registrar(Usuario u) {
        Usuario existente = usuarioDAO.findByLogin(u.getLogin());
        if (existente != null) return null;

        u.setSenha(PasswordUtil.hash(u.getSenha()));
        return usuarioDAO.save(u);
    }

    public void logout() {
        SessionContext.logout();
    }
}