package views;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

// Source Code: Claude AI

public class DataKamarPanel extends JPanel {
    private static final Color TEAL_PRIMARY  = new Color(20, 184, 166);
    private static final Color TEAL_DARK     = new Color(13, 148, 136);
    private static final Color BG_PAGE       = new Color(245, 247, 252);
    private static final Color BG_CARD       = Color.WHITE;
    private static final Color TEXT_DARK     = new Color(15, 23, 42);
    private static final Color TEXT_MED      = new Color(71, 85, 105);
    private static final Color TEXT_LIGHT    = new Color(148, 163, 184);
    private static final Color BORDER_COLOR  = new Color(226, 232, 240);

    private static final Color COLOR_TERISI    = new Color(20, 184, 166);
    private static final Color COLOR_TERSEDIA  = new Color(16, 185, 129);
    private static final Color COLOR_PERAWATAN = new Color(245, 158, 11);

    public DataKamarPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 252));
        add(createMainArea(), BorderLayout.CENTER);
    }

    private JPanel createMainArea() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG_PAGE);
        main.add(createTopBar(), BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setBackground(BG_PAGE);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(24, 28, 28, 28));

        content.add(createStatRow());
        content.add(Box.createRigidArea(new Dimension(0, 20)));
        content.add(createFilterBar());
        content.add(Box.createRigidArea(new Dimension(0, 16)));
        content.add(createKamarTableCard());

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        scroll.setBackground(BG_PAGE);
        main.add(scroll, BorderLayout.CENTER);

        return main;
    }

    private JPanel createTopBar() {
        JPanel bar = new JPanel(null);
        bar.setBackground(BG_CARD);
        bar.setPreferredSize(new Dimension(0, 64));
        bar.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_COLOR));

        JLabel title = new JLabel("Data Kamar");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(TEXT_DARK);
        title.setBounds(28, 14, 300, 26);
        bar.add(title);

        JLabel sub = new JLabel("Kelola semua kamar kost Anda");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(TEXT_LIGHT);
        sub.setBounds(28, 38, 300, 16);
        bar.add(sub);

        JButton addBtn = new JButton("+ Tambah Kamar") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? TEAL_DARK : TEAL_PRIMARY);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addBtn.setForeground(Color.WHITE);
        addBtn.setContentAreaFilled(false);
        addBtn.setBorderPainted(false);
        addBtn.setFocusPainted(false);
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.setBounds(680, 17, 148, 34);
        bar.add(addBtn);

        return bar;
    }

    private JPanel createStatRow() {
        JPanel row = new JPanel(new GridLayout(1, 3, 16, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 96));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        row.add(createMiniStatCard("Total Kamar", "24", new Color(20, 184, 166)));
        row.add(createMiniStatCard("Terisi", "18",     new Color(99, 102, 241)));
        row.add(createMiniStatCard("Tersedia", "4",    new Color(16, 185, 129)));

        return row;
    }

    private JPanel createMiniStatCard(String label, String value, Color accent) {
        JPanel card = new JPanel(null);
        card.setBackground(BG_CARD);
        card.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(0, 0, 0, 0)
        ));

        JPanel bar = new JPanel() {
            protected void paintComponent(Graphics g) {
                g.setColor(accent);
                g.fillRect(0, 0, getWidth(), 3);
            }
        };
        bar.setOpaque(false);
        bar.setBounds(0, 0, 400, 3);
        card.add(bar);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 26));
        val.setForeground(TEXT_DARK);
        val.setBounds(20, 18, 200, 36);
        card.add(val);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(TEXT_MED);
        lbl.setBounds(20, 52, 200, 18);
        card.add(lbl);

        return card;
    }

    private JPanel createFilterBar() {
        JPanel bar = new JPanel(null);
        bar.setOpaque(false);
        bar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        bar.setPreferredSize(new Dimension(0, 40));
        bar.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel search = new JPanel(new BorderLayout(6, 0));
        search.setBackground(BG_CARD);
        search.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(0, 10, 0, 10)
        ));
        search.setBounds(0, 0, 240, 36);

        JLabel ico = new JLabel("⌕");
        ico.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        ico.setForeground(TEXT_LIGHT);
        JTextField tf = new JTextField("Cari nomor / tipe kamar...");
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tf.setForeground(TEXT_LIGHT);
        tf.setBorder(null);
        tf.setBackground(BG_CARD);
        tf.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (tf.getText().startsWith("Cari")) { tf.setText(""); tf.setForeground(TEXT_DARK); }
            }
            public void focusLost(FocusEvent e) {
                if (tf.getText().isBlank()) { tf.setText("Cari nomor / tipe kamar..."); tf.setForeground(TEXT_LIGHT); }
            }
        });
        search.add(ico, BorderLayout.WEST);
        search.add(tf, BorderLayout.CENTER);
        bar.add(search);

        bar.add(createDropdown(new String[]{"Semua Status", "Terisi", "Tersedia", "Perawatan"}, 256, 36));

        bar.add(createDropdown(new String[]{"Semua Tipe", "Standard", "Deluxe", "Suite"}, 376, 36));

        return bar;
    }

    private JComboBox<String> createDropdown(String[] items, int x, int y) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cb.setForeground(TEXT_DARK);
        cb.setBackground(BG_CARD);
        cb.setBounds(x, 0, 150, 36);
        cb.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        cb.setFocusable(false);
        return cb;
    }

    private JPanel createKamarTableCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(BG_CARD);
        card.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_CARD);
        header.setBorder(new EmptyBorder(14, 18, 14, 18));

        JLabel title = new JLabel("Daftar Kamar");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(TEXT_DARK);
        header.add(title, BorderLayout.WEST);

        JLabel countLbl = new JLabel("24 kamar ditemukan");
        countLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        countLbl.setForeground(TEXT_LIGHT);
        header.add(countLbl, BorderLayout.EAST);

        card.add(header, BorderLayout.NORTH);

        String[] cols = {"No. Kamar", "Tipe", "Lantai", "Harga/Bulan", "Fasilitas", "Status", "Aksi"};
        Object[][] data = {
            {"1A",  "Standard", "1", "Rp 700.000",  "AC, Kasur, Lemari",          "Tersedia",  ""},
            {"1B",  "Standard", "1", "Rp 700.000",  "AC, Kasur, Lemari",          "Terisi",    ""},
            {"1C",  "Deluxe",   "1", "Rp 950.000",  "AC, Kasur, Lemari, TV",      "Terisi",    ""},
            {"2A",  "Standard", "2", "Rp 700.000",  "AC, Kasur, Lemari",          "Perawatan", ""},
            {"2B",  "Deluxe",   "2", "Rp 950.000",  "AC, Kasur, Lemari, TV",      "Terisi",    ""},
            {"2C",  "Deluxe",   "2", "Rp 950.000",  "AC, Kasur, Lemari, TV",      "Terisi",    ""},
            {"3A",  "Suite",    "3", "Rp 1.400.000","AC, Kasur, Lemari, TV, Dapur","Terisi",   ""},
            {"3B",  "Standard", "3", "Rp 700.000",  "AC, Kasur, Lemari",          "Tersedia",  ""},
            {"4A",  "Suite",    "4", "Rp 1.400.000","AC, Kasur, Lemari, TV, Dapur","Terisi",   ""},
            {"4B",  "Deluxe",   "4", "Rp 950.000",  "AC, Kasur, Lemari, TV",      "Tersedia",  ""},
        };

        JTable table = new JTable(data, cols) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(TEXT_DARK);
        table.setRowHeight(48);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBackground(BG_CARD);
        table.setSelectionBackground(new Color(240, 253, 250));
        table.setSelectionForeground(TEXT_DARK);

        JTableHeader th = table.getTableHeader();
        th.setFont(new Font("Segoe UI", Font.BOLD, 11));
        th.setForeground(TEXT_LIGHT);
        th.setBackground(new Color(248, 250, 252));
        th.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_COLOR));
        th.setPreferredSize(new Dimension(0, 38));
        th.setReorderingAllowed(false);

        DefaultTableCellRenderer padded = new DefaultTableCellRenderer() {
            { setBorder(new EmptyBorder(0, 18, 0, 0)); }
        };
        for (int i = 0; i < cols.length - 2; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(padded);
        }

        table.getColumnModel().getColumn(5).setCellRenderer(new TableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                String status = v.toString();
                Color c = status.equals("Terisi")    ? COLOR_TERISI
                        : status.equals("Tersedia")  ? COLOR_TERSEDIA
                        : COLOR_PERAWATAN;
                JLabel lbl = makeStatusBadge(status, c);
                lbl.setOpaque(true);
                lbl.setBackground(sel ? new Color(240, 253, 250) : BG_CARD);
                JPanel wrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 0));
                wrap.setBackground(sel ? new Color(240, 253, 250) : BG_CARD);
                wrap.add(lbl);
                return wrap;
            }
        });

        table.getColumnModel().getColumn(6).setCellRenderer(new TableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                return createActionPanel(sel);
            }
        });

        table.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            public Component getTableCellEditorComponent(JTable t, Object v,
                    boolean sel, int row, int col) {
                return createActionPanel(true);
            }
        });

        int[] widths = {80, 90, 70, 120, 220, 110, 130};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            { setBorder(new EmptyBorder(0, 18, 0, 0)); }
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                ((JComponent) c).setBorder(new MatteBorder(0, 0, 1, 0, new Color(241, 245, 249)));
                setBackground(sel ? new Color(240, 253, 250) : BG_CARD);
                setForeground(TEXT_DARK);
                return c;
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(8);
        card.add(sp, BorderLayout.CENTER);

        card.add(createPaginationBar(), BorderLayout.SOUTH);

        return card;
    }

    private JPanel createActionPanel(boolean selected) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        p.setBackground(selected ? new Color(240, 253, 250) : BG_CARD);

        JButton detail = makeIconBtn("Detail", new Color(99, 102, 241));
        JButton edit   = makeIconBtn("Edit",   TEAL_PRIMARY);
        JButton hapus  = makeIconBtn("Hapus",  new Color(239, 68, 68));

        p.add(detail);
        p.add(edit);
        p.add(hapus);
        return p;
    }

    private JButton makeIconBtn(String label, Color color) {
        JButton btn = new JButton(label) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = new Color(color.getRed(), color.getGreen(), color.getBlue(),
                        getModel().isRollover() ? 40 : 20);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 10));
        btn.setForeground(color);
        btn.setPreferredSize(new Dimension(52, 26));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel createPaginationBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG_CARD);
        bar.setBorder(new CompoundBorder(
                new MatteBorder(1, 0, 0, 0, BORDER_COLOR),
                new EmptyBorder(10, 18, 10, 18)
        ));

        JLabel info = new JLabel("Menampilkan 1–10 dari 24 kamar");
        info.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        info.setForeground(TEXT_LIGHT);
        bar.add(info, BorderLayout.WEST);

        JPanel pages = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));
        pages.setOpaque(false);

        String[] pageLabels = {"‹", "1", "2", "3", "›"};
        for (int i = 0; i < pageLabels.length; i++) {
            boolean isActive = pageLabels[i].equals("1");
            JButton pb = new JButton(pageLabels[i]) {
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    if (isActive) {
                        g2.setColor(TEAL_PRIMARY);
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                    } else if (getModel().isRollover()) {
                        g2.setColor(new Color(240, 253, 250));
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                    }
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            pb.setFont(new Font("Segoe UI", isActive ? Font.BOLD : Font.PLAIN, 12));
            pb.setForeground(isActive ? Color.WHITE : TEXT_MED);
            pb.setPreferredSize(new Dimension(32, 28));
            pb.setContentAreaFilled(false);
            pb.setBorderPainted(false);
            pb.setFocusPainted(false);
            pb.setCursor(new Cursor(Cursor.HAND_CURSOR));
            if (!isActive) pb.setBorder(new LineBorder(BORDER_COLOR, 1, true));
            pages.add(pb);
        }
        bar.add(pages, BorderLayout.EAST);

        return bar;
    }

    private JLabel makeStatusBadge(String text, Color color) {
        JLabel lbl = new JLabel(text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 20));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lbl.setForeground(color);
        lbl.setBorder(new EmptyBorder(3, 8, 3, 8));
        lbl.setOpaque(false);
        return lbl;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
            new DataKamarPanel();
        });
    }
}