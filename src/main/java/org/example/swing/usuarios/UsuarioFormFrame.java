package org.example.swing.usuarios;

import org.example.model.Empresa;
import org.example.model.Plano;
import org.example.model.Usuario;
import org.example.model.enums.Role;
import org.example.security.PasswordUtil;
import org.example.service.CartaoService;
import org.example.service.EmpresaService;
import org.example.service.PlanoService;
import org.example.service.UsuarioService;
import org.example.session.SessionContext;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UsuarioFormFrame extends JFrame {

    private final UsuarioService service = new UsuarioService();
    private final PlanoService planoService = new PlanoService();
    private final Usuario usuario;
    private final UsuarioListaFrame parent;
private final CartaoService cartaoService = new CartaoService();

    public UsuarioFormFrame(Usuario usuario, UsuarioListaFrame parent) {
        this.usuario = usuario;
        this.parent = parent;

        setTitle(usuario == null ? "Novo Usuário" : "Editar Usuário");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2));

        JTextField nomeField = new JTextField();
        JTextField loginField = new JTextField();
        JPasswordField senhaField = new JPasswordField();

        JComboBox<String> roleBox = new JComboBox<>(new String[]{"ADMIN", "EMPRESA", "USUARIO"});
        //JComboBox<Plano> planoBox = new JComboBox<>();
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"ATIVO", "INATIVO"});
        JComboBox<Empresa> empresaBox = new JComboBox<>();
        for (Empresa e : new EmpresaService().listar()) empresaBox.addItem(e);
        //List<Plano> planos = planoService.listar();
        //for (Plano p : planos) planoBox.addItem(p);

        JButton btnGerarCartao = new JButton("Gerar Cartão Social");
        btnGerarCartao.addActionListener(e -> {
            Usuario u = SessionContext.getCurrentUser();
            if (u.getPlano() == null) {
                JOptionPane.showMessageDialog(this, "Você precisa contratar um plano primeiro.");
                return;
            }
            cartaoService.emitirCartaoSocial(u, u.getPlano());
            JOptionPane.showMessageDialog(this, "Cartão gerado com sucesso!");
        });
        JButton salvar = new JButton("Salvar");

        roleBox.addActionListener(e -> {
            String role = roleBox.getSelectedItem().toString();
            empresaBox.setEnabled("EMPRESA".equals(role));
        });

        add(new JLabel("Nome:"));
        add(nomeField);
        add(new JLabel("Login:"));
        add(loginField);
        add(new JLabel("Senha:"));
        add(senhaField);
        add(new JLabel("Role:"));
        add(roleBox);
        //add(new JLabel("Plano:"));
        //add(planoBox);
        add(new JLabel("Status:"));
        add(statusBox);
        add(new JLabel("Empresa:"));
        add(empresaBox);
        add(new JLabel());
        add(salvar);
        //if ("EMPRESA".equals(roleBox.getSelectedItem().toString())) {
        //    usuario.setEmpresa((Empresa) empresaBox.getSelectedItem());
        //} else {
        //    usuario.setEmpresa(null);
        //}

        if (usuario != null) {
            nomeField.setText(usuario.getNome());
            loginField.setText(usuario.getLogin());
            roleBox.setSelectedItem(usuario.getRole().toString());
            statusBox.setSelectedItem(usuario.getStatus());
            if (usuario.getEmpresa() != null) empresaBox.setSelectedItem(usuario.getEmpresa());
            //if (usuario.getPlano() != null)
             //   planoBox.setSelectedItem(usuario.getPlano());
        }

        salvar.addActionListener(e -> {
            String senha = new String(senhaField.getPassword());
            if (usuario == null) {
                Usuario novo = new Usuario();
                novo.setNome(nomeField.getText());
                novo.setLogin(loginField.getText());
                novo.setSenha(PasswordUtil.hash(senha));
                novo.setRole(Role.valueOf(roleBox.getSelectedItem().toString()));
                novo.setStatus(statusBox.getSelectedItem().toString());
                service.salvar(novo);
            } else {
                usuario.setNome(nomeField.getText());
                usuario.setLogin(loginField.getText());
                if (!senha.isBlank()) usuario.setSenha(PasswordUtil.hash(senha));
                usuario.setRole(Role.valueOf(roleBox.getSelectedItem().toString()));
                usuario.setStatus(statusBox.getSelectedItem().toString());
                service.atualizar(usuario);
            }
            parent.carregarTabela();
            dispose();
        });

    }
}
