package components;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton; 

public class ZonalOperations extends JFrame {
	private JPanel contentPane;
	private ButtonGroup buttonGroup;
	private Layer raster1=null;
	private Layer raster2=null; 
	private Layer result;
	
	

	/**
	 * Launch the application.
	 */
	public static void main() {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LocalOperations frame = new LocalOperations();
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
	public ZonalOperations() {
		
		super();
		setResizable(false);
		setTitle("Conduct Zonal Operation");
		setDefaultCloseOperation(LocalOperations.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{232, 188, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 13, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblRaster1 = new JLabel("Select Value Raster : ");
		lblRaster1.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblRaster1 = new GridBagConstraints();
		gbc_lblRaster1.insets = new Insets(0, 0, 5, 5);
		gbc_lblRaster1.fill = GridBagConstraints.VERTICAL;
		gbc_lblRaster1.gridx = 0;
		gbc_lblRaster1.gridy = 0;
		contentPane.add(lblRaster1, gbc_lblRaster1);
		
		JButton btnOpenR1 = new JButton("Open file ...");
		btnOpenR1.addActionListener(new ActionListener() {
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
					btnOpenR1.setText(name);
					lblRaster1.setText("[Value Raster has been selected]");
					raster1 = new Layer(name,filePath); 
				}
			}
		});
		GridBagConstraints gbc_btnOpenR1 = new GridBagConstraints();
		gbc_btnOpenR1.insets = new Insets(0, 0, 5, 0);
		gbc_btnOpenR1.gridx = 1;
		gbc_btnOpenR1.gridy = 0;
		contentPane.add(btnOpenR1, gbc_btnOpenR1);
		
		JLabel lblRaster2 = new JLabel("Select Zone Raster : ");
		lblRaster2.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblRaster2 = new GridBagConstraints();
		gbc_lblRaster2.insets = new Insets(0, 0, 5, 5);
		gbc_lblRaster2.gridx = 0;
		gbc_lblRaster2.gridy = 2;
		contentPane.add(lblRaster2, gbc_lblRaster2);
		
		JButton btnOpenR2 = new JButton("Open file ...");
		btnOpenR2.addActionListener(new ActionListener() {
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
					btnOpenR2.setText(name);
					lblRaster2.setText("[Zone Raster has been selected]");
					raster2 = new Layer(name,filePath); 
				}
			}
		});
		GridBagConstraints gbc_btnOpenR2 = new GridBagConstraints();
		gbc_btnOpenR2.insets = new Insets(0, 0, 5, 0);
		gbc_btnOpenR2.gridx = 1;
		gbc_btnOpenR2.gridy = 2;
		contentPane.add(btnOpenR2, gbc_btnOpenR2);
		
		JLabel lblSelectLocalOperation = new JLabel("Select Zonal Operation :");
		lblSelectLocalOperation.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblSelectLocalOperation = new GridBagConstraints();
		gbc_lblSelectLocalOperation.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectLocalOperation.gridx = 0;
		gbc_lblSelectLocalOperation.gridy = 4;
		contentPane.add(lblSelectLocalOperation, gbc_lblSelectLocalOperation);
		
		buttonGroup = new ButtonGroup();
		JToggleButton tbMin = new JToggleButton("Minimum");
		GridBagConstraints gbc_tbMin = new GridBagConstraints();
		gbc_tbMin.fill = GridBagConstraints.HORIZONTAL;
		gbc_tbMin.insets = new Insets(0, 0, 5, 5);
		gbc_tbMin.gridx = 0;
		gbc_tbMin.gridy = 5;
		buttonGroup.add(tbMin);
		contentPane.add(tbMin, gbc_tbMin);
		
		JToggleButton tbMax = new JToggleButton("Maximum");
		GridBagConstraints gbc_tbMax = new GridBagConstraints();
		gbc_tbMax.fill = GridBagConstraints.HORIZONTAL;
		gbc_tbMax.insets = new Insets(0, 0, 5, 5);
		gbc_tbMax.gridx = 0;
		gbc_tbMax.gridy = 6;
		buttonGroup.add(tbMax);
		contentPane.add(tbMax, gbc_tbMax);
		
		JToggleButton tbMean = new JToggleButton("Mean");
		GridBagConstraints gbc_tbMean = new GridBagConstraints();
		gbc_tbMean.fill = GridBagConstraints.HORIZONTAL;
		gbc_tbMean.insets = new Insets(0, 0, 5, 5);
		gbc_tbMean.gridx = 0;
		gbc_tbMean.gridy = 7;
		buttonGroup.add(tbMean);
		contentPane.add(tbMean, gbc_tbMean);
		
		JToggleButton tbMajority = new JToggleButton("Majority");
		GridBagConstraints gbc_tbMajority = new GridBagConstraints();
		gbc_tbMajority.fill = GridBagConstraints.HORIZONTAL;
		gbc_tbMajority.insets = new Insets(0, 0, 0, 5);
		gbc_tbMajority.gridx = 0;
		gbc_tbMajority.gridy = 8;
		buttonGroup.add(tbMajority);
		contentPane.add(tbMajority, gbc_tbMajority);
		
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(raster1!=null && raster2!=null) {
					String operation = findSelected();
					if(operation!=null) {
						JRadioButton rdbtn;
						GridBagConstraints gbc_rdbtn;
						switch(operation) {
						
						case "Minimum":
							if (raster1.nRows == raster2.nRows && raster1.nCols == raster2.nCols) {
								result=raster1.zonalMinimum(raster2, "Min");	
							
								rdbtn = new JRadioButton("ZonalMin");
								rdbtn.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent e) {
										Layer current = UI.hm.get(rdbtn); 
										UI.greyScale(current,UI.currentZoom); 
									}
									
								
								});
								gbc_rdbtn = new GridBagConstraints();
								gbc_rdbtn.gridx = 0;
								gbc_rdbtn.gridy = UI.hm.size()+1;
								UI.btnGroupToc.add(rdbtn);
								rdbtn.setBackground(Color.white);
								UI.tocBar.add(rdbtn,gbc_rdbtn);
								UI.hm.put(rdbtn, result); 
								rdbtn.setSelected(true);
								UI.greyScale(result, UI.currentZoom);
								dispose();
							}
							else {
								JOptionPane.showMessageDialog(null,"Rasters must be of the same size");
							}
							break;
						
						case "Maximum":
							if (raster1.nRows == raster2.nRows && raster1.nCols == raster2.nCols) {
								result=raster1.zonalMaximum(raster2, "Min");	
							
								rdbtn = new JRadioButton("ZonalMax");
								rdbtn.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent e) {
										Layer current = UI.hm.get(rdbtn); 
										UI.greyScale(current,UI.currentZoom); 
									}
								});
								gbc_rdbtn = new GridBagConstraints();
								gbc_rdbtn.gridx = 0;
								gbc_rdbtn.gridy = UI.hm.size()+1;
								UI.btnGroupToc.add(rdbtn);
								rdbtn.setBackground(Color.white);
								UI.tocBar.add(rdbtn,gbc_rdbtn);
								UI.hm.put(rdbtn, result); 
								rdbtn.setSelected(true);
								UI.greyScale(result, UI.currentZoom);
								dispose();
								}
							else {
								JOptionPane.showMessageDialog(null,"Rasters must be of the same size");
							}
							break;
						

						case "Mean":
							if (raster1.nRows == raster2.nRows && raster1.nCols == raster2.nCols) {
								result=raster1.zonalMean(raster2, "Mean");	
							
								rdbtn= new JRadioButton("ZonalMean");
								rdbtn.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent e) {
										Layer current = UI.hm.get(rdbtn); 
										UI.greyScale(current,UI.currentZoom); 
									}
								});
								gbc_rdbtn= new GridBagConstraints();
								gbc_rdbtn.gridx = 0;
								gbc_rdbtn.gridy = UI.hm.size()+1;
								UI.btnGroupToc.add(rdbtn);
								rdbtn.setBackground(Color.white);
								UI.tocBar.add(rdbtn,gbc_rdbtn);
								UI.hm.put(rdbtn, result); 
								rdbtn.setSelected(true);
								UI.greyScale(result, UI.currentZoom);
								dispose();
							}
							else {
								JOptionPane.showMessageDialog(null,"Rasters must be of the same size");
							}
							break;
						
						
						case "Majority":
							if (raster1.nRows == raster2.nRows && raster1.nCols == raster2.nCols) {
								result=raster1.zonalMajority(raster2, "Majority");	
							
								rdbtn= new JRadioButton("ZonMajor");
								rdbtn.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent e) {
										Layer current = UI.hm.get(rdbtn); 
										UI.greyScale(current,UI.currentZoom); 
									}
								});
								gbc_rdbtn= new GridBagConstraints();
								gbc_rdbtn.gridx = 0;
								gbc_rdbtn.gridy = UI.hm.size()+1;
								UI.btnGroupToc.add(rdbtn);
								rdbtn.setBackground(Color.white);
								UI.tocBar.add(rdbtn,gbc_rdbtn);
								UI.hm.put(rdbtn, result); 
								rdbtn.setSelected(true);
								UI.greyScale(result, UI.currentZoom);
								dispose();
							}
							else {
								JOptionPane.showMessageDialog(null,"Rasters must be of the same size");
							}
							break;
						}						
					}
					else 
					{
						JOptionPane.showMessageDialog(null,"Please select an operation");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Please select 2 raster files");
				}
			}
		});
		
				GridBagConstraints gbc_btnOK = new GridBagConstraints();
				gbc_btnOK.insets = new Insets(0, 0, 5, 0);
				gbc_btnOK.gridx = 1;
				gbc_btnOK.gridy = 6;
				contentPane.add(btnOK, gbc_btnOK);
		


	}
	
	@SuppressWarnings("deprecation")
	private String findSelected() {
		JToggleButton currentButton = null; 
		//This code was found on stack overflow https://stackoverflow.com/questions/201287/how-do-i-get-which-jradiobutton-is-selected-from-a-buttongroup/13232816#13232816
		for(Enumeration<AbstractButton> b = buttonGroup.getElements(); b.hasMoreElements();) {
			AbstractButton cb = b.nextElement();
			if(cb.isSelected()) {
				currentButton = (JToggleButton) cb;
				System.out.println(currentButton.getLabel());
				return currentButton.getLabel(); 
			}
		}
		return null;
	}

}