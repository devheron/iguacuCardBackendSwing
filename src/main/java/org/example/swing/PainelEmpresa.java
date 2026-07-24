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
import org.example.swing.ui.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class PainelEmpresa extends JFrame {

    private final PlanoService planoService = new PlanoService();
    private final CartaoService cartaoService = new CartaoService();
    private final TransacaoService transacaoService = new TransacaoService();

    public PainelEmpresa() {
        setTitle("Painel da Empresa");
        setSize(900, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BG);

        Empresa empresa = SessionContext.getEmpresaLogada();
        if (empresa == null) {
            JOptionPane.showMessageDialog(this, "Empresa não vinculada ao usuário.");
            dispose();
            new LoginFrame().setVisible(true);
            return;
        }

        add(UITheme.headerBar("Empresa: " + empresa.getNome(),
                "Gerencie seus planos, cartões emitidos e transações"), BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> lista = new JList<>(model);
        lista.setFont(UITheme.FONT_BASE);
        lista.setBackground(UITheme.CARD_BG);
        lista.setBorder(new EmptyBorder(10, 12, 10, 12));

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(new EmptyBorder(0, 20, 0, 20));
        scroll.getViewport().setBackground(UITheme.CARD_BG);
        add(scroll, BorderLayout.CENTER);

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

        JPanel botoes = UITheme.footerBar();
        JButton btnMeusPlanos  = UITheme.primaryButton("📋  Meus Planos");
        JButton btnCriarPlano  = UITheme.successButton("➕  Criar Plano");
        JButton btnCartoes     = UITheme.primaryButton("💳  Cartões");
        JButton btnTransacoes  = UITheme.primaryButton("💰  Transações");
        JButton btnSair        = UITheme.dangerButton("↩  Sair");

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
