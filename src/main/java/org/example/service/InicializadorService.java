package org.example.service;

import org.example.DAO.UsuarioDAO;
import org.example.model.Usuario;
import org.example.model.enums.Role;

public class InicializadorService {
    public static void inicializar() {
        UsuarioDAO dao = new UsuarioDAO();
        Usuario admin = dao.findByLogin("admin");
        if (admin == null) {
            admin = new Usuario();
            admin.setLogin("admin");
            admin.setSenha("admin123"); // em produção, hash!
            admin.setNome("Administrador");
            admin.setRole(Role.ADMIN);
            dao.save(admin);
            System.out.println("Admin criado: admin / admin123");
        }
        // opcional: criar plano padrão, empresas de teste, etc.
    }
}
