package Domain;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {

	static int[] histogram = new int[100];
	static int count = 0;
	static Route[] population;
	static ArrayList<Route> initPopulation = new ArrayList<>();
	static double total = 0;
	static double longest = 0;
	static double shortest = 20;
	static double mean;
	static double totalSqr;
	static double dx = 19.6/100;
	static Route longRoute;
	static Route shortRoute;
	
	
	public static void main(String[] args) {

		// Generate the random population
		genRandomPopulation();
		
		ArrayList<Route> initial = initPopulation;
		int loopNumber = 1000;
		while (loopNumber > 1) {
			ArrayList<Route> nextGen = new ArrayList<>();
			nextGen = getNextGen(initial);
			for(Route rt : nextGen){
				double tempDist = rt.getDistance();
				total+= tempDist;
				totalSqr += tempDist*tempDist;
				count++;
				if (tempDist>longest){
					longest = tempDist;
					longRoute = rt;
				}
				if (tempDist<shortest){
					shortest = tempDist;
					shortRoute = rt;
				}
				//Add distances to the histogram array
			    int binNum = (int) (Math.abs((tempDist)/dx));
			    histogram[binNum]++; 
			}
			    initial = getNextGen(nextGen);
			    loopNumber--;
			
		}
		mean = total/count;
		System.out.println("The mean is: " + mean);
		System.out.println("The length of the longest route is: " + longest);
		System.out.println(longRoute);
		System.out.println("The length of the shortest route is: " + shortest);
		System.out.println(shortRoute);
		double variance = (totalSqr - ((total * total) / count)) / (count - 1);
		double stanDev = Math.sqrt(variance);
		System.out.println("The standard deviation is: " + stanDev);
		createScaledHistogramFile("Files/GAScaled.txt");
		createHistogramFile("Files/GeneticData.txt");
	}

	/**
	 * Generates a random population for the 2D array initPopulation
	 */
	public static void genRandomPopulation() {
		
		Route rt;
		Route temp;
		int n = 100;

		for (int i = 0; i < n; i++) {
			TSPGetData data = new TSPGetData("Files/TSPdata.txt");
			temp = data.getRoute();
			rt = temp.randomize();
			initPopulation.add(rt);
		}
		
	}

	/**
	 * Generates an array list of two Routes that will be the parents to generate
	 * offspring.
	 * @param population of Routes as an array
	 * @return an array of 2 parent Routes
	 */
	public static ArrayList<Route> getParents(ArrayList<Route> pop2) {

		int n = pop2.size();
		
		// Get the least fit of the population and cut the bottom 25%
		double leastFit = getMax(pop2);
		leastFit = leastFit - (leastFit * .25);

		// Generate an array of parents
		ArrayList<Route> parents = new ArrayList<>();
		
		// Check two random parents for above bottom 25%
		while (parents.size()<2){
			Random r = new Random();
			int r1 = r.nextInt(n-1);
			int r2 = r.nextInt(n-1);
			double temp1 = pop2.get(r1).getDistance();
			double temp2 = pop2.get(r2).getDistance();
			if (temp1 <= leastFit) {
				parents.add(pop2.get(r1));
					
			}
			else if (temp2 <= leastFit) {
				parents.add(pop2.get(r2));
				
			}
			else { 
				parents.add(pop2.get(0));
				
			}
		}
		
		return parents;
	}

	/**
	 * Method that finds the maximum route distance in the population. (The
	 * "least fit")
	 * @param a 2D array that is a population of routes
	 * @return a double that is the largest distance for the population or routes
	 */
	private static double getMax(ArrayList<Route> A) {

		int m = A.size();
		double max = 0;
		double tempDistance;

		// iterate through to get max distance
		for (int i = 0; i < m; i++) {
			tempDistance = A.get(i).getDistance();
			if (max < tempDistance) {
				max = tempDistance;
			}
		}
		return max;
	}

	
	/**
	 * Method that generates a route that is the combination of two parent routes
	 * @param p an array of two routes that represent the parent routes
	 * @return a Route that is the offspring of two parents passed as agruements
	 */
	public static Route genOffspring(ArrayList<Route> p) {

		ArrayList<Point> mom;
		ArrayList<Point> dad;
		mom = p.get(0).getPointList();
		dad = p.get(1).getPointList();
		
		Route offspring = new Route();

		Point[] m1 = new Point[6];
		Point[] m2 = new Point[8];
		Point[] d1 = new Point[6];
		Point[] d2 = new Point[8];

		for (int i = 0; i < 6; i++) {
			m1[i] = mom.get(i);
			d1[i] = dad.get(i);
		}

		for (int j = 0; j < 8; j++) {
			m2[j] = mom.get(j + 6);
			d2[j] = dad.get(j + 6);
		}
		while (offspring.getPointList().size()<14){
		// Combine mom and dad
		for (int i = 0; i < 6; i++)
			offspring.add(m1[i]);
		
		for (int j = 0; j < 8; j++){
			if(!offspring.getPointList().contains(d2[j])){
				offspring.add(d2[j]);
			}
			
			if(!offspring.getPointList().contains(m2[j])){
				offspring.add(m2[j]);
			}
		}
	}
		
		return offspring;
	}

	/**
	 * Method that generates a new population of Routes. The population size is 1000.
	 * @param pop1 an initial population of Routes
	 * @return an array list of routes that is the next generation
	 */
	public static ArrayList<Route> getNextGen(ArrayList<Route> pop1) {
		double leastFit = 0;
		ArrayList<Route> nextGen = new ArrayList<>();
		
		for (Route rt: pop1){
			double temp = rt.getDistance();
			if (temp > leastFit){
				leastFit = temp;
			}
		}
		leastFit = leastFit - leastFit * .25;
		
		while (nextGen.size() < 100) {
			// Get parents from the initial random population
			ArrayList<Route> parents = getParents(pop1);
			// Get the offspring or "child" of the parents
			Route offspring = genOffspring(parents);
			// Get a mutation
			Route mutant = offspring.swap();
			// Add the offspring and the mutant to a new population
			if (offspring.getDistance()<leastFit){
				nextGen.add(offspring);
			}
			nextGen.add(mutant);
		}
		return nextGen;
	}
	/**
	 * Method that creates generations using recursion. 
	 * @param n - the number of generations that are created
	 * @return - the final population after n number of generations
	 */
	public static ArrayList<Route> generations(int n) {
		ArrayList<Route> nextGeneration = new ArrayList<>();
		if (n == 1) {
			nextGeneration = getNextGen(initPopulation);
		} else {
			
			for(Route rt : nextGeneration){
				double tempDist = rt.getDistance();
				total+= tempDist;
				totalSqr += total*total;
				count++;
				if (tempDist>longest){
					longest = tempDist;
					longRoute = rt;
				}
				if (tempDist<shortest){
					shortest = tempDist;
					shortRoute = rt;
				}
				
				//Add distances to the histogram array
			    int binNum = (int) (Math.abs((tempDist)/dx));
			    histogram[binNum]++;   
			}
			
		}
		return generations(n-1);
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
