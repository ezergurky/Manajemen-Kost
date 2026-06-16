package dao;

import config.KoneksiDatabase;
import models.Kamar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class KamarDAO {

    private final Connection conn;

    public KamarDAO() {
        conn = KoneksiDatabase.getConnection();
    }

    public List<Kamar> getAll() {

        List<Kamar> list = new ArrayList<>();

        String sql = "SELECT * FROM kamar";

        try {

            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Kamar kamar = new Kamar(
                        rs.getInt("id_kamar"),
                        rs.getString("nomor_kamar"),
                        rs.getDouble("harga"),
                        rs.getString("status")
                );

                list.add(kamar);
            }

            rs.close();
            pst.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean insert(Kamar kamar) {

        String sql = "INSERT INTO kamar(nomor,harga,status) VALUES(?,?,?)";

        try {

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, kamar.getNomor());
            pst.setDouble(2, kamar.getHarga());
            pst.setString(3, kamar.getStatus());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(Kamar kamar) {

        String sql = "UPDATE kamar SET nomor=?, harga=?, status=? WHERE id_kamar=?";

        try {

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, kamar.getNomor());
            pst.setDouble(2, kamar.getHarga());
            pst.setString(3, kamar.getStatus());
            pst.setInt(4, kamar.getIdKamar());

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int idKamar) {

        String sql = "DELETE FROM kamar WHERE id_kamar=?";

        try {

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setInt(1, idKamar);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public Kamar getById(int idKamar) {

        String sql = "SELECT * FROM kamar WHERE id_kamar=?";

        try {

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idKamar);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                return new Kamar(
                        rs.getInt("id_kamar"),
                        rs.getString("nomor"),
                        rs.getDouble("harga"),
                        rs.getString("status")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}