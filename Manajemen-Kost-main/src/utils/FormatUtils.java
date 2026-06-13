package utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class FormatUtils {

    private static final String PATTERN_TANGGAL = "dd-MM-yyyy";

    private FormatUtils() {
    }

    public static String formatRupiah(double nominal) {
        NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("in", "ID"));
        return "Rp " + formatter.format(nominal);
    }

    public static String formatTanggal(Date tanggal) {
        if (tanggal == null) {
            return "-";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_TANGGAL);
        return sdf.format(tanggal);
    }

    public static Date parseTanggal(String tanggalStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_TANGGAL);
        try {
            return sdf.parse(tanggalStr);
        } catch (ParseException e) {
            System.out.println("Format tanggal tidak valid, gunakan dd-MM-yyyy");
            return null;
        }
    }

    public static String rapikanTeks(String teks) {
        if (teks == null) {
            return "";
        }
        return teks.trim();
    }
}
