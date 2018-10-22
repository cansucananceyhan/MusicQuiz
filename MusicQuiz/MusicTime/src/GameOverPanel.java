import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class GameOverPanel extends JPanel {

	/**
	 * Create the panel.
	 * 
	 */

	Font font = new Font("Arial", Font.BOLD, 20);

	private JPanel highScorePanel;

	public GameOverPanel(int quest, JPanel contentpane) {
		setBounds(0, 0, 800, 800);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("Game over " + UserInformation.username);
		lblNewLabel.setBounds(246, 87, 414, 60);
		lblNewLabel.setForeground(Color.red);
		add(lblNewLabel);

		JLabel question = new JLabel("You have failed at " + quest + "th question.");
		question.setBounds(290, 171, 291, 60);
		question.setForeground(Color.white);
		add(question);

		JLabel score = new JLabel("Your score is " + UserInformation.points);
		score.setForeground(Color.white);
		score.setBounds(290, 287, 297, 54);
		add(score);

		highScorePanel = new JPanel();
		highScorePanel.setLayout(new GridLayout(3, 1));
		highScorePanel.setBounds(504, 124, 256, 194);
		highScorePanel.setOpaque(false);

		ArrayList<Score> scores = DBManager.getHighScores();

		for (int a = 0; a < 3; a++) {
			String text = "";

			if (a < scores.size()) {
				Score s = scores.get(a);
				text = "" + (a + 1) + ") " + s.username + ", Points= " + s.score;
			} else {
				text = "" + (a + 1) + ")";
			}
			JLabel label = new JLabel(text);
			label.setFont(font);
			label.setBackground(Color.white);
			label.setForeground(Color.white);
			highScorePanel.add(label);
		}
		add(highScorePanel);

		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				contentpane.add(new MainScreen(contentpane));
				contentpane.repaint();
				contentpane.revalidate();
			}
		});
		btnBack.setBounds(22, 585, 117, 29);
		add(btnBack);

	}

	public void paintComponent(Graphics g) {
		g.drawImage((new ImageIcon("MainScreen_bg.jpg").getImage()), 0, 0, 800, 800, this);

	}

}
