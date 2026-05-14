package models;

public class Penghuni extends User {
    private Kamar kamarSewa;
    private String status;

    public Penghuni(int id, String nama, String email, String password, String status) {
        super(id, nama, email, password);
        this.status = status;
    }

    public void lihatTagihan() {
        
    }

    public void bayar() {

    }

    @Override
    public void displayDashboard() {

    }
}
