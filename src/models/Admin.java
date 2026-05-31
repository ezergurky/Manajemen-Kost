package models;

public class Admin extends User {
    private String level;

    public Admin(int id, String nama, String email, String password, String level) {
        super(id, nama, email, password);
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void kelolaKamar() {
        System.out.println("Mengelola data kamar...");
    }

    public void kelolaPenghuni() {
        System.out.println("Mengelola data penghuni...");
    }

    public void generateLaporan() {
        System.out.println("Membuat laporan keuangan...");
    }

    @Override
    public void displayDashboard() {
        System.out.println("Dashboard Admin");
    }
}
