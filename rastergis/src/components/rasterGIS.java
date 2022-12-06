package components;

import java.util.LinkedList;

//This is the main program. We use this part to store information that shouldn't be loaded multiple times.
//Things like layers, operation history etc. 

public class rasterGIS {
	public LinkedList<Layer> layerList =new LinkedList<Layer>(); //Used to keep track of all layers 
	
	public rasterGIS() {
		this.layerList=new LinkedList<Layer>();
	}
}
