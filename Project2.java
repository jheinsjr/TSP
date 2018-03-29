package Domain;

public class Project2 {

	public static void main(String[] args) {
		
		Matrix A = new Matrix("Files/Eigendata.txt");
		Matrix mean = A.getMean();
		mean = mean.transpose();
		
		System.out.println(mean);
		
		Matrix c1 = A.getCovariance();
		System.out.println(c1);
		
		double traceC1 = c1.trace();
		System.out.print("The trace of the covariance matrix is: ");
		System.out.println(traceC1 + "\n");
		
		
		double detC1 = c1.getDeterminate();
		System.out.print("The determinant of the covariance matrix is: ");
		System.out.println(detC1 + "\n");
		
		double [] results = quadratic(1,-traceC1, detC1);
		Vector result1 = new Vector(results);
		
	
		Matrix eValue1Ident = new Matrix(new double [2][2]);
		eValue1Ident = c1.identity().mult(results[0]);
		Matrix eValue1Matrix = c1.difference(eValue1Ident.getMatrix());
		System.out.println("Largest Eigenvalue: " + results[0]);
		System.out.println("Second Eigenvalue: " + results[1] + "\n");
		
		Matrix eValue2Ident = new Matrix(new double [2][2]);
		eValue2Ident = c1.identity().mult(results[1]);
		Matrix eValue2Matrix = c1.difference(eValue2Ident.getMatrix());
		
		//Get Unit Vectors
		double [] v1 = {-10.0037264,1};
		double [] v2 = {0.09996209,1};
		
		Vector vector1 = new Vector(v1);
		Vector vector2 = new Vector(v2);
		double vect1Length = vector1.getLength();
		double vect2Length = vector2.getLength();
		for (int i = 0; i < 2; i++){
			v1[i] = v1[i]/vect1Length;
			v2[i] = v2[i]/vect2Length;
		}
		Vector final1 = new Vector(v1);
		Vector final2 = new Vector(v2);
		System.out.println("The Unit Vectors are as follows: ");
		System.out.println(final1);
		System.out.println(final2 + "\n");
		
		
		question2();
			
		
	}
	
	/**
	* Methods that will answer question number 2
	*/
	private static void question2() {
		
		//Companion Matrix for #2
		double [][] a =  {{23,42,-902,-1577,2415},
						 {1,0,0,0,0,},
						 {0,1,0,0,0,},
						 {0,0,1,0,0,},
						 {0,0,0,1,0,}};				
		Matrix A2 = new Matrix(a);
		double [] coefA2 = A2.leverrier();
		Vector vA2 = new Vector(coefA2);
		System.out.println("The coefficients for matrix A are: " + vA2.toString());
		System.out.print("\n");
		double eigenA2 = A2.directPower();
		System.out.println("The largest eigenvalue for matrix A is: " + eigenA2);
		System.out.print("\n");
		
		double [] cPoly = {-2415,1577,902,-42,-23,1};
		double [] b4 = new double [5];
		b4 = linearSyntheticDiv(23,cPoly,5);
		Vector vA3 = new Vector(b4);
		System.out.println("The coefficients for matrix A2 are: " + vA3.toString());
		Matrix ex = new Matrix();
		ex = buildCompanionMatrix(b4);
		System.out.println(ex);
		Matrix A3 = ex;
		double eigenA3 = A3.directPower();
		System.out.println("The largest eigenvalue for matrix A2 is: " + eigenA3 + "\n");
		
		double [] cPoly2 = {105, -64, -42, 0, 1};
		double [] b3 = new double [3];
		b3 = linearSyntheticDiv(7,cPoly2,4);
		Vector vA4 = new Vector(b3);
		System.out.println("The coefficients for matrix A3 are: " + vA4.toString());
		Matrix A4 = new Matrix();
		A4 = buildCompanionMatrix(b3);
		System.out.print(A4);
		double eigenA4 = A4.directPower();
		System.out.println("The largest eigenvalue for matrix A3 is: " + eigenA4 + "\n");
		double [] cPoly3 = {-15,7,7,1};
		double [] b2 = new double [2];
		b2 = linearSyntheticDiv(-5,cPoly3,3);
		Vector vA5 = new Vector(b2);
		System.out.println("The coefficients for matrix A4 are: " + vA5.toString());
		Matrix A5 = new Matrix();
		A5 = buildCompanionMatrix(b2);
		System.out.print(A5);
		double eigenA5 = A5.directPower();
		System.out.println("The largest eigenValue for matrix A4 is " + eigenA5);
		double [] lastRoots = quadratic(1,2,-3);
		System.out.println("The final roots are: " + lastRoots[0] + " and " + lastRoots[1]);		
	}
	
	/**
	 * Quadratic equation calculator
	 * @return returns the two values (+ and -)
	 */
	public static double [] quadratic(double a, double b, double c) {
		double disc = (b * b) - (4 * a * c);
		
		double x1 = (-b + Math.sqrt(disc)) / 2*a;
		double x2 = (-b - Math.sqrt(disc)) / 2*a;
		double [] results = {x1,x2};
		return results;	
	}
	
	/**
	 * LinearSynthetic Division Algorithm
	 * @param root - root of the dividend
	 * @param coef - coefficients of the polynomial
	 * @param degree - degree of the polynomail
	 * @return - an array of coeffiecents for the polynomial of degree n-1
	 */
	public static double [] linearSyntheticDiv(double root, double [] coef, int degree) {
		double c = root;
		int n = degree;
		double [] b = new double [n+1];
		b[n-1] = coef[n];
		for (int k = 1; k < b.length; k++){
			b[n-k] = coef[n-k+1] + c * b[n-k+1];
		}
		double [] result = new double [b.length-1];
		for (int i = 0; i < b.length-1;  i++) {
			result[i] = b[b.length-2 - i];
		}
		return result;	
	}
	
	/**
	 * Method that builds a companion matrix
	 * @param an array of coefficients where the first value is zero and the second is the
	 * coefficient for a^n (must be 1)
	 * @return the companion matrix for the given array
	 */
	public static Matrix buildCompanionMatrix(double [] a) {
		int n = a.length-1;
		double [][] a1 = new double [n][n];
		for (int i = 0; i < n; i++) {
			a1[0][i] = (-1 * a[i+1]);
		}
		for (int i = 1; i < n; i++){
			for (int j = 0; j < n; j++){
				if (j == i-1){ 
					a1[i][j] = 1;
				}
				else a1[i][j] = 0;
			}
		}
		Matrix m = new Matrix(a1);
		return m;
	}
	
	
	
}
