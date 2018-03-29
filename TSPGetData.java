package Domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class that takes data from a file and constructs a Route object
 * @author Joe Heins
 */
public class TSPGetData {
	
	private Route route;
	double [][] distanceMatrix;
	
	public TSPGetData() {
		
	}
	/**
	 * Constructor that takes a file to build the initial route
	 * @param file that contains the point data
	 */
	public TSPGetData(String file) {
		route = getData(file);
		buildMatrix();
	}
	/**
	 * Getter that returns the route variable
	 * @return the route variable 
	 */
	public Route getRoute() {
		return this.route;
	}
	
	private Route getData(String fileName) {
		
		int numOfLines = getNumberOfLines(fileName);
		route = new Route();
		try {
			  Scanner s = new Scanner(new File(fileName));
			  
			  for (int i = 0; i < numOfLines; i++) {
	              String line = s.nextLine();
	              String [] numbersIn = line.split(" ");
	              
	              double x = Double.parseDouble(numbersIn[0]);
	              double y = Double.parseDouble(numbersIn[1]);
	              String name = numbersIn[2];
	              Point p = new Point(x,y,name);
	              route.add(p);
			  }
		s.close();
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return route;
	}
	
	public void buildMatrix() {
		int n = route.getNumberofPoints();
		distanceMatrix = new double [n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				distanceMatrix[i][j] = 
				route.getPointList().get(i).distanceTo(route.getPointList().get(j));
	}
	
	
	private int getNumberOfLines(String file) {
		int countRows = 0;
        try {
            File fileIn = new File(file);
            Scanner input = new Scanner(fileIn);
            
            while (input.hasNextLine()) {
                countRows++;
                input.nextLine();
            }
            
            input.close();
            
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return countRows; 
    }
		
		
	
	}
	
	
	
	
	
	
	
	
	
	
	

