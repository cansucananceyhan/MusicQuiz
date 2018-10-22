
// import required packages
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainScreen extends JPanel implements ActionListener {

	/**
	 * Create the panel.
	 */
	public JPanel contentPane;
	Thread thread;
	int script;
	JLabel scriptLabel;
	Font font = new Font("Arial", Font.BOLD, 20);

	JButton rock, pop, jazz, hiphop, classic, country, reggie, speech, film, profileButton;
	private JLabel mainLabel;
	private JPanel highScorePanel;

	public MainScreen(JPanel contentPane) {
		this.contentPane = contentPane;
		scriptLabel = new JLabel();
		scriptLabel.setBounds(308, 5, 300, 24);
		scriptLabel.setText("Welcome " + UserInformation.username + " have fun!");
		scriptLabel.setFont(font);
		scriptLabel.setForeground(Color.white);
		setBounds(0, 0, 800, 800);
		setLayout(null);
		add(scriptLabel);

		JLabel lblStartAQuiz = new JLabel("START A QUIZ NOW!");
		lblStartAQuiz.setBounds(69, 377, 220, 56);
		lblStartAQuiz.setFont(font);
		lblStartAQuiz.setForeground(Color.red);
		add(lblStartAQuiz);

		JPanel typesPanel = new JPanel();
		typesPanel.setBounds(37, 500, 297, 183);
		add(typesPanel);
		typesPanel.setLayout(new GridLayout(3, 3));

		// category button are created
		rock = new JButton("Rock!");
		pop = new JButton("Pop!");
		jazz = new JButton("Jazz!");
		hiphop = new JButton("hiphop!");
		classic = new JButton("Classic!");
		country = new JButton("Country!");
		reggie = new JButton("Reggie!");
		speech = new JButton("Speech!");
		film = new JButton("Film!");

		// actionListener is added to buttons
		film.addActionListener(this);
		speech.addActionListener(this);
		reggie.addActionListener(this);
		country.addActionListener(this);
		classic.addActionListener(this);
		rock.addActionListener(this);
		pop.addActionListener(this);
		jazz.addActionListener(this);
		hiphop.addActionListener(this);

		typesPanel.setOpaque(false);
		// buttons are added to panel
		typesPanel.add(rock);
		typesPanel.add(pop);
		typesPanel.add(classic);
		typesPanel.add(country);
		typesPanel.add(speech);
		typesPanel.add(film);
		typesPanel.add(hiphop);
		typesPanel.add(jazz);
		typesPanel.add(reggie);

		// HIGH SCORES label and panel are created
		mainLabel = new JLabel("HIGH SCORES!");
		mainLabel.setForeground(Color.RED);
		mainLabel.setFont(new Font("Arial", Font.BOLD, 20));
		mainLabel.setBounds(558, 73, 220, 56);
		add(mainLabel);

		highScorePanel = new JPanel();
		highScorePanel.setLayout(new GridLayout(3, 1));
		highScorePanel.setBounds(504, 124, 256, 194);
		highScorePanel.setOpaque(false);

		// High Scores are found and added to highScorePanel
		ArrayList<Score> scores = DBManager.getHighScores();
		for (int i = 0; i < 3; i++) {
			String text = "";
			if (i < scores.size()) {
				Score s = scores.get(i);
				text = "" + (i + 1) + ") " + s.username + ", Points= " + s.score;
			} else {
				text = "" + (i + 1) + ")";
			}
			JLabel label = new JLabel(text);
			label.setFont(font);
			label.setForeground(Color.WHITE);
			highScorePanel.add(label);
		}
		add(highScorePanel);

		// PROFILE button is created
		profileButton = new JButton("PROFILE");
		profileButton.setBounds(62, 84, 117, 40);
		add(profileButton);
		profileButton.addActionListener(this);
	}

	public void paintComponent(Graphics g) {
		// Main screen image is added
		g.drawImage((new ImageIcon("MainScreen_bg.jpg").getImage()), 0, 0, 800, 800, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == profileButton) {
			setVisible(false);
			contentPane.add(new ProfilePanel(contentPane));
			contentPane.repaint();
			contentPane.revalidate();
			return;
		}
		// if database has no question about the selected category
		if (DBManager.getNumberOfQuestions(e.getActionCommand()) <= 0) {
			JOptionPane.showMessageDialog(null, "There is no question.");
			return;
		}
		ArrayList<Question> questions = DBManager.getQuestions(e.getActionCommand());
		Quiz quiz = new Quiz(questions, e.getActionCommand());
		QuizPanel qp = new QuizPanel(contentPane, quiz);

		setVisible(false);
		contentPane.add(qp);
	}
}