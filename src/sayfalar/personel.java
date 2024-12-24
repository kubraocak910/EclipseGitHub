package sayfalar;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class personel extends JPanel {
    private static final long serialVersionUID = 1L;
    private String personelEmail = "personel@ornek.com"; // Burada örnek personel e-postasını kullanıyoruz, dinamik olarak alabilirsiniz.

    
    public personel() {
        this(""); // Parametresiz constructor, parametreli constructor'a yönlendiriyor.
    }
    
    public personel(String email) {
        this.personelEmail = email; // Personelin e-posta adresini constructor üzerinden alıyoruz.
        
        setLayout(null);

        // Başlık
        JLabel lblTitle = new JLabel("Atanan Görevler");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(10, 30, 500, 30);
        add(lblTitle);

        // Görevlerin listeleneceği panel
        JPanel panel = new JPanel();
        panel.setBounds(38, 94, 451, 299);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Görevler dikey olarak listelenecek
        add(panel);

        // Personelin görevlerini al
        loadTasks(panel);
        
        // Paneli yeniden çizme
        panel.revalidate();
        panel.repaint();
    }

    // Görevleri yüklemek için metod
    public void loadTasks(JPanel panel) {
        // Veritabanı bağlantısı ve görevleri çekme işlemi
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean taskFound = false;

        try {
            conn = VeritabaniYardimcisi.getConnection(); // Veritabanı bağlantısını al
            String sql = "SELECT gorev, tarih FROM gorevler WHERE personel_email = ? ORDER BY tarih DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, personelEmail); // Personel e-posta adresini buraya ekliyoruz
            rs = stmt.executeQuery();

            // Veritabanından gelen görevleri panelde göster
            while (rs.next()) {
                String gorev = rs.getString("gorev");
                String tarih = rs.getString("tarih");

                // Görevi ve tarihi birleştirerek bir JLabel ekleyin
                JLabel lblTask = new JLabel("Görev: " + gorev + " | Tarih: " + tarih);
                lblTask.setFont(new Font("Arial", Font.PLAIN, 14));
                panel.add(lblTask);
                taskFound = true;
            }

            // Eğer görev bulunmadıysa bilgilendirme etiketi ekle
            if (!taskFound) {
                JLabel lblNoTasks = new JLabel("Atanan görev bulunmamaktadır.");
                lblNoTasks.setFont(new Font("Arial", Font.PLAIN, 14));
                panel.add(lblNoTasks);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
