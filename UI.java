package components;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
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
import javax.swing.JToggleButton;
import javax.swing.JTable;
import java.awt.Dimension;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UI extends JFrame {
	private int currentZoom=1; 
	private JPanel view; 
	private JPanel rasterView; 
	private final Action action = new OpenRaster();
	private final Action save = new SaveRaster();
	private JPanel contentPane;
	private rasterGIS rg; 
	private ButtonGroup btnGroupToc = new ButtonGroup(); 
	//private final Action action_zoomIn = new ZoomIn();
	//private final Action action_zoomOut = new ZoomOut();
	private JTextField consoleOutput;
	private JPanel tocPanel; 
	private JToolBar tocBar;
	private HashMap<JRadioButton,Layer> hm = new HashMap<JRadioButton,Layer>(); 
	static int x; 
	static int y; 
	private JTextField txtFR;
	private JTextField txtFG;
	private JTextField txtFB;
	private static UI frame; 
	private JTextField txtCellValue;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new UI();
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
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\milton\\Downloads\\worldwide.png"));
		setTitle("rasterGIS");
		setDefaultCloseOperation(UI.EXIT_ON_CLOSE);
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
		
		JLabel lblTableOfContents = new JLabel("   Table of Contents    ");
		lblTableOfContents.setFont(new Font("Segoe UI", Font.BOLD, 12));
		tocPanel.add(lblTableOfContents, BorderLayout.NORTH);
		
		tocBar = new JToolBar();
		tocBar.setMinimumSize(new Dimension(10, 2));
		tocBar.setMaximumSize(new Dimension(10, 2));
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
		rasterView.addMouseListener(new MouseAdapter() {
			//@Override
			public void mouseClicked(MouseEvent e) {
				int x = rasterView.getY();
				int y = rasterView.getY();
				Layer current = findSelected();
				if(x>=0 && x<current.rows) {
					if(y>=0 && y<current.columns) {
						System.out.println(String.valueOf(current.values[x*y]));
						txtCellValue.setText(String.valueOf(current.values[x*y]));
					}
				}
			}
		});
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
					greyScale(findSelected(),currentZoom);
				}
				else {
					consoleOutput.setText("No layers");
				}
			}
		});
		//btnZoomIn.setAction(action_zoomIn);
		btnZoomIn.setFont(new Font("SimSun", Font.PLAIN, 13));
		operationPanel.add(btnZoomIn);
		
		JButton btnZoomOut = new JButton("-");
		btnZoomOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rg.layerList.size()>0) {
					if(currentZoom > 1) {
						currentZoom-=1; 
						greyScale(findSelected(),currentZoom);
					}
					else 
					{
						consoleOutput.setText("Max zoom reached");
					}
				}
				else 
				{
					consoleOutput.setText("No layers");
				}
			}
		});
		//btnZoomOut.setAction(action_zoomOut);
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
		btnLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					LocalOperations lo = new LocalOperations();
					
					frame.setEnabled(false); 
					lo.setVisible(true);
				}
			}
		);
		operationPanel.add(btnLocal);
		
		JButton btnZonal = new JButton("Zonal");
		operationPanel.add(btnZonal);
		
		JPanel interactivePanel = new JPanel();
		interactivePanel.setMaximumSize(new Dimension(32767, 100));
		interactivePanel.setBackground(new Color(204, 204, 204));
		interactivePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(interactivePanel, BorderLayout.SOUTH);
		GridBagLayout gbl_interactivePanel = new GridBagLayout();
		gbl_interactivePanel.columnWidths = new int[]{113, 84, 96, 103, 90, 218, 0};
		gbl_interactivePanel.rowHeights = new int[]{21, 21, 21, 0, 8, 0};
		gbl_interactivePanel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_interactivePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		interactivePanel.setLayout(gbl_interactivePanel);
		
		JButton btnRGB = new JButton("RGB");
		btnRGB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
//				double voiInput = Double.parseDouble(txtFieldVois.getText());
//				double[] vois = voiInput.split("//s");
//				if(voiInput.length()>0) { //We need to fix good error handling here
//					consoleOutput.setText("Yes");
//					RGB(findSelected(),(double)currentZoom,vois);
//				}
				
			}
		});
		
		JLabel lblColorSettings = new JLabel("Color Settings:");
		lblColorSettings.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_lblColorSettings = new GridBagConstraints();
		gbc_lblColorSettings.fill = GridBagConstraints.VERTICAL;
		gbc_lblColorSettings.insets = new Insets(0, 0, 5, 5);
		gbc_lblColorSettings.gridx = 0;
		gbc_lblColorSettings.gridy = 0;
		interactivePanel.add(lblColorSettings, gbc_lblColorSettings);
		
		JButton btnGreyscale = new JButton("Grey");
		btnGreyscale.setHorizontalAlignment(SwingConstants.LEFT);
		btnGreyscale.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		btnGreyscale.setBackground(new Color(192, 192, 192));
		GridBagConstraints gbc_btnGreyscale = new GridBagConstraints();
		gbc_btnGreyscale.fill = GridBagConstraints.VERTICAL;
		gbc_btnGreyscale.insets = new Insets(0, 0, 5, 5);
		gbc_btnGreyscale.gridx = 1;
		gbc_btnGreyscale.gridy = 0;
		interactivePanel.add(btnGreyscale, gbc_btnGreyscale);
		btnRGB.setBackground(new Color(192, 192, 192));
		GridBagConstraints gbc_btnRGB = new GridBagConstraints();
		gbc_btnRGB.fill = GridBagConstraints.VERTICAL;
		gbc_btnRGB.insets = new Insets(0, 0, 5, 5);
		gbc_btnRGB.gridx = 2;
		gbc_btnRGB.gridy = 0;
		interactivePanel.add(btnRGB, gbc_btnRGB);
		
		JLabel label = new JLabel("");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.BOTH;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 3;
		gbc_label.gridy = 0;
		interactivePanel.add(label, gbc_label);
		
		JLabel lblVOIS = new JLabel("VOIS:");
		lblVOIS.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_lblVOIS = new GridBagConstraints();
		gbc_lblVOIS.fill = GridBagConstraints.VERTICAL;
		gbc_lblVOIS.insets = new Insets(0, 0, 5, 5);
		gbc_lblVOIS.gridx = 0;
		gbc_lblVOIS.gridy = 1;
		interactivePanel.add(lblVOIS, gbc_lblVOIS);
		
		txtFR = new JTextField();
		txtFR.setToolTipText("");
		txtFR.setColumns(10);
		GridBagConstraints gbc_txtFR = new GridBagConstraints();
		gbc_txtFR.fill = GridBagConstraints.VERTICAL;
		gbc_txtFR.insets = new Insets(0, 0, 5, 5);
		gbc_txtFR.gridx = 1;
		gbc_txtFR.gridy = 1;
		interactivePanel.add(txtFR, gbc_txtFR);
		
		JLabel label_1 = new JLabel("");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.fill = GridBagConstraints.BOTH;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 1;
		interactivePanel.add(label_1, gbc_label_1);
		
		JLabel lblCellValue = new JLabel("Cell Value:");
		lblCellValue.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_lblCellValue = new GridBagConstraints();
		gbc_lblCellValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblCellValue.gridx = 3;
		gbc_lblCellValue.gridy = 1;
		interactivePanel.add(lblCellValue, gbc_lblCellValue);
		
		JLabel lblTerminal = new JLabel("Terminal");
		lblTerminal.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_lblTerminal = new GridBagConstraints();
		gbc_lblTerminal.fill = GridBagConstraints.BOTH;
		gbc_lblTerminal.insets = new Insets(0, 0, 5, 0);
		gbc_lblTerminal.gridx = 5;
		gbc_lblTerminal.gridy = 1;
		interactivePanel.add(lblTerminal, gbc_lblTerminal);
		
		JLabel label_2 = new JLabel("");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.fill = GridBagConstraints.BOTH;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 2;
		interactivePanel.add(label_2, gbc_label_2);
		
		txtFG = new JTextField();
		txtFG.setColumns(10);
		GridBagConstraints gbc_txtFG = new GridBagConstraints();
		gbc_txtFG.fill = GridBagConstraints.BOTH;
		gbc_txtFG.insets = new Insets(0, 0, 5, 5);
		gbc_txtFG.gridx = 1;
		gbc_txtFG.gridy = 2;
		interactivePanel.add(txtFG, gbc_txtFG);
		
		JLabel label_3 = new JLabel("");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.anchor = GridBagConstraints.EAST;
		gbc_label_3.fill = GridBagConstraints.VERTICAL;
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 2;
		gbc_label_3.gridy = 2;
		interactivePanel.add(label_3, gbc_label_3);
		
		txtCellValue = new JTextField();
		txtCellValue.setFont(new Font("Tahoma", Font.BOLD, 10));
		txtCellValue.setForeground(Color.BLUE);
		txtCellValue.setEditable(false);
		GridBagConstraints gbc_txtCellValue = new GridBagConstraints();
		gbc_txtCellValue.insets = new Insets(0, 0, 5, 5);
		gbc_txtCellValue.gridx = 3;
		gbc_txtCellValue.gridy = 2;
		interactivePanel.add(txtCellValue, gbc_txtCellValue);
		txtCellValue.setColumns(10);
		
		consoleOutput = new JTextField();
		consoleOutput.setForeground(Color.GREEN);
		consoleOutput.setCaretColor(Color.BLACK);
		consoleOutput.setBackground(new Color(0, 0, 0));
		consoleOutput.setEditable(false);
		consoleOutput.setColumns(10);
		GridBagConstraints gbc_consoleOutput = new GridBagConstraints();
		gbc_consoleOutput.gridheight = 2;
		gbc_consoleOutput.fill = GridBagConstraints.BOTH;
		gbc_consoleOutput.insets = new Insets(0, 0, 5, 0);
		gbc_consoleOutput.gridx = 5;
		gbc_consoleOutput.gridy = 2;
		interactivePanel.add(consoleOutput, gbc_consoleOutput);
		
		txtFB = new JTextField();
		txtFB.setColumns(10);
		GridBagConstraints gbc_txtFB = new GridBagConstraints();
		gbc_txtFB.fill = GridBagConstraints.BOTH;
		gbc_txtFB.insets = new Insets(0, 0, 5, 5);
		gbc_txtFB.gridx = 1;
		gbc_txtFB.gridy = 3;
		interactivePanel.add(txtFB, gbc_txtFB);
		
		JLabel label_4 = new JLabel("");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.fill = GridBagConstraints.BOTH;
		gbc_label_4.insets = new Insets(0, 0, 0, 5);
		gbc_label_4.gridx = 0;
		gbc_label_4.gridy = 4;
		interactivePanel.add(label_4, gbc_label_4);
		
		JLabel label_5 = new JLabel("");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.fill = GridBagConstraints.BOTH;
		gbc_label_5.insets = new Insets(0, 0, 0, 5);
		gbc_label_5.gridx = 2;
		gbc_label_5.gridy = 4;
		interactivePanel.add(label_5, gbc_label_5);
		
		JLabel label_6 = new JLabel("");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.insets = new Insets(0, 0, 0, 5);
		gbc_label_6.fill = GridBagConstraints.BOTH;
		gbc_label_6.gridx = 3;
		gbc_label_6.gridy = 4;
		interactivePanel.add(label_6, gbc_label_6);
		
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
		
		JMenuItem mntmSave = new JMenuItem("Save ...");
		mntmSave.setAction(save);
		fileMenu.add(mntmSave);
		
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
					
					String[] newName = name.split("[.]"); // Very ugly solution but I cant be bothered
					name = newName[0].toString(); 
					
					if(name.length()>12) {
						name = name.substring(0,12); 
					}
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
					rdbtn.setBackground(Color.white);
					tocBar.add(rdbtn,gbc_rdbtn);
					hm.put(rdbtn, raster); 
					rdbtn.setSelected(true);
					
				}
				//SelectFile fileSelection = new SelectFile();
				//fileSelection.FileScreen();
				//fileName = fileSelection.fileName;  
				
			}
		}
		
		public class SaveRaster extends AbstractAction{
			
			public SaveRaster() {
				putValue(NAME, "Save Raster ... "); 
				putValue(SHORT_DESCRIPTION,"Save file"); 
			}
			public void actionPerformed(ActionEvent e) {
				Layer outputSave = findSelected();
				outputSave.save("test.txt");
			}
		}
		
		//Public GUI functions 
		
		//Visualize raster in greyscale
		public void greyScale(Layer input,double dScale){
			int scale = (int)Math.round(dScale);
			BufferedImage image = input.toImage();
			MapPanel map  = new MapPanel(image,scale);
			map.setBackground(UIManager.getColor("Button.light"));
			rasterView.add(map);
			x = rasterView.getWidth()/2;
			y = rasterView.getHeight()/2;
			map.revalidate();
			map.repaint();
		}
		
		public void RGB(Layer input, double dScale,double[] voi){
			int scale = (int)Math.round(dScale);
			BufferedImage image = input.toImage(voi);
			MapPanel map  = new MapPanel(image,scale);
			map.setBackground(UIManager.getColor("Button.light"));
			rasterView.add(map);
			x = rasterView.getWidth()/2;
			y = rasterView.getHeight()/2;
			map.revalidate();
			map.repaint();
		}
		
		private Layer findSelected() {
			JRadioButton currentButton = null; 
			//This code was found on stack overflow https://stackoverflow.com/questions/201287/how-do-i-get-which-jradiobutton-is-selected-from-a-buttongroup/13232816#13232816
			for(Enumeration<AbstractButton> b = btnGroupToc.getElements(); b.hasMoreElements();) {
				AbstractButton cb = b.nextElement();
				if(cb.isSelected()) {
					currentButton = (JRadioButton) cb;
					return hm.get(currentButton); 
				}
			}
			return null;
		}
		
		private Layer loadRaster() {
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
				return raster;
			}else {
				return null;
			}
		}
}
