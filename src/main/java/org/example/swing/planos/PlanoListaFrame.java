package org.example.swing.planos;

import org.example.DAO.PlanoDAO;
import org.example.model.Plano;
import org.example.model.Usuario;
import org.example.service.CartaoService;
import org.example.service.UsuarioService;
import org.example.session.SessionContext;
import org.example.swing.LoginFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PlanoListaFrame extends JFrame {

    private final PlanoDAO planoDAO = new PlanoDAO();
    private final CartaoService cartaoService = new CartaoService();
    private JTable tabela;

    public PlanoListaFrame() {
        setTitle("Planos disponíveis");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(6, 6));

        tabela = new JTable();
        JButton btnCarregar = new JButton("Atualizar");
        JButton btnContratar = new JButton("Contratar plano selecionado");
        JButton btnVoltar = new JButton("Voltar");

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.add(btnCarregar);
        topo.add(btnContratar);
        topo.add(btnVoltar);
        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnCarregar.addActionListener(e -> carregar());
        btnContratar.addActionListener(e -> contratarPlano());
        btnVoltar.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        carregar();
    }

    public void carregar() {
        List<Plano> lista = planoDAO.findAll();
        String[] cols = new String[]{"ID", "Nome", "Descrição", "Preço", "Empresa", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        for (Plano p : lista) {
            String empresaNome = p.getEmpresa() != null ? p.getEmpresa().getNome() : "(sem empresa)";
            Object[] row = new Object[]{
                    p.getId(),
                    p.getNome(), p.getDescricao(), p.getPreco(), empresaNome, p.getStatus()
            };
            model.addRow(row);
        }
        tabela.setModel(model);
    }

    private void contratarPlano() {
        int sel = tabela.getSelectedRow();
        if (sel == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um plano.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long planoId = ((Number) tabela.getValueAt(sel, 0)).longValue();
        Usuario u = SessionContext.getCurrentUser();
        if (u == null) {
            JOptionPane.showMessageDialog(this, "Você precisa estar logado para contratar um plano.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Plano plano = planoDAO.findById(planoId);
            cartaoService.emitirCartaoSocial(u, plano);
            u.setPlano(plano);
            new UsuarioService().atualizar(u);
            JOptionPane.showMessageDialog(this, "Plano contratado e cartão criado com sucesso!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao contratar plano: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}