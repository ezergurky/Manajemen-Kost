package dao.Penghuni;

import config.KoneksiDatabase;
import models.Penghuni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfileDAO {
    private final Connection conn;

    public ProfileDAO() {
        conn = KoneksiDatabase.getConnection();
    }

    public Penghuni getProfilPenghuni(int idPenghuni) {
        Penghuni p = null;
        String sql = "SELECT p.*, u.name, u.email, u.username FROM penghuni p " +
                    "JOIN users u ON p.id_user = u.id " +
                    "WHERE p.id_penghuni = ?";
                    
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idPenghuni);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                p = new Penghuni(
                    rs.getInt("id_user"),
                    rs.getString("name"),
                    rs.getString("email"), 
                    "", 
                    rs.getString("username"),
                    rs.getInt("id_penghuni"),
                    rs.getString("nik"),
                    rs.getString("no_hp")
                );
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
}