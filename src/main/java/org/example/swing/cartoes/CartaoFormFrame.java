package org.example.swing.cartoes;

import org.example.model.Cartao;
import org.example.model.Plano;
import org.example.model.Usuario;
import org.example.service.CartaoService;
import org.example.service.PlanoService;
import org.example.service.UsuarioService;
import org.example.swing.ui.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CartaoFormFrame extends JFrame {

    private Cartao cartao;
    private CartaoListaFrame parent;
    private CartaoService service = new CartaoService();
    private UsuarioService usuarioService = new UsuarioService();
    private PlanoService planoService = new PlanoService();

    public CartaoFormFrame(Cartao c, CartaoListaFrame parent) {

        this.cartao = c;
        this.parent = parent;

        setTitle(c == null ? "Novo Cartão" : "Editar Cartão");
        setSize(520, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BG);

        add(UITheme.headerBar(c == null ? "Novo Cartão" : "Editar Cartão",
                "Informe os dados do cartão social"), BorderLayout.NORTH);

        JTextField numero = new JTextField();
        JTextField validade = new JTextField();
        JComboBox<Usuario> usuarioBox = new JComboBox<>();
        JComboBox<Plano> planoBox = new JComboBox<>();

        UITheme.styleTextField(numero);
        UITheme.styleTextField(validade);
        UITheme.styleComboBox(usuarioBox);
        UITheme.styleComboBox(planoBox);

        for (Usuario u : usuarioService.listar()) usuarioBox.addItem(u);
        for (Plano p : planoService.listar()) planoBox.addItem(p);

        JButton salvar = UITheme.successButton("💾  Salvar");

        JPanel card = UITheme.card(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 4, 0);
        int y = 0;
        gbc.gridy = y++; card.add(UITheme.formLabel("Número:"), gbc);
        gbc.gridy = y++; card.add(numero, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Validade:"), gbc);
        gbc.gridy = y++; card.add(validade, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Usuário:"), gbc);
        gbc.gridy = y++; card.add(usuarioBox, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Plano:"), gbc);
        gbc.gridy = y++; card.add(planoBox, gbc);
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

        if (c != null) {
            numero.setText(c.getNumero());
            validade.setText(c.getValidade());
            usuarioBox.setSelectedItem(c.getUsuario());
            planoBox.setSelectedItem(c.getPlano());
        }

        salvar.addActionListener(e -> {

            if (cartao == null) cartao = new Cartao();

            cartao.setNumero(numero.getText());
            cartao.setValidade(validade.getText());
            cartao.setUsuario((Usuario) usuarioBox.getSelectedItem());
            cartao.setPlano((Plano) planoBox.getSelectedItem());

            if (cartao.getId() == null) service.salvar(cartao);
            else service.atualizar(cartao);

            parent.carregar();
            dispose();
        });
    }
}
