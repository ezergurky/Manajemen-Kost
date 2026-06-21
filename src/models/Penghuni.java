package models;

public class Penghuni extends User {
    private int idPenghuni;
    private String nik;
    private String noHp;
    private Kamar kamarSewa;

    public Penghuni(int idUser, String nama, String email, String password, int idPenghuni, String nik, String noHp) {
        super(idUser, nama, email, password);
        this.idPenghuni = idPenghuni;
        this.nik = nik;
        this.noHp = noHp;
    }

    public int getIdPenghuni() {
        return idPenghuni;
    }

    public void setIdPenghuni(int idPenghuni) {
        this.idPenghuni = idPenghuni;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public Kamar getKamarSewa() {
        return kamarSewa;
    }

    public void setKamarSewa(Kamar kamarSewa) {
        this.kamarSewa = kamarSewa;
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