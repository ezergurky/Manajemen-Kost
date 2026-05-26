package models;

public class Kamar {

    private int idKamar;
    private String nomor;
    private double harga;
    private String status;

    public Kamar(int idKamar, String nomor, double harga, String status) {

        if (harga < 0) {
            throw new IllegalArgumentException("Harga kamar tidak boleh negatif!");
        }

        this.idKamar = idKamar;
        this.nomor = nomor;
        this.harga = harga;
        this.status = status;
    }

    public void updateStatus(String status) {

        if (status.equalsIgnoreCase("tersedia")
                || status.equalsIgnoreCase("terisi")) {

            this.status = status;
            System.out.println("Status kamar berhasil diperbarui.");
        } else {
            throw new IllegalArgumentException("Status kamar tidak valid!");
        }
    }

    public void updateStatus(boolean terisi) {

        if (terisi) {
            this.status = "Terisi";
        } else {
            this.status = "Tersedia";
        }
    }

    public int getIdKamar() {
        return idKamar;
    }

    public String getNomor() {
        return nomor;
    }

    public double getHarga() {
        return harga;
    }

    public String getStatus() {
        return status;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
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

    @Override
    public String toString() {
        return "Kamar{" +
                "idKamar=" + idKamar +
                ", nomor='" + nomor + '\'' +
                ", harga=" + harga +
                ", status='" + status + '\'' +
                '}';
    }
}
