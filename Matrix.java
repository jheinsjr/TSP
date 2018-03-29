package Domain;

import java.util.Random;
import java.util.Scanner;
import java.io.*;


/**
 * This is a Matrix class that produces a matrix object composed of a two
 * dimensional array and integers that represent the length of the array in each
 * dimension.
 * 
 * @author Joe Heins
 * @version 1.0
 */
public class Matrix extends Vector {
	//instance variables
	private double[][] matrix;
	private int rows;
	private int columns;

	/**
	 * Constructor for Matrix object that requires no parameters
	 */
	public Matrix() {

	}

	/**
	 * Constructor that takes a 2D array as a parameter and sets the matrix 2D
	 * array equal to the arguement. Also sets rows and columns variables to the
	 * values obtained from the 2D array
	 * 
	 * @param a 2D array of double values
	 */
	public Matrix(double[][] A) {
		this.matrix = A;
		rows = A.length;
		columns = A[0].length;
	}
	
	/**
	 * Constructor that takes a file and instantiates a 2D array
	 * from the information in the file to create a matrix object.
	 * 
	 * @param a text file to be read for a matrix
	 */
	public Matrix (String fileName) {
		
		int m = getNumberofRows(fileName);
        int n = getNumberofColumns(fileName);
        double [][] a = new double [m][n];
        double x;
        
		try {
		  Scanner s = new Scanner(new File(fileName));
		  
		  for (int i = 0; i < m; i++) {
              String line = s.nextLine();
              String [] numbersIn = line.split(" ");
              
              for(int k = 0; k < numbersIn.length;k++) 
                  numbersIn[k] = numbersIn[k].trim();
              
              for (int j = 0; j < numbersIn.length; j++) {
                  x = Double.parseDouble(numbersIn[j]);
                  a[i][j] = x;
              }
              this.matrix = a;
              this.rows = a.length;
              this.columns = a[0].length;
              
		}
		  s.close();
	}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	
	}

	/**
	 * Getter for the 2D array of the matrix object
	 * 
	 * @return a 2D array of double values
	 */
	public double[][] getMatrix() {
		return matrix;
	}

	/**
	 * Setter for the 2D array of the matrix object
	 * 
	 * @param a 2D array of double values
	 */
	public void setMatrix(double[][] A) {
		this.matrix = A;
	}

	/**
	 * Getter for integer value that represents the number of rows
	 * 
	 * @return an integer value that is the number of rows
	 */
	public int getRows() {
		return this.rows;
	}

	/**
	 * Getter for integer value that represents the number of columns
	 * 
	 * @return an integer value that is the number of columns
	 */
	public int getColumns() {
		return this.columns;
	}

	/**
	 * Adds two matrix objects together to return a new matrix object as long as
	 * both matrices are the same size (this + B = C) (nxn)
	 * 
	 * @param b 2D array that will be added to the 2D array of the object
	 * calling the method
	 * @return new matrix object that has a 2D array of sums
	 */
	public Matrix add(Matrix B) {
		int mA = this.rows;
		int nA = this.columns;
		int mB = B.getRows();
		int nB = B.getColumns();

		if (mA == mB && nA == nB) {

			double[][] c = new double[mA][nB];

			for (int i = 0; i < mA; i++)
				for (int j = 0; j < nA; j++)
					c[i][j] = this.matrix[i][j] + B.getMatrix()[i][j];

			Matrix C = new Matrix(c);

			return C;
		} else {
			System.out.println("Error: Incompatable Matrices");
			return null;
		}
	}

	/**
	 * Multiplies two matrices together (A * B = C)
	 * 
	 * @param 2D array to be multiplied with this 2D array
	 * @return a matrix that contains the product of this matrix and the
	 *         arguement matrix
	 */
	public Matrix mult(Matrix B) {

		int mA = this.rows;
		int nA = this.columns;
		int mB = B.getRows();
		int nB = B.getColumns();

		if (nA == mB) {

			double c[][] = new double[mA][nB];

			for (int i = 0; i < mA; i++)
				for (int j = 0; j < nB; j++)
					for (int k = 0; k < nA; k++)
						c[i][j] += this.matrix[i][k] * B.getMatrix()[k][j];

			Matrix C = new Matrix(c);

			return C;
		} else {
			System.out.print("Incorrect Matrix size.");
			return null;
		}

	}

	/**
	 * Subtracts a scalar value (double) from the matrix object
	 * 
	 * @param double value to be subtracted from the matrix
	 * @return the matrix after the scalar has been subtracted from it
	 */
	public Matrix subScalar(double s) {
		int m = this.matrix.length;
		int n = this.matrix[0].length;
		double[][] c = new double[m][n];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				c[i][j] = this.matrix[i][j] - s;

		Matrix C = new Matrix(c);
		return C;
	}

	/**
	 * Returns the transposition of a matrix
	 * 
	 * @return matrix that is the transposition of original matrix
	 */
	public Matrix transpose() {

		int m = this.matrix.length;
		int n = this.matrix[0].length;

		double[][] t = new double[n][m];
		Matrix transposition = new Matrix(t);

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				transposition.matrix[j][i] = this.matrix[i][j];
		}

		return transposition;
	}

	/**
	 * Subtract one matrix from another (this - B = C)
	 * 
	 * @param 2D array that will be subtracted from this matrix
	 * @return matrix object with 2D array that is the difference
	 */
	public Matrix difference(double[][] b) {
		int mA = this.matrix.length;
		int nA = this.matrix[0].length;
		int mB = b.length;
		int nB = b[0].length;

		if (mA == mB && nA == nB) {

			double[][] c = new double[mA][nA];
			for (int i = 0; i < mA; i++)
				for (int j = 0; j < nA; j++)
					c[i][j] = this.matrix[i][j] - b[i][j];

			Matrix C = new Matrix(c);

			return C;
		} else {
			System.out.println("Error: Incorrect Matrix size.");
			return null;
		}
	}

	/**
	 * Multiplies a matrix by a scalar value.
	 * 
	 * @param double value that the matrix will be multiplied by
	 * @return a matrix
	 */
	public Matrix mult(double s) {
		int m = this.rows;
		int n = this.columns;

		double[][] b = new double[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				b[i][j] = this.matrix[i][j] * s;
			}
		}

		Matrix C = new Matrix(b);

		return C;
	}
	
	/**
	 * Turns the matrix into a string, capabile of being printed.
	 * @return a string that represents the matrix values
	 */
	@Override
	public String toString() {

		String s = getRows() + "x" + getColumns();
		String s1 = new String();
		
		for (int i = 0; i < getMatrix().length; i++){
			if (i > 0) {
				 s1 += "\n";
			}
			for (int j = 0; j < getMatrix()[0].length; j++){
				s1 += this.getMatrix()[i][j] +  " ";
			}
		}
		s1 = s1.trim();
		String m = s + "matrix\n" + s1 + "\n";
		
		return m;	
	}
	
	/**
	 * Sets a row of the matrix to a vector
	 * 
	 *@param an int and a double array or vector
	 */
	public void setRow(int a, double [] v) {
		
		this.matrix[a] = v;	
	}
	
	 /**
	  * Calculates the mean of a matrix 
	  * @return an n x 1 matrix that contains the mean value
	  */
    public Matrix getMean() {
        
        int m = this.matrix.length;
        int n = this.matrix[0].length;
        
        double [][] m1 = new double [n][1];
        
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                m1[i][0] += this.matrix[j][i];
            
        for (int i = 0; i < n; i++)
             m1[i][0] = m1[i][0] / m;
        
        Matrix mean = new Matrix(m1);
        
        return mean;  
    }
    
  //returns the covariance matrix
    public Matrix getCovariance() {
        int m = this.matrix.length;
        int n = this.matrix[0].length;
        
        //Get mean matrix
        Matrix mean = this.getMean();
        
        //Get difference between mean and matrix
        Matrix diffMatrix = new Matrix();
        diffMatrix.setMatrix(new double [m][n]);
        
        
        Matrix meanT = mean.transpose();
        Matrix sum = new Matrix(new double [n][n]);
        double s = 1/(double)m;
        
        
        for (int i = 0; i < m; i++) {
            Vector v = new Vector(this.matrix[i]);
            diffMatrix.matrix[i] = v.difference(meanT.matrix[0]);
            Matrix t2 = diffMatrix.transpose();
            Matrix product = new Matrix(new double [n][n]);
            
            
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    product.matrix[j][k] = 
                            diffMatrix.matrix[i][k] * t2.matrix[j][i];
                }
            }
                sum = sum.add(product);
            }
        
        Matrix cMatrix;
        cMatrix = sum.mult(s);
            
        return cMatrix;
    }
    
public double  getDeterminate() {
        
        Matrix det = this.copy();
        int n = det.matrix.length;
      
        int r = 0;
        double delta;
        double total = 1;
        
        
        //iterate through matrix
        for (int j = 0; j < n; j++) {
                
            //find the pivot index
            int pivot = 0;
            for (int i = 1; i < n; i++) {
                if (Math.abs(det.matrix[i][j]) > Math.abs(det.matrix[pivot][j])) {
                       pivot = i;
                   }
                }
                double zero = 0.0;
                if (det.matrix[pivot][j] == zero) {
                    break;
                }
               
               //pivot rows and increment r
               if (pivot > j) {
                   double [] temp = new double [n + 1];
                   for (int k = 0; k < n; k++){
                       temp[k] = det.matrix[j][k];
                       det.matrix[j][k] = det.matrix[pivot][k];
                       det.matrix[pivot][k] = temp[k];
                   }
                   r++;   
               }
            
               for (int i = j + 1; i < n; i++) {                   
                    //multiply row times scalar
                   
            	   double s =  (1.0 / (double) det.matrix[j][j]) * det.matrix[i][j];
                   Vector v1 = new Vector(det.matrix[j]);
            	   double [] t2 = v1.vectorTimesScalar(s);
                    //subtract product from row i
                    Vector v = new Vector(det.matrix[i]);
                    det.matrix[i] = v.difference(t2);
                }
                
            }
        
            //get total a[j][j]*a[j+1][j+1]......
            for (int k = 0; k < n; k++){
                for (int p = 0; p < n; p++){
                    if (k == p) {
                        total *= det.matrix[k][p];
                    }
                }
            }
            //compute negation flag            
            delta = Math.pow(-1, r);
            total *= delta;
            
            return total;          
        }
	
	/**
	 * Private method to get the number of rows of the matrix in the text file
	 * @param text file to be read for a matrix
	 * @return integer value representing the number of rows.
	 */
	private int getNumberofRows(String file) {
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
	
    /**
     * Private method to get the number of columns of the matrix in the text file
     * @param text file to be read for a matrix
     * @return integer value representing the number of columns
     */
    private int getNumberofColumns(String file) {
        int countColumns = 0;
        try {
            File fileIn = new File(file);
            Scanner input = new Scanner(fileIn);
            
            String line = input.nextLine();
            String [] lineInput = line.split(" ");
            countColumns = lineInput.length;
            
            input.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());   
        }
        return countColumns;
    }
    
    /**
     * Checks values of matrix against values of another matrix
     * @param the matrix to be compared against this matrix
     * @return boolean value of whether or not the two matrices are equal
     */
    @Override
    public boolean equals(Object obj) {
    	
    	Matrix B = (Matrix) obj;
    	
		boolean flag = false;
		
		int mA = this.getRows();
		int nA = this.getColumns();
		
		int mB = B.getRows();
		int nB = B.getColumns();
		
		boolean sameSize = (mA == mB && nA == nB);
		if (!sameSize) {
			flag = false;
		}
		else if (sameSize) {
			for (int i = 0; i < mA; i++){
				for (int j = 0; j < nA; j++) {
					if (this.getMatrix()[i][j] != B.getMatrix()[i][j]) {
						flag = false;
					}
				}
			}
		}
		else flag = true;
		
		return flag;
    }
    
    /**
     * This method calculates the trace of the matrix and returns its value
     * @return a double value that is the trace of the matrix object
     */
    public double trace() {
    	
    	double sum = 0;
    	
    	for (int i = 0; i < this.getRows(); i++) {
    		for (int j = 0; j < this.getColumns(); j++) {
    			if (i == j) {
    				sum += this.getMatrix()[i][j];
    			}
    		} 		
    	}
    	return sum;
    }
    
    /**
     * This method creates an identity matrix for the matrix object and returns it
     * @return the identity matrix for the given matrix object
     */
    public Matrix identity() {
    	
    	double [][] a = new double [this.getRows()][this.getColumns()];
    	Matrix A = new Matrix(a);
    	
    	for (int i = 0; i < this.getMatrix().length; i++) {
    		for (int j = 0; j < this.getMatrix()[0].length; j++) {
    			if (i == j) {
    				A.matrix[i][j] = 1;
    			}
    			else A.matrix[i][j] = 0;
    		}
    	}
    	return A;
    }
    
    /**
     * Multiplication of a matrix and a vector
     */
    public Vector multByVector(Vector v){
    	double [] a = new double [this.getMatrix().length];
		Vector v1 = new Vector(a);
    	
		for (int i = 0; i < this.getColumns(); i++)
			for (int j = 0; j < this.getRows(); j++)
				v1.getVector()[i] += this.getMatrix()[i][j] * v.getVector()[j];
    	
    	return v1;
    }
    
    /**
     * Method uses Leverrier's Method to find the coefficients of the characteristic polynomial
     * @return an array of the coefficients
     */
    public double [] leverrier() {
    	
    	int n = this.getMatrix().length - 1;
    	
    	Matrix B = this.copy();
    	
    	double [] a = new double [this.getMatrix().length];
    	
    	Matrix I = B.identity();
    	
    	Matrix [] C = new Matrix [B.getMatrix().length];
    	
    	C[n] = B;
    	a[n] = -(B.trace());
    	
    	for (int k = n - 1; k > -1; k--) {	
    		C[k] = this.mult(C[k+1].add(I.mult(a[k+1])));
    		a[k] = -C[k].trace()*1 / (double)(n - k + 1);
    	}
    	double [] coefficients = new double [a.length];
    	for (int i = 0; i < a.length; i++){
    		coefficients[i] = a[(a.length - 1) - i];
    	}
    	return coefficients;
    }
    
    /**
	 * This method copies a matrix and returns the copy of the matrix
	 * @return a copy of this matrix
	 */
	public Matrix copy() {
		
		double [][] b = new double [this.getRows()][this.getColumns()];
		
		Matrix B = new Matrix(b);
		
		for (int i = 0; i < B.rows; i++){
			for (int j = 0; j < B.getColumns(); j++){
				B.getMatrix()[i][j] = this.getMatrix()[i][j];
			}
		}
		
		return B;	
	}
	
	/**
	 * Gaussian Elimination method
	 * @param b a vector of double values
	 * @return a vector of double values
	 */
	public double [] gaussianE(double [] b) {
	       
	       double [][] a = this.matrix;
	       int n = this.matrix.length;
	       
	       //form the augmented matrix [A,b]
	       double [][] c = new double [a.length][(a.length + 1)];
	       
	        //copy nxn matrix
	        for (int i = 0; i < n; i++){
	            for (int j = 0; j < n; j++){
	                c[i][j] = a[i][j];
	            }
	        }
	        //add n+1 column
	        for (int k = 0; k < n ; k++){
	            c[k][n] = b[k];
	        }
	                  
	       //for each column in c
	       for (int j = 0; j < n ; j++) {
	           
	         //find the pivot
	         int pivot = 0;
	         for (int i = 1; i < n; i++) {
	            if (Math.abs(c[i][j]) > Math.abs(c[pivot][j])) {
	                pivot = i;
	                }
	            }
	               
	            //swap rows if pivot is larger than row j
	            if (pivot > j) {
	                double [] temp = new double [n + 1];
	                for (int k = 0; k < temp.length; k++){
	                    temp[k] = c[j][k];
	                    c[j][k] = c[pivot][k];
	                    c[pivot][k] = temp[k];
	                }
	            }    
	            //loop through rows
	            for (int i = j + 1; i < n; i++) {                   
	                //multiply row times scalar
	            	Vector cJ = new Vector(c[j]);
	                double [] t2 = cJ.vectorTimesScalar((c[i][j]/c[j][j]));
	                //subtract product from row i
	                Vector v = new Vector(c[i]);
	                c[i] = v.difference(t2);
	            }         
	        }
	                //partician matrix and vector
	               double [][] D = new double [n][n];
	               for (int i = 0; i < n; i++){
	                   for (int j = 0; j < n; j++){
	                       D[i][j] = c[i][j];
	                   }
	               }
	               
	               double [] e = new double [n];
	               //seperate column n+1
	               for (int k = 0; k < n; k++)
	                   e[k] = c[k][n];
	               
	               //compute value of variable
	               for (int y = c.length ; y < 0; y--) {
	                   if (y == c.length) {
	                       e[y] = e[y]/c[y][y];
	                   }
	                   else {
	                       e[y] = 1/c[y][y] * (e[y] - c[y][y+1]*e[y+1]);
	                   }
	               }  
	           return e;
	    }
	
	public double directPower(){
		double [] x1 = new double[this.getMatrix().length];
		double  e =  0.0001;
		double  m = 20;
		double mean;
		int k = 0;
		
		Random rand = new Random();
		for (int i = 0; i < this.getMatrix().length; i++){
			x1[i] = rand.nextDouble()*10;
		}
		
		Vector y = new Vector(x1);
		Vector x = this.multByVector(y);
		Vector r;
		
		do {
			
			for (int i = 0; i < this.getMatrix().length;i++){
				y.getVector()[i] = x.getVector()[i] / x.maxValue();
			}
			
			x = this.multByVector(y);
			
			double a = y.dotProduct(x);
			double b = y.dotProduct(y);
			
		    mean = (a/b);
			
			double [] r1 = new double[y.vector.length];
			
			for (int j = 0; j < this.getMatrix().length; j++){
				r1[j] = (mean * y.getVector()[j]) - x.getVector()[j];
			}
			r = new Vector(r1);
			k = k +1;    
		}
		while ((r.maxValue()>e)&& (k<m));
		
		return mean;
	}
	

}
