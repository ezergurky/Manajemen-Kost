package models;

public class Penghuni extends User {
    private Kamar kamarSewa;
    private String status;

    public Penghuni(int id, String nama, String email, String password, String status) {
        super(id, nama, email, password);
        this.status = status;
    }

    public Kamar getKamarSewa() {
        return kamarSewa;
    }

    public void setKamarSewa(Kamar kamarSewa) {
        this.kamarSewa = kamarSewa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void lihatTagihan() {
        System.out.println("Menampilkan tagihan penghuni...");
    }

    public void bayar() {
        System.out.println("Melakukan pembayaran tagihan...");
    }

    @Override
    public void displayDashboard() {
        System.out.println("Dashboard Penghuni");
    }
}
