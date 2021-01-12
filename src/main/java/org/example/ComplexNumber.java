package org.example;

public class ComplexNumber
{
    /**
     * The real, Re(z), part of the <code>ComplexNumber</code>.
     */
    private double real;
    /**
     * The imaginary, Im(z), part of the <code>ComplexNumber</code>.
     */
    private double imaginary;
    /**
     * Constructs a new <code>ComplexNumber</code> object with both real and imaginary parts 0 (z = 0 + 0i).
     */

    public ComplexNumber()
    {
        real = 0.0;
        imaginary = 0.0;
    }

    /**
     * Constructs a new <code>ComplexNumber</code> object.
     * @param real the real part, Re(z), of the complex number
     * @param imaginary the imaginary part, Im(z), of the complex number
     */

    public ComplexNumber(double real, double imaginary)
    {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * Adds another <code>ComplexNumber</code> to the current complex number.
     * @param complex_number the complex number to be added to the current complex number
     */

    public void add(ComplexNumber complex_number)
    {
        this.real = this.real + complex_number.real;
        this.imaginary = this.imaginary + complex_number.imaginary;
    }

    /**
     * The complex conjugate of the current complex number.
     * @return a <code>ComplexNumber</code> object which is the conjugate of the current complex number
     */

    public ComplexNumber conjugate()
    {
        return new ComplexNumber(this.real,-this.imaginary);
    }

    /**
     * The modulus, magnitude or the absolute value of current complex number.
     * @return the magnitude or modulus of current complex number
     */

    public double mod()
    {
        return Math.sqrt(Math.pow(this.real,2) + Math.pow(this.imaginary,2));
    }

    /**
     * The square of the current complex number.
     * @return a <code>ComplexNumber</code> object which is the square of the current complex number
     */

    public ComplexNumber square()
    {
        double _real = this.real*this.real - this.imaginary*this.imaginary;
        double _imaginary = 2*this.real*this.imaginary;
        return new ComplexNumber(_real,_imaginary);
    }

    /**
     * Multiplies another <code>ComplexNumber</code> to the current complex number.
     * @param complex_number the complex number to be multiplied to the current complex number
     */

    public void multiply(ComplexNumber complex_number)
    {
        double _real = this.real*complex_number.real - this.imaginary*complex_number.imaginary;
        double _imaginary = this.real*complex_number.imaginary + this.imaginary*complex_number.real;

        this.real = _real;
        this.imaginary = _imaginary;
    }

    /**
     * Prints the complex number in x + yi format
     */

    public void print()
    {
        System.out.println(this.real+" + "+this.imaginary+"i");
    }
}