package org.example.swing.planos;

import org.example.model.Empresa;
import org.example.model.Plano;
import org.example.service.EmpresaService;
import org.example.service.PlanoService;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class PlanoAdminFormFrame extends JFrame {

    private final PlanoService planoService = new PlanoService();
    private final EmpresaService empresaService = new EmpresaService();

    public PlanoAdminFormFrame() {
        setTitle("Criar Plano");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2));

        JTextField nomeField = new JTextField();
        JTextField descricaoField = new JTextField();
        JTextField precoField = new JTextField();
        JComboBox<Empresa> empresaBox = new JComboBox<>();

       // for (Empresa e : empresaService.listar()) empresaBox.addItem(e);

        JButton salvarBtn = new JButton("Salvar");

        List<Empresa> empresas = empresaService.listar();
        if (empresas.isEmpty()) {
            empresaBox.addItem(new Empresa() {{ setNome("NÃO POSSUI EMPRESAS"); }});
            empresaBox.setEnabled(false);
            salvarBtn.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Nenhuma empresa ativa disponível. Crie uma empresa antes de cadastrar planos.");
        } else {
            for (Empresa e : empresas) empresaBox.addItem(e);
        }

        add(new JLabel("Nome:"));
        add(nomeField);
        add(new JLabel("Descrição:"));
        add(descricaoField);
        add(new JLabel("Preço:"));
        add(precoField);
        add(new JLabel("Empresa:"));
        add(empresaBox);
        add(new JLabel());
        add(salvarBtn);

        salvarBtn.addActionListener(e -> {
            try {
                Plano plano = new Plano();
                plano.setNome(nomeField.getText());
                plano.setDescricao(descricaoField.getText());
                plano.setPreco(new BigDecimal(precoField.getText()));
                plano.setEmpresa((Empresa) empresaBox.getSelectedItem());
                planoService.salvar(plano);
                JOptionPane.showMessageDialog(this, "Plano criado com sucesso!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });
    }
}