package views;

import components.SideBarAdmin;
import views.Admin.*;

import javax.swing.*;
import java.awt.*;

public class MainFrameAdmin extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    public MainFrameAdmin() {
        setTitle("Manajemen Kost — Admin");
        setSize(1200, 700);
        setMinimumSize(new Dimension(1100, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());

        SideBarAdmin sidebar = new SideBarAdmin(this::navigateTo);
        root.add(sidebar, BorderLayout.WEST);

        cardLayout   = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(new DashboardAdminPanel(), "DASH");
        contentPanel.add(new DataKamarPanel(),      "ROOM");
        contentPanel.add(new DataPenghuniPanel(), "TENANT");
        contentPanel.add(new TagihanPanel(),      "BILL");
        contentPanel.add(new PembayaranPanel(), "PAY");
        contentPanel.add(new LaporanPanel(), "REPORT");

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