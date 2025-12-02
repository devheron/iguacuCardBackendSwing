package org.example.swing;

import org.example.model.Cartao;
import org.example.model.Empresa;
import org.example.service.CartaoService;
import org.example.DAO.EmpresaDAO;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class CartaoPanel extends JPanel {
    private final CartaoService service = new CartaoService();
    private final EmpresaDAO empresaDAO = new EmpresaDAO();

    private final JList<String> lista = new JList<>();
    private Empresa empresaFilter = null; // opcional

    public CartaoPanel() {
        setLayout(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnNovo = new JButton("Novo Cartão");
        JButton btnAtual = new JButton("Atualizar lista");
        top.add(btnNovo);
        top.add(btnAtual);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(lista), BorderLayout.CENTER);


        btnNovo.addActionListener(e -> {
            try {
                String numero = JOptionPane.showInputDialog(this, "Número:");
                if (numero == null) return;
                String titular = JOptionPane.showInputDialog(this, "Titular:");
                if (titular == null) return;
                String saldoStr = JOptionPane.showInputDialog(this, "Saldo inicial (ex: 100.00):");
                BigDecimal saldo = new BigDecimal(saldoStr);
                String tipo = JOptionPane.showInputDialog(this, "Tipo (BENEFICIO/VALE/SOCIAL):");

                Cartao c = new Cartao();
                c.setNumero(numero);
                c.setTitular(titular);
                c.setSaldo(saldo);
                c.setTipo(tipo);

                // Opcional: vincular empresa se quiser
                String empId = JOptionPane.showInputDialog(this, "ID da empresa (vazio para nenhum):");
                if (empId != null && !empId.isBlank()) {
                    Empresa emp = empresaDAO.findById(Long.parseLong(empId));
                    if (emp != null) c.setEmpresa(emp);
                }

                service.salvar(c);
                carregar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        btnAtual.addActionListener(e -> carregar());
        carregar();

    }

    private void carregar() {
        List<Cartao> todos = service.listarTodos();
        DefaultListModel<String> m = new DefaultListModel<>();
        for (Cartao c : todos) {
            String emp = c.getEmpresa() != null ? c.getEmpresa().getNome() : "-";
            m.addElement(String.format("%d | %s | %s | %s | Saldo: %s", c.getId(), c.getNumero(), c.getTitular(), emp, c.getSaldo()));
        }
        lista.setModel(m);
    }
}
