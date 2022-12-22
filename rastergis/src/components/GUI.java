package components;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
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
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.JList;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextPane;
import java.awt.ComponentOrientation;
import javax.swing.border.LineBorder;
import java.awt.Rectangle;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.CardLayout;
import java.awt.Panel;

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
		setTitle("rasterGIS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 711, 464);
		//
		// MENU ITEMS 
		//
		//Initialize menu-bar  
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		//Create "File" menu 
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		//Add open raster to file menu 
		JMenuItem openRasterMenuItem = new JMenuItem("Open raster ...");
		openRasterMenuItem.setAction(action);
		fileMenu.add(openRasterMenuItem);
		
		//Create "About" Menu 
		JMenu aboutMenu = new JMenu("About");
		menuBar.add(aboutMenu);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 97, 382);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setMinimumSize(new Dimension(20, 10));
		panel.setBackground(new Color(128, 128, 128));
		getContentPane().add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		//
		// TOOL BAR
		//
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 382, 695, 21);
		getContentPane().add(toolBar);
		
		JButton btnNewButton = new JButton("Focal Sum");
		btnNewButton.setAction(focalSum);
		toolBar.add(btnNewButton);
		
		//
		// JPANEL FOR DISPLAYYING RASTER
		//
		JPanel displayPanel = new JPanel();
		displayPanel.setBounds(107, 0, 588, 382);
		getContentPane().add(displayPanel);
		displayPanel.setForeground(new Color(255, 128, 64));
		displayPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		displayPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		displayPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		displayPanel.setBackground(new Color(128, 255, 0));
		displayPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Focal Sum");
			putValue(SHORT_DESCRIPTION, "Perform focal sum");
		}
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	
	//Action - Run when opening a raster from file -> open raster 
	private class OpenRaster extends AbstractAction {
		//private String fileName; 
		
		public OpenRaster() {
			putValue(NAME, "Open raster ...");
			putValue(SHORT_DESCRIPTION, "Open ASCII raster file");
		}
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileSelect = new JFileChooser(); //Create a new file chooser 
			fileSelect.setAcceptAllFileFilterUsed(false); //Set accept all file types to false
			
			//Create and apply an extension filter as to only accept txt files 
			FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("ASCII Raster Files (.txt)","txt"); 
			fileSelect.addChoosableFileFilter(fileFilter);
			
			int r = fileSelect.showDialog(null,"Select a file"); 
			if(r==JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileSelect.getSelectedFile(); 
				String filePath = selectedFile.getAbsolutePath(); 
				Layer raster = new Layer("",filePath); 
				greyScale(raster,2);
				
				
			}
			//SelectFile fileSelection = new SelectFile();
			//fileSelection.FileScreen();
			//fileName = fileSelection.fileName;  
			
		}
	}
	
	//Public GUI functions 
	
	//Visualize raster in greyscale
	public void greyScale(Layer input,double dScale){
		int scale = (int)Math.round(dScale);
		BufferedImage image = input.toImage();
		MapPanel map  = new MapPanel(image,scale);
		map.setBackground(UIManager.getColor("Button.light"));
		getContentPane().add(map, BorderLayout.CENTER);
		this.setVisible(true);
		
	}
}
