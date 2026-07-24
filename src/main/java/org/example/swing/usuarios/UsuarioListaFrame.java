package org.example.swing.usuarios;

import org.example.model.Usuario;
import org.example.service.UsuarioService;
import org.example.swing.ui.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UsuarioListaFrame extends JFrame {

    private final UsuarioService service = new UsuarioService();
    private JTable tabela;

    public UsuarioListaFrame() {
        setTitle("Usuários");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BG);

        add(UITheme.headerBar("Gerenciar Usuários", "Cadastre, edite ou remova usuários do sistema"),
                BorderLayout.NORTH);

        JButton btnNovo    = UITheme.successButton("➕  Novo Usuário");
        JButton btnEditar  = UITheme.primaryButton("✎  Editar");
        JButton btnExcluir = UITheme.dangerButton("🗑  Excluir");

        JPanel topo = UITheme.toolbar();
        topo.add(btnNovo);
        topo.add(btnEditar);
        topo.add(btnExcluir);

        tabela = new JTable();
        UITheme.styleTable(tabela);
        JScrollPane sp = new JScrollPane(tabela);
        sp.setBorder(new EmptyBorder(0, 16, 16, 16));
        sp.getViewport().setBackground(UITheme.CARD_BG);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(UITheme.BG);
        body.add(topo, BorderLayout.NORTH);
        body.add(sp, BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);

        carregarTabela();

        btnNovo.addActionListener(e -> new UsuarioFormFrame(null, this).setVisible(true));

        btnEditar.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um usuário");
                return;
            }
            Long id = Long.parseLong(tabela.getValueAt(row, 0).toString());
            Usuario u = service.buscar(id);
            new UsuarioFormFrame(u, this).setVisible(true);
        });

        btnExcluir.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um usuário");
                return;
            }
            Long id = Long.parseLong(tabela.getValueAt(row, 0).toString());
            service.deletar(id);
            carregarTabela();
        });
    }

    public void carregarTabela() {
        List<Usuario> lista = service.listar();

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Login", "Role", "Plano", "Empresa", "Status"}, 0
        );

        for (Usuario u : lista) {
            model.addRow(new Object[]{
                    u.getId(),
                    u.getNome(),
                    u.getLogin(),
                    u.getRole(),
                    u.getPlano() != null ? u.getPlano().getNome() : "Nenhum",
                    u.getEmpresa() != null ? u.getEmpresa().getNome() : "Nenhuma",
                    u.getStatus()
            });
        }

        tabela.setModel(model);
        UITheme.styleTable(tabela);
    }
}
