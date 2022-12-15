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
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel; 

	public class FocalOperations extends JFrame {
	private JPanel contentPane;
	private ButtonGroup buttonGroup;
	private ButtonGroup shapeGroup;
	private Layer raster1=null;
	private Layer result;
	private boolean isSquare;
	
	

	/**
	 * Launch the application.
	 */
	public static void main() {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FocalOperations frame = new FocalOperations();
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
	public FocalOperations() {
		
		super();
		setResizable(false);
		setTitle("Conduct Focal Operation");
		setDefaultCloseOperation(LocalOperations.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		buttonGroup = new ButtonGroup();
		shapeGroup = new ButtonGroup();
		
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{232, 188, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 13, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblRaster = new JLabel("Select raster : ");
		lblRaster.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblRaster = new GridBagConstraints();
		gbc_lblRaster.insets = new Insets(0, 0, 5, 5);
		gbc_lblRaster.fill = GridBagConstraints.VERTICAL;
		gbc_lblRaster.gridx = 0;
		gbc_lblRaster.gridy = 0;
		contentPane.add(lblRaster, gbc_lblRaster);
		
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
					lblRaster.setText("[Raster 1 has been selected]");
					raster1 = new Layer(name,filePath); 
				}
			}
		});
		GridBagConstraints gbc_btnOpenR1 = new GridBagConstraints();
		gbc_btnOpenR1.insets = new Insets(0, 0, 5, 0);
		gbc_btnOpenR1.gridx = 1;
		gbc_btnOpenR1.gridy = 0;
		contentPane.add(btnOpenR1, gbc_btnOpenR1);
		
		JLabel lblNSize = new JLabel("Select neighborhood size : ");
		lblNSize.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblNSize = new GridBagConstraints();
		gbc_lblNSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblNSize.gridx = 0;
		gbc_lblNSize.gridy = 1;
		contentPane.add(lblNSize, gbc_lblNSize);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 0);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 1;
		contentPane.add(spinner, gbc_spinner);
		
		JLabel lblShape = new JLabel("Select kernel shape: ");
		lblShape.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblShape = new GridBagConstraints();
		gbc_lblShape.insets = new Insets(0, 0, 5, 5);
		gbc_lblShape.gridx = 0;
		gbc_lblShape.gridy = 2;
		contentPane.add(lblShape, gbc_lblShape);
		
		JRadioButton rdbtnCircle = new JRadioButton("Circle");
		GridBagConstraints gbc_rdbtnCircle = new GridBagConstraints();
		gbc_rdbtnCircle.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnCircle.gridx = 0;
		gbc_rdbtnCircle.gridy = 3;
		contentPane.add(rdbtnCircle, gbc_rdbtnCircle);
		
		JRadioButton rdbtnSquare = new JRadioButton("Square");
		GridBagConstraints gbc_rdbtnSquare = new GridBagConstraints();
		gbc_rdbtnSquare.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnSquare.gridx = 1;
		gbc_rdbtnSquare.gridy = 3;
		contentPane.add(rdbtnSquare, gbc_rdbtnSquare);
		
		JLabel lblSelectLocalOperation = new JLabel("Select Local Operation :");
		lblSelectLocalOperation.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblSelectLocalOperation = new GridBagConstraints();
		gbc_lblSelectLocalOperation.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectLocalOperation.gridx = 0;
		gbc_lblSelectLocalOperation.gridy = 4;
		contentPane.add(lblSelectLocalOperation, gbc_lblSelectLocalOperation);
		
		JToggleButton tbVariety = new JToggleButton("Variety");
		GridBagConstraints gbc_tbVariety = new GridBagConstraints();
		gbc_tbVariety.fill = GridBagConstraints.HORIZONTAL;
		gbc_tbVariety.insets = new Insets(0, 0, 5, 5);
		gbc_tbVariety.gridx = 0;
		gbc_tbVariety.gridy = 5;
		buttonGroup.add(tbVariety);
		contentPane.add(tbVariety, gbc_tbVariety);
		
		JToggleButton tbMax = new JToggleButton("Maximum");
		GridBagConstraints gbc_tglbtnMaximum = new GridBagConstraints();
		gbc_tglbtnMaximum.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnMaximum.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnMaximum.gridx = 1;
		gbc_tglbtnMaximum.gridy = 5;
		buttonGroup.add(tbMax);
		contentPane.add(tbMax, gbc_tglbtnMaximum);
		
		JToggleButton tbSum = new JToggleButton("Sum");
		GridBagConstraints gbc_tbSum = new GridBagConstraints();
		gbc_tbSum.fill = GridBagConstraints.HORIZONTAL;
		gbc_tbSum.insets = new Insets(0, 0, 5, 5);
		gbc_tbSum.gridx = 0;
		gbc_tbSum.gridy = 6;
		buttonGroup.add(tbSum);
		contentPane.add(tbSum, gbc_tbSum);
		
		JToggleButton tbMin = new JToggleButton("Minimum");
		GridBagConstraints gbc_tglbtnMinimum = new GridBagConstraints();
		gbc_tglbtnMinimum.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnMinimum.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnMinimum.gridx = 1;
		gbc_tglbtnMinimum.gridy = 6;
		buttonGroup.add(tbMin);
		contentPane.add(tbMin, gbc_tglbtnMinimum);
		
		JToggleButton tbSlope = new JToggleButton("Slope");
		GridBagConstraints gbc_tbSlope = new GridBagConstraints();
		gbc_tbSlope.fill = GridBagConstraints.HORIZONTAL;
		gbc_tbSlope.insets = new Insets(0, 0, 5, 5);
		gbc_tbSlope.gridx = 0;
		gbc_tbSlope.gridy = 7;
		buttonGroup.add(tbSlope);
		contentPane.add(tbSlope, gbc_tbSlope);
		
		JToggleButton tbAspect = new JToggleButton("Aspect");
		GridBagConstraints gbc_tbAspect = new GridBagConstraints();
		gbc_tbAspect.fill = GridBagConstraints.HORIZONTAL;
		gbc_tbAspect.insets = new Insets(0, 0, 5, 0);
		gbc_tbAspect.gridx = 1;
		gbc_tbAspect.gridy = 7;
		buttonGroup.add(tbAspect);
		contentPane.add(tbAspect, gbc_tbAspect);
		
		JToggleButton tbMean = new JToggleButton("Mean");
		GridBagConstraints gbc_tbMean = new GridBagConstraints();
		gbc_tbMean.fill = GridBagConstraints.HORIZONTAL;
		gbc_tbMean.insets = new Insets(0, 0, 5, 5);
		gbc_tbMean.gridx = 0;
		gbc_tbMean.gridy = 8;
		contentPane.add(tbMean, gbc_tbMean);
		
		buttonGroup.add(tbVariety);
		buttonGroup.add(tbSlope);
		buttonGroup.add(tbSum);
		buttonGroup.add(tbMin);
		buttonGroup.add(tbMax);
		buttonGroup.add(tbMean);
		
		shapeGroup.add(rdbtnCircle);
		shapeGroup.add(rdbtnSquare);
		

				JButton btnOK = new JButton("OK");
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int nhs = spinner.getComponentCount();
						if(raster1!=null) {
							String shape = findSelected(shapeGroup);
							if(shape!="N") {
								if(shape=="Circle") {
									isSquare=false;
								}
								else
								{
									isSquare=true;
								}
								
								String operation = findSelected(buttonGroup);
								if(operation!=null) {
									JRadioButton rdbtn;
									GridBagConstraints gbc_rdbtn;
									switch(operation) {
									case "Variety":
										result=raster1.focalVariety(nhs, isSquare, shape);	
										
										rdbtn = new JRadioButton("FocalVar");
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
										break;
										
									case "Sum":
										result=raster1.focalSum(nhs, isSquare, shape);	
										
										rdbtn = new JRadioButton("FocalSum");
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
										break;
										
										
									case "Slope":
										result=raster1.focalSlope("slope");	
										
										rdbtn = new JRadioButton("Slope");
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
										break;
										
									case "Mean":
										result=raster1.focalMean(nhs, isSquare, shape);	
										
										rdbtn = new JRadioButton("FocalMean");
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
										break;
										
									case "Maximum":
										double max=raster1.focalMaximum(nhs, isSquare, shape);	
										JOptionPane.showMessageDialog(null,"The focal maximum is: "+max);	
										dispose();
										break;
										
									case "Minimum":
										double min=raster1.focalMinimum(nhs, isSquare, shape);	
										JOptionPane.showMessageDialog(null,"The focal minimum is: "+min);	
										dispose();
										break;
										
									case "Aspect":
										result=raster1.focalAspect("Aspect");	
										
										rdbtn = new JRadioButton("Aspect");
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
								JOptionPane.showMessageDialog(null,"Please select a kernel shape");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null,"Please select a raster file");
						}
					}
				});
				
						GridBagConstraints gbc_btnOK = new GridBagConstraints();
						gbc_btnOK.insets = new Insets(0, 0, 5, 0);
						gbc_btnOK.gridx = 1;
						gbc_btnOK.gridy = 8;
						contentPane.add(btnOK, gbc_btnOK);
	}
	
	@SuppressWarnings("deprecation")
	private String findSelected(ButtonGroup btnG) {
		JToggleButton currentButton = null; 
		//This code was found on stack overflow https://stackoverflow.com/questions/201287/how-do-i-get-which-jradiobutton-is-selected-from-a-buttongroup/13232816#13232816
		for(Enumeration<AbstractButton> b = btnG.getElements(); b.hasMoreElements();) {
			AbstractButton cb = b.nextElement();
			if(cb.isSelected()) {
				currentButton = (JToggleButton) cb;
				System.out.println(currentButton.getLabel());
				return currentButton.getLabel(); 
			}
		}
		return "N";
	}

}