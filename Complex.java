package Domain;
/**
 * This class represents complex numbers and will contain method to perform operations on 
 * complex numbers.
 * @author Joe Heins
 */
public class Complex {
	private double rl;
	private double im;
	//Constructor with no params
	public Complex(){
	}
	/**
	 * Constructor that takes in two parameters
	 * @param a double value that represents the real number and a double value that represents the imaginary
	 * number
	 */
	public Complex(double r, double i){
		rl = r;
		im = i;
	}
	//Getter for real part of complex number
	public double getReal(){
		return this.rl;
	}
	
	//Getter for imaginary part of complex number
	public double getImaginary(){
		return this.im;
	}
	
	//Adds two complex numbers
	public Complex add(Complex B){	
		double a = this.rl + B.rl;
		double b = this.im + B.im;
		Complex C = new Complex(a,b);
		return C;
	}
	
	//Multiply complex numbers
	public double multiply(Complex B){
		double a = this.getReal() * B.getReal();
		double b1 = this.getReal() * B.getImaginary();
		double b2 = this.getImaginary() * B.getReal();
		double c = this.getImaginary() * B.getImaginary();
		//combine like terms
		double b3 = b1 + b2;
		double real = a - c;
		double imaginary = b3;
		double result = real + imaginary;
		return result;
	}
	
	//Divide complex numbers
	public double divide(Complex B) {	
		Complex conj = B.getConjugate();
		double denom = B.multiply(conj);
		double numer = this.multiply(conj);	
		return numer/denom;
	}
	
	//Getter for the conjugate of a complex number
	public Complex getConjugate() {
		double real = this.getReal();
		double imag = this.getImaginary() * -1;
		Complex C = new Complex(real,imag);
		return C;
	}
}
