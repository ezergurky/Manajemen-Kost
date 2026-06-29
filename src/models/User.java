package models;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import views.LoginPanel;

public abstract class User {
    protected int id;
    protected String nama;
    protected String username;
    private String email;
    private String password;

    public User(int id, String nama, String email, String password, String username) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.username = username;
    }
    
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void logout(JFrame frame) {
        int confirm = JOptionPane.showConfirmDialog(frame, 
            "Apakah Anda yakin ingin keluar?", "Konfirmasi Logout", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            frame.dispose(); 
            new LoginPanel();
        } 
    }

    public abstract void displayDashboard();
}
