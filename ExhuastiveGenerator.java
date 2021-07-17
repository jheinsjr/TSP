package TSP;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ExhuastiveGenerator {
	static Route rt;
	static int[] val;
	static int now = 0;
	static String[] pList;
	static int V = 14;
	static double count = 0;
	static double total = 0.0;
	static double mean;
	static double max = 0;
	static double min = 20;
	static Route longest;
	static Route shortest;
	static double totalSqr;
	static int[] histogram = new int [100];
	static double dx;
	static final double START = 0;
	static final double END = 19.6;

	public static void main(String arg[]) {

		TSPGetData data = new TSPGetData("Files/TSPdata.txt");
		rt = data.getRoute();
	    dx = (END - START)/100;
		pList = new String[V + 1];
		val = new int[V + 1];
		for (int i = 0; i <= V; i++)
			val[i] = 0;

		long startTime = System.currentTimeMillis();
		p(1);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		mean = total / count;
		System.out.println("Exhaustive Search: ");
		System.out.println("Mean: " + mean);
		System.out.println("The max distance route is: " + max);
		System.out.println("The min distance route is: " + min);
		System.out.println("Longest Route: \n" + longest);
		System.out.println("Shortest Route: \n" + shortest);
		System.out.println("Time for the search: " + totalTime);
		double variance = (totalSqr - ((total * total) / count)) / (count - 1);
		double stanDev = Math.sqrt(variance);
		System.out.println(stanDev);
		createHistogramFile("Files/ExhaustiveData.txt");
		createScaledHistogramFile("Files/ExhaustiveScaled.txt");
			
	}

	public static void p(int k) {
		now++;
		val[k] = now;
		if (now == V)
			handleP();
		for (int i = 1; i <= V; i++)
			if (val[i] == 0)
				p(i);
		now--;
		val[k] = 0;
	}

	public static void handleP() {
	
	Route nRoute = new Route();
    count = count + 1.0;
    
    for (int i=1; i<=V; i++){
      pList[i] = Character.toString((char)(val[i] + 64));}
    
    for (int j=1; j<pList.length; j++){
    	Point p2 = new Point();
    	p2 = rt.getPoint(pList[j]);
    	nRoute.add(p2);
    }
    double distance = nRoute.getDistance();
    
    if (distance>max){
    	max = distance;
    	longest = nRoute;
    }
    if (distance<min){
    	min = distance;
    	shortest = nRoute;
    }
    
    //Calculate the total distance and total distance^2.
    total += distance;
    totalSqr += (distance * distance);   
    
    //Add distances to the histogram array
    int binNum = (int) (Math.abs((distance - START)/dx));
    histogram[binNum]++;
      
  }

	/**
	 * Private factorial method for calculating divisor of (Max-Min/n)
	 * @param integer value that is the factorial number
	 * @return a double value
	 */
	@SuppressWarnings("unused")
	private static double factorial(int fNumber) {
		double result = 1;
		for (int i = 1; i <= fNumber; i++)
			result *= i;

		return result;
	}
	
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
