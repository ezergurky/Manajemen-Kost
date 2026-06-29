package dao.Admin;

import config.KoneksiDatabase;
import models.Tagihan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TagihanDAO {

    private final Connection conn;

    public TagihanDAO() {
        conn = KoneksiDatabase.getConnection();
    }

    public List<Object[]> getAllTableData() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT t.id_tagihan, p.id_penghuni, u.name, k.nomor_kamar, t.bulan, t.tahun, t.jumlah, t.status, t.jatuh_tempo, t.denda " +
                     "FROM tagihan t " +
                     "JOIN penghuni p ON t.id_penghuni = p.id_penghuni " +
                     "JOIN users u ON p.id_user = u.id " +
                     "LEFT JOIN kontrak_sewa ks ON p.id_penghuni = ks.id_penghuni AND ks.status = 'aktif' " +
                     "LEFT JOIN kamar k ON ks.id_kamar = k.id_kamar " +
                     "ORDER BY t.jatuh_tempo DESC";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Object[]{
                    "INV-" + String.format("%04d", rs.getInt("id_tagihan")),
                    rs.getString("name"),
                    rs.getString("nomor_kamar") != null ? "Kamar " + rs.getString("nomor_kamar") : "-",
                    rs.getString("bulan") + " " + rs.getInt("tahun"),
                    rs.getDouble("jumlah") + rs.getDouble("denda"),
                    rs.getString("status"),
                    rs.getInt("id_tagihan"),
                    rs.getInt("id_penghuni"),
                    rs.getString("bulan"),
                    rs.getInt("tahun"),
                    rs.getDouble("jumlah"),
                    rs.getDouble("denda"),
                    rs.getDate("jatuh_tempo")
                });
            }
            rs.close();
            pst.close();
        } catch (Exception e) {}
        return list;
    }

    public boolean insert(Tagihan tagihan) {
        String sql = "INSERT INTO tagihan (id_penghuni, bulan, tahun, jatuh_tempo, jumlah, denda, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, tagihan.getIdPenghuni());
            pst.setString(2, tagihan.getBulan());
            pst.setInt(3, tagihan.getTahun());
            pst.setDate(4, new java.sql.Date(tagihan.getJatuhTempo().getTime()));
            pst.setDouble(5, tagihan.getJumlah());
            pst.setDouble(6, tagihan.getDenda());
            pst.setString(7, tagihan.getStatus());
            boolean res = pst.executeUpdate() > 0;
            pst.close();
            return res;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean update(Tagihan tagihan) {
        String sql = "UPDATE tagihan SET id_penghuni=?, bulan=?, tahun=?, jatuh_tempo=?, jumlah=?, denda=?, status=? WHERE id_tagihan=?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, tagihan.getIdPenghuni());
            pst.setString(2, tagihan.getBulan());
            pst.setInt(3, tagihan.getTahun());
            pst.setDate(4, new java.sql.Date(tagihan.getJatuhTempo().getTime()));
            pst.setDouble(5, tagihan.getJumlah());
            pst.setDouble(6, tagihan.getDenda());
            pst.setString(7, tagihan.getStatus());
            pst.setInt(8, tagihan.getIdTagihan());
            boolean res = pst.executeUpdate() > 0;
            pst.close();
            return res;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(int idTagihan) {
        String sql = "DELETE FROM tagihan WHERE id_tagihan=?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idTagihan);
            boolean res = pst.executeUpdate() > 0;
            pst.close();
            return res;
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<Object[]> getPenghuniAktif() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT p.id_penghuni, u.name, k.nomor_kamar FROM penghuni p " +
                     "JOIN users u ON p.id_user = u.id " +
                     "JOIN kontrak_sewa ks ON p.id_penghuni = ks.id_penghuni AND ks.status = 'aktif' " +
                     "JOIN kamar k ON ks.id_kamar = k.id_kamar";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                list.add(new Object[]{rs.getInt("id_penghuni"), rs.getString("name"), rs.getString("nomor_kamar")});
            }
        }catch(Exception e){}
        return list;
    }
}