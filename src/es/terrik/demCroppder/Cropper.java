package es.terrik.demCroppder;

import java.io.File;



public class Cropper {

	public static void main(String[] args) {

		if (args.length != 5 ) {
			printHelp();
			System.exit(1);
		}
		
		File inputFile = new File(args[0]);
		int xMin = Integer.parseInt(args[1]);
		int xMax = Integer.parseInt(args[2]);
		int yMin = Integer.parseInt(args[3]);
		int yMax = Integer.parseInt(args[4]);
		
		
		File outputFile = new File(inputFile.getAbsoluteFile()+"_Cropped.asc");
		
		DemCropper dc = new DemCropper(xMin, xMax, yMin, yMax, inputFile, outputFile);
		dc.process();
		
		
		
	}

	private static void printHelp() {
		System.out.println("Parametros: dem_file xmin xmax ymin ymax");
	}
	
	

}
