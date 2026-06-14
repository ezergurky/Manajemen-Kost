package views;

import javax.swing.*;
import java.awt.*;

import components.SideBarPenghuni;

public class MainFramePenghuni extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;

    public MainFramePenghuni() {
        setTitle("Manajemen Kost — Penghuni");
        setSize(1200, 700);
        setMinimumSize(new Dimension(1100, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());

        SideBarPenghuni sidebar = new SideBarPenghuni(this::navigateTo);
        root.add(sidebar, BorderLayout.WEST);

        cardLayout   = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // contentPanel.add(new DashboardAdminPanel(), "DASH");
        // contentPanel.add(new DataKamarPanel(),      "ROOM");
        // contentPanel.add(new DataPenghuniPanel(), "TENANT");
        // contentPanel.add(new TagihanPanel(),      "BILL");

        root.add(contentPanel, BorderLayout.CENTER);
        setContentPane(root);

        navigateTo("DASH");
    }

    public void navigateTo(String key) {
        cardLayout.show(contentPanel, key);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
            new MainFrameAdmin();
        });
    }
}
