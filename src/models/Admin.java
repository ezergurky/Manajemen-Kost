package models;

public class Admin extends User {
    private String level;

    public Admin(int id, String nama, String email, String password, String level) {
        super(id, nama, email, password);
        this.level = level;
    }

    public void kelolaKamar() {

    }

    public void kelolaPenghuni() {

    }

    @Override
    public void displayDashboard() {
        
    }
}
