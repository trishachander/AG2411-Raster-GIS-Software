package components;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import java.awt.BorderLayout;

public class SelectFile {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void FileScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectFile window = new SelectFile();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SelectFile() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JFileChooser fileChooser = new JFileChooser();
		frame.getContentPane().add(fileChooser, BorderLayout.CENTER);
	}

}
