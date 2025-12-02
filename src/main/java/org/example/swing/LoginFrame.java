package org.example.swing;

import org.example.model.Usuario;
import org.example.service.AuthService;
import org.example.session.SessionContext;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final AuthService authService = new AuthService();

    public LoginFrame() {

        setTitle("IguaçuCard - Login");
        setSize(350, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));


        JTextField loginField = new JTextField();
        JPasswordField senhaField = new JPasswordField();

        JButton entrarBtn = new JButton("Entrar");
        JButton registerBtn = new JButton("Cadastrar Usuário");

        add(new JLabel("Login:"));
        add(loginField);

        add(new JLabel("Senha:"));
        add(senhaField);

        add(entrarBtn);
        add(registerBtn);

        entrarBtn.addActionListener(e -> fazerLogin(loginField, senhaField));

        registerBtn.addActionListener(e -> {
            new RegisterDialog(this).setVisible(true);
        });
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
