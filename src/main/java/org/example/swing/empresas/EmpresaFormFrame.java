package org.example.swing.empresas;

import org.example.model.Empresa;
import org.example.model.Usuario;
import org.example.model.enums.Role;
import org.example.security.PasswordUtil;
import org.example.service.EmpresaService;
import org.example.service.UsuarioService;
import org.example.swing.ui.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EmpresaFormFrame extends JFrame {

    private Empresa empresa;
    private EmpresaListaFrame parent;
    private EmpresaService service = new EmpresaService();

    public EmpresaFormFrame(Empresa emp, EmpresaListaFrame parent) {

        this.empresa = emp;
        this.parent = parent;

        setTitle(emp == null ? "Nova Empresa" : "Editar Empresa");
        setSize(520, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BG);

        add(UITheme.headerBar(emp == null ? "Nova Empresa" : "Editar Empresa",
                "Preencha os dados da empresa parceira"), BorderLayout.NORTH);

        JTextField nome = new JTextField();
        JTextField cnpj = new JTextField();
        JTextField tel = new JTextField();
        JTextField loginField = new JTextField();
        JPasswordField senhaField = new JPasswordField();

        UITheme.styleTextField(nome);
        UITheme.styleTextField(cnpj);
        UITheme.styleTextField(tel);
        UITheme.styleTextField(loginField);
        UITheme.styleTextField(senhaField);

        JButton salvar = UITheme.successButton("💾  Salvar");

        JPanel card = UITheme.card(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 4, 0);
        int y = 0;
        gbc.gridy = y++; card.add(UITheme.formLabel("Nome:"), gbc);
        gbc.gridy = y++; card.add(nome, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Login da Empresa:"), gbc);
        gbc.gridy = y++; card.add(loginField, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Senha:"), gbc);
        gbc.gridy = y++; card.add(senhaField, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("CNPJ:"), gbc);
        gbc.gridy = y++; card.add(cnpj, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Telefone:"), gbc);
        gbc.gridy = y++; card.add(tel, gbc);
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

        if (emp != null) {
            nome.setText(emp.getNome());
            cnpj.setText(emp.getCnpj());
            tel.setText(emp.getTelefone());
        }

        salvar.addActionListener(e -> {

            if (empresa == null) empresa = new Empresa();

            empresa.setNome(nome.getText());
            empresa.setCnpj(cnpj.getText());
            empresa.setTelefone(tel.getText());

            if (empresa.getId() == null) {
                empresa = service.salvar(empresa);

                Usuario u = new Usuario();
                u.setLogin(loginField.getText());
                u.setSenha(PasswordUtil.hash(new String(senhaField.getPassword())));
                u.setNome(empresa.getNome());
                u.setRole(Role.EMPRESA);
                u.setStatus("ATIVO");
                u.setEmpresa(empresa);

                new UsuarioService().salvar(u);
            } else {
                service.atualizar(empresa);
            }

            parent.carregarTabela();
            dispose();
        });
    }
}
