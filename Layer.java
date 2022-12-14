package components;
//Imports 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader; 
import java.io.FileWriter; 
import java.io.IOException;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.*;
import java.lang.Math;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Layer {
	
	//Declare public Attributes 
	public String name;
	public int rows; 
	public int columns; 
	public double[] origin = new double[2];
	public double cellSize;
	public double[] values; 
	public double nullValue;
	
	//Constructors  
	public Layer(String layerName,String fileName){
		this.name = layerName; //Set Layer object name as input
		
		//Try parsing the file 
		try {
			File r = new File(fileName);
			FileReader fReader = new FileReader(r);
			BufferedReader bReader = new BufferedReader(fReader);
			// Rows and what they include: 
			// 1 - # columns 
			// 2 - # rows
			// 3 - # x-origin 
			// 4 - # y-origin 
			// 5 - cell size
			// 6 - no-data value
			String tt; //Temporary text variable
			String[] tt2; //Temporary text array variable 2 
			int xo; //X origin value
			int yo; //Y origin value
			String[] lineValues; 
			int size;  // Size variable for use in loop
			double vP = 0; //Value of cell for use in loop
			int count = 0;
			
			// COLUMNS
			tt = bReader.readLine(); 
			tt2=tt.split("\\s+");
			columns = Integer.parseInt(tt2[1]); //Finds # columns 
			
			// ROWS 
			tt = bReader.readLine(); 
			tt2=tt.split("\\s+");
			rows =Integer.parseInt(tt2[1]); //Finds # rows 
			
			// XORIGIN
			tt = bReader.readLine(); 
			tt2=tt.split("\\s+");
			xo = Integer.parseInt(tt2[1]);//X - origin 
			
			//YORIGIN
			tt = bReader.readLine(); 
			tt2=tt.split("\\s+");
			yo = Integer.parseInt(tt2[1]);//y - origin 

			origin[0]=xo; // Set x origin 
			origin[1]=yo; // Set y origin 
			
			//CELL SIZE
			tt = bReader.readLine(); 
			tt2=tt.split("\\s+");
			cellSize =Integer.parseInt(tt2[1]); //Finds cell size 
			
			//NO DATA
			tt = bReader.readLine(); 
			tt2=tt.split("\\s+");
			Double temp = Double.parseDouble(tt2[1]);
			nullValue =(int)Math.round(temp); //Finds no-data value 
			
			//SIZE
			size = this.rows*this.columns;
			values = new double[size]; 
			
			for(int i = 0; i < this.rows; i++) {
				tt = bReader.readLine(); 
				lineValues = tt.split(" "); 
				for(String s : lineValues) {
					vP = Double.parseDouble(s);
					this.values[count]=vP;
					count += 1;
				}
			}	
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public Layer(String layerName, int nRows, int nCols, double[] ori, double resolution, double nullV){
		name = layerName; 
		rows=nRows; 
		columns=nCols; 
		origin = ori;
		cellSize=resolution; 
		nullValue=nullV;
		
		values = new double[rows*columns];
	}
	
	//--------------------
	//Methods other than constructor(s)
	//--------------------
	
	public void print(){
		System.out.println("Number of Columns: " + columns);
		System.out.println("Number of Rows: " + rows);
		System.out.println("X-origin: "+origin[0]);
		System.out.println("Y-origin: "+origin[1]);
		System.out.println("Cell Size: "+cellSize);
		System.out.println("No Data Value: "+nullValue);
		
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < columns; c++) {
				System.out.print(values[r*columns+c]+" ");
			}
			System.out.println();
		}
		
	}
	
	public BufferedImage toImage() {
		// create a BufferedImage of the layer in grayscale 
		BufferedImage image = new BufferedImage(this.columns,this.rows, BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = image.getRaster(); 
		
		double min = getMin();
		double max = getMax();
		
		int[] color = new int[3];
		int colorValue; 
		
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.columns; j++) {
				colorValue = (int)Math.round(convertToRange(values[i*this.columns+j],min,max));
				color[0]=colorValue;
				color[1]=colorValue;
				color[2]=colorValue;
				raster.setPixel(j,i,color);
			}
		}
		return image;
	}
	
	public BufferedImage toImage(double[] voi) {
		// visualize a BufferedImage of the layer in color 
		// create a BufferedImage of the layer in grey-scale 
		BufferedImage image = new BufferedImage(this.columns,this.rows, BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = image.getRaster(); 
		
		int[] color = new int[3]; // Instantiate an empty vector 
		
		int numberOfValues = voi.length;
		int[][] colorPalette = new int[numberOfValues][3]; // Instantiate a color palette   
		
		int randRange = 255;
		int rand1;
		int rand2;
		int rand3;
		int pCount=0; 
		//Make random colors 
		for(int h=0; h<numberOfValues; h++) {
			Random randomColorValue = new Random();  
			rand1 = randomColorValue.nextInt(randRange); 
			rand2 = randomColorValue.nextInt(randRange);
			rand3 = randomColorValue.nextInt(randRange); 

			colorPalette[pCount][0] = rand1;
			colorPalette[pCount][1] = rand2;
			colorPalette[pCount][2] = rand3;
			pCount += 1; 
		}
		
		//Define default color 
		int[] defColor = new int[3];
		defColor[0]=0;
		defColor[1]=0;
		defColor[2]=0;
		
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.columns; j++) {
				boolean found = false;

				
				for(int o = 0; o<numberOfValues;o++) {
					if(voi[o] == values[i*this.columns+j]){
						color=colorPalette[o];
						found = true;
						raster.setPixel(j,i,color);
						break;
					}
				}
				
				if(found == false) {
					raster.setPixel(j,i,defColor);
				}
			}
		}
		return image; 
	}
	
	public void save(String outputFileName) {
		File file = new File(outputFileName); 
		double[] lineTemp = new double [columns];  
		try {
			FileWriter fWriter = new FileWriter(file);
			fWriter.write("ncols         "+columns+"\n");
			fWriter.write("nrows         "+rows+"\n");
			fWriter.write("xllcorner     "+(int)Math.round(origin[0])+"\n");
			fWriter.write("yllcorner     "+(int)Math.round(origin[1])+"\n");
			fWriter.write("cellsize      "+(long)Math.round(cellSize)+"\n");
			fWriter.write("NODATA_value  "+nullValue+"\n");
			for(int r = 0; r < rows; r++) {
				for(int c = 0; c < columns; c++) {
					lineTemp[c]=values[r*columns+c]; 
				}
				for(double v : lineTemp) {
					fWriter.write(v+" "); 
				}
				fWriter.write("\n"); 
			}
			fWriter.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//Method for gray scale visualization 
	public void greyScale(String title ,double dScale){
		int scale = (int)Math.round(dScale);
		BufferedImage image = this.toImage();
		MapPanel map  = new MapPanel(image,scale);

		JFrame appFrame= new JFrame(title);
		appFrame.add(map);
		appFrame.pack();
		appFrame.setSize(new Dimension(600,600));
		appFrame.setVisible(true);
		appFrame.setResizable(false);
		appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//--------------------
	//Map Algebraic Methods
	//--------------------
	
	//LOCAL SUM
	public Layer localSum(Layer inLayer, String outLayerName){
		Layer outLayer = new Layer(outLayerName, rows, columns, origin, cellSize, nullValue);
		for(int i=0; i<(rows*columns); i++) {
			outLayer.values[i]=values[i]+inLayer.values[i];
		}
		return outLayer;
	}
	
	//FOCAL SUM - For testing neighborhood function // Code from lecture slides 
	public Layer focalSum(int r, boolean isSquare, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, rows, columns, origin, cellSize, nullValue);
		
		int[] neighborhood;
		double value;
			for (int i=0; i<(rows*columns); i++) {
				neighborhood = getNeighborhood(i, r, isSquare);
				value = 0;
				for (int j=0; j<neighborhood.length; j++) {
					value = value + values[neighborhood[j]];
					}
				outLayer.values[i] = value;
		}
		return outLayer;
	}
	
	//FOCAL VARIETY 
	public Layer focalVariety(int r, boolean IsSquare,String outLayerName) {
		Layer outLayer = new Layer(outLayerName, rows, columns, origin, cellSize, nullValue);
				
		int[] neighborhood;
			for (int i=0; i<(rows*columns); i++) {
				ArrayList<Double> l = new ArrayList<Double>(); 
				Double cellValueObj; 
				
				neighborhood = getNeighborhood(i, r, IsSquare);
				for (int j=0; j<neighborhood.length; j++) {
					cellValueObj = values[neighborhood[j]]; 
					l.add(cellValueObj); // Add cell value object to ArrayList
					}
				//Create a hashset in order to find the unique values 
				HashSet<Double> hashSet = new HashSet<Double>(l);
								
				outLayer.values[i] = hashSet.size(); // Gives the number of unique values 
		}
		return outLayer;
	}

	//ZONAL MINIMUM
	public Layer zonalMinimum(Layer zoneLayer, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, rows, columns, origin, cellSize, nullValue);
		HashMap<Double, Double> hm = new HashMap<Double,Double>(); //Create hashmap
		for(int i=0;i<(rows*columns);i++) {
			if(!hm.containsKey(zoneLayer.values[i])) { //There is no key/value for the zone  
				hm.put(zoneLayer.values[i],values[i]); // Add key if there is none (!="If there is no key")
			}
			else{ // There is a value for the zone 
				if(values[i]<hm.get(zoneLayer.values[i])) { //The value is smaller 
					hm.put(zoneLayer.values[i],values[i]); //Overwrite previous lowest value 
				}
			}
		}
		
		for(int j = 0; j < (rows*columns);j++) { //Write the lowest value for each zone to each cell in the outlayer
			outLayer.values[j]=hm.get(zoneLayer.values[j]);
		}
		return outLayer;
	}
	
	//--------------------
	// Private methods
	//--------------------
	private int[] getNeighborhood(int index, int radius, boolean IsSquare) {
	
		ArrayList<Integer> neighborhoodList = new ArrayList<Integer>();
		int neighbor; 
		//Integer neighborObj; 
		
		int r = index/columns; //Find row and column for index
		int c = index%columns; 
		//boolean isInBound = false; // Variables for use with in-bound function 
		double distance;
		double rD;
		
		int rLowBound = Math.max(r - radius, 0); // Find bounds to loop
		int rUppBound = Math.min(r + radius, rows-1);
		int cLowBound = Math.max(c - radius,0);
		int cUppBound = Math.min(c + radius,  columns-1);
		
			for(int row=rLowBound; row<rUppBound;row++) {
				for(int col=cLowBound; col<cUppBound;col++) {
					neighbor = row*columns+col; //Find neighbor index			
					//isInBound = isInBound(row,col); //Check that the neighbor is valid
					//if(isInBound) {
						if(IsSquare==true) { // Square neighborhood 
							//neighborObj= Integer.valueOf(neighbor); //The example code is deprecated
							//neighborObj= new Integer(neighbor);
							neighborhoodList.add(neighbor);	
						}
						else { // Circular neighborhood
							distance = Math.sqrt(Math.pow(row-r,2)+Math.pow(col-c,2));
							rD = Double.valueOf(r);
							if(distance <= rD) {
								//neighborObj= Integer.valueOf(neighbor); //The example code is deprecated
								neighborhoodList.add(neighbor);
							}
						}
					//}
				}
			}
			int[] neighborhood = new int[neighborhoodList.size()]; //Get size of list 
			int counter = 0; // Initiate counter 
			for(Integer n : neighborhoodList) { 
				neighborhood[counter]=n;
				counter ++;
			}
			return neighborhood;
	}
	
	/*private boolean isInBound(int r,int c) {

			if(r<0||r>=rows) {
				return false;
			}
			else {
				if(c<0||c>=columns) {
					return false;
				}
				else {
					return true;
				}
			}
		

	}*/
	
	private double getMax() {
		double max = Double.NEGATIVE_INFINITY;
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.columns; j++) {
				if(values[i*this.columns+j] > max) {
					max = values[i*this.columns+j];
				}
			}
		}
		return max;
	}

	private double getMin() {
		double min = Double.POSITIVE_INFINITY;
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.columns; j++) {
				if(values[i*this.columns+j] < min) {
					min = values[i*this.columns+j];
				}
			}
		}
		return min;
	}
	
	private double convertToRange(double v, double min, double max) {
		double oldRange = max-min;
		double newRange = 255; 
		double newValue = ((max-v)/(oldRange))*newRange;
		return newValue;
	}
	
	
	
}
