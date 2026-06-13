package models;

public abstract class User {
    protected int id;
    protected String nama;
    protected String statusAkun;
    private String email;
    private String password;

    public User(int id, String nama, String email, String password) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.password = password;
    }
    
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public boolean login() {
        statusAkun = "Online";
        return true;
    }

    public void logout() {
        statusAkun = "Offline";
    }

    protected boolean validasiSesi() {
        return statusAkun.equals("Online");
    }

    public abstract void displayDashboard();
}
