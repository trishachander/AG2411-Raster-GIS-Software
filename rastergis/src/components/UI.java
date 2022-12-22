package components;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

//import components.UI.OpenWebHelp;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.MouseInfo;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import java.awt.ComponentOrientation;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Toolkit;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseMotionAdapter;


public class UI extends JFrame {
	static int currentZoom=1; 
	private JPanel view; 
	private static JPanel rasterView; 
	private final Action action = new OpenRaster();
	private final Action save = new SaveRaster();
	private final Action web_help = new OpenWebHelp();
	private JPanel contentPane;
	static ButtonGroup btnGroupToc = new ButtonGroup(); 
	private JTextField consoleOutput;
	private JPanel tocPanel; 
	static JToolBar tocBar;
	static HashMap<JRadioButton,Layer> hm = new HashMap<JRadioButton,Layer>(); 
	static int x; 
	static int y; 
	private JTextField txtFR;
	private JTextField txtFG;
	private JTextField txtFB; 
	private JTextField txtCellValue;
	static JScrollBar scrollBarVertical;
	static JScrollBar scrollBarHorizontal;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI uiFrame = new UI();
					uiFrame.setVisible(true);
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
		// INITIALIZE WINDOW 
		// 
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\milton\\Downloads\\enzyme.png"));
		setTitle("kevlarGIS");
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
		rasterView.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int xv = (e.getX()-MapPanel.x)/currentZoom;
				int yv = (e.getY()-MapPanel.y)/currentZoom;
				
				if(!hm.isEmpty()) {
					Layer current = findSelected();
					if(xv>=0 && xv<current.nRows) {
						if(yv>=0 && yv<current.nCols) {
							//System.out.println("X: "+xv+" Y: "+yv);
							//System.out.println(String.valueOf(current.values[yv][xv]));
							txtCellValue.setText(String.valueOf(current.values[yv][xv]));
						}
						else {
							txtCellValue.setText("");
						}
					}
					else {
						txtCellValue.setText("");
					}
				}
			}
		});
		rasterView.setBackground(new Color(255, 255, 255));
		rasterView.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				int scroll = e.getWheelRotation();
				switch (scroll) {
				case -1:
					if(hm.size()>0) {
						currentZoom+=1; 
						greyScale(findSelected(),currentZoom);
					}
					else {
						consoleOutput.setText("No layers");
					}
					break;
				
				case 1:
					if(hm.size()>0) {
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

			}
		});
		rasterView.addMouseListener(new MouseAdapter() {
			//@Override
			public void mouseClicked(MouseEvent e) {
				//System.out.println("t");
				int xv = e.getX()-MapPanel.x;
				int yv = e.getY()-MapPanel.y;
				//System.out.println("X: "+xv+" Y: "+yv);
				if(!hm.isEmpty()) {
					Layer current = findSelected();
					if(xv>=0 && xv<current.nRows) {
						if(yv>=0 && yv<current.nCols) {
							//System.out.println(String.valueOf(current.values[xv][yv]));
							txtCellValue.setText(String.valueOf(current.values[xv][yv]));
						}
						else {
							consoleOutput.setText("Clicked outside layer");
						}
					}
					else {
						consoleOutput.setText("Clicked outside layer");
					}
				}
			}
		});
		x = rasterView.getWidth();
		y = rasterView.getHeight();
		rasterView.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		rasterView.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_rasterView = new GridBagConstraints();
		gbc_rasterView.fill = GridBagConstraints.BOTH;
		gbc_rasterView.insets = new Insets(0, 0, 5, 5);
		gbc_rasterView.gridx = 0;
		gbc_rasterView.gridy = 0;
		view.add(rasterView, gbc_rasterView);
		rasterView.setLayout(new BorderLayout(0, 0));
		
		scrollBarVertical = new JScrollBar();
		scrollBarVertical.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if(!hm.isEmpty()) {
					greyScale(findSelected(),currentZoom);
				}
				
			}
		});
		
		scrollBarVertical.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!hm.isEmpty()) {
					greyScale(findSelected(),currentZoom);
				}
			}
		});
		scrollBarVertical.setMinimum(-100);
		scrollBarVertical.setBackground(new Color(255, 255, 255));
		scrollBarVertical.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		scrollBarVertical.setUnitIncrement(10);
		GridBagConstraints gbc_scrollBarVertical = new GridBagConstraints();
		gbc_scrollBarVertical.insets = new Insets(0, 0, 5, 0);
		gbc_scrollBarVertical.fill = GridBagConstraints.BOTH;
		gbc_scrollBarVertical.gridx = 1;
		gbc_scrollBarVertical.gridy = 0;
		view.add(scrollBarVertical, gbc_scrollBarVertical);
		
		scrollBarHorizontal = new JScrollBar();
		scrollBarHorizontal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!hm.isEmpty()) {
					greyScale(findSelected(),currentZoom);
				}
			}
		});
		scrollBarHorizontal.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if(!hm.isEmpty()) {
					greyScale(findSelected(),currentZoom);
				}
			}
		});
		scrollBarHorizontal.setForeground(Color.LIGHT_GRAY);
		scrollBarHorizontal.setMinimum(-100);
		scrollBarHorizontal.setBackground(new Color(255, 255, 255));
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
		operationPanel.setBackground(new Color(255, 255, 204));
		contentPane.add(operationPanel, BorderLayout.NORTH);
		
		JLabel lblZoom = new JLabel("Zoom");
		lblZoom.setFont(new Font("Segoe UI", Font.BOLD, 12));
		operationPanel.add(lblZoom);
		
		JButton btnZoomIn = new JButton("+");
		btnZoomIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(hm.size()>0) {
					currentZoom+=1; 
					greyScale(findSelected(),currentZoom);
				}
				else {
					consoleOutput.setText("No layers");
				}
			}
		});

		btnZoomIn.setFont(new Font("SimSun", Font.PLAIN, 13));
		operationPanel.add(btnZoomIn);
		
		JButton btnZoomOut = new JButton("-");
		btnZoomOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(hm.size()>0) {
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

		btnZoomOut.setFont(new Font("SimSun", Font.PLAIN, 13));
		operationPanel.add(btnZoomOut);
		
		JLabel lblMapAlgebra = new JLabel("Map Algebra");
		lblMapAlgebra.setFont(new Font("Segoe UI", Font.BOLD, 12));
		operationPanel.add(lblMapAlgebra);
		
		JButton btnFocal = new JButton("Focal");
		btnFocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FocalOperations fo = new FocalOperations();					
				fo.setVisible(true);
			}
		});
		operationPanel.add(btnFocal);
		
		JButton btnLocal = new JButton("Local");
		btnLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					LocalOperations lo = new LocalOperations();					
					lo.setVisible(true);
					
				}
			}
		);
		operationPanel.add(btnLocal);
		
		JButton btnZonal = new JButton("Zonal");
		btnZonal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ZonalOperations zo = new ZonalOperations();
				zo.setVisible(true);
			}
		});
		operationPanel.add(btnZonal);
		
		JPanel interactivePanel = new JPanel();
		interactivePanel.setMaximumSize(new Dimension(32767, 100));
		interactivePanel.setBackground(new Color(0, 204, 204));
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
				if(hm.size()>0) {
					String text1 = txtFR.getText();
					String text2 = txtFG.getText();
					String text3 = txtFB.getText();
					int counter = 0;
					int otherInput = 0;
					ArrayList<Double> numberList = new ArrayList<Double>();
				try {
					double n1 = Double.parseDouble(text1);
					counter+=1;
					numberList.add(n1);
					
				} catch (NumberFormatException nfe) {
					if (text1.length() > 0) {
						otherInput = 1;
						}
					}
				try {
					double n2 = Double.parseDouble(text2);
					counter+=1;
					numberList.add(n2);
					
				} catch (NumberFormatException nfe) {
					if (text2.length() > 0) {
						otherInput = 1;
						}
					}
				try {
					double n3 = Double.parseDouble(text3);
					counter+=1;
					numberList.add(n3);
					
				} catch (NumberFormatException nfe) {
					if (text3.length() > 0) {
						otherInput = 1;
						}
					}
				if (counter > 0 && otherInput == 0) {
					double[] vois = new double[counter];
					for (int i=0;i<counter;i++) {
						vois[i]=numberList.get(i);
					}
					RGB(findSelected(),(double)currentZoom,vois);
				}
				if (text1.length() == 0 && text2.length() == 0 && text3.length() == 0) {
					consoleOutput.setText("No values given");
					}
				if (otherInput == 1) {
					consoleOutput.setText("Inputs need to be numbers");
					}
				}
			else {
				consoleOutput.setText("No layers");
				}
			}
		});
				/*try {	
					double n1 = Double.parseDouble(text1);
					double n2 = Double.parseDouble(text2);
					double n3 = Double.parseDouble(text3);
					
					double[] vois = new double[3];
					vois[0]=n1;
					vois[1]=n2;
					vois[2]=n3;
					
					RGB(findSelected(),(double)currentZoom,vois);
				} catch (NumberFormatException nfe) {
					if (text1.length() > 0 && text2.length() > 0 && text3.length() > 0) {
						consoleOutput.setText("Inputs need to be numbers");
					}
					else {
						consoleOutput.setText("Three values needed");
						}
					}
				}
				else
					consoleOutput.setText("No layers");
			}
		});*/
		
		JLabel lblColorSettings = new JLabel("Color Settings:");
		lblColorSettings.setFont(new Font("Tahoma", Font.BOLD, 10));
		GridBagConstraints gbc_lblColorSettings = new GridBagConstraints();
		gbc_lblColorSettings.fill = GridBagConstraints.VERTICAL;
		gbc_lblColorSettings.insets = new Insets(0, 0, 5, 5);
		gbc_lblColorSettings.gridx = 0;
		gbc_lblColorSettings.gridy = 0;
		interactivePanel.add(lblColorSettings, gbc_lblColorSettings);
		
		JButton btnGreyscale = new JButton("Grey");
		btnGreyscale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(hm.size()>0) {
					greyScale(findSelected(),currentZoom);
				}
				else {
					consoleOutput.setText("No layers");
				}	
			}
		});
		btnGreyscale.setHorizontalAlignment(SwingConstants.LEFT);
		btnGreyscale.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		btnGreyscale.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_btnGreyscale = new GridBagConstraints();
		gbc_btnGreyscale.fill = GridBagConstraints.VERTICAL;
		gbc_btnGreyscale.insets = new Insets(0, 0, 5, 5);
		gbc_btnGreyscale.gridx = 1;
		gbc_btnGreyscale.gridy = 0;
		interactivePanel.add(btnGreyscale, gbc_btnGreyscale);
		btnRGB.setBackground(new Color(255, 255, 255));
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
		
		txtFG = new JTextField();
		txtFG.setColumns(10);
		GridBagConstraints gbc_txtFG = new GridBagConstraints();
		gbc_txtFG.fill = GridBagConstraints.BOTH;
		gbc_txtFG.insets = new Insets(0, 0, 5, 5);
		gbc_txtFG.gridx = 1;
		gbc_txtFG.gridy = 2;
		interactivePanel.add(txtFG, gbc_txtFG);
		
		txtCellValue = new JTextField();
		txtCellValue.setBackground(new Color(255, 255, 255));
		txtCellValue.setFont(new Font("Tahoma", Font.BOLD, 10));
		txtCellValue.setForeground(new Color(0, 0, 0));
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
//		JMenu aboutMenu = new JMenu("About");
//		menuBar.add(aboutMenu);
		
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		JMenuItem webHelp = new JMenuItem("Web Help");
		webHelp.setAction(web_help);
		helpMenu.add(webHelp);
		
	}
	
	//Action - Run when opening a raster from file -> open raster 
		private class OpenRaster extends AbstractAction {
			
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
					gbc_rdbtn.gridy = hm.size()+1;
					btnGroupToc.add(rdbtn);
					rdbtn.setBackground(Color.white);
					tocBar.add(rdbtn,gbc_rdbtn);
					hm.put(rdbtn, raster); 
					rdbtn.setSelected(true);
					
				}
			}
		}
		
		private class OpenWebHelp extends AbstractAction {
			//private String fileName; 
			
			public OpenWebHelp() {
				
				putValue(NAME, "Open Web Help ...");
				putValue(SHORT_DESCRIPTION, "Opens web help");
			}
			public static boolean openWebpage(URI uri) {
		        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
		            try {
		                desktop.browse(uri);
		                return true;
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
		        return false;
		    }
			public void actionPerformed(ActionEvent e) {
				
				URI link;
				try {
	                link = new URI("https://trishachander01.wixsite.com/rastergis");
	                openWebpage(link);

	            } catch (URISyntaxException e1) {
	                // TODO Add dialog saying something is wrong
	                e1.printStackTrace();
	            }
			}
				
		}
		public class SaveRaster extends AbstractAction{
			
			public SaveRaster() {
				putValue(NAME, "Save Raster ... "); 
				putValue(SHORT_DESCRIPTION,"Save file"); 
			}
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");   

				int userSelection = fileChooser.showSaveDialog(null);
				
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				Layer outputSave = findSelected();
				File fileToSave = fileChooser.getSelectedFile();
				String fileName = fileToSave.getName();
				outputSave.save(fileName+".txt");
				}
			}
		}
		
		//Public GUI functions 
		
		//Visualize raster in grey-scale
		public static void greyScale(Layer input,double dScale){
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