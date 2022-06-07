public class ComplexTest {
    private static double dp = 1000000000000.0;
    private final double real; //Real part
    private final double imaginary; //Imaginary part

    //Constructor
    public ComplexTest(double real, double imaginary) {
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
    public static ComplexTest plus(ComplexTest a, ComplexTest b) {
        double real = (a.real * dp)/dp + (b.real * dp)/dp;
        double imag = (a.imaginary * dp)/dp + (b.imaginary * dp)/dp;
        return new ComplexTest(Math.round(real * dp)/dp, Math.round(imag * dp)/dp);
    }

    //Return (a * b)
    public static ComplexTest times(ComplexTest a, ComplexTest b) {
        double real = ((a.real * dp)/dp * (b.real * dp)/dp) - ((a.imaginary * dp)/dp * (b.imaginary * dp)/dp);
        double imag = ((a.real * dp)/dp * (b.imaginary * dp)/dp) + ((a.imaginary * dp)/dp * (b.real * dp)/dp);
        return new ComplexTest(Math.round(real * dp)/dp, Math.round(imag * dp)/dp);
    }

    //Return a new Complex object whose value is the reciprocal of this
    public ComplexTest reciprocal() {
        double scale = (real*real*dp)/dp + (imaginary*imaginary*dp)/dp;
        return new ComplexTest(real / scale, -imaginary / scale);
    }

    //Return (a / b)
    public static ComplexTest divides(ComplexTest a, ComplexTest b) {return ComplexTest.times(a, b.reciprocal());}

    //Polar form
    public static String polar(ComplexTest a) {
        String x;
        return
                x = Math.round(a.abs() * 10000.0)/10000.0 + "∠" + Math.round((a.phase()*57.296)*10000.0)/10000.0 + "°";
    }

    /*//Auxiliary method:
    public static void decimal(double real, double imaginary) {
        if (String.valueOf(real).contains("E-12"))
            dpR = 1000000000000000.0;
        else if (String.valueOf(real).contains("E-"))
            dpR = 1000000000000.0;
        else
            dpR = 1000.0;

        if (String.valueOf(imaginary).contains("E-12"))
            dpI = 1000000000000000.0;
        else if (String.valueOf(imaginary).contains("E-"))
            dpI = 1000000000000.0;
        else
            dpI = 1000.0;
    }*/

    //Getters
    public double getReal() {
        return real;
    }
    public double getImaginary() {
        return imaginary;
    }
    public static double getDp() {
        return dp;
    }

    //Setters
    public static void setDp(double dp) {
        ComplexTest.dp = dp;
    }
}