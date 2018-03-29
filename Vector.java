package Domain;

/**
 * Vector class represents a vector or a single array of a matrix
 * @author Joe Heins
 */
public class Vector {
    
    double [] vector;
    int n;
    
    public Vector() {
        
    }
    
    public Vector(double [] v) {
        vector = v;
        n = vector.length;
    }
    
    public double [] getVector() {
        return this.vector;
    }
    
    public void setVector(double [] x) {
        this.vector = x;
    }
    
    //prints a vector
    public void printVector() {
    	int n = getVector().length;
        System.out.println();
        for (int i = 0; i < n; i++){
            System.out.print(getVector()[i] + "  ");
        }
    }
    //subtract two vectors to produce a vector (a - b = c)
    public double [] difference(double [] b) {
        int x = this.n;
        int y = b.length;
        double [] c = new double[x];
        if (x == y) {
            for (int i = 0; i < x; i++)
                c[i] = this.vector[i] - b[i];
            
        return c;
        }
        else {
            System.out.println("Error: Incorrect vector parameters");
            return null;
        }
    }
    
    //multiply two vectors to produce a Matrix (a * b = c)
    public double [][] productOfTrans(double [] a, double [] b) {
        int x = a.length;
        int y = b.length;
        double [][] c = new double[x][x];
        if (x == y) {
            for (int i = 0; i < x; i++)
                for (int j = 0; j < y; j++)
                    c[i][j] = a[i] * b[j];
        return c;
        }
        else {
            System.out.println("Error: Incorrect vector parameters");
            return null;
        }
    }
    //multiply a vector by a scalar (v * s = v2)
    public  double [] vectorTimesScalar(double s) {
        double [] x = new double [this.n];
        for (int i = 0; i < n; i++) {
            x[i] = s * this.getVector()[i];
        }
        return x;
    }
    
    //toString method for the Vector class
    @Override
    public String toString(){
    	String s = "";
    	for (int i = 0; i < this.n; i++){
    		s = s + this.getVector()[i] + " ";
    	}
    	return s;	
    }
    
    /**
     * Find and return the max value of a vector
     * @return the max absolute value of a vector
     */
    public double maxValue(){
    	
    	double max = Math.abs(this.getVector()[0]);
    	
    	for (int i = 0; i < this.getVector().length; i++){
    		if (Math.abs(this.getVector()[i]) > max){
    			max = Math.abs(this.getVector()[i]);
    		}
    	}
		return max;		
    }
    
    /**
     * Dot product returns the multiplication of two vectors
     * @return a double value that is the dot product of two vectors
     */
    public double dotProduct(Vector B) {
    	
    	int n1 = this.getVector().length;
    	int n2 = B.getVector().length;
    	double C = 0;
    	
    	if (n1 == n2) {
    		for (int i = 0; i < n1; i++){
    				C += this.getVector()[i] * B.getVector()[i];
    			}	
    	}
    	else {
    		System.out.println("Error: incorrect vector dimensions");
    	}
    	return C;
    				
    	}
    
    public double getLength() {
    	
    	Vector v = new Vector(this.getVector());
    	double dProduct = this.dotProduct(v);
    	double length = Math.sqrt(dProduct);
    	return length;	
    }
    	
    
    
    
    	
    
    
    }
    
    
    
  
