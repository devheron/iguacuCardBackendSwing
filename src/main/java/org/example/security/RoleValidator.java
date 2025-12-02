package org.example.security;

import javax.swing.*;

public class RoleValidator {

    public static boolean allow(String... roles) {
        if (!Sessao.isLogged()) {
            JOptionPane.showMessageDialog(null,
                    "Você precisa estar logado.");
            return false;
        }

        for (String r : roles) {
            if (Sessao.hasRole(r)) return true;
        }

        JOptionPane.showMessageDialog(null,
                "Acesso negado! Seu perfil não permite esta ação.");
        return false;
    }
}
