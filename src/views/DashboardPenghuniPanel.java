package views;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

// Source Code: Claude AI

public class DashboardPenghuniPanel extends JPanel {

    private JPanel contentArea;

    private static final Color TEAL_PRIMARY  = new Color(20, 184, 166);
    private static final Color TEAL_DARKER   = new Color(15, 118, 110);
    private static final Color BG_PAGE       = new Color(245, 247, 252);
    private static final Color BG_CARD       = Color.WHITE;
    private static final Color TEXT_DARK     = new Color(15, 23, 42);
    private static final Color TEXT_MED      = new Color(71, 85, 105);
    private static final Color TEXT_LIGHT    = new Color(148, 163, 184);
    private static final Color BORDER_COLOR  = new Color(226, 232, 240);

    private String namaPenghuni = "Budi";
    private String nomorKamar = "3A";

    public DashboardPenghuniPanel() {
        setLayout(new BorderLayout());
        setBackground(BG_PAGE);
        add(createMainArea(), BorderLayout.CENTER);
    }

    public DashboardPenghuniPanel(String namaPenghuni, String nomorKamar) {
        this.namaPenghuni = namaPenghuni;
        this.nomorKamar = nomorKamar;
        setLayout(new BorderLayout());
        setBackground(BG_PAGE);
        add(createMainArea(), BorderLayout.CENTER);
    }

    private JPanel createMainArea() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG_PAGE);

        JPanel topBar = new JPanel(null);
        topBar.setBackground(BG_CARD);
        topBar.setPreferredSize(new Dimension(0, 64));
        topBar.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_COLOR));

        JLabel greeting = new JLabel("Selamat Datang, " + namaPenghuni);
        greeting.setFont(new Font("Segoe UI", Font.BOLD, 18));
        greeting.setForeground(TEXT_DARK);
        greeting.setBounds(28, 14, 400, 26);
        topBar.add(greeting);

        JLabel dateLbl = new JLabel(new java.text.SimpleDateFormat("EEEE, dd MMMM yyyy", new java.util.Locale("id", "ID")).format(new java.util.Date()));
        dateLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dateLbl.setForeground(TEXT_LIGHT);
        dateLbl.setBounds(28, 40, 300, 16);
        topBar.add(dateLbl);

        main.add(topBar, BorderLayout.NORTH);

        contentArea = new JPanel();
        contentArea.setBackground(BG_PAGE);
        contentArea.setLayout(new BoxLayout(contentArea, BoxLayout.Y_AXIS));
        contentArea.setBorder(new EmptyBorder(28, 28, 28, 28));

        buildDashboardContent();

        JScrollPane scroll = new JScrollPane(contentArea);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        scroll.setBackground(BG_PAGE);
        main.add(scroll, BorderLayout.CENTER);

        return main;
    }

    private void buildDashboardContent() {
        contentArea.removeAll();

        JPanel hero = createHeroBanner();
        hero.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        hero.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentArea.add(hero);
        contentArea.add(Box.createRigidArea(new Dimension(0, 24)));

        JPanel stats = new JPanel(new GridLayout(1, 3, 16, 0));
        stats.setOpaque(false);
        stats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        stats.setAlignmentX(Component.LEFT_ALIGNMENT);
        stats.add(createMiniCard("Kamar", nomorKamar, "Lantai 1", new Color(20, 184, 166)));
        stats.add(createMiniCard("Tagihan Bulan Ini", "Rp 800.000", "Jatuh tempo 1 Jul", new Color(245, 158, 11)));
        stats.add(createMiniCard("Status Hunian", "Aktif", "Sejak Jan 2025", new Color(16, 185, 129)));
        contentArea.add(stats);
        contentArea.add(Box.createRigidArea(new Dimension(0, 24)));

        JPanel twoCol = new JPanel(new GridLayout(1, 2, 20, 0));
        twoCol.setOpaque(false);
        twoCol.setMaximumSize(new Dimension(Integer.MAX_VALUE, 320));
        twoCol.setAlignmentX(Component.LEFT_ALIGNMENT);
        twoCol.add(createTagihanCard());
        twoCol.add(createInfoKamarCard());
        contentArea.add(twoCol);
        contentArea.add(Box.createRigidArea(new Dimension(0, 24)));

        JPanel riwayat = createRiwayatCard();
        riwayat.setMaximumSize(new Dimension(Integer.MAX_VALUE, 260));
        riwayat.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentArea.add(riwayat);

        contentArea.revalidate();
        contentArea.repaint();
    }

    private JPanel createHeroBanner() {
        JPanel hero = new JPanel(null) {
            { setPreferredSize(new Dimension(0, 120)); }
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, TEAL_PRIMARY, getWidth(), getHeight(), TEAL_DARKER));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(new Color(255, 255, 255, 20));
                g2.fillOval(getWidth() - 120, -30, 160, 160);
                g2.setColor(new Color(255, 255, 255, 12));
                g2.fillOval(getWidth() - 60, 40, 100, 100);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        JLabel title = new JLabel("Selamat Tinggal di Kost Barokah");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBounds(28, 24, 500, 28);
        hero.add(title);

        JLabel sub = new JLabel("Tagihan bulan ini sudah tersedia. Jangan lupa bayar sebelum tanggal 1 Juli 2025.");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(new Color(204, 245, 240));
        sub.setBounds(28, 56, 600, 20);
        hero.add(sub);

        JButton payBtn = new JButton("Bayar Sekarang") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? new Color(255, 255, 255, 200) : Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        payBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        payBtn.setForeground(TEAL_DARKER);
        payBtn.setContentAreaFilled(false);
        payBtn.setBorderPainted(false);
        payBtn.setFocusPainted(false);
        payBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        payBtn.setBounds(28, 82, 130, 30);
        hero.add(payBtn);

        return hero;
    }

    private JPanel createMiniCard(String title, String value, String sub, Color accent) {
        JPanel card = new JPanel(null);
        card.setBackground(BG_CARD);
        card.setBorder(new LineBorder(BORDER_COLOR, 1, true));

        JPanel bar = new JPanel() {
            protected void paintComponent(Graphics g) {
                ((Graphics2D) g).setColor(accent);
                g.fillRect(0, 0, getWidth(), 3);
            }
        };
        bar.setOpaque(false);
        bar.setBounds(0, 0, 400, 3);
        card.add(bar);

        JLabel valLbl = new JLabel(value);
        valLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valLbl.setForeground(TEXT_DARK);
        valLbl.setBounds(18, 14, 250, 28);
        card.add(valLbl);

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        titleLbl.setForeground(TEXT_LIGHT);
        titleLbl.setBounds(18, 44, 250, 16);
        card.add(titleLbl);

        JLabel subLbl = new JLabel(sub);
        subLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subLbl.setForeground(accent);
        subLbl.setBounds(18, 68, 250, 16);
        card.add(subLbl);

        return card;
    }

    private JPanel createTagihanCard() {
        JPanel card = new JPanel(null);
        card.setBackground(BG_CARD);
        card.setBorder(new LineBorder(BORDER_COLOR, 1, true));

        JLabel title = new JLabel("Tagihan Aktif");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(TEXT_DARK);
        title.setBounds(18, 16, 200, 22);
        card.add(title);

        Object[][] items = {
            {"Sewa Kamar — Juni 2025", "Rp 800.000", "1 Jul 2025", "Belum"},
            {"Listrik — Juni 2025",    "Rp 45.000",  "1 Jul 2025", "Belum"},
            {"Air — Juni 2025",        "Rp 20.000",  "1 Jul 2025", "Lunas"},
        };

        int iy = 50;
        for (Object[] item : items) {
            JPanel row = new JPanel(null);
            row.setBackground(BG_CARD);
            row.setBounds(12, iy, 420, 52);

            JPanel iconBox = new JPanel() {
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(245, 247, 252));
                    g2.fillRoundRect(0, 0, 38, 38, 10, 10);
                    g2.setColor(TEXT_MED);
                    g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawRect(9, 10, 20, 18);
                    g2.drawLine(13, 10, 13, 6); g2.drawLine(25, 10, 25, 6);
                    g2.drawLine(13, 17, 25, 17);
                    g2.dispose();
                }
            };
            iconBox.setOpaque(false);
            iconBox.setBounds(0, 7, 38, 38);
            row.add(iconBox);

            JLabel nameLbl = new JLabel(item[0].toString());
            nameLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            nameLbl.setForeground(TEXT_DARK);
            nameLbl.setBounds(48, 6, 240, 18);
            row.add(nameLbl);

            JLabel dueLbl = new JLabel("Jatuh tempo: " + item[2]);
            dueLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            dueLbl.setForeground(TEXT_LIGHT);
            dueLbl.setBounds(48, 26, 240, 14);
            row.add(dueLbl);

            JLabel amtLbl = new JLabel(item[1].toString());
            amtLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
            amtLbl.setForeground(TEXT_DARK);
            amtLbl.setBounds(290, 6, 120, 18);
            row.add(amtLbl);

            boolean lunas = item[3].toString().equals("Lunas");
            Color statusColor = lunas ? new Color(16, 185, 129) : new Color(245, 158, 11);
            JLabel statusLbl = makeStatusBadge(item[3].toString(), statusColor);
            statusLbl.setBounds(290, 26, 60, 18);
            row.add(statusLbl);

            JSeparator line = new JSeparator();
            line.setForeground(new Color(240, 244, 248));
            line.setBounds(0, 51, 440, 1);
            row.add(line);

            card.add(row);
            iy += 56;
        }

        JLabel totalLbl = new JLabel("Total Tagihan:");
        totalLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        totalLbl.setForeground(TEXT_MED);
        totalLbl.setBounds(18, iy + 8, 200, 18);
        card.add(totalLbl);

        JLabel totalVal = new JLabel("Rp 865.000");
        totalVal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalVal.setForeground(TEXT_DARK);
        totalVal.setBounds(18, iy + 28, 200, 26);
        card.add(totalVal);

        JButton payBtn = new JButton("Bayar Tagihan") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? new Color(13, 148, 136) : TEAL_PRIMARY);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        payBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        payBtn.setForeground(Color.WHITE);
        payBtn.setContentAreaFilled(false);
        payBtn.setBorderPainted(false);
        payBtn.setFocusPainted(false);
        payBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        payBtn.setBounds(18, iy + 60, 130, 36);
        card.add(payBtn);

        card.setPreferredSize(new Dimension(0, iy + 110));
        return card;
    }

    private JPanel createInfoKamarCard() {
        JPanel card = new JPanel(null);
        card.setBackground(BG_CARD);
        card.setBorder(new LineBorder(BORDER_COLOR, 1, true));

        JLabel title = new JLabel("Info Kamar " + nomorKamar);
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(TEXT_DARK);
        title.setBounds(18, 16, 200, 22);
        card.add(title);

        JPanel roomImg = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, new Color(240, 244, 248), getWidth(), getHeight(), new Color(226, 232, 240)));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(new Color(200, 215, 230));
                g2.fillRect(30, 20, 60, 50); g2.fillRect(100, 20, 60, 50);
                g2.setColor(Color.WHITE);
                g2.drawLine(60, 20, 60, 70); g2.drawLine(30, 45, 90, 45);
                g2.drawLine(130, 20, 130, 70); g2.drawLine(100, 45, 160, 45);
                g2.setColor(new Color(174, 214, 241));
                g2.fillRoundRect(30, 90, 130, 70, 8, 8);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(32, 92, 50, 30, 6, 6);
                g2.fillRoundRect(90, 92, 50, 30, 6, 6);
                g2.setColor(TEXT_LIGHT);
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                String label = "Kamar " + nomorKamar;
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(label, (getWidth() - fm.stringWidth(label)) / 2, getHeight() - 10);
                g2.dispose();
            }
        };
        roomImg.setOpaque(false);
        roomImg.setBounds(18, 46, 200, 160);
        card.add(roomImg);

        String[][] details = {
            {"Lantai", "1"},
            {"Ukuran", "3 x 4 meter"},
            {"Fasilitas", "AC, Kasur, Lemari"},
            {"Wifi", "Tersedia"},
            {"Parkir", "Motor & Mobil"},
            {"Harga", "Rp 800.000 / bulan"},
        };

        int dy = 46;
        for (String[] d : details) {
            JLabel key = new JLabel(d[0]);
            key.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            key.setForeground(TEXT_LIGHT);
            key.setBounds(230, dy, 80, 16);
            card.add(key);

            JLabel val = new JLabel(d[1]);
            val.setFont(new Font("Segoe UI", Font.BOLD, 12));
            val.setForeground(TEXT_DARK);
            val.setBounds(230, dy + 16, 160, 18);
            card.add(val);

            dy += 40;
        }

        card.setPreferredSize(new Dimension(0, Math.max(dy + 20, 230)));
        return card;
    }

    private JPanel createRiwayatCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(BG_CARD);
        card.setBorder(new LineBorder(BORDER_COLOR, 1, true));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_CARD);
        header.setBorder(new EmptyBorder(14, 18, 14, 18));
        JLabel title = new JLabel("Riwayat Pembayaran");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(TEXT_DARK);
        header.add(title, BorderLayout.WEST);
        card.add(header, BorderLayout.NORTH);

        JPanel timeline = new JPanel(null);
        timeline.setBackground(BG_CARD);
        timeline.setBorder(new EmptyBorder(0, 18, 16, 18));

        String[][] history = {
            {"Mei 2025", "Rp 800.000", "Lunas", "30 Mei 2025"},
            {"Apr 2025", "Rp 800.000", "Lunas", "28 Apr 2025"},
            {"Mar 2025", "Rp 800.000", "Lunas", "25 Mar 2025"},
            {"Feb 2025", "Rp 800.000", "Lunas", "27 Feb 2025"},
        };

        int hy = 4;
        for (String[] h : history) {
            JPanel dot = new JPanel() {
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(16, 185, 129));
                    g2.fillOval(3, 3, 10, 10);
                    g2.setColor(new Color(16, 185, 129, 40));
                    g2.fillOval(0, 0, 16, 16);
                    g2.dispose();
                }
            };
            dot.setOpaque(false);
            dot.setBounds(0, hy + 10, 16, 16);
            timeline.add(dot);

            if (!h[0].equals("Feb 2025")) {
                JPanel line = new JPanel() {
                    protected void paintComponent(Graphics g) {
                        g.setColor(new Color(226, 232, 240));
                        g.fillRect(7, 0, 2, getHeight());
                    }
                };
                line.setOpaque(false);
                line.setBounds(7, hy + 26, 2, 44);
                timeline.add(line);
            }

            JLabel monthLbl = new JLabel(h[0]);
            monthLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
            monthLbl.setForeground(TEXT_DARK);
            monthLbl.setBounds(28, hy + 6, 120, 18);
            timeline.add(monthLbl);

            JLabel dateLbl = new JLabel("Dibayar: " + h[3]);
            dateLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            dateLbl.setForeground(TEXT_LIGHT);
            dateLbl.setBounds(28, hy + 24, 200, 14);
            timeline.add(dateLbl);

            JLabel amtLbl = new JLabel(h[1]);
            amtLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
            amtLbl.setForeground(TEXT_DARK);
            amtLbl.setBounds(400, hy + 6, 120, 18);
            timeline.add(amtLbl);

            JLabel statusLbl = makeStatusBadge(h[2], new Color(16, 185, 129));
            statusLbl.setBounds(400, hy + 24, 60, 18);
            timeline.add(statusLbl);

            hy += 52;
        }

        timeline.setPreferredSize(new Dimension(0, hy + 16));
        card.add(timeline, BorderLayout.CENTER);
        return card;
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
}