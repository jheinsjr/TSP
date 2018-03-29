package Domain;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class RandomSearch {

	static int n = 1000000;
	static Route longest;
	static Route shortest;
	static double stanDev;
	static double mean;
	static int [] histogram = new int[100];
	static double total;
	static double max;
	static double min=20;
	static double totalSqr;
	static final double dx = 19.6 / 100;
	
	
	public static void main(String[] args) {
	
		Route rt = new Route();
		TSPGetData data = new TSPGetData("Files/TSPdata.txt");
		rt = data.getRoute();
		
		//Get the time and loop through the random routes
		long startTime = System.currentTimeMillis();
		for (int i = n; i > 0; i--){
			double distance = 0;
			Route nRoute = new Route();
			nRoute = rt.randomize();
			
			distance = nRoute.getDistance();
			total += distance;
			totalSqr += distance*distance;
			
			if (distance>max){
		    	max = distance;	
		    }
			if (distance<min){
				min = distance;	
			}
			int binNum = (int) (Math.abs((distance)/dx));
			histogram[binNum]++;
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		
		mean = total/n;
		double variance = (totalSqr - ((total * total) / n)) / (n - 1);
		double stanDev = Math.sqrt(variance);
		
		
		System.out.println("Random Search:");
		System.out.println("Mean: " + mean);
		System.out.println("Max Distance: " + max);
		System.out.println("Min Distance: " + min);
		System.out.println("Standard Deviation: " + stanDev);
		System.out.println("Time for the search: " + totalTime);
		createScaledHistogramFile("Files/RandomScaled.txt");
		createHistogramFile("Files/RandomData.txt");

	}
	

	/**
	 * Creates a text file with the values of the histogram array 
	 * @param fileName - a string file name that will have the histogram data written 
	 * to it.
	 *
	 */
	private static void createScaledHistogramFile(String fileName) {
		
		double maxFreq = 0;
	
		for (int i = 0; i < histogram.length;i++){
			double temp = histogram[i];
			if (temp>maxFreq) {
				maxFreq = temp;
			}
		}
		
		try {
			File outFile = new File(fileName);
			outFile.createNewFile();
			PrintWriter histo = new PrintWriter(outFile);
			for (int i = 0; i < histogram.length; i++)
				histo.println(histogram[i]/maxFreq);
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
