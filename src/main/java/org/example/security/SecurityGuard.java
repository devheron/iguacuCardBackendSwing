package org.example.security;

import javax.swing.*;
import org.example.session.SessionContext;
import org.example.model.Usuario;

public class SecurityGuard {

    public static boolean requireAdmin(JFrame parent) {
        if (!SessionContext.isAdmin()) {
            JOptionPane.showMessageDialog(parent,
                    "Acesso negado. Apenas administradores!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean requireEmpresa(JFrame parent) {
        if (!SessionContext.isEmpresa()) {
            JOptionPane.showMessageDialog(parent,
                    "Acesso negado. Apenas empresas!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean requireUsuario(JFrame parent) {
        if (!SessionContext.isUsuario()) {
            JOptionPane.showMessageDialog(parent,
                    "Acesso negado. Apenas usuários!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
