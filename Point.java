package Domain;

/**
 * Point class will be used to represent points in the TSP problem.
 * Each point has an x coordinate, a y coordinate and a name.
 * This class also provides the method for calculating the distance between two points.
 * 
 * @author Joe Heins
 *
 */
public class Point {
	double x;
	double y;
	String name;
	int id;
		
	/**
	 * Contstructor with no parameters
	 */
	public Point(){
		
	}
	
	/**
	 * Constructor that takes three parameters. 
	 * @param double x value, double y value and a String name
	 */
	public Point(double xIn, double yIn, String nameLetter) {
		x = xIn;
		y = yIn;
		name = nameLetter;
		char temp = name.charAt(0);
		id = (int) temp - 64;
	}

	/**
	 * Getter method for the x coordinate
	 * @return double value that is the x coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * Setter method for the x coordinate
	 * @param double value that will become the x coordinate of the point
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Getter method for the y coordinate
	 * @return double value that is the y coordinate of the point
	 */
	public double getY() {
		return y;
	}

	/**
	 * Setter method for the y value of the point
	 * @param double value that will become the y coordinate of the point
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Getter method for the point name
	 * @return String value of the point name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method for the point name
	 * @param id the id to set
	 */
	public void setName(String nameLetter) {
		this.name = nameLetter;
	}
	
	/**
	 * Getter method for the integer id value
	 * @return integer value that represents the id of the point
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Method that calculates the distance between two points
	 * @param point that represents the next point
	 * @return double distance value
	 */
	public double distanceTo(Point p) {
		double x = Math.pow((p.getX() - this.getX()),2);
        double y = Math.pow((p.getY() - this.getY()),2);
        double distance = Math.sqrt((x + y));
        return distance; 
	}
	
	public boolean equals(Object o) {
		Point p = new Point();
		p = (Point) o;
		return (this.getName().equals(p.getName()));	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
