import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProfilePanel extends JPanel {

	JPanel contentPane;

	public ProfilePanel(JPanel contentPane) {

		this.contentPane = contentPane;
		setBounds(0, 0, 800, 800);
		setLayout(null);

		//creation of username label.
		JLabel lblUsername = new JLabel("USERNAME:");
		lblUsername.setBounds(308, 227, 108, 25);
		lblUsername.setForeground(Color.red);
		add(lblUsername);
		
		//creation of label which contains username info.
		JLabel username = new JLabel("New label");
		username.setBounds(392, 220, 207, 36);
		username.setForeground(Color.white);
		username.setText(UserInformation.username);
		add(username);
		
		//creation of high score label.
		JLabel lblHighscore = new JLabel("HIGH SCORE:");
		lblHighscore.setBounds(308, 350, 207, 36);
		lblHighscore.setForeground(Color.red);
		add(lblHighscore);
		
		//creation of label which contains highest score of that user.
		JLabel score = new JLabel("New label");
		score.setBounds(395, 355, 108, 25);
		score.setForeground(Color.white);
		score.setText("" + DBManager.getHighScore(UserInformation.username));
		add(score);
		
		//creation of back button.
		JButton btnBack = new JButton("BACK");
		//adding action listener to back button and if there is an action,returns main screen.
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				contentPane.add(new MainScreen(contentPane));
				contentPane.repaint();
				contentPane.revalidate();
			}
		});
		btnBack.setBounds(22, 630, 117, 29);
		add(btnBack);
		
		//creation of select pic button.
		JButton btnSelectPic = new JButton("SELECT PIC");
		
		/*adding action listener to select pic button and if there is an action,
		 *open a file chooser.when file is chosen, gets path of the file and
		 *add path to the database. */
		btnSelectPic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					String image = fileChooser.getSelectedFile().getAbsolutePath();
					DBManager.updatePic(image);
					UserInformation.im = (new ImageIcon(image)).getImage();
					repaint();
				}
			}
		});
		btnSelectPic.setBounds(341, 180, 117, 29);
		add(btnSelectPic);

	}

	public void paintComponent(Graphics g) {
		g.drawImage((new ImageIcon("MainScreen_bg.jpg").getImage()), 0, 0, 800, 800, this);
		g.drawImage(UserInformation.im, 330, 40, 120, 120, this);

	}
}
