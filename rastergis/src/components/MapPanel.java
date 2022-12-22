package components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import components.UI;
public class MapPanel extends JPanel{
	//Attributes 
	public BufferedImage image; 
	public int scale; 
	static int x;
	static int y;
	
	
	//Constructors 
	public MapPanel(BufferedImage image, int scale) {
		super(); 
		this.image = image; 
		this.scale = scale; 
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		x=UI.x-(image.getWidth()*scale)/2+(UI.scrollBarHorizontal.getValue())*UI.currentZoom;
		y=UI.y-(image.getHeight()*scale)/2+(UI.scrollBarVertical.getValue())*UI.currentZoom;
		g.drawImage(image,UI.x-(image.getWidth()*scale)/2+(UI.scrollBarHorizontal.getValue())*UI.currentZoom,UI.y-(image.getHeight()*scale)/2+(UI.scrollBarVertical.getValue())*UI.currentZoom,image.getWidth()*scale,image.getHeight()*scale,this);
	}
}
