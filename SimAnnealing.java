package Domain;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class SimAnnealing {

	static TSPGetData data = new TSPGetData("Files/TSPdata.txt");
	static Route initial = data.getRoute();
	static double temp = 10000;
	static double cRate = 0.0003;
	static double totalSqr;
	static double mean;
	static double total;
	static int[] histogram = new int[100];
	static double dx;
	static double shortest = 19.6;
	static double longest = 0;
	static double count = 0;
	
	public static void main(String[] args) {

		// Generate a new random route
		
		Route rInitial = initial.randomize();
		dx = (19.6) / 100;
		

		while (temp > 1) {
			// Generate a new copy of the route
			Route nRoute = rInitial.copy();
			//Mutate the route
			nRoute = nRoute.swap();
			
			// Get the distances for each route
			double initDistance = rInitial.getDistance();
			double nDistance = nRoute.getDistance();
			
			if (nDistance < initDistance) {
				count++;
				total += nDistance;
				totalSqr += nDistance*nDistance;
				int binNum = (int) Math.abs((nDistance)/dx);
				histogram[binNum]++;
				if (nDistance < shortest) {
					shortest = nDistance;
				}
				rInitial = new Route(nRoute.getPointList());	
			}
			
			else if (nDistance > initDistance) {
				// Get the probability
				double pOfRetain = probabilityCalc(initDistance, nDistance, count);
				// Get a random value for comparison
				double z = Math.random();
				if (pOfRetain > z) {
					count++;
					total += nDistance;
					totalSqr += nDistance*nDistance;
					int binNum = (int) Math.abs((initDistance) / dx);
					histogram[binNum]++;
					if (nDistance > longest) {
						longest = nDistance;
					}
					rInitial = new Route(nRoute.getPointList());
				}	
			}
			
			temp *= (1 - cRate);
		
		}
		mean = total/count;
		System.out.println("The mean for SA is: " + mean);
		System.out.println("The length of the longest route is: " + longest);
		System.out.println("The length of the shortest route is: " + shortest);
		double variance = (totalSqr - ((total * total) / count)) / (count - 1);
		double stanDev = Math.sqrt(variance);
		System.out.println("The standard deviation is: " + stanDev);
		createScaledHistogramFile("Files/SimAnnealingScaled.txt");
		createHistogramFile("Files/SimAnnealingData.txt");

	}

	public static double probabilityCalc(double dist, double newDist, double temp) {
			return Math.exp((dist - newDist) / temp);
		}
	

	private static void createScaledHistogramFile(String fileName) {
		double max = 0;
		for (int i = 0; i < histogram.length; i++)
			if (histogram[i] > max){
				max = histogram[i];
			}
		
		try {
			File outFile = new File(fileName);
			outFile.createNewFile();
			PrintWriter histo = new PrintWriter(outFile);
			for (int i = 0; i < histogram.length; i++){
				double temp = histogram[i]/max;
				histo.println(temp);
			}
			histo.close();
		}
		catch (IOException e) {
			System.out.print(e);
		}
	}	
	
	private static void createHistogramFile(String fileName) {
		
		try {
			File outFile = new File(fileName);
			outFile.createNewFile();
			PrintWriter histo = new PrintWriter(outFile);
			for (int i = 0; i < histogram.length; i++){
				double temp = histogram[i];
				histo.println(temp);
			}
			histo.close();
		}
		catch (IOException e) {
			System.out.print(e);
		}
	}	
}
