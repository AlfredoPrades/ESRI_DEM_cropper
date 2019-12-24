package es.terrik.demCroppder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;


public class DemCropper {

	private static final int NUM_HEADLINES = 6;
	int minx;
	int maxx;
	int miny;
	int maxy;
	File inputFile;
	File outputFile;
	private EsriHeader header;
	private FileWriter fw;

	public DemCropper(int minx, int maxx, int miny, int maxy, File inputFile, File outputFiles) {
		super();
		this.minx = minx;
		this.maxx = maxx;
		this.miny = miny;
		this.maxy = maxy;
		this.inputFile = inputFile;
		this.outputFile = outputFiles;
	}
	
	public void process() {
		
		
		
		try {
			header = EsriHeader.builHeader(inputFile);
			
			validateParameters();
			

			try {
				fw = new FileWriter(outputFile);
				
				writeNewHeader();
				writeCroppedValues();
				


			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
			System.out.println("END");

		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		}
		
		
		
		
	}

	private void writeCroppedValues() throws IOException {
		Scanner fileScaner = new Scanner(inputFile);
		long currentLineNumber = 0;

		
		String currentLine;
		int yPosition = 0 ;
		while (fileScaner.hasNextLine() ) {
			System.out.println("Row : " + yPosition++ );
			currentLine = fileScaner.nextLine();
			
			// Skip Header
			if (++currentLineNumber <= NUM_HEADLINES || currentLineNumber - NUM_HEADLINES  < miny ) {
				continue;
			}
			if ( currentLineNumber - NUM_HEADLINES > maxy) {
				break;
			}

			Scanner lineScanner = new Scanner(currentLine);
			int xPosition = 0;
			while (lineScanner.hasNext()) {
				String token = lineScanner.next();
				xPosition++;
				if (xPosition < minx ) {
					continue;
				}
				if ( xPosition > maxx) {
					break;
				}

				IOUtils.write(token+" ", fw);
			}
			IOUtils.write("\r\n", fw);
			lineScanner.close();
			
		}
		fileScaner.close();

		
	}

	private void writeNewHeader() throws IOException {
		IOUtils.write("NCOLS ", fw);
		IOUtils.write((maxx - minx+1)+"\r\n", fw);
		IOUtils.write("NROWS ", fw);
		IOUtils.write((maxy - miny+1)+"\r\n", fw);
		IOUtils.write("XLLCENTER ", fw);
		IOUtils.write(header.getxCenter()+"\r\n", fw);
		IOUtils.write("YLLCENTER ", fw);
		IOUtils.write(header.getyCenter()+"\r\n", fw);
		IOUtils.write("CELLSIZE ", fw);
		IOUtils.write(header.getCellSize()+"\r\n", fw);
		IOUtils.write("NODATA_VALUE ", fw);
		IOUtils.write(header.getNoDataValue()+"\r\n", fw);
		
	}

	private void validateParameters() {
		String msg ="";
		if (minx < 1 ) {
			msg = "-> minx < que 1.\n\n";
		}
		
		if ( minx >=  maxx ) {
			msg ="-> minx > que maxx.\n\n";
		}
		
		if  (minx >= header.getnCols()) {
			msg ="-> minx > COLS header.\n\n";
		}

		if  (maxx >= header.getnCols()) {
			msg ="-> maxx > COLS header.\n\n";
		}

		if (miny < 1 ) {
			msg = "-> miny < que 1.\n\n";
		}
		
		if ( miny >=  maxy ) {
			msg ="-> miny > que maxy.\n\n";
		}
		
		if  (miny >= header.getnCols()) {
			msg ="-> miny > COLS header.\n\n";
		}

		if  (maxy >= header.getnRows()) {
			msg ="-> maxy > ROWS header.\n\n";
		}
		
		if (!inputFile.exists()) {
			msg ="Input File doesnt exist\r\n";
		}
		
		if (StringUtils.isNotEmpty(msg)) {
			System.out.println(msg);
			throw new InvalidParameterException();
		}
		else {
			System.out.println("Parameters ok");
		}
	}
	
}
