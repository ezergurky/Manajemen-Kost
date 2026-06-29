package models;

public class Kamar {
    private int idKamar;
    private int idKost;
    private String nomorKamar;
    private String tipeKamar;
    private String fasilitas;
    private double harga;
    private String status;

    public Kamar(int idKamar, int idKost, String nomorKamar, String tipeKamar, String fasilitas, double harga, String status) {
        if (harga < 0) {
            throw new IllegalArgumentException("Harga kamar tidak boleh negatif!");
        }

        this.idKamar = idKamar;
        this.idKost = idKost;
        this.nomorKamar = nomorKamar;
        this.tipeKamar = tipeKamar;
        this.fasilitas = fasilitas;
        this.harga = harga;
        this.status = status;
    }

    public int getIdKamar() {
        return idKamar;
    }

    public int getIdKost() {
        return idKost;
    }

    public String getNomorKamar() {
        return nomorKamar;
    }

    public String getTipeKamar() {
        return tipeKamar;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public double getHarga() {
        return harga;
    }

    public String getStatus() {
        return status;
    }

    public void setIdKost(int idKost) {
        this.idKost = idKost;
    }

    public void setNomorKamar(String nomorKamar) {
        this.nomorKamar = nomorKamar;
    }

    public void setTipeKamar(String tipeKamar) {
        this.tipeKamar = tipeKamar;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    public void setHarga(double harga) {
        if (harga < 0) {
            throw new IllegalArgumentException("Harga tidak boleh negatif!");
        }
        this.harga = harga;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}