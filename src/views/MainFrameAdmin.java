package views;

import components.SideBarAdmin;
import views.Admin.*;

import javax.swing.*;
import java.awt.*;

public class MainFrameAdmin extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    private DashboardAdminPanel dashAdmin;
    private DataKamarPanel dataKamarPanel;
    private DataPenghuniPanel dataPenghuniPanel;
    private LaporanPanel laporanPanel;
    private PembayaranPanel pembPanel;
    private TagihanPanel tagPanel;

    public MainFrameAdmin() {
        setTitle("Manajemen Kost — Admin");
        setSize(1200, 700);
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        dashAdmin = new DashboardAdminPanel();
        dataKamarPanel = new DataKamarPanel();
        dataPenghuniPanel = new DataPenghuniPanel();
        tagPanel = new TagihanPanel();
        pembPanel = new PembayaranPanel();
        laporanPanel = new LaporanPanel();
 
        contentPanel.add(dashAdmin, "DASH");
        contentPanel.add(dataKamarPanel,      "ROOM");
        contentPanel.add(dataPenghuniPanel, "TENANT");
        contentPanel.add(tagPanel,      "BILL");
        contentPanel.add(pembPanel, "PAY");
        contentPanel.add(laporanPanel, "REPORT");
        contentPanel.add(new PengaturanPanel("Admin"), "SETTINGS");

        root.add(contentPanel, BorderLayout.CENTER);
        setContentPane(root);

        navigateTo("DASH");
    }

    public void navigateTo(String key) { 
        if (key.equals("LOGOUT")) {
            models.Admin admin = new models.Admin(0, "Admin", "", "", "");
            admin.logout(this); 
        } else {
            if(key.equals("DASH") && dashAdmin != null) {
                dashAdmin.refreshData();
            } else if (key.equals("ROOM") && dataKamarPanel != null) {
                dataKamarPanel.refreshData();
            } else if (key.equals("TENANT") && dataPenghuniPanel != null) {
                dataPenghuniPanel.refreshData();
            } else if (key.equals("REPORT") && laporanPanel != null) {
                laporanPanel.refreshData();
            } else if (key.equals("PAY") && pembPanel != null) {
                pembPanel.refreshData();
            } else if (key.equals("BILL") && tagPanel != null) {
                tagPanel.refreshData();
            }

            cardLayout.show(contentPanel, key);
        }
    }
}