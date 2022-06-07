package com.example.zcalculator;

public class Complex {
    private final double real; //Real part
    private final double imaginary; //Imaginary part
    private static double polarunit = 57.296;
    private static String unit = "º";

    //Constructor
    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    //Return a string representation
    public String toString() {
        if (imaginary == 0) return real + "";
        if (real == 0) return imaginary + "i";
        if (imaginary <  0) return real + " - " + (-imaginary) + "i";
        return real + " + " + imaginary + "i";
    } //3 + 3i

    //Return modulus
    public double abs() {
        return Math.hypot(real, imaginary);
    }

    //Return angle
    public double phase() {
        return Math.atan2(imaginary, real);
    }

    //Return (a + b)
    public static Complex plus(Complex a, Complex b) {
        double real = a.real + b.real;
        double imag = a.imaginary + b.imaginary;
        return new Complex(real, imag);
    }

    //Return (a * b)
    public static Complex times(Complex a, Complex b) {
        double real = a.real * b.real - a.imaginary * b.imaginary;
        double imag = a.real * b.imaginary + a.imaginary * b.real;
        return new Complex(real, imag);
    }

    //Return a new Complex object whose value is the reciprocal of this
    public Complex reciprocal() {
        double scale = real*real + imaginary*imaginary;
        return new Complex(real/scale, -imaginary/scale);
    }

    //Return (a / b)
    public static Complex divides(Complex a, Complex b) {return Complex.times(a, b.reciprocal());}

    //Polar form
    public static String polar(Complex a) {
        return
                Math.round(a.abs() * 1000.0)/1000.0 + "∠" + Math.round((a.phase()*polarunit)*1000.0)/1000.0 + unit;
    }

    //Getters
    public double getReal() {
        return real;
    }
    public double getImaginary() {
        return imaginary;
    }

    //Setters
    public static void setPolarunit(double polarunit) {
        Complex.polarunit = polarunit;
        if (polarunit == 1)
            unit = "rad";
        else
            unit = "º";
    }
}

