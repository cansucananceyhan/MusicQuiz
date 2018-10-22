/*
 * Import required packages
 */
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.sql.*;
import com.mysql.jdbc.Connection;

public class DBManager 
{
	/*
	 * Requires that you initialize a driver so you can open a communications
	 * channel with the database.
	 * JDBC driver name and database URL
	 */
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	String URL = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7149733";
	
	/*
	 * Database credentials
	 */
	String USER = "sql7149733";
	String PASS = "JdU4PnK3ry";

	public static Connection conn = null;

	/*
	 * Constructor
	 */
	public DBManager() 
	{
		try 
		{
			//Dynamically load the driver
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("Connecting to database...");
		try 
		{
			conn = (Connection) DriverManager.getConnection(URL, USER, PASS);

			if (conn != null) 
			{
				System.out.println("Connected to database");
			} else 
			{
				System.out.println("Cannot connect to database");
			}
		} catch (SQLException e) {
		}
	}

	// A method makes execution of query
	public static ResultSet executeQuery(String sql) {
		Statement stmt = null;
		// A table of data representing a database result set
		ResultSet rs = null;
		try {
			if (conn == null) {
				System.out.println("No connection!");
				return null;
			}
			stmt = conn.createStatement();
			// Executes the given SQL statement, which returns a single
			// ResultSet object.
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static int executeUpdate(String sql) {
		Statement stmt = null;

		int result = -1;
		try {
			if (conn == null) {
				System.out.println("No connection!");
				return -1;
			}
			stmt = conn.createStatement();
			// Executes the given SQL statement, which may be an INSERT, UPDATE,
			// or DELETE statement
			result = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int login(String username, String password) {
		int result = -1;

		String sql = "SELECT * FROM USERS WHERE username='" + username + "' AND password='" + password + "'";
		ResultSet rs = executeQuery(sql);
		try {
			if (rs.next()) {
				UserInformation.user_id = rs.getInt(1);
				UserInformation.username = rs.getString(2);
				UserInformation.password = rs.getString(3);
			} else {
				JOptionPane.showConfirmDialog(null, "Username or password is wrong..");
			}
		} catch (SQLException e) {
		}

		String image = null;
		try {
			image = rs.getString(5);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (image == null) {
			UserInformation.im = (new ImageIcon("noimage.jpg")).getImage();
		} else {
			UserInformation.im = (new ImageIcon(image)).getImage();
		}

		return result;
	}

	public static void register(String username, String password) {
		String sql = "INSERT INTO USERS VALUES(null,'" + username + "','" + password + "',0,null)";
		int result = executeUpdate(sql);

		if (result == -1) {
			JOptionPane.showMessageDialog(null, "Couldnt register..");
		} else {
			JOptionPane.showMessageDialog(null, " Have fun..");
		}
	}

	public static int getNumberOfQuestions(String type) {
		String sql = "SELECT * FROM QUESTION WHERE type='" + type + "'";
		ResultSet rs = executeQuery(sql);

		int total = 0;
		try {
			while (rs.next()) {
				total += 1;
			}
		} catch (SQLException e) {
		}
		return total;
	}

	public static ArrayList<Question> getQuestions(String type) {
		ArrayList<Question> questions = new ArrayList<Question>();
		String sql = "SELECT * FROM QUESTION WHERE type='" + type + "' ORDER BY difficulty";
		ResultSet rs = executeQuery(sql);

		try {
			while (rs.next()) {
				Question ques = new Question(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9));
				questions.add(ques);
			}
		} catch (SQLException e) {
		}
		return questions;
	}

	public static void saveScore() {
		String sql = "INSERT INTO SCORES VALUES('" + UserInformation.username + "'," + UserInformation.points + ")";
		executeUpdate(sql);
	}

	public static ArrayList<Score> getHighScores() {
		ArrayList<Score> scores = new ArrayList<Score>();
		String sql = "SELECT * FROM SCORES ORDER BY score DESC";
		ResultSet rs = executeQuery(sql);
		try {
			while (rs.next()) {
				scores.add(new Score(rs.getString(1), rs.getInt(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return scores;
	}

	public static int getHighScore(String username) {
		String sql = "SELECT * FROM SCORES  WHERE username='" + username + "' ORDER BY score DESC";
		int score = 0;
		ResultSet rs = executeQuery(sql);
		try {
			if (rs.next()) {
				score = rs.getInt(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return score;
	}

	public static void updatePic(String image) {
		String sql = "UPDATE USERS SET image='" + image + "' WHERE username='" + UserInformation.username + "'";
		executeUpdate(sql);
	}
}