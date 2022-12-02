package components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class MapPanel extends JPanel{
	//Attributes 
	public BufferedImage image; 
	public int scale; 
	
	//Constructors 
	public MapPanel(BufferedImage image, int scale) {
		super(); 
		this.image = image; 
		this.scale = scale; 
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image,0,0,image.getWidth()*scale,image.getHeight()*scale,this);
	}
}
