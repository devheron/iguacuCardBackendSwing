package org.example.swing.usuarios;

import org.example.model.Usuario;
import org.example.service.UsuarioService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UsuarioListaFrame extends JFrame {

    private final UsuarioService service = new UsuarioService();
    private JTable tabela;

    public UsuarioListaFrame() {
        setTitle("Usuários");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JButton btnNovo = new JButton("Novo Usuário");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");

        JPanel topo = new JPanel();
        topo.add(btnNovo);
        topo.add(btnEditar);
        topo.add(btnExcluir);
        add(topo, BorderLayout.NORTH);

        tabela = new JTable();
        add(new JScrollPane(tabela), BorderLayout.CENTER);

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
    }
}