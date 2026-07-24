package org.example.swing;

import org.example.model.Empresa;
import org.example.model.enums.Role;
import org.example.model.Usuario;
import org.example.service.AuthService;
import org.example.DAO.EmpresaDAO;
import org.example.swing.ui.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterDialog extends JDialog {

    private final AuthService authService = new AuthService();

    public RegisterDialog(Frame owner) {
        super(owner, "Registrar Usuário", true);
        setSize(480, 560);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BG);

        add(UITheme.headerBar("Cadastrar Usuário", "Crie sua conta para acessar o IguaçuCard"), BorderLayout.NORTH);

        JTextField loginField = new JTextField();
        JPasswordField senhaField = new JPasswordField();
        JTextField nomeField = new JTextField();
        JComboBox<Role> roleBox = new JComboBox<>(Role.values());
        JTextField empresaField = new JTextField();

        UITheme.styleTextField(loginField);
        UITheme.styleTextField(senhaField);
        UITheme.styleTextField(nomeField);
        UITheme.styleTextField(empresaField);
        UITheme.styleComboBox(roleBox);

        JButton salvarBtn = UITheme.successButton("Salvar");
        JButton btnVoltar = UITheme.secondaryButton("Voltar");

        JPanel card = UITheme.card(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 4, 0);

        int y = 0;
        gbc.gridy = y++; card.add(UITheme.formLabel("Login:"), gbc);
        gbc.gridy = y++; card.add(loginField, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Senha:"), gbc);
        gbc.gridy = y++; card.add(senhaField, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Nome:"), gbc);
        gbc.gridy = y++; card.add(nomeField, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Tipo de Conta:"), gbc);
        gbc.gridy = y++; card.add(roleBox, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Empresa (somente EMPRESA):"), gbc);
        gbc.gridy = y++; card.add(empresaField, gbc);

        JPanel botoes = new JPanel(new GridLayout(1, 2, 10, 0));
        botoes.setOpaque(false);
        botoes.add(salvarBtn);
        botoes.add(btnVoltar);

        gbc.gridy = y++;
        gbc.insets = new Insets(14, 0, 4, 0);
        card.add(botoes, gbc);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(UITheme.BG);
        wrapper.setBorder(new EmptyBorder(20, 30, 20, 30));
        GridBagConstraints wc = new GridBagConstraints();
        wc.fill = GridBagConstraints.HORIZONTAL;
        wc.weightx = 1.0;
        wrapper.add(card, wc);
        add(wrapper, BorderLayout.CENTER);

        btnVoltar.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        salvarBtn.addActionListener(e -> {
            try {

                Usuario u = new Usuario();
                u.setLogin(loginField.getText());
                u.setSenha(new String(senhaField.getPassword()));
                u.setNome(nomeField.getText());
                u.setRole((Role) roleBox.getSelectedItem());

                if (u.getRole() == Role.EMPRESA) {

                    if (empresaField.getText().isBlank()) {
                        JOptionPane.showMessageDialog(this, "Nome da empresa obrigatório!");
                        return;
                    }

                    Empresa emp = new Empresa();
                    emp.setNome(empresaField.getText());

                    emp = new EmpresaDAO().save(emp);

                    u.setEmpresa(emp);
                }

                if (u.getRole() == Role.USUARIO) {
                    u.setEmpresa(null);
                }

                Usuario salvo = authService.registrar(u);

                if (salvo == null) {
                    JOptionPane.showMessageDialog(this,
                            "Login já existe!",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                JOptionPane.showMessageDialog(this, "Usuário registrado com sucesso!");
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });
    }

    public RegisterDialog() {

    }
}
