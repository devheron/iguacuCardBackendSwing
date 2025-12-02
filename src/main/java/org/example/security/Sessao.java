package org.example.security;

import org.example.model.Usuario;

public class Sessao {

    private static Usuario usuarioLogado;

    public static void login(Usuario u) {
        usuarioLogado = u;
    }

    public static void logout() {
        usuarioLogado = null;
    }

    public static Usuario getUsuario() {
        return usuarioLogado;
    }

    public static boolean isLogged() {
        return usuarioLogado != null;
    }

    public static boolean hasRole(String role) {
        if (usuarioLogado == null) return false;
        return usuarioLogado.getRole().name().equals(role);
    }
}
