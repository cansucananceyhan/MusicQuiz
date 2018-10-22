import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javazoom.jl.player.Player;
import javax.swing.JLabel;

public class QuizPanel extends JPanel implements Runnable, ActionListener {

	Question currentQuestion = null;
	String currentMusicFile = null;
	Player player = null;
	Quiz quiz;

	JPanel contentPane;
	JPanel answerBox;

	Thread gameThread;
	Thread playerThread;

	boolean gameFinished, questionsFinished = false;
	int currentQuestionIndex = -1;
	int realAnswer;
	int timeRemaining = 60;
	long timeStart;

	private JLabel questionLabel;
	private JLabel timeLabel;
	private JLabel qlabel;
	private JLabel pointLabel;

	// buttons for answers
	JButton answer1, answer2, answer3, answer4;

	// buttons for lifelines
	private JButton half;
	private JButton doubleAnswer;
	private JButton plus30;
	private JButton giveup;

	// checks whether lifelines is used or not
	boolean doubleAns = false;
	boolean rightAnswer = false;
	boolean halfJoker = false;
	boolean changeMusic = false;

	Font font = new Font("Arial", Font.BOLD, 20);//

	public QuizPanel(JPanel contentPane, Quiz quiz) {

		setBounds(0, 0, 800, 800);
		this.contentPane = contentPane;
		this.quiz = quiz;
		setLayout(null);
		UserInformation.points = 0;
		answer1 = new JButton("");
		answer2 = new JButton("");
		answer3 = new JButton("");
		answer4 = new JButton("");
		answer1.addActionListener(this);
		answer2.addActionListener(this);
		answer3.addActionListener(this);
		answer4.addActionListener(this);

		GridLayout layout = new GridLayout(2, 2);
		layout.setHgap(20);
		layout.setVgap(20);
		answerBox = new JPanel();
		answerBox.setLayout(layout);
		answerBox.setBounds(6, 542, 788, 182);
		add(answerBox);

		answerBox.setOpaque(false);
		answerBox.add(answer1);
		answerBox.add(answer2);
		answerBox.add(answer3);
		answerBox.add(answer4);

		questionLabel = new JLabel("New label", (int) CENTER_ALIGNMENT);
		questionLabel.setFont(font);
		questionLabel.setForeground(Color.white);
		questionLabel.setBounds(6, 460, 788, 64);
		add(questionLabel);

		timeLabel = new JLabel("New label");
		timeLabel.setBounds(26, 42, 197, 35);
		timeLabel.setFont(font);
		timeLabel.setForeground(Color.white);
		timeLabel.setText("Remaining time: " + timeRemaining);

		add(timeLabel);

		qlabel = new JLabel("Question: " + 0);
		qlabel.setForeground(Color.WHITE);
		qlabel.setFont(new Font("Arial", Font.BOLD, 20));
		qlabel.setBounds(559, 42, 197, 35);
		add(qlabel);

		pointLabel = new JLabel("Points: " + 0);
		pointLabel.setForeground(Color.WHITE);
		pointLabel.setFont(new Font("Arial", Font.BOLD, 20));
		pointLabel.setBounds(559, 89, 197, 35);
		add(pointLabel);

		gameThread = new Thread(this);
		playerThread = new Thread(new PlayerRun(this, playerThread));

		half = new JButton("%50");
		half.setBounds(21, 111, 56, 41);
		add(half);
		half.addActionListener(this);

		doubleAnswer = new JButton("2X");
		doubleAnswer.setBounds(89, 111, 56, 41);
		add(doubleAnswer);
		doubleAnswer.addActionListener(this);

		plus30 = new JButton("+30");
		plus30.setBounds(157, 111, 56, 41);
		plus30.addActionListener(this);
		add(plus30);

		giveup = new JButton("GIVEUP");
		giveup.setBounds(117, 160, 56, 41);

		giveup.addActionListener(this);
		add(giveup);

		// START GAME
		gameFinished = setNewQuestion();
		gameThread.start();
		playerThread.start();
	}

	public void paintComponent(Graphics g) {
		g.drawImage((new ImageIcon("quiz_bg.jpg").getImage()), 0, 0, 800, 800, this);
	}

	public boolean setNewQuestion() {
		answer1.setVisible(true);
		answer2.setVisible(true);
		answer3.setVisible(true);
		answer4.setVisible(true);
		boolean finished = false;
		currentQuestionIndex++;
		if (currentQuestionIndex < quiz.questions.size()) {
			currentQuestion = quiz.questions.get(currentQuestionIndex);
			answer1.setText(currentQuestion.option1);
			answer2.setText(currentQuestion.option2);
			answer3.setText(currentQuestion.option3);
			answer4.setText(currentQuestion.option4);
			questionLabel.setText(currentQuestion.question);
			currentMusicFile = currentQuestion.path;
			realAnswer = currentQuestion.answer;
			timeRemaining = 60;
			timeStart = System.currentTimeMillis();
			timeLabel.setText("" + timeRemaining);
			changeMusic = true;
			if (player != null)
				player.close();

			if (halfJoker) {
				halfJoker = false;
				answer1.setVisible(true);
				answer2.setVisible(true);
				answer3.setVisible(true);
				answer4.setVisible(true);
			}
			repaint();
		} else {
			finished = true;
		}

		return finished;
	}

	@Override
	public void run() {

		long lastCheckTime = System.currentTimeMillis();

		while (gameFinished == false && questionsFinished == false) {

			// SETUP REMAINING TIME
			long timeNow = System.currentTimeMillis();
			if (timeNow - lastCheckTime > 1000) {
				lastCheckTime = timeNow;
				timeRemaining--;
				timeLabel.setText("Remaining time: " + timeRemaining);
				if (timeRemaining == 0) {
					gameFinished = true;
				}
			}

			// CHECK IF ANSWER IS GIVEN
			if (rightAnswer == true) {
				rightAnswer = false;
				// MAKE GREEN
				if (realAnswer == 1) {
					answer1.setBackground(Color.green);
					answer1.setOpaque(true);
				}
				if (realAnswer == 2) {
					answer2.setBackground(Color.green);
					answer2.setOpaque(true);
				}
				if (realAnswer == 3) {
					answer3.setBackground(Color.green);
					answer3.setOpaque(true);
				}
				if (realAnswer == 4) {
					answer4.setBackground(Color.green);
					answer4.setOpaque(true);
				}
				// WAIT 2 SECONDS
				repaint();
				try {
					gameThread.sleep(2000);
				} catch (InterruptedException e) {
					System.out.println("green");
				}
				// MAKE BUTTON NORMAL AGAIN
				if (realAnswer == 1) {
					answer1.setBackground(Color.black);
					answer1.setOpaque(false);
				}
				if (realAnswer == 2) {
					answer2.setBackground(Color.black);
					answer2.setOpaque(false);
				}
				if (realAnswer == 3) {
					answer3.setBackground(Color.black);
					answer3.setOpaque(false);
				}
				if (realAnswer == 4) {
					answer4.setBackground(Color.black);
					answer4.setOpaque(false);
				}

				// INCRREASE POINTS
				UserInformation.points += 10;

				// SETUP NEW QUESTION
				questionsFinished = setNewQuestion();
			}

			repaint();
			try {
				gameThread.sleep(150);
			} catch (InterruptedException e) {
				questionLabel.setText("Question: " + (currentQuestionIndex + 1));
			}

			pointLabel.setText("Points: " + UserInformation.points);
			

		}

		// if game finished
		try {
			player.close();
		} catch (NullPointerException e) {
			System.out.println("Exception : " + e.getCause());
		}

		DBManager.saveScore();
		setVisible(false);
		if (questionsFinished == true) {
			contentPane.add(new CongratPanel(currentQuestionIndex, contentPane));
			contentPane.repaint();
			contentPane.revalidate();

		}
		if (gameFinished == true) {
			contentPane.add(new GameOverPanel(currentQuestionIndex, contentPane));
			contentPane.repaint();
			contentPane.revalidate();

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == giveup) {
			gameFinished = true;
		}
		if (e.getSource() == doubleAnswer) {
			doubleAnswer.setEnabled(false);
			doubleAns = true;

		}
		if (e.getSource() == plus30) {
			timeRemaining = timeRemaining + 30;
			plus30.setEnabled(false);
		}
		if (e.getSource() == half) {
			Random random = new Random();

			for (int count = 0;count<2; ) {
				int delete = realAnswer;
				int delete2 = realAnswer;
				while (delete == realAnswer) {
					delete = random.nextInt(5) + 1;
				}
				while (delete2 == realAnswer || delete2 == delete) {
					delete2 = random.nextInt(5) + 1;
				}
				if (delete == 1 || delete2 == 1) {
					answer1.setVisible(false);
					count++;
				}
				if (delete == 2 || delete2 == 2) {
					answer2.setVisible(false);
					count++;
				}
				if (delete == 3 || delete2 == 3) {
					answer3.setVisible(false);
					count++;
				}
				if (delete == 4 || delete2 == 4) {
					answer4.setVisible(false);
					count++;
				}

			}
			halfJoker = true;
			half.setEnabled(false);
		}
		if (e.getSource() == answer1) {
			if (realAnswer == 1) {
				rightAnswer = true;
			} else {
				if (doubleAns == true) {
					answer1.setVisible(false);
					doubleAns = false;
				} else
					gameFinished = true;
			}
		}
		if (e.getSource() == answer2) {
			if (realAnswer == 2) {
				rightAnswer = true;
			} else {
				if (doubleAns == true) {
					answer2.setVisible(false);
					doubleAns = false;
				} else
					gameFinished = true;
			}
		}
		if (e.getSource() == answer3) {
			if (realAnswer == 3) {
				rightAnswer = true;
			} else {
				if (doubleAns == true) {
					answer3.setVisible(false);
					doubleAns = false;
				} else
					gameFinished = true;
			}
		}
		if (e.getSource() == answer4) {
			if (realAnswer == 4) {
				rightAnswer = true;
			} else {
				if (doubleAns == true) {
					answer4.setVisible(false);
					doubleAns = false;
				} else
					gameFinished = true;
			}
		}

	}

}

class PlayerRun implements Runnable {

	QuizPanel q;
	Thread thread;

	public PlayerRun(QuizPanel q, Thread t) {
		this.thread = t;
		this.q = q;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (q.changeMusic == true) {
				q.changeMusic = false;
				try {
					if (q.player != null)

						q.player.close();
					q.player = new Player(new FileInputStream(new File(q.currentMusicFile)));
					q.player.play();

				} catch (Exception i) {
					System.out.println("error");
				}

			} else {
				try {
					thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}

	}

}
