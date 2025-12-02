package org.example.session;

import org.example.model.Empresa;
import org.example.model.Usuario;

public class SessionContext {
    private static Usuario current;

    public static void setCurrentUser(Usuario u) { current = u; }
    public static Usuario getCurrentUser() { return current; }
    public static Empresa getEmpresaLogada() {
        return isEmpresa() ? current.getEmpresa() : null;
    } // acessar empresa diretamente.

    public static boolean isAdmin() {
        return current != null && current.getRole() != null && current.getRole().name().equals("ADMIN");
    }

    public static boolean isEmpresa() {
        return current != null && current.getRole() != null && current.getRole().name().equals("EMPRESA");
    }

    public static boolean isUsuario() {
        return current != null && current.getRole() != null && current.getRole().name().equals("USUARIO");
    }

    public static void login(Usuario u) { current = u; }
    public static void logout() { current = null; }
}