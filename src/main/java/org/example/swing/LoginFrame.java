package org.example.swing;

import org.example.model.Usuario;
import org.example.service.AuthService;
import org.example.session.SessionContext;
import org.example.swing.ui.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final AuthService authService = new AuthService();

    public LoginFrame() {

        setTitle("IguaçuCard - Login");
        setSize(460, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BG);

        JPanel header = UITheme.headerBar("IguaçuCard", "Sistema de gestão de cartões sociais");
        add(header, BorderLayout.NORTH);

        JTextField loginField = new JTextField();
        JPasswordField senhaField = new JPasswordField();
        UITheme.styleTextField(loginField);
        UITheme.styleTextField(senhaField);

        JButton entrarBtn = UITheme.primaryButton("Entrar");
        JButton registerBtn = UITheme.secondaryButton("Cadastrar Usuário");

        JPanel card = UITheme.card(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(6, 0, 6, 0);

        JLabel titulo = new JLabel("Acesse sua conta");
        titulo.setFont(UITheme.FONT_H2);
        titulo.setForeground(UITheme.PRIMARY_DARK);
        gbc.gridy = 0; card.add(titulo, gbc);

        gbc.gridy = 1; card.add(UITheme.formLabel("Login:"), gbc);
        gbc.gridy = 2; card.add(loginField, gbc);

        gbc.gridy = 3; card.add(UITheme.formLabel("Senha:"), gbc);
        gbc.gridy = 4; card.add(senhaField, gbc);

        gbc.gridy = 5; gbc.insets = new Insets(16, 0, 6, 0);
        card.add(entrarBtn, gbc);
        gbc.gridy = 6; gbc.insets = new Insets(0, 0, 6, 0);
        card.add(registerBtn, gbc);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(UITheme.BG);
        wrapper.setBorder(new EmptyBorder(24, 40, 24, 40));
        GridBagConstraints wc = new GridBagConstraints();
        wc.fill = GridBagConstraints.HORIZONTAL;
        wc.weightx = 1.0;
        wrapper.add(card, wc);

        add(wrapper, BorderLayout.CENTER);

        JLabel foot = new JLabel("© 2025 IguaçuCard", SwingConstants.CENTER);
        foot.setForeground(UITheme.TEXT_MUTED);
        foot.setFont(UITheme.FONT_SMALL);
        foot.setBorder(new EmptyBorder(10, 0, 12, 0));
        add(foot, BorderLayout.SOUTH);

        entrarBtn.addActionListener(e -> fazerLogin(loginField, senhaField));
        registerBtn.addActionListener(e -> new RegisterDialog(this).setVisible(true));
    }

    private void fazerLogin(JTextField loginField, JPasswordField senhaField) {
        try {
            String login = loginField.getText();
            String senha = new String(senhaField.getPassword());

            Usuario u = authService.autenticar(login, senha);

            if (u == null) {
                JOptionPane.showMessageDialog(this, "Login inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "Bem-vindo " + u.getNome());

            //SessionContext.setUsuarioLogado(u);
            abrirPainelPorRole(u);
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void abrirPainelPorRole(Usuario u) {

        switch (u.getRole()) {
            case ADMIN:
                new PainelAdmin().setVisible(true);
                break;
            case EMPRESA:
                new PainelEmpresa().setVisible(true);
                break;
            case USUARIO:
                new PainelUsuario().setVisible(true);
                break;
        }
    }
}
