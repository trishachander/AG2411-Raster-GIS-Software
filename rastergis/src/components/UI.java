package components;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import java.awt.ComponentOrientation;
import java.awt.Font;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Toolkit;

public class UI extends JFrame {
	private JPanel view; 
	private final Action action = new OpenRaster();
	private JPanel contentPane;
	private rasterGIS rg; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
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
	public UI() {
		//
		// INITIALIZE LOGIC COMPONENTS
		//
		rg = new rasterGIS();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\milton\\Downloads\\worldwide.png"));
		setTitle("rasterGIS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 774, 531);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.WEST);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("Content View ");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
		panel.add(lblNewLabel);
		
		view = new JPanel();
		view.setBorder(new LineBorder(new Color(0, 0, 0)));
		view.setBackground(new Color(255, 128, 64));
		contentPane.add(view, BorderLayout.CENTER);
		view.setLayout(new CardLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel_1 = new JLabel("Operation History");
		lblNewLabel_1.setFont(new Font("Leelawadee UI", Font.BOLD, 12));
		panel_1.add(lblNewLabel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBackground(Color.WHITE);
		contentPane.add(panel_2, BorderLayout.NORTH);
		
		JLabel lblNewLabel_2 = new JLabel("Zoom");
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 12));
		panel_2.add(lblNewLabel_2);
		
		JButton btnZoomIn = new JButton("+");
		btnZoomIn.setFont(new Font("SimSun", Font.PLAIN, 13));
		panel_2.add(btnZoomIn);
		
		JButton btnZoomOut = new JButton("-");
		btnZoomOut.setFont(new Font("SimSun", Font.PLAIN, 13));
		panel_2.add(btnZoomOut);
		
		JLabel lblNewLabel_3 = new JLabel("Map Algebra");
		lblNewLabel_3.setFont(new Font("Segoe UI", Font.BOLD, 12));
		panel_2.add(lblNewLabel_3);
		
		JButton btnNewButton = new JButton("Focal");
		panel_2.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Local");
		panel_2.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Zonal");
		panel_2.add(btnNewButton_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel_3, BorderLayout.SOUTH);
		
		//
		// MENU ITEMS 
		//
		//Initialize menu-bar  
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 255, 255));
		setJMenuBar(menuBar);
		
		//Create "File" menu 
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		//Add open raster to file menu 
		JMenuItem openRasterMenuItem = new JMenuItem("Open raster ...");
		openRasterMenuItem.setAction(action);
		fileMenu.add(openRasterMenuItem);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Save ...");
		fileMenu.add(mntmNewMenuItem);
		
		//Create "About" Menu 
		JMenu aboutMenu = new JMenu("About");
		menuBar.add(aboutMenu);
		
		JMenu mnNewMenu = new JMenu("Help");
		menuBar.add(mnNewMenu);
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
					Layer raster = new Layer("Layer 1",filePath); 
					greyScale(raster,2);	
					rg.layerList.add(raster);
					
					
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
			//this.getContentPane().remove();
			view.removeAll();
			view.add(map);
			this.setVisible(true);
			
		}
}
