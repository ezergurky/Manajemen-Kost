package views;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;

// Source Code: Claude AI

public class DashboardAdminPanel extends JPanel {

    private JPanel contentArea;
    private JLabel pageTitle;

    private static final Color TEAL_PRIMARY  = new Color(20, 184, 166);
    private static final Color BG_PAGE       = new Color(245, 247, 252);
    private static final Color BG_CARD       = Color.WHITE;
    private static final Color TEXT_DARK     = new Color(15, 23, 42);
    private static final Color TEXT_MED      = new Color(71, 85, 105);
    private static final Color TEXT_LIGHT    = new Color(148, 163, 184);
    private static final Color BORDER_COLOR  = new Color(226, 232, 240);

    public DashboardAdminPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 252));
        add(createMainArea(), BorderLayout.CENTER);
    }

    private JPanel createMainArea() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG_PAGE);

        // Top bar
        JPanel topBar = new JPanel(null);
        topBar.setBackground(BG_CARD);
        topBar.setPreferredSize(new Dimension(0, 64));
        topBar.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_COLOR));

        pageTitle = new JLabel("Dashboard");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pageTitle.setForeground(TEXT_DARK);
        pageTitle.setBounds(28, 18, 300, 28);
        topBar.add(pageTitle);

        // Date label
        JLabel dateLbl = new JLabel(new java.text.SimpleDateFormat("EEEE, dd MMMM yyyy", new java.util.Locale("id","ID")).format(new java.util.Date()));
        dateLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dateLbl.setForeground(TEXT_LIGHT);
        dateLbl.setBounds(28, 44, 300, 16);
        topBar.add(dateLbl);

        // Search bar (decorative)
        JPanel searchBar = new JPanel(new BorderLayout(8, 0));
        searchBar.setBackground(new Color(248, 250, 252));
        searchBar.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(0, 12, 0, 12)
        ));
        searchBar.setBounds(400, 16, 220, 32);
        JLabel searchIcon = new JLabel("⌕");
        searchIcon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchIcon.setForeground(TEXT_LIGHT);
        JTextField searchField = new JTextField("Cari...");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        searchField.setForeground(TEXT_LIGHT);
        searchField.setBorder(null);
        searchField.setBackground(new Color(248, 250, 252));
        searchBar.add(searchIcon, BorderLayout.WEST);
        searchBar.add(searchField, BorderLayout.CENTER);
        topBar.add(searchBar);

        // Notification badge
        JPanel notifBtn = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(248, 250, 252));
                g2.fillRoundRect(0, 0, 36, 36, 10, 10);
                g2.setColor(BORDER_COLOR);
                g2.drawRoundRect(0, 0, 35, 35, 10, 10);
                // Bell icon
                g2.setColor(TEXT_MED);
                g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawArc(11, 8, 14, 14, 0, 180);
                g2.drawLine(11, 15, 11, 22); g2.drawLine(25, 15, 25, 22);
                g2.drawArc(8, 20, 20, 6, 0, -180);
                g2.fillOval(16, 25, 4, 4);
                // Red dot
                g2.setColor(new Color(239, 68, 68));
                g2.fillOval(22, 7, 8, 8);
                g2.dispose();
            }
        };
        notifBtn.setOpaque(false);
        notifBtn.setBounds(640, 14, 36, 36);
        notifBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        topBar.add(notifBtn);

        main.add(topBar, BorderLayout.NORTH);

        // Content
        contentArea = new JPanel();
        contentArea.setBackground(BG_PAGE);
        contentArea.setLayout(new BoxLayout(contentArea, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(contentArea);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        scroll.setBackground(BG_PAGE);

        buildDashboardContent();
        main.add(scroll, BorderLayout.CENTER);

        return main;
    }

    private void buildDashboardContent() {
        contentArea.removeAll();
        contentArea.setBorder(new EmptyBorder(28, 28, 28, 28));

        // ── Stat cards row ──
        JPanel statsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        statsRow.setOpaque(false);
        statsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        statsRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        statsRow.add(createStatCard("Total Kamar", "24", "+2 bulan ini", new Color(20, 184, 166), "ROOM"));
        statsRow.add(createStatCard("Terisi", "18", "75% hunian", new Color(99, 102, 241), "PEOPLE"));
        statsRow.add(createStatCard("Tagihan Aktif", "6", "Jatuh tempo", new Color(245, 158, 11), "BILL"));
        statsRow.add(createStatCard("Pendapatan", "Rp 9,6Jt", "Bulan ini", new Color(16, 185, 129), "MONEY"));

        contentArea.add(statsRow);
        contentArea.add(Box.createRigidArea(new Dimension(0, 24)));

        // ── Two-column row ──
        JPanel twoCol = new JPanel(new GridLayout(1, 2, 20, 0));
        twoCol.setOpaque(false);
        twoCol.setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));
        twoCol.setAlignmentX(Component.LEFT_ALIGNMENT);

        twoCol.add(createRecentPaymentsCard());
        twoCol.add(createRoomStatusCard());

        contentArea.add(twoCol);
        contentArea.add(Box.createRigidArea(new Dimension(0, 24)));

        // ── Recent tenants table ──
        JPanel tableCard = createTableCard();
        tableCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        tableCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentArea.add(tableCard);

        contentArea.revalidate();
        contentArea.repaint();
    }

    private JPanel createStatCard(String title, String value, String sub, Color accent, String iconType) {
        JPanel card = new JPanel(null);
        card.setBackground(BG_CARD);
        card.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(0, 0, 0, 0)
        ));
        card.setPreferredSize(new Dimension(0, 110));

        // Accent bar top
        JPanel accentBar = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(accent);
                g2.fillRect(0, 0, getWidth(), 3);
                g2.dispose();
            }
        };
        accentBar.setOpaque(false);
        accentBar.setBounds(0, 0, 300, 3);
        card.add(accentBar);

        // Icon circle
        JPanel iconCircle = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 20));
                g2.fillOval(0, 0, 42, 42);
                g2.setColor(accent);
                g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                drawIcon(g2, iconType, 21, 21, 10);
                g2.dispose();
            }
        };
        iconCircle.setOpaque(false);
        iconCircle.setBounds(16, 18, 42, 42);
        card.add(iconCircle);

        JLabel valLbl = new JLabel(value);
        valLbl.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valLbl.setForeground(TEXT_DARK);
        valLbl.setBounds(70, 14, 180, 30);
        card.add(valLbl);

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLbl.setForeground(TEXT_MED);
        titleLbl.setBounds(70, 44, 180, 18);
        card.add(titleLbl);

        JLabel subLbl = new JLabel(sub);
        subLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subLbl.setForeground(accent);
        subLbl.setBounds(16, 74, 250, 16);
        card.add(subLbl);

        return card;
    }

    private void drawIcon(Graphics2D g2, String type, int cx, int cy, int r) {
        switch (type) {
            case "ROOM":
                g2.drawRect(cx-r, cy-2, r*2, r+2);
                int[] rx = {cx-r-3, cx, cx+r+3}; int[] ry = {cy-2, cy-r-3, cy-2};
                g2.drawPolyline(rx, ry, 3);
                break;
            case "PEOPLE":
                g2.drawOval(cx-5, cy-r, 10, 10);
                g2.drawArc(cx-r, cy+2, r*2, r, 0, -180);
                break;
            case "BILL":
                g2.drawRect(cx-r+2, cy-r+2, (r-2)*2, (r-2)*2);
                g2.drawLine(cx-r+6, cy-4, cx+r-6, cy-4);
                g2.drawLine(cx-r+6, cy+2, cx+2, cy+2);
                break;
            case "MONEY":
                g2.drawOval(cx-r, cy-r, r*2, r*2);
                g2.drawString("Rp", cx-7, cy+5);
                break;
        }
    }

    private JPanel createRecentPaymentsCard() {
        JPanel card = new JPanel(null);
        card.setBackground(BG_CARD);
        card.setBorder(new LineBorder(BORDER_COLOR, 1, true));

        JLabel title = new JLabel("Pembayaran Terbaru");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(TEXT_DARK);
        title.setBounds(18, 16, 220, 22);
        card.add(title);

        String[][] rows = {
            {"Budi Santoso", "Kamar 3A", "Rp 800.000", "Lunas"},
            {"Siti Rahayu", "Kamar 1B", "Rp 750.000", "Lunas"},
            {"Andi Wijaya", "Kamar 2C", "Rp 850.000", "Proses"},
            {"Dina Lestari", "Kamar 4A", "Rp 800.000", "Belum"},
        };

        int ry = 48;
        for (String[] row : rows) {
            JPanel item = new JPanel(null);
            item.setBackground(BG_CARD);
            item.setBounds(12, ry, 380, 44);

            // Avatar
            JPanel av = makeInitialAvatar(row[0].substring(0, 1), new Color(99, 102, 241));
            av.setBounds(0, 4, 34, 34);
            item.add(av);

            JLabel name = new JLabel(row[0]);
            name.setFont(new Font("Segoe UI", Font.BOLD, 12));
            name.setForeground(TEXT_DARK);
            name.setBounds(42, 4, 140, 16);
            item.add(name);

            JLabel room = new JLabel(row[1]);
            room.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            room.setForeground(TEXT_LIGHT);
            room.setBounds(42, 22, 140, 14);
            item.add(room);

            JLabel amount = new JLabel(row[2]);
            amount.setFont(new Font("Segoe UI", Font.BOLD, 12));
            amount.setForeground(TEXT_DARK);
            amount.setBounds(200, 4, 100, 16);
            item.add(amount);

            Color statusColor = row[3].equals("Lunas") ? new Color(16, 185, 129)
                    : row[3].equals("Proses") ? new Color(245, 158, 11) : new Color(239, 68, 68);
            JLabel status = makeStatusBadge(row[3], statusColor);
            status.setBounds(200, 22, 80, 16);
            item.add(status);

            JSeparator line = new JSeparator();
            line.setForeground(new Color(240, 244, 248));
            line.setBounds(0, 43, 390, 1);
            item.add(line);

            card.add(item);
            ry += 48;
        }

        card.setPreferredSize(new Dimension(0, ry + 10));
        return card;
    }

    private JPanel createRoomStatusCard() {
        JPanel card = new JPanel(null);
        card.setBackground(BG_CARD);
        card.setBorder(new LineBorder(BORDER_COLOR, 1, true));

        JLabel title = new JLabel("Status Kamar");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(TEXT_DARK);
        title.setBounds(18, 16, 220, 22);
        card.add(title);

        // Donut chart area (drawn)
        JPanel donut = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int cx = getWidth()/2, cy = getHeight()/2, r = 70;
                // BG ring
                g2.setColor(new Color(240, 244, 248));
                g2.setStroke(new BasicStroke(20, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
                g2.drawOval(cx-r, cy-r, r*2, r*2);
                // Terisi (75%)
                g2.setColor(TEAL_PRIMARY);
                g2.setStroke(new BasicStroke(20, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
                g2.drawArc(cx-r, cy-r, r*2, r*2, 90, -270);
                // Tersedia (25%)
                g2.setColor(new Color(226, 232, 240));
                g2.drawArc(cx-r, cy-r, r*2, r*2, -180, -90);
                // Center text
                g2.setColor(TEXT_DARK);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 22));
                FontMetrics fm = g2.getFontMetrics();
                String pct = "75%";
                g2.drawString(pct, cx - fm.stringWidth(pct)/2, cy + 8);
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                g2.setColor(TEXT_LIGHT);
                String sub = "Terisi";
                g2.drawString(sub, cx - fm.stringWidth(sub)/2 + 2, cy + 24);
                g2.dispose();
            }
        };
        donut.setOpaque(false);
        donut.setBounds(50, 44, 200, 160);
        card.add(donut);

        // Legend
        String[][] legend = {{"Terisi", "18", "#14B8A6"}, {"Tersedia", "4", "#E2E8F0"}, {"Perawatan", "2", "#F59E0B"}};
        int ly = 216;
        for (String[] leg : legend) {
            JPanel dot = new JPanel() {
                Color c;
                JPanel init(Color col) { c = col; setOpaque(false); return this; }
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(c); g2.fillRoundRect(0, 3, 12, 12, 4, 4); g2.dispose();
                }
            }.init(Color.decode(leg[2]));
            dot.setBounds(30, ly, 12, 18);
            card.add(dot);
            JLabel ll = new JLabel(leg[0] + ": " + leg[1] + " kamar");
            ll.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            ll.setForeground(TEXT_MED);
            ll.setBounds(50, ly, 200, 18);
            card.add(ll);
            ly += 22;
        }

        card.setPreferredSize(new Dimension(0, ly + 16));
        return card;
    }

    private JPanel createTableCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(BG_CARD);
        card.setBorder(new LineBorder(BORDER_COLOR, 1, true));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_CARD);
        header.setBorder(new EmptyBorder(14, 18, 14, 18));

        JLabel title = new JLabel("Data Penghuni Aktif");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(TEXT_DARK);
        header.add(title, BorderLayout.WEST);

        JButton seeAll = new JButton("Lihat Semua") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? new Color(240, 253, 250) : Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(TEAL_PRIMARY);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                super.paintComponent(g);
            }
        };
        seeAll.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        seeAll.setForeground(TEAL_PRIMARY);
        seeAll.setPreferredSize(new Dimension(100, 30));
        seeAll.setContentAreaFilled(false);
        seeAll.setBorderPainted(false);
        seeAll.setFocusPainted(false);
        seeAll.setCursor(new Cursor(Cursor.HAND_CURSOR));
        header.add(seeAll, BorderLayout.EAST);
        card.add(header, BorderLayout.NORTH);

        String[] cols = {"Nama Penghuni", "Kamar", "Masuk", "Tagihan", "Status"};
        Object[][] data = {
            {"Budi Santoso", "3A", "01 Jan 2025", "Rp 800.000", "Aktif"},
            {"Siti Rahayu", "1B", "15 Mar 2025", "Rp 750.000", "Aktif"},
            {"Andi Wijaya", "2C", "10 Apr 2025", "Rp 850.000", "Aktif"},
            {"Dina Lestari", "4A", "22 Apr 2025", "Rp 800.000", "Aktif"},
            {"Riko Putra",   "2B", "05 Mei 2025", "Rp 700.000", "Aktif"},
        };

        JTable table = new JTable(data, cols) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setForeground(TEXT_DARK);
        table.setRowHeight(42);
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
        th.setPreferredSize(new Dimension(0, 36));

        // Custom renderer for Status column
        table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                JLabel lbl = makeStatusBadge(v.toString(), new Color(16, 185, 129));
                lbl.setOpaque(true);
                lbl.setBackground(sel ? new Color(240, 253, 250) : BG_CARD);
                return lbl;
            }
        });

        // Row padding via renderer
        DefaultTableCellRenderer paddedRenderer = new DefaultTableCellRenderer() {
            { setBorder(new EmptyBorder(0, 18, 0, 0)); }
        };
        for (int i = 0; i < 4; i++) table.getColumnModel().getColumn(i).setCellRenderer(paddedRenderer);

        card.add(new JScrollPane(table) {{ setBorder(null); }}, BorderLayout.CENTER);
        return card;
    }

    private JPanel makeInitialAvatar(String initial, Color color) {
        return new JPanel() {
            { setOpaque(false); }
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 30));
                g2.fillOval(0, 0, 34, 34);
                g2.setColor(color);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(initial, (34 - fm.stringWidth(initial)) / 2, (34 + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
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
        lbl.setBorder(new EmptyBorder(2, 7, 2, 7));
        lbl.setOpaque(false);
        return lbl;
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> {
    //         try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
    //         catch (Exception ignored) {}
    //         new DashboardAdminPanel();
    //     });
    // }
}