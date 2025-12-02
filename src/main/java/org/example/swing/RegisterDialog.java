package org.example.swing;

import org.example.model.Empresa;
import org.example.model.enums.Role;
import org.example.model.Usuario;
import org.example.service.AuthService;
import org.example.DAO.EmpresaDAO;

import javax.swing.*;
import java.awt.*;

public class RegisterDialog extends JDialog {

    private final AuthService authService = new AuthService();

    public RegisterDialog(Frame owner) {
        super(owner, "Registrar Usuário", true);
        setSize(350, 350);
        setLocationRelativeTo(owner);
        setLayout(new GridLayout(10, 1));


        JTextField loginField = new JTextField();
        JPasswordField senhaField = new JPasswordField();
        JTextField nomeField = new JTextField();

        JComboBox<Role> roleBox = new JComboBox<>(Role.values());

        // Campo de empresa só quando necessário
        JTextField empresaField = new JTextField();

        JButton salvarBtn = new JButton("Salvar");

        add(new JLabel("Login:"));
        add(loginField);

        add(new JLabel("Senha:"));
        add(senhaField);

        add(new JLabel("Nome:"));
        add(nomeField);

        add(new JLabel("Tipo de Conta:"));
        add(roleBox);

        add(new JLabel("Empresa (somente EMPRESA):"));
        add(empresaField);

        add(salvarBtn);
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        add(btnVoltar, BorderLayout.SOUTH);

        salvarBtn.addActionListener(e -> {
            try {

                Usuario u = new Usuario();
                u.setLogin(loginField.getText());
                u.setSenha(new String(senhaField.getPassword()));
                u.setNome(nomeField.getText());
                u.setRole((Role) roleBox.getSelectedItem());

                // ✅ CRIAÇÃO DE EMPRESA AO REGISTRAR
                if (u.getRole() == Role.EMPRESA) {

                    if (empresaField.getText().isBlank()) {
                        JOptionPane.showMessageDialog(this, "Nome da empresa obrigatório!");
                        return;
                    }

                    Empresa emp = new Empresa();
                    emp.setNome(empresaField.getText());

                    emp = new EmpresaDAO().save(emp); // salva empresa

                    u.setEmpresa(emp); // relaciona
                }

                // ✅ USUÁRIO COMUM NÃO PRECISA DIGITAR EMPRESA
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
