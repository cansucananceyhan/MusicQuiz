import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterPanel extends JPanel implements ActionListener {

	JPanel contentPane;
	//a JTextField component to allow user typing.
	private JTextField username;
	//a JPasswordField component to allow user typing but does not show the content.
	private JPasswordField password;
	//buttons to turn back or confirm.
	JButton back, okey;

	/*A constructor which draws a panel with two field getting password
	 * and username information from user and two button which make user able
	 * to return back or confirm.*/
	public RegisterPanel(JPanel contentPane) {

		this.contentPane = contentPane;
		setBounds(0, 0, 800, 800);
		setLayout(null);

		//creation of username label.
		JLabel lblUsername = new JLabel("USERNAME:");
		lblUsername.setForeground(Color.white);
		lblUsername.setBounds(158, 261, 107, 16);
		add(lblUsername);
		
		//creation of JTextField component to allow users typing their username.
		username = new JTextField();
		username.setBounds(328, 256, 130, 26);
		add(username);
		username.setColumns(10);
		
		//creation of password label.
		JLabel lblPassword = new JLabel("PASSWORD:");
		lblPassword.setForeground(Color.white);
		lblPassword.setBounds(158, 364, 107, 16);
		add(lblPassword);
		
		//creation of JPasswordField component to allow users typing their password.
		password = new JPasswordField();
		password.setBounds(328, 359, 130, 26);
		add(password);
		password.setColumns(10);

		//creation of back button and adding action listener.
		back = new JButton("BACK");
		back.setBounds(113, 552, 117, 29);
		back.addActionListener(this);
		add(back);

		//creation of okey button and adding action listener.
		okey = new JButton("REGISTER");
		okey.setBounds(415, 552, 117, 29);
		okey.addActionListener(this);
		add(okey);

	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage((new ImageIcon("MusicQuiz_BG.png").getImage()), 0, 0, 800, 800, this);
	}

	public void actionPerformed(ActionEvent event) {
		
		//returning back to the login page and add user to database when action occured on okey button.
		if (event.getSource() == okey) {
			DBManager.register(username.getText(), password.getText());
			setVisible(false);
			contentPane.add(new LoginPanel(contentPane));
			contentPane.repaint();
			contentPane.revalidate();
		}
		//returning back to the login page when action occured on back button.
		else if (event.getSource() == back) {
			setVisible(false);
			contentPane.add(new LoginPanel(contentPane));
			contentPane.repaint();
			contentPane.revalidate();
		}

	}
}
