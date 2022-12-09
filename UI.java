package components;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

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
import javax.swing.ButtonGroup;
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
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import java.awt.Canvas;
import javax.swing.JScrollBar;
import java.awt.Cursor;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;

public class UI extends JFrame {
	private int currentZoom=1; 
	private JPanel view; 
	private JPanel rasterView; 
	private final Action action = new OpenRaster();
	private JPanel contentPane;
	private rasterGIS rg; 
	ButtonGroup btnGroupToc = new ButtonGroup(); 
	//private final Action action_zoomIn = new ZoomIn();
	private final Action action_zoomOut = new ZoomOut();
	private JTextField consoleOutput;
	private JPanel tocPanel; 
	private JToolBar tocBar;
	private HashMap<JRadioButton,Layer> hm = new HashMap<JRadioButton,Layer>(); 
	static int x; 
	static int y; 

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
		
		//
		// INITIALIZE WINDOW 
		// 
		//setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\milton\\Downloads\\worldwide.png"));
		setTitle("rasterGIS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 774, 531);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		// SET CONTENT PANE
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		tocPanel = new JPanel();
		tocPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		tocPanel.setBackground(Color.WHITE);
		contentPane.add(tocPanel, BorderLayout.WEST);
		tocPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTableOfContents = new JLabel("Table Of Contents ");
		lblTableOfContents.setFont(new Font("Segoe UI", Font.BOLD, 12));
		tocPanel.add(lblTableOfContents, BorderLayout.NORTH);
		
		tocBar = new JToolBar();
		tocBar.setBackground(new Color(255, 255, 255));
		tocBar.setOrientation(SwingConstants.VERTICAL);
		tocBar.setFloatable(false);
		tocPanel.add(tocBar, BorderLayout.CENTER);
		
		view = new JPanel();
		view.setBorder(new LineBorder(new Color(0, 0, 0)));
		view.setBackground(new Color(255, 255, 255));
		contentPane.add(view, BorderLayout.CENTER);
		GridBagLayout gbl_view = new GridBagLayout();
		gbl_view.columnWidths = new int[]{0, 0, 0};
		gbl_view.rowHeights = new int[]{0, 0, 0};
		gbl_view.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_view.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		view.setLayout(gbl_view);
		
		rasterView = new JPanel();
//		x = rasterView.getWidth();
//		y = rasterView.getHeight();
		rasterView.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		rasterView.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_rasterView = new GridBagConstraints();
		gbc_rasterView.fill = GridBagConstraints.BOTH;
		gbc_rasterView.insets = new Insets(0, 0, 5, 5);
		gbc_rasterView.gridx = 0;
		gbc_rasterView.gridy = 0;
		view.add(rasterView, gbc_rasterView);
		rasterView.setLayout(new BorderLayout(0, 0));
		
		JScrollBar scrollBarVertical = new JScrollBar();
		scrollBarVertical.setBackground(new Color(255, 255, 255));
		scrollBarVertical.setValue(50);
		scrollBarVertical.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		scrollBarVertical.setUnitIncrement(10);
		GridBagConstraints gbc_scrollBarVertical = new GridBagConstraints();
		gbc_scrollBarVertical.insets = new Insets(0, 0, 5, 0);
		gbc_scrollBarVertical.fill = GridBagConstraints.BOTH;
		gbc_scrollBarVertical.gridx = 1;
		gbc_scrollBarVertical.gridy = 0;
		view.add(scrollBarVertical, gbc_scrollBarVertical);
		
		JScrollBar scrollBarHorizontal = new JScrollBar();
		scrollBarHorizontal.setBackground(new Color(255, 255, 255));
		scrollBarHorizontal.setValue(50);
		scrollBarHorizontal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		scrollBarHorizontal.setOrientation(JScrollBar.HORIZONTAL);
		GridBagConstraints gbc_scrollBarHorizontal = new GridBagConstraints();
		gbc_scrollBarHorizontal.fill = GridBagConstraints.HORIZONTAL;
		gbc_scrollBarHorizontal.insets = new Insets(0, 0, 0, 5);
		gbc_scrollBarHorizontal.gridx = 0;
		gbc_scrollBarHorizontal.gridy = 1;
		view.add(scrollBarHorizontal, gbc_scrollBarHorizontal);
		
		JPanel historyPanel = new JPanel();
		historyPanel.setBackground(Color.WHITE);
		historyPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(historyPanel, BorderLayout.EAST);
		historyPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblHistory = new JLabel("Operation History");
		lblHistory.setFont(new Font("Leelawadee UI", Font.BOLD, 12));
		historyPanel.add(lblHistory);
		
		JPanel operationPanel = new JPanel();
		operationPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		operationPanel.setBackground(Color.WHITE);
		contentPane.add(operationPanel, BorderLayout.NORTH);
		
		JLabel lblZoom = new JLabel("Zoom");
		lblZoom.setFont(new Font("Segoe UI", Font.BOLD, 12));
		operationPanel.add(lblZoom);
		
		JButton btnZoomIn = new JButton("+");
		btnZoomIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rg.layerList.size()>0) {
					currentZoom+=1; 
					greyScale(rg.layerList.get(rg.layerList.size()-1),currentZoom);
					System.out.println(rg.layerList.size()); 
				}
				else {
					System.out.println("Error: You can not zoom as you have no layers");
				}
			}
		});
		//btnZoomIn.setAction(action_zoomIn);
		btnZoomIn.setFont(new Font("SimSun", Font.PLAIN, 13));
		operationPanel.add(btnZoomIn);
		
		JButton btnZoomOut = new JButton("-");
		btnZoomOut.setAction(action_zoomOut);
		btnZoomOut.setFont(new Font("SimSun", Font.PLAIN, 13));
		operationPanel.add(btnZoomOut);
		
		JLabel lblMapAlgebra = new JLabel("Map Algebra");
		lblMapAlgebra.setFont(new Font("Segoe UI", Font.BOLD, 12));
		operationPanel.add(lblMapAlgebra);
		
		JButton btnFocal = new JButton("Focal");
		btnFocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		operationPanel.add(btnFocal);
		
		JButton btnLocal = new JButton("Local");
		operationPanel.add(btnLocal);
		
		JButton btnZonal = new JButton("Zonal");
		operationPanel.add(btnZonal);
		
		JPanel interactivePanel = new JPanel();
		interactivePanel.setBackground(new Color(204, 204, 204));
		interactivePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(interactivePanel, BorderLayout.SOUTH);
		
		JLabel lblColorSettings = new JLabel("Color Settings");
		lblColorSettings.setFont(new Font("Segoe UI", Font.BOLD, 12));
		
		JLabel lblNewLabel_5 = new JLabel("Cell Value");
		
		consoleOutput = new JTextField();
		consoleOutput.setEditable(false);
		consoleOutput.setColumns(10);
		GroupLayout gl_interactivePanel = new GroupLayout(interactivePanel);
		gl_interactivePanel.setHorizontalGroup(
			gl_interactivePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_interactivePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblColorSettings)
					.addGap(296)
					.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
					.addGap(64)
					.addComponent(consoleOutput, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
		);
		gl_interactivePanel.setVerticalGroup(
			gl_interactivePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_interactivePanel.createSequentialGroup()
					.addGap(2)
					.addGroup(gl_interactivePanel.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(lblNewLabel_5)
						.addComponent(lblColorSettings)
						.addComponent(consoleOutput, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		interactivePanel.setLayout(gl_interactivePanel);
		
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
					String name = selectedFile.getName(); 
					Layer raster = new Layer(name,filePath); 
					greyScale(raster,currentZoom);	
					rg.layerList.add(raster);
					
					JRadioButton rdbtn = new JRadioButton(name);
					rdbtn.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							Layer current = hm.get(rdbtn); 
							greyScale(current,currentZoom); 
						}
					});
						
					GridBagConstraints gbc_rdbtn = new GridBagConstraints();
					gbc_rdbtn.gridx = 0;
					gbc_rdbtn.gridy = rg.layerList.size()+1;
					btnGroupToc.add(rdbtn);
					tocBar.add(rdbtn,gbc_rdbtn);
					hm.put(rdbtn, raster); 
					
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
			//rasterView.remove(map);
			rasterView.add(map);
			x = rasterView.getWidth()/2;
			y = rasterView.getHeight()/2;
//			map.setSize(rasterView.getSize().width/2,rasterView.getSize().height/2);
			map.revalidate();
			map.repaint();
			
			
		}
	//private class ZoomIn extends AbstractAction {
	//	public ZoomIn() {
	//		putValue(NAME, "+");
	//		putValue(SHORT_DESCRIPTION, "Zoom in");
	//	}
	//	public void actionPerformed(ActionEvent e) {

	//	}
	//}
	private class ZoomOut extends AbstractAction {
		public ZoomOut() {
			putValue(NAME, "-");
			putValue(SHORT_DESCRIPTION, "Zoom out");
		}
		public void actionPerformed(ActionEvent e) {
			if(rg.layerList.size()>0 && currentZoom > 1) {
				currentZoom-=1; 
				greyScale(rg.layerList.get(rg.layerList.size()-1),currentZoom);
			}
			else {
				consoleOutput.setText("No layers");
			}
		}
	}
}
