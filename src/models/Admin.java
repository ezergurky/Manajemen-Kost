package models;

import views.MainFrameAdmin;

public class Admin extends User {
    private String role;

    public Admin(int id, String nama, String email, String password, String username) {
        super(id, nama, email, password, username);
        this.role = "admin";
    }

    public String getRole() {
        return role;
    }

    @Override
    public void displayDashboard() {
        new MainFrameAdmin().setVisible(true);
    }
}