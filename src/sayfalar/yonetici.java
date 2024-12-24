package sayfalar;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class yonetici extends JPanel {
	private JTextField textField;
	private JTextField textField_2;

	public yonetici() {
		setLayout(null);

		JLabel lblPersonel = new JLabel("Personel :");
		lblPersonel.setBounds(30, 145, 46, 13);
		add(lblPersonel);

		JLabel lblTarih = new JLabel("Tarih :");
		lblTarih.setBounds(30, 42, 31, 13);
		add(lblTarih);

		JTextArea txtGorevler = new JTextArea();
		txtGorevler.setBounds(388, 82, 455, 374);
		txtGorevler.setEditable(false);
		add(txtGorevler);

		JLabel lblGorev = new JLabel("Görev :");
		lblGorev.setBounds(30, 94, 34, 13);
		add(lblGorev);

		textField = new JTextField();
		textField.setBounds(23, 65, 243, 19);
		add(textField);

		JComboBox<String> cmbPersonel = new JComboBox<String>();
		cmbPersonel.setBounds(23, 168, 243, 21);
		add(cmbPersonel);

		ArrayList<String> emailList = VeritabaniYardimcisi.getEmailList();
		for (String email : emailList) {
			cmbPersonel.addItem(email);
		}

		JButton btnGorevEkle = new JButton("Görev Ekle");
		btnGorevEkle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String tarih = textField.getText().trim();
				String gorev = textField_2.getText().trim();
				String personel = (String) cmbPersonel.getSelectedItem();

				if (tarih.isEmpty() || gorev.isEmpty() || personel == null) {
					txtGorevler.append("Lütfen tüm alanları doldurun!\n");
					return;
				}

				String formattedDate = VeritabaniYardimcisi.formatDate(tarih);
				if (formattedDate == null) {
					txtGorevler.append("Geçersiz tarih formatı!\n");
					return;
				}

				String gorevBilgisi = "Tarih: " + tarih + ", Görev: " + gorev + ", Personel: " + personel + "\n";
				txtGorevler.append(gorevBilgisi);

				textField.setText("");
				textField_2.setText("");
				cmbPersonel.setSelectedIndex(0);

				VeritabaniYardimcisi.addGorevToDatabase(tarih, gorev, personel, personel);
			}
		});
		btnGorevEkle.setBounds(100, 220, 81, 21);
		add(btnGorevEkle);

		textField_2 = new JTextField();
		textField_2.setBounds(23, 117, 243, 19);
		add(textField_2);

		JLabel lblGrevListesi = new JLabel("Görev listesi");
		lblGrevListesi.setBounds(388, 59, 182, 13);
		add(lblGrevListesi);

		JButton btnNewButton = new JButton("çıkış");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		btnNewButton.setBounds(783, 488, 85, 21);
		add(btnNewButton);
	}

	private static final long serialVersionUID = 1L;
}
