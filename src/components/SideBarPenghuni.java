package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class SideBarPenghuni extends JPanel {
    private static final Color TEAL_PRIMARY = new Color(20, 184, 166);
    private static final Color TEAL_DARK    = new Color(13, 148, 136);
    private static final Color BG_SIDEBAR   = new Color(249, 251, 253);
    private static final Color TEXT_DARK    = new Color(15, 23, 42);
    private static final Color TEXT_MED     = new Color(71, 85, 105);
    private static final Color BORDER_COLOR = new Color(218, 225, 234);

    private final Consumer<String> onNavigate;
    private NavButton activeNavBtn;

    public SideBarPenghuni(Consumer<String> onNavigate) {
        this.onNavigate = onNavigate;
        setLayout(null);
        setPreferredSize(new Dimension(230, 700));
        setBackground(BG_SIDEBAR);
        setBorder(new MatteBorder(0, 0, 0, 1, BORDER_COLOR));
        buildSidebar();
    }

    private void buildSidebar() {

        JPanel logoArea = new JPanel(new BorderLayout());
        logoArea.setBackground(BG_SIDEBAR);
        logoArea.setBounds(0, 0, 230, 72);
        logoArea.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_COLOR));

        JPanel logoInner = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 0));
        logoInner.setBackground(BG_SIDEBAR);

        JPanel miniIcon = new JPanel() {
            @Override
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
        appName.setForeground(TEXT_DARK);
        JLabel roleTag = new JLabel("Penghuni");
        roleTag.setFont(new Font("Segoe UI", Font.BOLD, 11));
        roleTag.setForeground(TEAL_DARK);
        logoText.add(appName);
        logoText.add(roleTag);

        logoInner.add(miniIcon);
        logoInner.add(logoText);
        logoArea.add(logoInner, BorderLayout.CENTER);
        this.add(logoArea); 

        String[][] navItems = {
            {"Dashboard",          "DASH"},
            {"Kamar Saya",         "ROOM"},
            {"Tagihan",            "BILL"},
            {"Riwayat Pembayaran", "HISTORY"},
            {"Profile",            "PROFILE"},
            {"Pengaturan",         "SETTINGS"},
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

        JSeparator div = new JSeparator();
        div.setForeground(BORDER_COLOR);
        div.setBounds(16, navY + 4, 198, 1);
        this.add(div); 

        NavButton logoutBtn = new NavButton("Keluar", "LOGOUT");
        logoutBtn.setBounds(10, navY + 14, 210, 44);
        logoutBtn.addActionListener(e -> onNavigate.accept("LOGOUT"));
        this.add(logoutBtn);

        JPanel userCard = new JPanel(null);
        userCard.setBackground(Color.WHITE);
        userCard.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        userCard.setBounds(10, 620, 210, 56);

        JPanel avatar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(TEAL_PRIMARY);
                g2.fillOval(0, 0, 36, 36);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                String init = "P";
                g2.drawString(init,
                    (36 - fm.stringWidth(init)) / 2,
                    (36 + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        avatar.setOpaque(false);
        avatar.setBounds(10, 10, 36, 36);
        userCard.add(avatar);

        JLabel userName = new JLabel("Penghuni");
        userName.setFont(new Font("Segoe UI", Font.BOLD, 12));
        userName.setForeground(TEXT_DARK);
        userName.setBounds(54, 10, 120, 18);
        userCard.add(userName);

        JLabel userEmail = new JLabel("penghuni@kost.id");
        userEmail.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        userEmail.setForeground(TEXT_MED);
        userEmail.setBounds(54, 28, 150, 14);
        userCard.add(userEmail);

        this.add(userCard); 
    }

    private void setActiveNav(NavButton btn) {
        btn.setActive(true);
        activeNavBtn = btn;
    }

    private class NavButton extends JButton {
        private boolean active = false;

        NavButton(String label, String type) {
            setText(label);
            setFont(new Font("Segoe UI", Font.PLAIN, 13));
            setForeground(TEXT_MED);
            setHorizontalAlignment(SwingConstants.LEFT);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(new EmptyBorder(0, 14, 0, 0));
            setIconTextGap(10);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { if (!active) repaint(); }
                @Override
                public void mouseExited(MouseEvent e)  { if (!active) repaint(); }
            });
        }

        public void setActive(boolean a) {
            this.active = a;
            setForeground(a ? TEAL_DARK : TEXT_MED);
            setFont(new Font("Segoe UI", a ? Font.BOLD : Font.PLAIN, 13));
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (active) {
                g2.setColor(new Color(20, 184, 166, 18));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(TEAL_PRIMARY);
                g2.fillRoundRect(0, 8, 3, getHeight() - 16, 3, 3);
            } else if (getModel().isRollover()) {
                g2.setColor(new Color(15, 23, 42, 10));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }
}