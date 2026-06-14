package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class SideBarAdmin extends JPanel {

    private static final Color TEAL_PRIMARY  = new Color(20, 184, 166);
    private static final Color SIDEBAR_BG    = new Color(18, 24, 38);
    private static final Color TEXT_LIGHT    = new Color(148, 163, 184);

    private final Consumer<String> onNavigate;
    private NavButton activeNavBtn;

    public SideBarAdmin(Consumer<String> onNavigate) {
        this.onNavigate = onNavigate;
        setLayout(null);
        setPreferredSize(new Dimension(230, 700));
        setBackground(SIDEBAR_BG);
        buildSidebar();
    }

    private void buildSidebar() {

        JPanel logoArea = new JPanel(new BorderLayout());
        logoArea.setBackground(SIDEBAR_BG);
        logoArea.setBounds(0, 0, 230, 72);
        logoArea.setBorder(new MatteBorder(0, 0, 1, 0, new Color(255, 255, 255, 12)));

        JPanel logoInner = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 0));
        logoInner.setBackground(SIDEBAR_BG);

        JPanel miniIcon = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(TEAL_PRIMARY);
                g2.fillRoundRect(0, 0, 36, 36, 10, 10);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int[] rx = {5, 18, 31};
                int[] ry = {18, 6, 18};
                g2.drawPolyline(rx, ry, 3);
                g2.drawRect(8, 18, 20, 12);
                g2.drawRect(13, 22, 10, 8);
                g2.dispose();
            }
        };
        miniIcon.setOpaque(false);
        miniIcon.setPreferredSize(new Dimension(36, 36));

        JPanel logoText = new JPanel();
        logoText.setOpaque(false);
        logoText.setLayout(new BoxLayout(logoText, BoxLayout.Y_AXIS));
        JLabel appName = new JLabel("ManajemenKost");
        appName.setFont(new Font("Segoe UI", Font.BOLD, 13));
        appName.setForeground(Color.WHITE);
        JLabel roleTag = new JLabel("Administrator");
        roleTag.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        roleTag.setForeground(TEAL_PRIMARY);
        logoText.add(appName);
        logoText.add(roleTag);

        logoInner.add(miniIcon);
        logoInner.add(logoText);
        logoArea.add(logoInner, BorderLayout.CENTER);
        this.add(logoArea); 

        String[][] navItems = {
            {"Dashboard",     "DASH"},
            {"Data Kamar",    "ROOM"},
            {"Data Penghuni", "TENANT"},
            {"Tagihan",       "BILL"},
            {"Pembayaran",    "PAY"},
            {"Laporan",       "REPORT"},
        };

        int navY = 84;
        for (String[] item : navItems) {
            NavButton btn = new NavButton(item[0], item[1]);
            btn.setBounds(10, navY, 210, 44);
            if (navY == 84) setActiveNav(btn); 

            btn.addActionListener(e -> {
                if (activeNavBtn != null) activeNavBtn.setActive(false);
                setActiveNav(btn);
                onNavigate.accept(item[1]);
            });

            this.add(btn);
            navY += 50;
        }

        // Divider
        JSeparator div = new JSeparator();
        div.setForeground(new Color(255, 255, 255, 12));
        div.setBounds(16, navY + 4, 198, 1);
        this.add(div); // FIX: add ke this

        // Logout button
        NavButton logoutBtn = new NavButton("Keluar", "LOGOUT");
        logoutBtn.setBounds(10, navY + 14, 210, 44);
        this.add(logoutBtn); // FIX: add ke this

        // User info at bottom
        JPanel userCard = new JPanel(null);
        userCard.setBackground(new Color(255, 255, 255, 8));
        userCard.setBorder(new LineBorder(new Color(255, 255, 255, 15), 1, true));
        userCard.setBounds(10, 620, 210, 56);

        JPanel avatar = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(TEAL_PRIMARY);
                g2.fillOval(0, 0, 36, 36);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                String init = "A";
                g2.drawString(init,
                    (36 - fm.stringWidth(init)) / 2,
                    (36 + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        avatar.setOpaque(false);
        avatar.setBounds(10, 10, 36, 36);
        userCard.add(avatar);

        JLabel userName = new JLabel("Admin");
        userName.setFont(new Font("Segoe UI", Font.BOLD, 12));
        userName.setForeground(Color.WHITE);
        userName.setBounds(54, 10, 120, 18);
        userCard.add(userName);

        JLabel userEmail = new JLabel("admin@kost.id");
        userEmail.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        userEmail.setForeground(TEXT_LIGHT);
        userEmail.setBounds(54, 28, 150, 14);
        userCard.add(userEmail);

        this.add(userCard); // FIX: add ke this
    }

    private void setActiveNav(NavButton btn) {
        btn.setActive(true);
        activeNavBtn = btn;
    }

    // FIX 4: NavButton dipindahkan ke sini (dari DashboardAdminView) supaya SidebarAdmin bisa pakai
    private class NavButton extends JButton {
        private boolean active = false;

        NavButton(String label, String type) {
            setText(label);
            setFont(new Font("Segoe UI", Font.PLAIN, 13));
            setForeground(TEXT_LIGHT);
            setHorizontalAlignment(SwingConstants.LEFT);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(new EmptyBorder(0, 14, 0, 0));
            setIconTextGap(10);

            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { if (!active) repaint(); }
                public void mouseExited(MouseEvent e)  { if (!active) repaint(); }
            });
        }

        public void setActive(boolean a) {
            this.active = a;
            setForeground(a ? Color.WHITE : TEXT_LIGHT);
            setFont(new Font("Segoe UI", a ? Font.BOLD : Font.PLAIN, 13));
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (active) {
                g2.setColor(new Color(20, 184, 166, 25));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(TEAL_PRIMARY);
                g2.fillRoundRect(0, 8, 3, getHeight() - 16, 3, 3);
            } else if (getModel().isRollover()) {
                g2.setColor(new Color(255, 255, 255, 8));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }
}