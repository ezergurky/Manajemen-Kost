package models;

public class Kamar {
    private int idKamar;
    private String nomor;
    private double harga;
    private String status;

    public Kamar(int idKamar, String nomor, double harga, String status) {
        this.idKamar = idKamar;
        this.nomor = nomor;
        this.harga = harga;
        this.status = status;
    }

    public void updateStatus(String status) {

    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }
}
