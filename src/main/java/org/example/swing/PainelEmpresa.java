package org.example.swing;

import org.example.model.Cartao;
import org.example.model.Empresa;
import org.example.model.Plano;
import org.example.model.Transacao;
import org.example.service.CartaoService;
import org.example.service.PlanoService;
import org.example.service.TransacaoService;
import org.example.session.SessionContext;
import org.example.swing.cartoes.CartaoListaFrame;
import org.example.swing.planos.PlanoAdminFormFrame;
import org.example.swing.planos.PlanoListaFrame;
import org.example.swing.transacoes.TransacaoListaFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PainelEmpresa extends JFrame {

    private final PlanoService planoService = new PlanoService();
    private final CartaoService cartaoService = new CartaoService();
    private final TransacaoService transacaoService = new TransacaoService();

    public PainelEmpresa() {
        setTitle("Painel da Empresa");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Empresa empresa = SessionContext.getEmpresaLogada();
        if (empresa == null) {
            JOptionPane.showMessageDialog(this, "Empresa não vinculada ao usuário.");
            dispose();
            new LoginFrame().setVisible(true);
            return;
        }

        JLabel info = new JLabel("Empresa: " + empresa.getNome(), SwingConstants.CENTER);
        add(info, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> lista = new JList<>(model);
        add(new JScrollPane(lista), BorderLayout.CENTER);

        model.addElement("=== Planos da Empresa ===");
        List<Plano> planos = planoService.findByEmpresa(empresa);
        for (Plano p : planos) {
            model.addElement("Plano: " + p.getNome() + " | Preço: " + p.getPreco());
            List<Cartao> cartoes = cartaoService.findByPlano(p);
            for (Cartao c : cartoes) {
                model.addElement("  Cartão: " + c.getNumero() + " | Usuário: " + c.getUsuario().getNome());
            }
        }

        model.addElement("=== Transações ===");
        List<Transacao> transacoes = transacaoService.findByEmpresa(empresa);
        for (Transacao t : transacoes) {
            model.addElement("Transação: " + t.getDescricao() + " | Valor: " + t.getValor() + " | Cartão: " + t.getCartao().getNumero());
        }

        JPanel botoes = new JPanel(new GridLayout(1, 5));
        JButton btnMeusPlanos = new JButton("Meus Planos");
        JButton btnCriarPlano = new JButton("Criar Plano");
        JButton btnCartoes = new JButton("Cartões");
        JButton btnTransacoes = new JButton("Transações");
        JButton btnSair = new JButton("Sair");

        btnMeusPlanos.addActionListener(e -> new PlanoListaFrame().setVisible(true));
        btnCriarPlano.addActionListener(e -> new PlanoAdminFormFrame().setVisible(true));
        btnCartoes.addActionListener(e -> new CartaoListaFrame().setVisible(true));
        btnTransacoes.addActionListener(e -> new TransacaoListaFrame().setVisible(true));
        btnSair.addActionListener(e -> {
            SessionContext.logout();
            dispose();
            new LoginFrame().setVisible(true);
        });

        botoes.add(btnMeusPlanos);
        botoes.add(btnCriarPlano);
        botoes.add(btnCartoes);
        botoes.add(btnTransacoes);
        botoes.add(btnSair);
        add(botoes, BorderLayout.SOUTH);
    }
}