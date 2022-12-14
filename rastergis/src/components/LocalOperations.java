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

public class LocalOperations extends JFrame {

	private JPanel contentPane;
	private ButtonGroup buttonGroup;
	private Layer raster1=null;
	private Layer raster2=null; 
	private Layer result;
	//private LocalOperations frame;
	
	

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
	public LocalOperations() {
		setResizable(false);
		setTitle("Local Operation");
		setDefaultCloseOperation(LocalOperations.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{232, 188, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 13, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblRaster1 = new JLabel("Select raster 1 : ");
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
					lblRaster1.setText("[Raster 1 has been selected]");
					raster1 = new Layer(name,filePath); 
				}
			}
		});
		GridBagConstraints gbc_btnOpenR1 = new GridBagConstraints();
		gbc_btnOpenR1.insets = new Insets(0, 0, 5, 0);
		gbc_btnOpenR1.gridx = 1;
		gbc_btnOpenR1.gridy = 0;
		contentPane.add(btnOpenR1, gbc_btnOpenR1);
		
		JLabel lblRaster2 = new JLabel("Select raster 2 : ");
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
					lblRaster2.setText("[Raster 2 has been selected]");
					raster2 = new Layer(name,filePath); 
				}
			}
		});
		GridBagConstraints gbc_btnOpenR2 = new GridBagConstraints();
		gbc_btnOpenR2.insets = new Insets(0, 0, 5, 0);
		gbc_btnOpenR2.gridx = 1;
		gbc_btnOpenR2.gridy = 2;
		contentPane.add(btnOpenR2, gbc_btnOpenR2);
		
		JLabel lblSelectLocalOperation = new JLabel("Select Local Operation :");
		lblSelectLocalOperation.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblSelectLocalOperation = new GridBagConstraints();
		gbc_lblSelectLocalOperation.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectLocalOperation.gridx = 0;
		gbc_lblSelectLocalOperation.gridy = 4;
		contentPane.add(lblSelectLocalOperation, gbc_lblSelectLocalOperation);
		
		buttonGroup = new ButtonGroup();
		JToggleButton tbSum = new JToggleButton("Sum");
		GridBagConstraints gbc_tglbtnNewToggleButton = new GridBagConstraints();
		gbc_tglbtnNewToggleButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnNewToggleButton.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnNewToggleButton.gridx = 0;
		gbc_tglbtnNewToggleButton.gridy = 5;
		buttonGroup.add(tbSum);
		contentPane.add(tbSum, gbc_tglbtnNewToggleButton);
		
		JToggleButton tbMax = new JToggleButton("Maximum");
		GridBagConstraints gbc_tglbtnMaximum = new GridBagConstraints();
		gbc_tglbtnMaximum.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnMaximum.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnMaximum.gridx = 1;
		gbc_tglbtnMaximum.gridy = 5;
		buttonGroup.add(tbMax);
		contentPane.add(tbMax, gbc_tglbtnMaximum);
		
		JToggleButton tbSub = new JToggleButton("Subtraction");
		GridBagConstraints gbc_tglbtnNewToggleButton_1_1 = new GridBagConstraints();
		gbc_tglbtnNewToggleButton_1_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnNewToggleButton_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnNewToggleButton_1_1.gridx = 0;
		gbc_tglbtnNewToggleButton_1_1.gridy = 6;
		buttonGroup.add(tbSub);
		contentPane.add(tbSub, gbc_tglbtnNewToggleButton_1_1);
		
		JToggleButton tbMin = new JToggleButton("Minimum");
		GridBagConstraints gbc_tglbtnMinimum = new GridBagConstraints();
		gbc_tglbtnMinimum.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnMinimum.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnMinimum.gridx = 1;
		gbc_tglbtnMinimum.gridy = 6;
		buttonGroup.add(tbMin);
		contentPane.add(tbMin, gbc_tglbtnMinimum);
		
		JToggleButton tbProduct = new JToggleButton("Product");
		GridBagConstraints gbc_tglbtnNewToggleButton_1 = new GridBagConstraints();
		gbc_tglbtnNewToggleButton_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnNewToggleButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnNewToggleButton_1.gridx = 0;
		gbc_tglbtnNewToggleButton_1.gridy = 7;
		buttonGroup.add(tbProduct);
		contentPane.add(tbProduct, gbc_tglbtnNewToggleButton_1);
		
		JToggleButton tbMean = new JToggleButton("Mean");
		GridBagConstraints gbc_tglbtnMean = new GridBagConstraints();
		gbc_tglbtnMean.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnMean.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnMean.gridx = 1;
		gbc_tglbtnMean.gridy = 7;
		buttonGroup.add(tbMean);
		contentPane.add(tbMean, gbc_tglbtnMean);
		
		JToggleButton tbDiv = new JToggleButton("Division");
		GridBagConstraints gbc_tglbtnNewToggleButton_1_2 = new GridBagConstraints();
		gbc_tglbtnNewToggleButton_1_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnNewToggleButton_1_2.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnNewToggleButton_1_2.gridx = 0;
		gbc_tglbtnNewToggleButton_1_2.gridy = 8;
		buttonGroup.add(tbDiv);
		contentPane.add(tbDiv, gbc_tglbtnNewToggleButton_1_2);
		
		buttonGroup.add(tbSum);
		buttonGroup.add(tbDiv);
		buttonGroup.add(tbProduct);
		buttonGroup.add(tbSub);
		buttonGroup.add(tbMin);
		buttonGroup.add(tbMax);
		
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(raster1!=null && raster2!=null) {
					String operation = findSelected();
					if(operation!=null) {
						switch(operation) {
						case "Sum":
							result=raster1.localSum(raster1, "test");	
							
							JRadioButton rdbtn = new JRadioButton("Local");
							rdbtn.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent e) {
									Layer current = UI.hm.get(rdbtn); 
									UI.greyScale(current,UI.currentZoom); 
								}
							});
								
							GridBagConstraints gbc_rdbtn = new GridBagConstraints();
							gbc_rdbtn.gridx = 0;
							gbc_rdbtn.gridy = UI.hm.size()+1;
							UI.btnGroupToc.add(rdbtn);
							rdbtn.setBackground(Color.white);
							UI.tocBar.add(rdbtn,gbc_rdbtn);
							UI.hm.put(rdbtn, result); 
							rdbtn.setSelected(true);
							
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
		gbc_btnOK.gridx = 1;
		gbc_btnOK.gridy = 9;
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