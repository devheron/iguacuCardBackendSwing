package org.example.swing.planos;

import org.example.model.Empresa;
import org.example.model.Plano;
import org.example.service.EmpresaService;
import org.example.service.PlanoService;
import org.example.swing.ui.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class PlanoAdminFormFrame extends JFrame {

    private final PlanoService planoService = new PlanoService();
    private final EmpresaService empresaService = new EmpresaService();

    public PlanoAdminFormFrame() {
        setTitle("Criar Plano");
        setSize(520, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BG);

        add(UITheme.headerBar("Criar Plano", "Cadastre um novo plano vinculado a uma empresa"),
                BorderLayout.NORTH);

        JTextField nomeField = new JTextField();
        JTextField descricaoField = new JTextField();
        JTextField precoField = new JTextField();
        JComboBox<Empresa> empresaBox = new JComboBox<>();
        UITheme.styleTextField(nomeField);
        UITheme.styleTextField(descricaoField);
        UITheme.styleTextField(precoField);
        UITheme.styleComboBox(empresaBox);

        JButton salvarBtn = UITheme.successButton("💾  Salvar");

        List<Empresa> empresas = empresaService.listar();
        if (empresas.isEmpty()) {
            empresaBox.addItem(new Empresa() {{ setNome("NÃO POSSUI EMPRESAS"); }});
            empresaBox.setEnabled(false);
            salvarBtn.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Nenhuma empresa ativa disponível. Crie uma empresa antes de cadastrar planos.");
        } else {
            for (Empresa e : empresas) empresaBox.addItem(e);
        }

        JPanel card = UITheme.card(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 4, 0);
        int y = 0;
        gbc.gridy = y++; card.add(UITheme.formLabel("Nome:"), gbc);
        gbc.gridy = y++; card.add(nomeField, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Descrição:"), gbc);
        gbc.gridy = y++; card.add(descricaoField, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Preço:"), gbc);
        gbc.gridy = y++; card.add(precoField, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Empresa:"), gbc);
        gbc.gridy = y++; card.add(empresaBox, gbc);
        gbc.gridy = y++;
        gbc.insets = new Insets(14, 0, 4, 0);
        card.add(salvarBtn, gbc);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(UITheme.BG);
        wrapper.setBorder(new EmptyBorder(16, 24, 16, 24));
        GridBagConstraints wc = new GridBagConstraints();
        wc.fill = GridBagConstraints.HORIZONTAL;
        wc.weightx = 1.0;
        wrapper.add(card, wc);
        add(wrapper, BorderLayout.CENTER);

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
