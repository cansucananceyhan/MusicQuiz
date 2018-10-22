import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {

	private JPanel contentPanel;
	public DBManager db;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		Runnable r = new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		EventQueue.invokeLater(r);
	}

	/**
	 * Create the frame.
	 * 
	 * @return
	 */
	public MainFrame() {
		db = new DBManager();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 800, 800);
		// contentPanel is created and added
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPanel);
		LoginPanel loginPanelObject = new LoginPanel(contentPanel);
		contentPanel.add(loginPanelObject);
	}
}