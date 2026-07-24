package org.example.swing.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Enumeration;

public final class UITheme {

    public static final Color PRIMARY       = new Color(0x1E88E5);
    public static final Color PRIMARY_DARK  = new Color(0x1565C0);
    public static final Color PRIMARY_LIGHT = new Color(0xE3F2FD);
    public static final Color ACCENT        = new Color(0x00ACC1);
    public static final Color DANGER        = new Color(0xE53935);
    public static final Color SUCCESS       = new Color(0x2E7D32);

    public static final Color BG            = new Color(0xF6F8FC);
    public static final Color CARD_BG       = Color.WHITE;
    public static final Color TEXT          = new Color(0x1F2A44);
    public static final Color TEXT_MUTED    = new Color(0x64748B);
    public static final Color BORDER        = new Color(0xE2E8F0);

    public static final Font FONT_BASE   = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOLD   = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_TITLE  = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_H2     = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_SMALL  = new Font("Segoe UI", Font.PLAIN, 12);

    private UITheme() {}

    public static void install() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        UIManager.put("nimbusBase",        PRIMARY_DARK);
        UIManager.put("nimbusBlueGrey",    new Color(0xCBD5E1));
        UIManager.put("control",           BG);
        UIManager.put("info",              PRIMARY_LIGHT);
        UIManager.put("nimbusSelection",   PRIMARY);
        UIManager.put("nimbusFocus",       PRIMARY);
        UIManager.put("text",              TEXT);
        UIManager.put("Table.background",  CARD_BG);
        UIManager.put("Table.alternateRowColor", new Color(0xF1F5F9));

        setGlobalFont(new FontUIResource(FONT_BASE));
    }

    private static void setGlobalFont(FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }

    public static JPanel card(LayoutManager layout) {
        JPanel p = new JPanel(layout);
        p.setBackground(CARD_BG);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(18, 18, 18, 18)));
        return p;
    }

    public static JPanel page(LayoutManager layout) {
        JPanel p = new JPanel(layout);
        p.setBackground(BG);
        p.setBorder(new EmptyBorder(16, 16, 16, 16));
        return p;
    }

    public static JLabel title(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_TITLE);
        l.setForeground(PRIMARY_DARK);
        l.setBorder(new EmptyBorder(0, 0, 12, 0));
        return l;
    }

    public static JLabel subtitle(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_H2);
        l.setForeground(TEXT);
        l.setBorder(new EmptyBorder(0, 0, 8, 0));
        return l;
    }

    public static JLabel muted(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_SMALL);
        l.setForeground(TEXT_MUTED);
        return l;
    }

    public static JLabel formLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_BOLD);
        l.setForeground(TEXT);
        l.setBorder(new EmptyBorder(6, 0, 6, 10));
        return l;
    }

    public static JButton primaryButton(String text) {
        JButton b = new JButton(text);
        styleButton(b, PRIMARY, Color.WHITE);
        return b;
    }

    public static JButton secondaryButton(String text) {
        JButton b = new JButton(text);
        styleButton(b, Color.WHITE, PRIMARY_DARK);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY, 1, true),
                new EmptyBorder(8, 18, 8, 18)));
        return b;
    }

    public static JButton dangerButton(String text) {
        JButton b = new JButton(text);
        styleButton(b, DANGER, Color.WHITE);
        return b;
    }

    public static JButton successButton(String text) {
        JButton b = new JButton(text);
        styleButton(b, SUCCESS, Color.WHITE);
        return b;
    }

    public static void styleAsPrimary(AbstractButton b)  { styleButton(b, PRIMARY, Color.WHITE); }
    public static void styleAsSecondary(AbstractButton b) {
        styleButton(b, Color.WHITE, PRIMARY_DARK);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY, 1, true),
                new EmptyBorder(8, 18, 8, 18)));
    }
    public static void styleAsDanger(AbstractButton b)   { styleButton(b, DANGER, Color.WHITE); }
    public static void styleAsSuccess(AbstractButton b)  { styleButton(b, SUCCESS, Color.WHITE); }

    private static void styleButton(AbstractButton b, Color bg, Color fg) {
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(FONT_BOLD);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(9, 20, 9, 20));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorderPainted(false);
    }

    public static void styleTextField(JTextField f) {
        f.setFont(FONT_BASE);
        f.setForeground(TEXT);
        f.setBackground(Color.WHITE);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(7, 10, 7, 10)));
    }

    public static void styleComboBox(JComboBox<?> c) {
        c.setFont(FONT_BASE);
        c.setBackground(Color.WHITE);
        c.setForeground(TEXT);
    }

    public static void styleTable(JTable table) {
        table.setFont(FONT_BASE);
        table.setRowHeight(28);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(PRIMARY_LIGHT);
        table.setSelectionForeground(TEXT);
        table.setBackground(CARD_BG);
        table.setGridColor(BORDER);

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_BOLD);
        header.setBackground(PRIMARY_DARK);
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(100, 34));

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(PRIMARY_DARK);
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setFont(FONT_BOLD);
        headerRenderer.setOpaque(true);
        headerRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        headerRenderer.setBorder(new EmptyBorder(6, 10, 6, 10));
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.LEFT);
        center.setBorder(new EmptyBorder(4, 10, 4, 10));
        table.setDefaultRenderer(Object.class, center);
    }

    public static JPanel headerBar(String title) {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(PRIMARY_DARK);
        bar.setBorder(new EmptyBorder(14, 20, 14, 20));
        JLabel l = new JLabel(title);
        l.setForeground(Color.WHITE);
        l.setFont(FONT_TITLE.deriveFont(20f));
        bar.add(l, BorderLayout.WEST);
        return bar;
    }

    public static JPanel headerBar(String title, String subtitle) {
        JPanel bar = new JPanel(new GridLayout(2, 1));
        bar.setBackground(PRIMARY_DARK);
        bar.setBorder(new EmptyBorder(14, 20, 14, 20));

        JLabel t = new JLabel(title);
        t.setForeground(Color.WHITE);
        t.setFont(FONT_TITLE.deriveFont(20f));

        JLabel s = new JLabel(subtitle);
        s.setForeground(new Color(0xBBDEFB));
        s.setFont(FONT_SMALL);

        bar.add(t);
        bar.add(s);
        return bar;
    }

    public static JPanel toolbar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        bar.setBackground(BG);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER));
        return bar;
    }

    public static JPanel footerBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bar.setBackground(BG);
        bar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER));
        return bar;
    }
}
