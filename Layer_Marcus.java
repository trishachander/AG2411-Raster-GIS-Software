package se.kth.ag2411.mapalgebra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.*;

public class Layer {
	// Attributes (This is complete)
	public String name; // name of this layer
	public int nRows; // number of rows
	public int nCols; // number of columns
	public double[] origin = new double[2]; // x,y-coordinates of lower-left corner
	public double resolution; // cell size
	public double[][] values; // data. Alternatively, public double[][] values;
	public double nullValue; // value designated as "No data"
	
	public double max = Double.NEGATIVE_INFINITY; 
	public double min = Double.POSITIVE_INFINITY; 
		
	//Constructor 
	public Layer(String layerName, String fileName) {
	
		File rFile = new File (fileName);
		if(rFile.exists()) {
			this.name = layerName;
		
				try { 				
					
					File inputfile = new File(fileName);
					FileReader fReader = new FileReader(inputfile);
					BufferedReader bReader = new BufferedReader(fReader);
					
					String temporary;
						
					temporary = bReader.readLine().substring(14).trim();
					nCols = Integer.parseInt(temporary);
					temporary = bReader.readLine().substring(14).trim();
					nRows = Integer.parseInt(temporary);
					temporary = bReader.readLine().substring(14).trim();
					origin[0] = Double.parseDouble(temporary);
					temporary = bReader.readLine().substring(14).trim();
					origin[1] = Double.parseDouble(temporary);
					temporary = bReader.readLine().substring(14).trim();
					resolution = Double.parseDouble(temporary);
					temporary = bReader.readLine().substring(14).trim();
					nullValue = Double.parseDouble(temporary);
						
					this.values = new double[nRows][nCols];
								
					// Read each of the remaining lines, which represents a row of raster values
					for (int i = 0; i < nRows; i++) {
						
						String arow=bReader.readLine();					
						String[] arow_value=arow.split("\\s+");
						
						for (int j = 0; j < nCols; j++) {
							this.values[i][j]=Double.parseDouble(arow_value[j].trim());
							if (values[i][j] > max) {
								max = values[i][j];
							}
							if (values[i][j] < min) {
								min = values[i][j];
							}
							
						}
						
					}
					
					
					bReader.close();
			 						
					} catch (Exception e) {
						e.printStackTrace();
					}
		}			
		else {
			System.out.println("the file does not exist");
		}
	}
	
	public Layer(String outLayerName, int nRows2, int nCols2, double[] ori, double res, double nullV) {
		this.name = outLayerName;
		this.nRows = nRows2;
		this.nCols = nCols2;
		this.origin[0] = ori[0];
		this.origin[1] = ori[1];
		this.resolution = res;
		this.nullValue = nullV;
		this.values = new double[nRows][nCols];
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
	
	public void save(String outputFileName) {
	
		File file = new File(outputFileName);		
		
		try {
			
			FileWriter fWriter = new FileWriter(file);
			
			fWriter.write("ncols         "+nCols+"\n"); 
			fWriter.write("nrows         "+nRows+"\n"); 
			fWriter.write("xllcorner     "+origin[0]+"\n"); 
			fWriter.write("yllcorner     "+origin[1]+"\n"); 
			fWriter.write("cellsize      "+resolution+"\n"); 
			fWriter.write("NODATA_value  "+nullValue+"\n"); 
			
			for (int i = 0; i < nRows; i++) {
				for (int j = 0; j < nCols; j++) {
					fWriter.write(values[i][j]+" "); 
				}
				fWriter.write("\n");
			}
			
			fWriter.close();
			
		}catch(Exception e) {
			e.printStackTrace();
				
		}
	}
	public BufferedImage toImage() { 
		
		
		BufferedImage image = new BufferedImage(nCols, nRows, BufferedImage.TYPE_INT_RGB); 
		 
		
		WritableRaster raster = image.getRaster(); 
		 
		
		double range = max-min;
		for (int i=0; i<nRows; i++) {
			for (int j=0; j<nCols; j++) {
				int[] color = new int[3]; 
				int temp_col = (int)Math.round((max-values[i][j])/range*255);
				color[0] = temp_col; // Red
				color[1] = temp_col; // Green 
				color[2] = temp_col; // Blue 
				raster.setPixel(j, i, color); // (19,0) is the pixel at the top-right corner. 
			}
		}
		
		return image;
	}
		
	public BufferedImage toImage(double[] v) { 
		BufferedImage image = new BufferedImage(nCols, nRows, BufferedImage.TYPE_INT_RGB); 
		WritableRaster raster = image.getRaster(); 
		
		double range = max-min;
		int length = v.length;
		int[][] colors = new int [length][3];
		
		for (int p=0; p < length; p++) {	
			int[] randomcolor = new int[3];
			randomcolor[0] = (int) Math.round(Math.random()*255);
			randomcolor[1] = (int) Math.round(Math.random()*255);
			randomcolor[2] = (int) Math.round(Math.random()*255);
			colors[p] = randomcolor;
		}
			
		for (int i=0; i<nRows; i++) {
			for (int j=0; j<nCols; j++) {
				int[] color = new int[3]; 
				int temp_col = (int) Math.round((max-values[i][j])*255/range);
				color[0] = (int) temp_col; // Red
				color[1] = (int) temp_col; // Green 
				color[2] = (int) temp_col; // Blue 
				
				for (int p=0; p<length; p++) {
						if (v[p] == values[i][j]) {
							color[0] = colors[p][0]; // Red
							color[1] = colors[p][1]; // Green 
							color[2] = colors[p][2]; // Blue	
					}
				}
				raster.setPixel(j, i, color);
			}
		}	
		return image;					
	}	

	public Layer localSum(Layer inLayer, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (values[i][j] == nullValue || inLayer.values[i][j] == nullValue) {
					outLayer.values[i][j] = nullValue;
				}
				else {
					outLayer.values[i][j] = values[i][j] + inLayer.values[i][j];
				}
			}
		}
		return outLayer;
	}
	
	public Layer localDifference(Layer inLayer, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (values[i][j] == nullValue || inLayer.values[i][j] == nullValue) {
					outLayer.values[i][j] = nullValue;
				}
				else {
					outLayer.values[i][j] = values[i][j] - inLayer.values[i][j];
				}
			}
		}
		return outLayer;
	}
	
	public Layer localProduct(Layer inLayer, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (values[i][j] == nullValue || inLayer.values[i][j] == nullValue) {
					outLayer.values[i][j] = nullValue;
				}
				else {
					outLayer.values[i][j] = values[i][j] * inLayer.values[i][j];
				}
			}
		}
		return outLayer;
	}
	
	public Layer localRatio(Layer inLayer, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (values[i][j] == nullValue || inLayer.values[i][j] == nullValue) {
					outLayer.values[i][j] = nullValue;
				}
				else {
					if (inLayer.values[i][j] == 0) {
						outLayer.values[i][j] = nullValue;
					}
					else {
						outLayer.values[i][j] = values[i][j] / inLayer.values[i][j];
					}
				}
			}
		}
		return outLayer;
	}
	public Layer localMaximum(Layer inLayer, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (values[i][j] == nullValue || inLayer.values[i][j] == nullValue) {
					outLayer.values[i][j] = nullValue;
				}
				else {
					outLayer.values[i][j] = Math.max(values[i][j], inLayer.values[i][j]);
				}
			}
		}
		return outLayer;
	}
	public Layer localMinimum(Layer inLayer, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (values[i][j] == nullValue || inLayer.values[i][j] == nullValue) {
					outLayer.values[i][j] = nullValue;
				}
				else {
					outLayer.values[i][j] = Math.min(values[i][j], inLayer.values[i][j]);
				}
			}
		}
		return outLayer;
	}
	public Layer localMean(Layer inLayer, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (values[i][j] == nullValue || inLayer.values[i][j] == nullValue) {
					outLayer.values[i][j] = nullValue;
				}
				else {
					outLayer.values[i][j] = (values[i][j] + inLayer.values[i][j]) / 2;
				}
			}
		}
		return outLayer;
	}
	
	
	public Layer zonalMinimum(Layer zoneLayer, String outLayerName) {
		Layer zoneOutPutLayer = new Layer(name, nRows, nCols, origin,
				resolution, nullValue);
		HashMap<Double, Double> uniqueZone = new HashMap<>(); 
		double zoneCount = 0;
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (uniqueZone.containsValue(zoneLayer.values[i][j]) == false) {
					zoneCount = zoneCount + 1;
					uniqueZone.put(zoneCount, zoneLayer.values[i][j]); 
				}

			}

		}
		
		HashMap<Double, Double> minZone = new HashMap<>(); 
		for (double z = 1; z <= zoneCount; z++) {
			double minimum = Double.POSITIVE_INFINITY;
			for (int i = 0; i < nRows; i++) {
				for (int j = 0; j < nCols; j++) {
					if (zoneLayer.values[i][j] == uniqueZone.get(z)) { 
						if (values[i][j] < minimum) {
							minimum = values[i][j];
						}
					}
				}
			}
			minZone.put(z, minimum); 
		}
		
		for (double z = 1; z <= zoneCount; z++) {
			for (int i = 0; i < nRows; i++) {
				for (int j = 0; j < nCols; j++) {
					if (zoneLayer.values[i][j] == uniqueZone.get(z)) {  
						zoneOutPutLayer.values[i][j] = minZone.get(z);
					}

				}
			}
		}

		return zoneOutPutLayer;
	}
	

	public int[][] getNeighborhood(int i, int j, int radius, boolean square) { // Gets index-values for neighborhood.
		ArrayList<int[]> neighborhood = new ArrayList<int[]>();
		
		int U = i - radius;
		int D = i + radius;
		int L = j - radius;
		int R = j + radius;
		
		
		for (int n = Math.max(U, 0); n <= Math.min(D, nRows-1); n++) { 
			for (int m = Math.max(L,0); m <= Math.min(R, nCols-1); m++) {
				int[] neighbor = new int[2];

				neighbor[0] = n;
				neighbor[1] = m;
				
				if (square) {
					neighborhood.add(neighbor);
					
				}
				else {
					if((Math.pow((i-n), 2) + Math.pow((j-m),2)) <= Math.pow(radius, 2)) {	
						neighborhood.add(neighbor);
				}
				
				}
			}
			}
			int[][] neighborCells= new int[neighborhood.size()][2];
			for (int k=0; k<neighborhood.size();k++) {
				neighborCells[k] = neighborhood.get(k);
				

			
			}
		return neighborCells;
	}

	
	public Layer focalVariety(int radius, boolean square, String outLayerName) {
		Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
		
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				int[][] neighborCell;
				neighborCell = getNeighborhood(i, j, radius, square);
				double varietyCount = 0;
				HashMap<Double, Double> uniqueValues = new HashMap<Double, Double>();
				for (int p = 0; p < neighborCell.length; p++) {
					double cellValue = values[neighborCell[p][0]][neighborCell[p][1]];
					if (uniqueValues.containsValue(cellValue) == false) { 
							varietyCount = varietyCount + 1;
							uniqueValues.put(varietyCount, cellValue);
						
						}
					
					}
				outLayer.values[i][j] = varietyCount;
				
			}
		}
		return outLayer;
	}
	
	public Layer zonalMean(Layer zoneLayer, String outLayerName) {
		Layer zoneOutPutLayer = new Layer(name, nRows, nCols, origin,
				resolution, nullValue);
		HashMap<Double, Double> uniqueZone = new HashMap<>(); // Save all zones.
		double zoneCount = 0;
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (uniqueZone.containsValue(zoneLayer.values[i][j]) == false) { // Does the value exist in zonemap?
					zoneCount = zoneCount + 1;
					uniqueZone.put(zoneCount, zoneLayer.values[i][j]); // If not, add it.
				}
			}
		}
		
		HashMap<Double, Double> meanZone = new HashMap<>(); 
		for (double z = 1; z <= zoneCount; z++) {
			ArrayList<Double> zoneValues = new ArrayList<Double>();
			double mean = 0;
			for (int i = 0; i < nRows; i++) {
				for (int j = 0; j < nCols; j++) {
					if (zoneLayer.values[i][j] == uniqueZone.get(z)) { 
						zoneValues.add(values[i][j]);
						}
					}
				}
			double total = 0;
			for (int k = 0; k<=zoneValues.size()-1; k++) {
				total = total + zoneValues.get(k);
				mean = total/zoneValues.size();
			}
			meanZone.put(z, mean); 
		}
		
		for (double z = 1; z <= zoneCount; z++) {
			for (int i = 0; i < nRows; i++) {
				for (int j = 0; j < nCols; j++) {
					if (zoneLayer.values[i][j] == uniqueZone.get(z)) { 
						zoneOutPutLayer.values[i][j] = meanZone.get(z);
					}
				}
			}
		}
		return zoneOutPutLayer;
	}
	public Layer zonalMajority(Layer zoneLayer, String outLayerName) {
		Layer zoneOutPutLayer = new Layer(name, nRows, nCols, origin,
				resolution, nullValue);
		HashMap<Double, Double> uniqueZone = new HashMap<>(); // Save all zones.
		double zoneCount = 0;
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (uniqueZone.containsValue(zoneLayer.values[i][j]) == false) { // Does the value exist in zonemap?
					zoneCount = zoneCount + 1;
					uniqueZone.put(zoneCount, zoneLayer.values[i][j]); // If not, add it.
				}
			}
		}
		
		HashMap<Double, Double> majorityValues = new HashMap<>(); 
		for (double z = 1; z <= zoneCount; z++) {
			ArrayList<Double> zoneValues = new ArrayList<Double>();
			double majorityCount = 0;
			double majorityValue = 0;
			for (int i = 0; i < nRows; i++) {
				for (int j = 0; j < nCols; j++) {
					if (zoneLayer.values[i][j] == uniqueZone.get(z)) { 
						zoneValues.add(values[i][j]);
						}
					}
				}
			HashMap<Double, Integer> valueCount = new HashMap<>();
			for (int k = 0; k<=zoneValues.size()-1; k++) {
				double zoneValue = zoneValues.get(k);
				if (!valueCount.containsKey(zoneValue)) {
					valueCount.put(zoneValue, 0);
				}
				if (valueCount.containsKey(zoneValue)) {
					valueCount.computeIfPresent(zoneValue, (value, count) -> count + 1);
				}
			}
			for (double key : valueCount.keySet()) {
				if (valueCount.get(key) > majorityCount) {
					majorityCount = valueCount.get(key);
					majorityValue = key;
				}
				else if (valueCount.get(key) == majorityCount){
					if (key < majorityValue) {
						majorityCount = valueCount.get(key);
						majorityValue = key;
					}
				}
				
			}
			majorityValues.put(z, majorityValue); 
		}
		
		for (double z = 1; z <= zoneCount; z++) {
			for (int i = 0; i < nRows; i++) {
				for (int j = 0; j < nCols; j++) {
					if (zoneLayer.values[i][j] == uniqueZone.get(z)) { 
						zoneOutPutLayer.values[i][j] = majorityValues.get(z);
					}
				}
			}
		}
		return zoneOutPutLayer;
	}
	
}




		

