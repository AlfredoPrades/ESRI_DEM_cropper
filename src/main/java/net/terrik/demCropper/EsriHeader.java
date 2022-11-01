package net.terrik.demCropper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class EsriHeader {


	private int nCols;
	private int nRows;
	private int xCenter;
	private int yCenter;
	private int cellSize;
	private Float noDataValue;

	public void addData (String line, int currentLine) {
		Float rawValue = Float.parseFloat(StringUtils.split(line," ")[1]);
		Integer value = (int) Math.floor(rawValue); 
		
		switch (currentLine) {
		case 1: 
			nCols = value; 
			break;
		case 2: 
			nRows = value; 
			break;
		case 3: 
			xCenter = value;
			break;
		case 4: 
			setyCenter(value);
			break;
		case 5: 
			cellSize = value;
			break;
		case 6: 
			noDataValue = rawValue;
			break;
		}
	}


	public int getnCols() {
		return nCols;
	}
	public void setnCols(int nCols) {
		this.nCols = nCols;
	}
	public int getnRows() {
		return nRows;
	}
	public void setnRows(int nRows) {
		this.nRows = nRows;
	}
	public int getxCenter() {
		return xCenter;
	}
	public void setxCenter(int xCenter) {
		this.xCenter = xCenter;
	}
	public int getCellSize() {
		return cellSize;
	}
	public void setCellSize(int cellSize) {
		this.cellSize = cellSize;
	}
	public float getNoDataValue() {
		return noDataValue;
	}
	public void setNoDataValue(float noDataValue) {
		this.noDataValue = noDataValue;
	}


	public static EsriHeader builHeader(File inputFile) throws FileNotFoundException {
		Scanner scaner = new Scanner(inputFile);
		int currentLine = 1;
		EsriHeader head = new EsriHeader();
		while (scaner.hasNext() && currentLine <= 6) {
			String line = scaner.nextLine();
			head.addData(line, currentLine++);

		}
		scaner.close();
		return head;
	}


	public int getyCenter() {
		return yCenter;
	}


	public void setyCenter(int yCenter) {
		this.yCenter = yCenter;
	}

}
