package dao.Admin;

import config.KoneksiDatabase;
import models.Kamar;
import models.Kost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KostDAO {

    private final Connection conn;

    public KostDAO() {
        conn = KoneksiDatabase.getConnection();
    }

    public List<Kost> getAll() {
        List<Kost> list = new ArrayList<>();
        String sql = "SELECT * FROM kost";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Kost kost = new Kost(rs.getInt("id_kost"), rs.getString("nama_kost"), rs.getString("alamat"));
                List<Kamar> kamarnya = new KamarDAO().getAll().stream().filter(k -> k.getIdKost() == kost.getIdKost()).collect(Collectors.toList());

                kost.setDaftarKamar(kamarnya);
                list.add(kost);
            }

            rs.close();
            pst.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}