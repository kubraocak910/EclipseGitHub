package sayfalar;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class personel extends JPanel {
    private static final long serialVersionUID = 1L;
    private String personelEmail = "personel@ornek.com"; 

    public personel() {
        this(""); 
    }
    
    public personel(String email) {
        this.personelEmail = email; 
        
        setLayout(null);

        JLabel lblTitle = new JLabel("Atanan Görevler");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(10, 30, 500, 30);
        add(lblTitle);

        JPanel panel = new JPanel();
        panel.setBounds(38, 94, 451, 299);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); 
        add(panel);

        loadTasks(panel);
        
        JButton btnNewButton = new JButton("çıkış");
        btnNewButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		System.exit(0);  
        	}
        });
        btnNewButton.setBounds(751, 415, 85, 21);
        add(btnNewButton);
        
        panel.revalidate();
        panel.repaint();
    }

    public void loadTasks(JPanel panel) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean taskFound = false;

        try {
            conn = VeritabaniYardimcisi.getConnection(); 
            String sql = "SELECT gorev, tarih FROM gorevler WHERE personel_email = ? ORDER BY tarih DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, personelEmail); 
            rs = stmt.executeQuery();

            while (rs.next()) {
                String gorev = rs.getString("gorev");
                String tarih = rs.getString("tarih");

                JLabel lblTask = new JLabel("Görev: " + gorev + " | Tarih: " + tarih);
                lblTask.setFont(new Font("Arial", Font.PLAIN, 14));
                panel.add(lblTask);
                taskFound = true;
            }

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
