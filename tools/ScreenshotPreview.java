import org.example.model.*;
import org.example.model.enums.Role;
import org.example.session.SessionContext;
import org.example.swing.*;
import org.example.swing.ui.UITheme;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;

/**
 * Renders the refreshed Swing screens to PNG files without needing a display.
 * Uses mock data so the frames don't hit the database.
 */
public class ScreenshotPreview {

    private static final String OUT_DIR = "docs/screenshots/";

    public static void main(String[] args) throws Exception {
        System.setProperty("java.awt.headless", "false");
        UITheme.install();

        new File(OUT_DIR).mkdirs();

        capture(buildLoginFrame(),      "01-login.png");
        capture(buildRegisterDialog(),  "02-register.png");
        capture(buildPainelAdmin(),     "03-painel-admin.png");
        capture(buildPainelUsuario(),   "04-painel-usuario.png");
        capture(buildPainelEmpresa(),   "05-painel-empresa.png");
        capture(buildUsuarioLista(),    "06-usuarios-lista.png");
        capture(buildUsuarioForm(),     "07-usuario-form.png");
        capture(buildEmpresaLista(),    "08-empresas-lista.png");
        capture(buildEmpresaForm(),     "09-empresa-form.png");
        capture(buildPlanoLista(),      "10-planos-lista.png");
        capture(buildPlanoForm(),       "11-plano-form.png");
        capture(buildCartaoLista(),     "12-cartoes-lista.png");
        capture(buildCartaoForm(),      "13-cartao-form.png");
        capture(buildBeneficioLista(),  "14-beneficios-lista.png");
        capture(buildBeneficioForm(),   "15-beneficio-form.png");
        capture(buildTransacaoLista(),  "16-transacoes-lista.png");

        System.out.println("OK - screenshots saved under " + OUT_DIR);
        System.exit(0);
    }

    /* ---------- screenshot helper ---------- */

    private static final java.awt.Robot ROBOT;
    static {
        try { ROBOT = new java.awt.Robot(); }
        catch (java.awt.AWTException e) { throw new RuntimeException(e); }
    }

    private static void capture(JFrame frame, String filename) throws Exception {
        Dimension chosen = frame.getSize();
        if (chosen.width < 400 || chosen.height < 300) chosen = new Dimension(720, 520);
        frame.setUndecorated(true);
        frame.setLocation(0, 0);
        frame.setSize(chosen);
        frame.setVisible(true);
        // Give Swing time to lay out and paint into the X server
        Thread.sleep(400);
        SwingUtilities.invokeAndWait(() -> {
            frame.validate();
            frame.repaint();
        });
        Thread.sleep(200);

        Point loc = frame.getLocationOnScreen();
        java.awt.Rectangle rect = new java.awt.Rectangle(loc.x, loc.y, chosen.width, chosen.height);
        BufferedImage img = ROBOT.createScreenCapture(rect);

        File out = new File(OUT_DIR + filename);
        ImageIO.write(img, "png", out);
        System.out.println("wrote " + out.getPath() + " (" + img.getWidth() + "x" + img.getHeight() + ")");
        frame.dispose();
    }

    /* ---------- frame builders (avoid DB) ---------- */

    private static JFrame buildLoginFrame() {
        return new LoginFrame();
    }

    private static JFrame buildRegisterDialog() {
        // RegisterDialog is a JDialog — wrap it in a JFrame-like preview
        JFrame f = new JFrame("IguaçuCard - Cadastrar Usuário");
        f.setSize(480, 560);
        f.setLayout(new BorderLayout());
        f.getContentPane().setBackground(UITheme.BG);
        f.add(UITheme.headerBar("Cadastrar Usuário",
                "Crie sua conta para acessar o IguaçuCard"), BorderLayout.NORTH);

        JTextField login = new JTextField("joao.silva");
        JPasswordField senha = new JPasswordField("********");
        JTextField nome = new JTextField("João da Silva");
        JComboBox<Role> role = new JComboBox<>(Role.values());
        JTextField emp = new JTextField();

        UITheme.styleTextField(login);
        UITheme.styleTextField(senha);
        UITheme.styleTextField(nome);
        UITheme.styleTextField(emp);
        UITheme.styleComboBox(role);

        JButton salvar = UITheme.successButton("Salvar");
        JButton voltar = UITheme.secondaryButton("Voltar");

        JPanel card = UITheme.card(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 4, 0);
        int y = 0;
        gbc.gridy = y++; card.add(UITheme.formLabel("Login:"), gbc);
        gbc.gridy = y++; card.add(login, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Senha:"), gbc);
        gbc.gridy = y++; card.add(senha, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Nome:"), gbc);
        gbc.gridy = y++; card.add(nome, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Tipo de Conta:"), gbc);
        gbc.gridy = y++; card.add(role, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Empresa (somente EMPRESA):"), gbc);
        gbc.gridy = y++; card.add(emp, gbc);
        JPanel bs = new JPanel(new GridLayout(1, 2, 10, 0));
        bs.setOpaque(false);
        bs.add(salvar); bs.add(voltar);
        gbc.gridy = y++; gbc.insets = new Insets(14, 0, 4, 0); card.add(bs, gbc);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(UITheme.BG);
        wrapper.setBorder(new EmptyBorder(20, 30, 20, 30));
        GridBagConstraints wc = new GridBagConstraints();
        wc.fill = GridBagConstraints.HORIZONTAL; wc.weightx = 1.0;
        wrapper.add(card, wc);
        f.add(wrapper, BorderLayout.CENTER);
        return f;
    }

    private static JFrame buildPainelAdmin() {
        return new PainelAdmin();
    }

    private static JFrame buildPainelUsuario() {
        Usuario u = mockUsuario("João da Silva", Role.USUARIO, null);
        SessionContext.setCurrentUser(u);

        JFrame f = new JFrame("Painel do Usuário");
        f.setSize(820, 560);
        f.setLayout(new BorderLayout());
        f.getContentPane().setBackground(UITheme.BG);
        f.add(UITheme.headerBar("Bem-vindo, " + u.getNome(),
                "Área do usuário — visualize seus cartões e transações"), BorderLayout.NORTH);

        DefaultListModel<String> m = new DefaultListModel<>();
        m.addElement("=== Meus Cartões ===");
        m.addElement("Cartão: 5541 2039 7712 8834 | Saldo: R$ 145,00 | Tipo: SOCIAL");
        m.addElement("Cartão: 4408 9987 1156 4029 | Saldo: R$ 320,50 | Tipo: SOCIAL");
        JList<String> list = new JList<>(m);
        list.setFont(UITheme.FONT_BASE);
        list.setBackground(UITheme.CARD_BG);
        list.setBorder(new EmptyBorder(10, 12, 10, 12));
        JScrollPane sp = new JScrollPane(list);
        sp.setBorder(new EmptyBorder(0, 20, 0, 20));
        sp.getViewport().setBackground(UITheme.CARD_BG);
        f.add(sp, BorderLayout.CENTER);

        JPanel bar = UITheme.footerBar();
        bar.add(UITheme.primaryButton("💳  Ver Cartões"));
        bar.add(UITheme.primaryButton("📋  Ver Planos"));
        bar.add(UITheme.primaryButton("💰  Ver Transações"));
        bar.add(UITheme.dangerButton("↩  Sair"));
        f.add(bar, BorderLayout.SOUTH);
        return f;
    }

    private static JFrame buildPainelEmpresa() {
        Empresa emp = new Empresa();
        emp.setNome("Farmácia Iguaçu Ltda");

        JFrame f = new JFrame("Painel da Empresa");
        f.setSize(900, 620);
        f.setLayout(new BorderLayout());
        f.getContentPane().setBackground(UITheme.BG);
        f.add(UITheme.headerBar("Empresa: " + emp.getNome(),
                "Gerencie seus planos, cartões emitidos e transações"), BorderLayout.NORTH);

        DefaultListModel<String> m = new DefaultListModel<>();
        m.addElement("=== Planos da Empresa ===");
        m.addElement("Plano: Saúde Bronze | Preço: 29.90");
        m.addElement("  Cartão: 5541 2039 7712 8834 | Usuário: João da Silva");
        m.addElement("  Cartão: 4408 9987 1156 4029 | Usuário: Maria Souza");
        m.addElement("Plano: Saúde Prata | Preço: 49.90");
        m.addElement("  Cartão: 6612 5551 0090 2237 | Usuário: Carlos Nunes");
        m.addElement("=== Transações ===");
        m.addElement("Transação: Consulta Cardiologia | Valor: 90.00 | Cartão: 5541 2039 7712 8834");
        m.addElement("Transação: Exame Hemograma      | Valor: 45.00 | Cartão: 4408 9987 1156 4029");

        JList<String> list = new JList<>(m);
        list.setFont(UITheme.FONT_BASE);
        list.setBackground(UITheme.CARD_BG);
        list.setBorder(new EmptyBorder(10, 12, 10, 12));
        JScrollPane sp = new JScrollPane(list);
        sp.setBorder(new EmptyBorder(0, 20, 0, 20));
        sp.getViewport().setBackground(UITheme.CARD_BG);
        f.add(sp, BorderLayout.CENTER);

        JPanel bar = UITheme.footerBar();
        bar.add(UITheme.primaryButton("📋  Meus Planos"));
        bar.add(UITheme.successButton("➕  Criar Plano"));
        bar.add(UITheme.primaryButton("💳  Cartões"));
        bar.add(UITheme.primaryButton("💰  Transações"));
        bar.add(UITheme.dangerButton("↩  Sair"));
        f.add(bar, BorderLayout.SOUTH);
        return f;
    }

    private static JFrame buildUsuarioLista() {
        JFrame f = listFrameShell("Gerenciar Usuários",
                "Cadastre, edite ou remova usuários do sistema",
                new String[]{"ID", "Nome", "Login", "Role", "Plano", "Empresa", "Status"},
                new Object[][]{
                        {1L, "Admin Master",     "admin",     "ADMIN",    "Nenhum",       "Nenhuma",                 "ATIVO"},
                        {2L, "João da Silva",    "joao",      "USUARIO",  "Saúde Prata",  "Nenhuma",                 "ATIVO"},
                        {3L, "Maria Souza",      "maria",     "USUARIO",  "Saúde Bronze", "Nenhuma",                 "ATIVO"},
                        {4L, "Farmácia Iguaçu",  "farmacia",  "EMPRESA",  "Nenhum",       "Farmácia Iguaçu Ltda",    "ATIVO"},
                        {5L, "Clínica Vida",     "clinica",   "EMPRESA",  "Nenhum",       "Clínica Vida S/A",        "ATIVO"},
                        {6L, "Carlos Nunes",     "carlos",    "USUARIO",  "Saúde Ouro",   "Nenhuma",                 "INATIVO"},
                });
        return f;
    }

    private static JFrame buildUsuarioForm() {
        return simpleFormShell("Novo Usuário", "Informe os dados do usuário",
                new String[][]{
                        {"Nome:",   "João da Silva"},
                        {"Login:",  "joao.silva"},
                        {"Senha:",  "********"},
                        {"Role:",   "USUARIO"},
                        {"Status:", "ATIVO"},
                        {"Empresa:","Nenhuma"},
                }, 520, 540);
    }

    private static JFrame buildEmpresaLista() {
        return listFrameShell("Empresas", "Cadastre e gerencie empresas parceiras",
                new String[]{"ID", "Nome", "CNPJ", "Telefone"},
                new Object[][]{
                        {1L, "Farmácia Iguaçu Ltda", "12.345.678/0001-90", "(45) 3555-1234"},
                        {2L, "Clínica Vida S/A",     "98.765.432/0001-11", "(45) 3555-9876"},
                        {3L, "Laboratório Oeste",    "45.678.912/0001-22", "(45) 3222-3344"},
                        {4L, "Ótica Foz",            "33.221.100/0001-77", "(45) 3577-0011"},
                });
    }

    private static JFrame buildEmpresaForm() {
        return simpleFormShell("Nova Empresa", "Preencha os dados da empresa parceira",
                new String[][]{
                        {"Nome:",              "Farmácia Iguaçu Ltda"},
                        {"Login da Empresa:",  "farmacia.iguacu"},
                        {"Senha:",             "********"},
                        {"CNPJ:",              "12.345.678/0001-90"},
                        {"Telefone:",          "(45) 3555-1234"},
                }, 520, 520);
    }

    private static JFrame buildPlanoLista() {
        return listFrameShell("Planos disponíveis",
                "Visualize e contrate planos oferecidos pelas empresas",
                new String[]{"ID", "Nome", "Descrição", "Preço", "Empresa", "Status"},
                new Object[][]{
                        {1L, "Saúde Bronze", "Consultas e exames básicos",          "R$ 29,90",  "Clínica Vida S/A",        "ATIVO"},
                        {2L, "Saúde Prata",  "Bronze + odontologia",                "R$ 49,90",  "Clínica Vida S/A",        "ATIVO"},
                        {3L, "Saúde Ouro",   "Prata + descontos em farmácia",       "R$ 79,90",  "Farmácia Iguaçu Ltda",    "ATIVO"},
                        {4L, "Empresarial",  "Plano corporativo, sob consulta",     "Sob consulta", "Farmácia Iguaçu Ltda", "ATIVO"},
                });
    }

    private static JFrame buildPlanoForm() {
        return simpleFormShell("Criar Plano", "Cadastre um novo plano vinculado a uma empresa",
                new String[][]{
                        {"Nome:",       "Saúde Ouro"},
                        {"Descrição:",  "Prata + descontos em farmácia"},
                        {"Preço:",      "79.90"},
                        {"Empresa:",    "Farmácia Iguaçu Ltda"},
                }, 520, 480);
    }

    private static JFrame buildCartaoLista() {
        return listFrameShell("Cartões", "Todos os cartões sociais emitidos",
                new String[]{"ID", "Número", "Validade", "Usuário", "Plano"},
                new Object[][]{
                        {1L, "5541 2039 7712 8834", "2030-01-14", "João da Silva",  "Saúde Prata"},
                        {2L, "4408 9987 1156 4029", "2030-02-08", "Maria Souza",    "Saúde Bronze"},
                        {3L, "6612 5551 0090 2237", "2030-05-22", "Carlos Nunes",   "Saúde Ouro"},
                        {4L, "3388 7712 4432 8811", "2030-07-01", "Ana Beatriz",    "Saúde Bronze"},
                });
    }

    private static JFrame buildCartaoForm() {
        return simpleFormShell("Novo Cartão", "Informe os dados do cartão social",
                new String[][]{
                        {"Número:",   "5541 2039 7712 8834"},
                        {"Validade:", "2030-01-14"},
                        {"Usuário:",  "João da Silva"},
                        {"Plano:",    "Saúde Prata"},
                }, 520, 480);
    }

    private static JFrame buildBeneficioLista() {
        return listFrameShell("Benefícios", "Benefícios oferecidos por cada plano",
                new String[]{"ID", "Benefício", "Plano"},
                new Object[][]{
                        {1L, "Consulta clínico geral",    "Saúde Bronze"},
                        {2L, "Exames laboratoriais",      "Saúde Bronze"},
                        {3L, "Consulta odontológica",     "Saúde Prata"},
                        {4L, "Desconto em farmácia 30%",  "Saúde Ouro"},
                        {5L, "Consulta com especialista", "Saúde Ouro"},
                });
    }

    private static JFrame buildBeneficioForm() {
        return simpleFormShell("Novo Benefício", "Vincule o benefício ao plano correspondente",
                new String[][]{
                        {"Nome:",  "Consulta com especialista"},
                        {"Plano:", "Saúde Ouro"},
                }, 500, 380);
    }

    private static JFrame buildTransacaoLista() {
        return listFrameShell("Transações",
                "Movimentações financeiras dos cartões emitidos",
                new String[]{"ID", "Descrição", "Valor", "Cartão", "Empresa", "Data"},
                new Object[][]{
                        {1L, "Consulta Cardiologia", "R$ 90,00", "5541 2039 7712 8834", "Clínica Vida S/A",    "2026-07-01 09:12"},
                        {2L, "Hemograma completo",   "R$ 45,00", "4408 9987 1156 4029", "Laboratório Oeste",   "2026-07-05 14:30"},
                        {3L, "Consulta odontológica","R$ 120,00","6612 5551 0090 2237", "Clínica Vida S/A",    "2026-07-08 10:00"},
                        {4L, "Remédio antibiótico",  "R$ 34,50", "3388 7712 4432 8811", "Farmácia Iguaçu Ltda","2026-07-10 17:45"},
                });
    }

    /* ---------- generic shells ---------- */

    private static JFrame listFrameShell(String title, String subtitle, String[] cols, Object[][] rows) {
        JFrame f = new JFrame(title);
        f.setSize(900, 500);
        f.setLayout(new BorderLayout());
        f.getContentPane().setBackground(UITheme.BG);
        f.add(UITheme.headerBar(title, subtitle), BorderLayout.NORTH);

        JPanel topo = UITheme.toolbar();
        topo.add(UITheme.successButton("➕  Novo"));
        topo.add(UITheme.primaryButton("✎  Editar"));
        topo.add(UITheme.dangerButton("🗑  Excluir"));

        JTable tabela = new JTable(new DefaultTableModel(rows, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
        UITheme.styleTable(tabela);

        JScrollPane sp = new JScrollPane(tabela);
        sp.setBorder(new EmptyBorder(0, 16, 16, 16));
        sp.getViewport().setBackground(UITheme.CARD_BG);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(UITheme.BG);
        body.add(topo, BorderLayout.NORTH);
        body.add(sp, BorderLayout.CENTER);
        f.add(body, BorderLayout.CENTER);
        return f;
    }

    private static JFrame simpleFormShell(String title, String subtitle,
                                          String[][] fields, int w, int h) {
        JFrame f = new JFrame(title);
        f.setSize(w, h);
        f.setLayout(new BorderLayout());
        f.getContentPane().setBackground(UITheme.BG);
        f.add(UITheme.headerBar(title, subtitle), BorderLayout.NORTH);

        JPanel card = UITheme.card(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 4, 0);
        int y = 0;
        for (String[] row : fields) {
            gbc.gridy = y++; card.add(UITheme.formLabel(row[0]), gbc);
            JTextField tf = new JTextField(row[1]);
            UITheme.styleTextField(tf);
            gbc.gridy = y++; card.add(tf, gbc);
        }
        JButton salvar = UITheme.successButton("💾  Salvar");
        gbc.gridy = y++; gbc.insets = new Insets(14, 0, 4, 0);
        card.add(salvar, gbc);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(UITheme.BG);
        wrapper.setBorder(new EmptyBorder(16, 24, 16, 24));
        GridBagConstraints wc = new GridBagConstraints();
        wc.fill = GridBagConstraints.HORIZONTAL; wc.weightx = 1.0;
        wrapper.add(card, wc);
        f.add(wrapper, BorderLayout.CENTER);
        return f;
    }

    private static Usuario mockUsuario(String nome, Role r, Empresa e) {
        Usuario u = new Usuario();
        u.setNome(nome);
        u.setLogin(nome.toLowerCase().replace(' ', '.'));
        u.setRole(r);
        u.setEmpresa(e);
        u.setStatus("ATIVO");
        return u;
    }
}
