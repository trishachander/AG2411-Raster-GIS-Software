package components;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import java.awt.Color;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFileChooser;

public class GUI extends JFrame {
	private final Action focalSum = new SwingAction();
	private final Action action = new OpenRaster();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 711, 464);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Open raster ...");
		mntmNewMenuItem.setAction(action);
		mnNewMenu.add(mntmNewMenuItem);
		
		JButton btnNewButton_1 = new JButton("New button");
		mnNewMenu.add(btnNewButton_1);
		
		JMenu aboutMenu = new JMenu("About");
		menuBar.add(aboutMenu);
		
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Focal Sum");
		btnNewButton.setAction(focalSum);
		toolBar.add(btnNewButton);
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Focal Sum");
			putValue(SHORT_DESCRIPTION, "Perform focal sum");
		}
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	

	private class OpenRaster extends AbstractAction {
		public OpenRaster() {
			putValue(NAME, "Open raster ...");
			putValue(SHORT_DESCRIPTION, "Open ASCII rster file");
		}
		public void actionPerformed(ActionEvent e) {
			SelectFile.FileScreen();
		}
	}
}
