package org.example.swing.empresas;

import org.example.model.Empresa;
import org.example.model.Usuario;
import org.example.model.enums.Role;
import org.example.security.PasswordUtil;
import org.example.service.EmpresaService;
import org.example.service.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class EmpresaFormFrame extends JFrame {

    private Empresa empresa;
    private EmpresaListaFrame parent;
    private EmpresaService service = new EmpresaService();

    public EmpresaFormFrame(Empresa emp, EmpresaListaFrame parent) {

        this.empresa = emp;
        this.parent = parent;

        setTitle(emp == null ? "Nova Empresa" : "Editar Empresa");
        setSize(400,300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5,2));

        JTextField nome = new JTextField();
        JTextField cnpj = new JTextField();
        JTextField tel = new JTextField();
        JTextField loginField = new JTextField();
        JPasswordField senhaField = new JPasswordField();

        JButton salvar = new JButton("Salvar");

        add(new JLabel("Nome:"));
        add(nome);
        add(new JLabel("Login da Empresa:"));
        add(loginField);
        add(new JLabel("Senha:"));
        add(senhaField);
        add(new JLabel("CNPJ:"));
        add(cnpj);
        add(new JLabel("Telefone:"));
        add(tel);
        add(new JLabel());
        add(salvar);

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
