package models;

public class Tagiham {
    private int idTagihan;
    private String bulan;
    private double total;
    private double denda;

    public Tagiham(int idTagihan, String bulan, double total, double denda) {
        this.idTagihan = idTagihan;
        this.bulan = bulan;
        this.total = total;
        this.denda = denda;
    }

    public double hitungTotal(double denda) {
        return total + denda;
    }

    public double hitungTotal() {
        return total;
    }
}
