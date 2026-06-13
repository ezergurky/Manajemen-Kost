package models;

public class Tagihan {
    private int idTagihan;
    private String bulan;
    private double total;
    private double denda;

    public Tagihan(int idTagihan, String bulan, double total, double denda) {
        this.idTagihan = idTagihan;
        this.bulan = bulan;
        this.total = total;
        this.denda = denda;
    }

    public int getIdTagihan() {
        return idTagihan;
    }

    public void setIdTagihan(int idTagihan) {
        this.idTagihan = idTagihan;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDenda() {
        return denda;
    }

    public void setDenda(double denda) {
        this.denda = denda;
    }

    public double hitungTotal(double denda) {
        return total + denda;
    }

    public double hitungTotal() {
        return total;
    }
}
