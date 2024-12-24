package sayfalar;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class giris extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPasswordField passwordField;
	private JTextField textField;

	public giris(CardLayout cl, JPanel contentPane) {
		setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(365, 95, 206, 13);
		add(lblNewLabel_1);

		JButton btnNewButton = new JButton("üye olmak için ");
		btnNewButton.setBounds(410, 292, 123, 21);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cl.show(contentPane, "uyeolma");
			}
		});
		add(btnNewButton);

		passwordField = new JPasswordField();
		passwordField.setBounds(365, 209, 206, 19);
		add(passwordField);

		JLabel lblNewLabel = new JLabel("Şifre");
		lblNewLabel.setBounds(365, 186, 45, 13);
		add(lblNewLabel);

		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(365, 133, 45, 13);
		add(lblEmail);

		textField = new JTextField();
		textField.setBounds(365, 157, 206, 19);
		add(textField);
		textField.setColumns(10);

		JButton btnGiri = new JButton("Giriş");
		btnGiri.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String email = textField.getText();
				String sifre = new String(passwordField.getPassword());
				if (VeritabaniYardimcisi.girisBilgileriDogruMu(email, sifre)) {
				    if (VeritabaniYardimcisi.rolKontrolu(email, sifre)) {
	                    cl.show(contentPane, "yonetici");
	                } else {
	                    cl.show(contentPane, "personel");
	                }
				} else {
					lblNewLabel_1.setText("E-posta veya şifre yanlış!");
				}
			}
		});
		btnGiri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnGiri.setBounds(410, 261, 123, 21);
		add(btnGiri);
	}
}
