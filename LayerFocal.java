package se.kth.ag2411.mapalegra;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.io.BufferedWriter;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.random.*;
import java.util.Scanner;

public class LayerFocal {
	// Attributes (This is complete)
	public String name; // name of this layer
	public int nRows; // number of rows
	public int nCols; // number of columns
	public double[] origin = new double[2]; // x,y-coordinates of lower-left corner
	public double resolution; // cell size
	public double[][] values; // data. Alternatively, public double[][] values;
	public double nullValue; // value designated as "No data"
	//Constructor (This is not complete)
	public LayerFocal(String layerName, String fileName) {
	// You may want to do some work before reading a file.
	try { 
		// Exception may be thrown while reading (and writing) a file.
		File rFile = new File(fileName);
		FileReader fReader = new FileReader(rFile);
		BufferedReader bReader = new BufferedReader(fReader);
		
		// Get access to the lines of Strings stored in the file
		String text,text2;
		// stores each line of Strings temporarily
		
		
	   	    
	   
	   
		// Read first line, which starts with "ncols"
		text = bReader.readLine(); // first line is read
		text2 = text.substring(5).trim();
		
		nCols = Integer.parseInt(text2);
		// Read second line, which starts with "nrows"
		text = bReader.readLine(); // second line is read
		text2 = text.substring(5).trim();
	    
	    nRows = Integer.parseInt(text2);
		// Read third line, which starts with "xllcorner"
		text = bReader.readLine();
	    text2 = text.substring(9).trim();
	    
	    origin[0] = Double.parseDouble(text2);
	    
	    // Read forth line, which starts with "yllcorner"
	    text = bReader.readLine();
	    text2 = text.substring(9).trim();
	    
	    origin[1] = Double.parseDouble(text2);
		// Read fifth line, which starts with "cellsize"
	    text = bReader.readLine();
	    text2 = text.substring(8).trim();
	    
	    resolution = Double.parseDouble(text2);
		// Read sixth line, which starts with "NODATA_value"
	    text = bReader.readLine();
	    text2 = text.substring(12).trim();
	    
	    nullValue = Double.parseDouble(text2);
		// Read each of the remaining lines, which represents a row of raster
		values = new double[nRows][nCols];
	    for(int i=0; i<nRows;i++) {
			text= bReader.readLine();
			String[]temp = text.split(" ");
			for(int j=0;j<nCols;j++) {
				double temp1 = Double.parseDouble(temp[j]);
				values[i][j] = temp1;
			}
		}
		// values
		bReader.close();
	} catch (Exception e) {
	e.printStackTrace();
			}
		}
	
	// Print (This is complete)
	public void print(){
	//Print this layer to console
	System.out.println("ncols "+nCols);
	System.out.println("nrows "+nRows);
	System.out.println("xllcorner "+origin[0]);
	System.out.println("yllcorner "+origin[1]);
	System.out.println("cellsize "+resolution);
	System.out.println("NODATA_value " + nullValue);
	for (int i = 0; i < nRows; i++) {
	for (int j = 0; j < nCols; j++) {
	System.out.print(values[i][j]+" ");
		}
	System.out.println();
			}
		}
	// Save (This is not complete)
	public void save(String outputFileName) {
		try {
	// save this layer as an ASCII file that can be imported to ArcGIS
		File file = new File(outputFileName);
		FileWriter fWriter = new FileWriter(file);
		fWriter.write("ncols   " + nCols + "\n");
		fWriter.write("nrows   " + nRows + "\n");
		fWriter.write("xllcorner   " + origin[0] + "\n");
		fWriter.write("yllcorner   " + origin[1] + "\n");
		fWriter.write("cellsize " + resolution + "\n");
		fWriter.write("NODATA_value   " + nullValue  + "\n");
		for(int i=0; i<nRows;i++) {
			for(int j=0;j<nCols;j++) {
				fWriter.write(values[i][j] + " ");
			}
			fWriter.write("\n");
		}
		fWriter.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public double getMax() {
		double Max = values[1][1];
		for(int i=0; i<nRows;i++) {
			for(int j=0;j<nCols;j++) {
				 if(values[i][j]>Max) {
					Max = values[i][j];
				}
			}
		}
		return Max;
	}
	
	public double getMin() {
		double Min = values[1][1];
		for(int i=0; i<nRows;i++) {
			for(int j=0;j<nCols;j++) {
				 if(values[i][j]<Min) {
					Min = values[i][j];
				}
			}
		}
		return Min;
	}
		
		public BufferedImage toImage() {
			double Maxvalue = getMax();
			double Minvalue = getMin();
			int intMax = (int)Maxvalue;
			int intMin = (int)Minvalue;
		BufferedImage image = new BufferedImage(nCols,nRows,BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = image.getRaster();
		int[]color = new int [3];
		int RGB;
		for(int i=0 ;i<nRows;i++) {
			for(int j=0 ; j<nCols;j++) {
				double temp = 255*(Maxvalue-(values[i][j]))/(Maxvalue-Minvalue);
				RGB = (int) Math.round(temp);
				color[0]=RGB;
				color[1]=RGB;
				color[2]=RGB;
				raster.setPixel(j,i,color);
				}
			}
			return image;
		}
	
		public BufferedImage toImage1(double[] interest) {
			BufferedImage image1 = new BufferedImage(nCols,nRows,BufferedImage.TYPE_INT_RGB);
			WritableRaster raster = image1.getRaster();
			int[][]color = new int[interest.length][3];
			
			for(int i=0;i<interest.length;i++) {
				Random r = new Random();
				color[i][0] = r.nextInt(256);
				color[i][1] = r.nextInt(256);
				color[i][2] = r.nextInt(256);
				}
			
			int[]color1 = new int[3];
			for(int i =0; i < nRows; i++) {
				for(int j = 0; j < nCols;j++) {
					double currentvalue = values[i][j];	
					for (int z = 0;z < interest.length;z++) {
						if(currentvalue == interest[z]) {							
							//color1[0] = color[z][0];
							//color1[1] = color[z][1];
							//color1[2] = color[z][2];
							raster.setPixel(j, i, color[z]);
						}
					}
				}
			}
		return image1;
		}
		public LayerFocal(String name, int nRows, int nCols, double[] origin, double resolution, double nullValue) {
				// construct a new layer by assigning a value to each of its attributes
				this.name = name; // on the left hand side are the attributes of
				this.nRows = nRows; // the new layer;
				this.nCols = nCols; // on the right hand side are the parameters.
				this.origin =origin;
				this.resolution = resolution;
				this.nullValue = nullValue;
				this.values = new double[nRows][nCols];// to be continued...
				
				}
				
		public LayerFocal localSum(LayerFocal inLayer, String outLayerName){
			LayerFocal outLayer = new LayerFocal(outLayerName, nRows, nCols , origin, resolution, nullValue);
			for(int i=0; i<nRows; i++){
				for(int j = 0; j <nCols; j++) {
					outLayer.values[i][j] = values[i][j] + inLayer.values[i][j];
				}
			
		} return outLayer;	
	}	
		public LayerFocal focalVariety(int r, boolean IsSquare, String outLayerName){
			LayerFocal outLayer = new LayerFocal(outLayerName, nRows, nCols , origin, resolution, nullValue);
			for(int i = 0;i < nRows;i++) {
				for (int j = 0; j<nCols;j++) {
					int[] arrayneighbor = getneighbourhood(i,j,r,IsSquare);
					double[] Array = new double[arrayneighbor.length];
					for(int k=0; k <arrayneighbor.length;k++) {
						int m = arrayneighbor[k]/nCols;
						int n = arrayneighbor[k]%nCols;
						Array[k] = values[m][n];
					}
					Arrays.sort(Array);
					//System.out.print(Arrays.toString(Array));
					int variety = 1;
					for(int k = 0; k < Array.length-1;k++){
						if(Array[k+1]!=Array[k]) {
							variety++;
							
						}
						else {
							continue;
						}
						outLayer.values[i][j] = variety;
					}
				}
			}
			return outLayer;
		}
		
		public LayerFocal focalSum(int r ,boolean IsSquare, String outLayerName) {
			LayerFocal outLayer = new LayerFocal(outLayerName,nRows,nCols,origin,resolution, nullValue);
			for(int i = 0;i < nRows;i++) {
				for (int j = 0; j<nCols;j++) {
					int[] arrayneighbor = getneighbourhood(i,j,r,IsSquare);
					double[] Array = new double[arrayneighbor.length];
					for(int k=0; k <arrayneighbor.length;k++) {
						int m = arrayneighbor[k]/nCols;
						int n = arrayneighbor[k]%nCols;
						Array[k] = values[m][n];
					}
					Arrays.sort(Array);
					int sum=0;
					for(int k=0;k<Array.length;k++) {
						sum+=Array[k];
					}
					outLayer.values[i][j] = sum;
				}
			}
			return outLayer;
		}
		public double focalMaximum(int r ,boolean IsSquare, String outLayerName) {
			LayerFocal outLayer = new LayerFocal(outLayerName,nRows,nCols,origin,resolution, nullValue);
			outLayer.focalSum(r, IsSquare, outLayerName);
			double Max = values[0][0];
			for(int i=0;i<nRows;i++) {
				for(int j=0;j<nCols;j++) {
					if(Max< values[i][j]) {
						Max = values[i][j];
						}					
					}
				}				
				return Max;
			}
		public double focalMinimum(int r ,boolean IsSquare, String outLayerName) {
			LayerFocal outLayer = new LayerFocal(outLayerName,nRows,nCols,origin,resolution, nullValue);
			outLayer.focalSum(r, IsSquare, outLayerName);
			double Min = values[0][0];
			for(int i=0;i<nRows;i++) {
				for(int j=0;j<nCols;j++) {
					if(Min > values[i][j]) {
						Min = values[i][j];
						}					
					}
				}				
				return Min;
			}
		
		public LayerFocal focalSlope(String outLayerName) {
			LayerFocal outLayer = new LayerFocal(outLayerName,nRows,nCols,origin,resolution, nullValue);
		
			double Slope;
			for(int i=0;i<nRows;i++) {
				for(int j=0;j<nCols;j++) {
					if(i>=1&&i<nRows-1&&j>=1&&j<nCols-1){
						double x_slope = ((values[i-1][j+1]+2*values[i][j+1]+values[i+1][j+1])-(values[i-1][j-1]+2*values[i][j-1]+values[i+1][j-1]))/8*resolution;
						double y_slope = ((values[i+1][j-1]+2*values[i+1][j]+values[i+1][j+1])-(values[i-1][j-1]+2*values[i-1][j]+values[i-1][j+1]))/8*resolution;
						Slope = Math.atan(Math.sqrt(Math.pow(x_slope,2)+Math.pow(y_slope,2)))*180/Math.PI;
//						System.out.println(Slope);
					}
					else {
						Slope = nullValue;
					}
					outLayer.values[i][j]=Math.round(Slope);
					System.out.print(" "+outLayer.values[i][j]);	
				}
				System.out.println();
			}
			return outLayer;
		}
			
		public LayerFocal focalAspect(String outLayerName) {
			LayerFocal outLayer = new LayerFocal(outLayerName,nRows,nCols,origin,resolution, nullValue);
			double Aspect;
			double cell;
				for(int i=0;i<nRows;i++) {
					for(int j=0;j<nCols;j++) {
						if(i>=1&&i<nRows-1&&j>=1&&j<nCols-1) {
							double x_slope = ((values[i-1][j+1]+2*values[i][j+1]+values[i+1][j+1])-(values[i-1][j-1]+2*values[i][j-1]+values[i+1][j-1]))/8*resolution;
							double y_slope = ((values[i+1][j-1]+2*values[i+1][j]+values[i+1][j+1])-(values[i-1][j-1]+2*values[i-1][j]+values[i-1][j+1]))/8*resolution;
							Aspect = 180*Math.atan2(y_slope, x_slope)/Math.PI;
//							System.out.print(" "+Aspect);
							if(y_slope==0&&x_slope==0) {
								cell=-1;
							}
							else if(Aspect<0) {
								cell = 90.0-Aspect;	
								}
							else if(Aspect>90) {
								cell = 360.0-Aspect+90;
								}
							else {
								cell=90.0-Aspect;
								}
//							System.out.print(" "+cell);
							}	
						else {
							cell = nullValue;
						}
						outLayer.values[i][j]=Math.round(cell);
						System.out.print(" "+outLayer.values[i][j]);
					}
					System.out.println();
				}
				return outLayer;
		}
		
		public LayerFocal focalMean(int r,boolean IsSquare,String outLayerName) {
			LayerFocal outLayer = new LayerFocal(outLayerName,nRows,nCols,origin,resolution, nullValue);			
			outLayer.values=this.values;	
			outLayer=outLayer.focalSum(r,IsSquare,outLayerName);	
				for(int i= 0 ; i<nRows;i++) {
					for(int j=0;j<nCols;j++) {						
							outLayer.values[i][j] = Math.round(outLayer.values[i][j]/9); 
							
						System.out.print("\t"+outLayer.values[i][j]);
					}
					System.out.println();	
				}			
				return outLayer;
			}	
		public LayerFocal focalRange(int r,boolean IsSquare,String outLayerName) {
			LayerFocal outLayer = new LayerFocal(outLayerName,nRows,nCols,origin,resolution, nullValue);
			for(int i = 0;i < nRows;i++) {
				for (int j = 0; j<nCols;j++) {
					int[] arrayneighbor = getneighbourhood(i,j,r,IsSquare);
					double[] Array = new double[arrayneighbor.length];
					for(int k=0; k <arrayneighbor.length;k++) {
						int m = arrayneighbor[k]/nCols;
						int n = arrayneighbor[k]%nCols;
						Array[k] = values[m][n];
					}
					Arrays.sort(Array);
					outLayer.values[i][j]=Array[Array.length-1]-Array[0];
					System.out.print("\t"+outLayer.values[i][j]);
				}
				System.out.println();
			}
			return outLayer;
		}
	
		
		public int[] getneighbourhood(int row, int col ,int radius, boolean isSquare){
			ArrayList<Integer> l  = new ArrayList<Integer> ();	
			if(isSquare == true) {
				for(int i = row - radius; i <=row + radius; i++) {
					for(int j = col - radius; j <= col + radius; j++) {
						if((i>=0)&&(i<nRows)&&(j>=0)&&(j<nCols)) {
							int neighborObj = i*nCols+j;
							l.add(neighborObj);
						}
						else{
							continue;
						}
					}
				}
				
			}
			else {
				for(int i=0;i<nRows;i++) {
					for(int j =0;j<nCols;j++) {
						if((Math.pow((i-row),2)+ Math.pow((j-col),2))<=Math.pow(radius,2)){
							int neighObj = i*nCols+j;
							l.add(neighObj);
						}
					}
				}
			}
			int [] intObjArray = new int[l.size()];
			int count = 0;
			for(int intObj:l) {
				intObjArray[count] = intObj;
				count++;
				}
			return intObjArray;
			}
		
		public LayerFocal zonalMinimum(LayerFocal zoneLayer, String outLayerName) {
			LayerFocal outLayer = new LayerFocal(outLayerName, nRows, nCols , origin, resolution, nullValue);
			HashMap<Double, Double> hm1 = new HashMap<Double, Double>();			
			for(int i =0;i<nRows;i++) {
				for(int j =0; j<nCols; j++) {
					if(!hm1.containsKey(zoneLayer.values[i][j])) {
						hm1.put(zoneLayer.values[i][j],values[i][j]);
					}
					else {
						if(values[i][j]<hm1.get(zoneLayer.values[i][j])) {
							hm1.put(zoneLayer.values[i][j],values[i][j]);
							}	
						}
					}
				}
				for(int i=0;i<nRows;i++) {
					for(int j =0; j<nCols;j++) {
						outLayer.values[i][j]= hm1.get(zoneLayer.values[i][j]);
					}
				}
				return outLayer;
			}
			
			
		
		
}