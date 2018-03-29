package Domain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
/**
 * Route class represents a collection of Point objects.  This class provides a method 
 * for calculating the length of a route. 
 * @author Joe Heins
 *
 */
public class Route {

	private ArrayList<Point> listOfPoints;
	private static int count = 0;
	private int id;
	private double length;
	
	public Route(){
		count++;
		id = count;
	    listOfPoints = new ArrayList<>();	
	    
	}
	
	public Route(ArrayList<Point> pList) {
		count++;
		id = count;
		listOfPoints = pList;
		
	}
	
	public void add(Point p){
		this.listOfPoints.add(p);
	}
	
	public Route add(Route r){
		
		Route next = new Route();
		
		for (Point p: this.listOfPoints){
			next.listOfPoints.add(p);
		}
		for (Point p: r.listOfPoints){
			next.listOfPoints.add(p);
		}
		return next;
	}
	
	public double getDistance(){
	    length = 0.0;
		for (int i = 0; i < listOfPoints.size() - 1; i++){
			length += listOfPoints.get(i).distanceTo(listOfPoints.get(i+1));
		}
		length += listOfPoints.get(listOfPoints.size()-1).distanceTo(listOfPoints.get(0));
		return length;
	}
	
	@Override
	public String toString(){
		
		String s = "[" + listOfPoints.get(0).getName();
		
        for (int i = 1; i < listOfPoints.size(); i++){
        	s = s + " --> " + listOfPoints.get(i).getName(); 
        }
        s = s + "]";
		return s;
		
	}
	
	public int getID(){
		return id;
	}
	
	public int getNumberofPoints() {
		return listOfPoints.size();
	}

	public ArrayList<Point> getPointList() {
		return this.listOfPoints;
	}
	
	public void setPointList(ArrayList<Point> p) {
		this.listOfPoints = p;
	}
	
	/**
	 * Method that returns a point that has the name passed as an argument
	 * @param pName - String object that is the name of the point being searched for
	 * @return - the point with the matching name
	 */
	public Point getPoint(String pName) {
		Point p1 = null;
		for (Point p: listOfPoints){
			if (p.name.equals(pName)){
				p1 = p;
			}
		}	
		return p1;
	}
	
	/**
	 * Method that shuffles the array list of points
	 * @return a new route with a list of points in a random order
	 */
	public Route randomize() {
		Collections.shuffle(this.listOfPoints);
		Route newRoute = new Route(this.listOfPoints);
		return newRoute;	
	}
	
	/**
	 * Swap method swaps two random sets of Points in the Route
	 * @return a Route that has had two changes to the order of its points
	 */
	public Route swap() {
		
		Route refRoute = new Route(this.listOfPoints);
		Random r = new Random();
		int n = (this.getNumberofPoints()-1);
		int p1 = r.nextInt(n);
		int p2 = (n - p1);
		Collections.swap(refRoute.listOfPoints, p1, p2);
		return refRoute;	
	}
	
	public Route copy() {
		Route rt = new Route();
		for (Point p: this.listOfPoints) {
			rt.add(p);
		}
		return rt;
	}
	


}
