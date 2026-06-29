package controllers.Penghuni;

import dao.Penghuni.KamarSayaDAO;
import utils.FormatUtils;
import views.Penghuni.KamarSayaPanel;
import java.awt.Color;

public class KamarSayaController {
    private final KamarSayaPanel view;
    private final KamarSayaDAO dao;
    private final int idPenghuni;

    public KamarSayaController(KamarSayaPanel view, int idPenghuni) {
        this.view = view;
        this.idPenghuni = idPenghuni;
        this.dao = new KamarSayaDAO();
        
        loadData();
    }

    public void loadData() {
        Object[] detail = dao.getDetailKamarPenghuni(idPenghuni);

        if (detail != null) {
            view.getLblNomorKamar().setText("Kamar " + detail[0].toString());
            view.getLblHarga().setText(FormatUtils.formatRupiah((Double) detail[1]));
            
            String fasilitas = detail[2] != null ? detail[2].toString() : "Tidak ada detail fasilitas";
            fasilitas = "• " + fasilitas.replace(",", "\n•");
            view.getTxtFasilitas().setText(fasilitas);

            view.getLblNamaKost().setText(detail[3].toString());
            view.getLblAlamatKost().setText(detail[4].toString());

            if (detail[5] instanceof java.util.Date) {
                view.getLblTanggalMulai().setText(FormatUtils.formatTanggal((java.util.Date) detail[5]));
            }
            if (detail[6] instanceof java.util.Date) {
                view.getLblTanggalSelesai().setText(FormatUtils.formatTanggal((java.util.Date) detail[6]));
            }
            
            String status = detail[7].toString();
            view.getLblStatusKontrak().setText(status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase());
            if (status.equalsIgnoreCase("aktif")) {
                view.getLblStatusKontrak().setForeground(new Color(16, 185, 129)); 
            } else {
                view.getLblStatusKontrak().setForeground(new Color(239, 68, 68)); 
            }

        } else {
            view.getLblNomorKamar().setText("Belum ada kamar");
            view.getLblHarga().setText("-");
            view.getTxtFasilitas().setText("Tidak ada info kamar.");
            view.getLblNamaKost().setText("-");
            view.getLblAlamatKost().setText("-");
            view.getLblTanggalMulai().setText("-");
            view.getLblTanggalSelesai().setText("-");
            view.getLblStatusKontrak().setText("Tidak Aktif");
            view.getLblStatusKontrak().setForeground(new Color(239, 68, 68)); 
        }
    }
}