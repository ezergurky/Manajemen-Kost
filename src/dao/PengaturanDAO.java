package dao;

import config.KoneksiDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PengaturanDAO {
    
    public boolean updatePassword(String oldPass, String newPass, String role) {
        String checkSql = "SELECT id FROM users WHERE password = ? AND role = ?";
        
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement checkPst = conn.prepareStatement(checkSql)) {
            
            checkPst.setString(1, oldPass);
            checkPst.setString(2, role.toLowerCase());
            
            try (ResultSet rs = checkPst.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String updateSql = "UPDATE users SET password = ? WHERE id = ?";
                    
                    try (PreparedStatement updatePst = conn.prepareStatement(updateSql)) {
                        updatePst.setString(1, newPass);
                        updatePst.setInt(2, id);
                        return updatePst.executeUpdate() > 0;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}