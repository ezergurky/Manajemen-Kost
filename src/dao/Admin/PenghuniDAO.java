package dao.Admin;

import config.KoneksiDatabase;

import models.Penghuni;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PenghuniDAO {

    private final Connection conn;

    public PenghuniDAO() {
        conn = KoneksiDatabase.getConnection();
    }

    public List<Object[]> getAllPenghuniTableData() {
        List<Object[]> list = new ArrayList<>();
        
        String sql = "SELECT p.id_penghuni, u.name, k.nomor_kamar, p.no_hp, ks.tanggal_mulai, ks.status " +
                     "FROM penghuni p " +
                     "JOIN users u ON p.id_user = u.id " +
                     "LEFT JOIN kontrak_sewa ks ON p.id_penghuni = ks.id_penghuni AND ks.status = 'aktif' " + 
                     "LEFT JOIN kamar k ON ks.id_kamar = k.id_kamar " +
                     "ORDER BY p.id_penghuni ASC";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    "P-" + String.format("%03d", rs.getInt("id_penghuni")),
                    rs.getString("name"),
                    rs.getString("nomor_kamar") != null ? "Kamar " + rs.getString("nomor_kamar") : "Belum ada kamar",
                    rs.getString("no_hp"),
                    rs.getDate("tanggal_mulai"),
                    rs.getString("status") 
                };
                list.add(row);
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Penghuni p) throws SQLException {
        try {
            conn.setAutoCommit(false); 
            
            String sqlUser = "INSERT INTO users (name, username, email, password, role) VALUES (?, ?, ?, ?, 'penghuni')";
            PreparedStatement pstUser = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);
            pstUser.setString(1, p.getNama());
            pstUser.setString(2, p.getUsername());
            pstUser.setString(3, p.getEmail());
            pstUser.setString(4, p.getPassword());
            pstUser.executeUpdate();
            
            ResultSet rs = pstUser.getGeneratedKeys();
            int idUser = 0;
            if(rs.next()) idUser = rs.getInt(1);

            String sqlPenghuni = "INSERT INTO penghuni (id_user, nik, no_hp) VALUES (?, ?, ?)";
            PreparedStatement pstPenghuni = conn.prepareStatement(sqlPenghuni);
            pstPenghuni.setInt(1, idUser);
            pstPenghuni.setString(2, p.getNik());
            pstPenghuni.setString(3, p.getNoHp());
            pstPenghuni.executeUpdate();
            
            conn.commit(); 
            return true;
        } catch (SQLException e) {
            conn.rollback(); 
            throw e; 
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public Penghuni getDetailById(int idPenghuni) {
        String sql = "SELECT p.id_penghuni, p.id_user, u.name, u.username, u.email, u.password, p.nik, p.no_hp " +
                     "FROM penghuni p JOIN users u ON p.id_user = u.id WHERE p.id_penghuni = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idPenghuni);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                return new Penghuni(
                    rs.getInt("id_user"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("username"),
                    rs.getInt("id_penghuni"),
                    rs.getString("nik"),
                    rs.getString("no_hp")
                );
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public boolean update(Penghuni p) throws SQLException {
        try {
            conn.setAutoCommit(false);
            
            String sqlUser = "UPDATE users SET name=?, username=?, email=?, password=? WHERE id=?";
            PreparedStatement pstUser = conn.prepareStatement(sqlUser);
            pstUser.setString(1, p.getNama());
            pstUser.setString(2, p.getUsername());
            pstUser.setString(3, p.getEmail());
            pstUser.setString(4, p.getPassword());
            pstUser.setInt(5, p.getId()); 
            pstUser.executeUpdate();

            String sqlPenghuni = "UPDATE penghuni SET nik=?, no_hp=? WHERE id_penghuni=?";
            PreparedStatement pstPenghuni = conn.prepareStatement(sqlPenghuni);
            pstPenghuni.setString(1, p.getNik());
            pstPenghuni.setString(2, p.getNoHp());
            pstPenghuni.setInt(3, p.getIdPenghuni());
            pstPenghuni.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public boolean delete(int idPenghuni) {
        String sql = "DELETE FROM users WHERE id = (SELECT id_user FROM penghuni WHERE id_penghuni = ?)";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idPenghuni);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}