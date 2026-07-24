package org.example.swing.usuarios;

import org.example.model.Empresa;
import org.example.model.Usuario;
import org.example.model.enums.Role;
import org.example.security.PasswordUtil;
import org.example.service.CartaoService;
import org.example.service.EmpresaService;
import org.example.service.PlanoService;
import org.example.service.UsuarioService;
import org.example.session.SessionContext;
import org.example.swing.ui.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

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
        setSize(520, 540);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BG);

        add(UITheme.headerBar(usuario == null ? "Novo Usuário" : "Editar Usuário",
                "Informe os dados do usuário"), BorderLayout.NORTH);

        JTextField nomeField = new JTextField();
        JTextField loginField = new JTextField();
        JPasswordField senhaField = new JPasswordField();
        UITheme.styleTextField(nomeField);
        UITheme.styleTextField(loginField);
        UITheme.styleTextField(senhaField);

        JComboBox<String> roleBox = new JComboBox<>(new String[]{"ADMIN", "EMPRESA", "USUARIO"});
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"ATIVO", "INATIVO"});
        JComboBox<Empresa> empresaBox = new JComboBox<>();
        UITheme.styleComboBox(roleBox);
        UITheme.styleComboBox(statusBox);
        UITheme.styleComboBox(empresaBox);
        for (Empresa e : new EmpresaService().listar()) empresaBox.addItem(e);

        JButton btnGerarCartao = UITheme.secondaryButton("Gerar Cartão Social");
        btnGerarCartao.addActionListener(e -> {
            Usuario u = SessionContext.getCurrentUser();
            if (u.getPlano() == null) {
                JOptionPane.showMessageDialog(this, "Você precisa contratar um plano primeiro.");
                return;
            }
            cartaoService.emitirCartaoSocial(u, u.getPlano());
            JOptionPane.showMessageDialog(this, "Cartão gerado com sucesso!");
        });
        JButton salvar = UITheme.successButton("💾  Salvar");

        roleBox.addActionListener(e -> {
            String role = roleBox.getSelectedItem().toString();
            empresaBox.setEnabled("EMPRESA".equals(role));
        });

        JPanel card = UITheme.card(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 4, 0);
        int y = 0;
        gbc.gridy = y++; card.add(UITheme.formLabel("Nome:"), gbc);
        gbc.gridy = y++; card.add(nomeField, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Login:"), gbc);
        gbc.gridy = y++; card.add(loginField, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Senha:"), gbc);
        gbc.gridy = y++; card.add(senhaField, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Role:"), gbc);
        gbc.gridy = y++; card.add(roleBox, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Status:"), gbc);
        gbc.gridy = y++; card.add(statusBox, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Empresa:"), gbc);
        gbc.gridy = y++; card.add(empresaBox, gbc);
        gbc.gridy = y++;
        gbc.insets = new Insets(14, 0, 4, 0);
        card.add(salvar, gbc);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(UITheme.BG);
        wrapper.setBorder(new EmptyBorder(16, 24, 16, 24));
        GridBagConstraints wc = new GridBagConstraints();
        wc.fill = GridBagConstraints.HORIZONTAL;
        wc.weightx = 1.0;
        wrapper.add(card, wc);
        add(wrapper, BorderLayout.CENTER);

        if (usuario != null) {
            nomeField.setText(usuario.getNome());
            loginField.setText(usuario.getLogin());
            roleBox.setSelectedItem(usuario.getRole().toString());
            statusBox.setSelectedItem(usuario.getStatus());
            if (usuario.getEmpresa() != null) empresaBox.setSelectedItem(usuario.getEmpresa());
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
