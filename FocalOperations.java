package se.kth.ag2411.mapalegra;

public class FocalOperations {
	
	public static void main(String[] args) {
		String operation = args[0];
		
	if (operation.equals("focalVariety")) {
		LayerFocal inLayer1 = new LayerFocal("", args[1]);
		LayerFocal outLayer = inLayer1.focalVariety(1,true,"");
		outLayer.save("./data/focalVariety.txt");
		// perform focalVariety and save & visualize the output layer
	}else if (operation.equals("focalSum")){
		LayerFocal inLayer1 = new LayerFocal("", args[1]);
		LayerFocal outLayer = inLayer1.focalSum(1,true,"");
		outLayer.save("./data/focalSum.txt");
	}else if (operation.equals("focalMaximum")) {
		LayerFocal inLayer1 = new LayerFocal("", args[1]);
		double res = inLayer1.focalMaximum(1,true,"");
		System.out.println("The value of focalMaximum is:"+res);
	}else if(operation.equals("focalMinimum")) {
		LayerFocal inLayer1 = new LayerFocal("", args[1]);
		double res = inLayer1.focalMinimum(1,true,"");
		System.out.println("The value of focalMinimum is:"+res);
	}else if(operation.equals("focalSlope")){
		LayerFocal inLayer1 = new LayerFocal("",args[1]);
		LayerFocal outLayer = inLayer1.focalSlope("");
		outLayer.save("./data/focalSlope.txt");
	}else if(operation.equals("focalAspect")){
		LayerFocal inLayer1 = new LayerFocal("",args[1]);
		LayerFocal outLayer = inLayer1.focalAspect("");
		outLayer.save("./data/focalAspect.txt");
	}else if(operation.equals("focalMean")) {
		LayerFocal inLayer1 = new LayerFocal("",args[1]);
		LayerFocal outLayer = inLayer1.focalMean(1,true,"");
		outLayer.save("./data/focalStatistic.txt");		
	}else if(operation.equals("focalRange")) {
		LayerFocal inLayer1 = new LayerFocal("",args[1]);
		LayerFocal outLayer = inLayer1.focalRange(1,true,"");
		outLayer.save("./data/focalRange.txt");	
	}else {
		System.out.print(operation + " is not currently available.");
		}
	
	}
	
}
