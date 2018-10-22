import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel implements ActionListener {

	// a JTextField component to allow user typing username.
	private JTextField usertf;
	//// a JPasswordField component to allow user typing but does not show the
	//// content.
	private JPasswordField pwtf;
	// buttons to login or register.
	private JButton btnLogin, btnRegister;
	JPanel contentPane;

	public LoginPanel(JPanel contentPane) {

		this.contentPane = contentPane;
		setBounds(0, 0, 800, 800);
		setLayout(null);

		// creation of login label.
		JLabel lblLogin = new JLabel("LOGIN :");
		lblLogin.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblLogin.setForeground(Color.RED);
		lblLogin.setBounds(83, 75, 103, 39);
		add(lblLogin);

		// creation of username label.
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(Color.white);
		lblUsername.setBounds(16, 149, 86, 16);
		add(lblUsername);

		// creation of JTextField component to allow users typing their
		// username.
		usertf = new JTextField();
		usertf.setBounds(135, 144, 130, 26);
		add(usertf);
		usertf.setColumns(10);

		// creation of username label.
		JLabel label = new JLabel("Password:");
		label.setForeground(Color.white);
		label.setBounds(16, 213, 86, 16);
		add(label);

		//// creation of JPasswordField component to allow users typing their
		//// password.
		pwtf = new JPasswordField();
		pwtf.setColumns(10);
		pwtf.setBounds(135, 208, 130, 26);
		add(pwtf);

		// creation of login button and adding listener.
		btnLogin = new JButton("LOGIN");
		btnLogin.setBounds(16, 257, 117, 29);
		btnLogin.addActionListener(this);
		add(btnLogin);

		// creation of register button and adding listener.
		btnRegister = new JButton("REGISTER");
		btnRegister.setBounds(145, 257, 117, 29);
		btnRegister.addActionListener(this);
		add(btnRegister);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage((new ImageIcon("MusicQuiz_BG.png").getImage()), 0, 0, 800, 800, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		/*when action occured on login button, checks whether the user is
		 *  registered or not. If it is, allows user to access main screen.*/
		if (e.getSource() == btnLogin) {
			int login = DBManager.login(usertf.getText(), pwtf.getText());
			//if (login == 0) {

				setVisible(false);
				contentPane.add(new MainScreen(contentPane));
				contentPane.repaint();
				contentPane.revalidate();
			//}

		}
		//when action occured on register button, opens the registerpanel
		if (e.getSource() == btnRegister) {

			setVisible(false);
			contentPane.add(new RegisterPanel(contentPane));
			contentPane.repaint();
			contentPane.revalidate();

		}
	}

}
