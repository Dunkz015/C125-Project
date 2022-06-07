import java.util.Stack;

public class EvaluateTest {
    public static double multiplier = 1.0; //multiplier
    public static int multipliers = 0;
    public static double test = 0;
    public static double test2 = 0;

    public static ComplexTest evaluate(String expression) {
        char[] input = expression.toCharArray();

        // Stack for numbers: 'real' & 'imag'
        Stack<Double> real = new Stack<>();
        Stack<Double> imag = new Stack<>();

        Stack<ComplexTest> ComplexTest = new Stack<>();

        // Stack for Operators: 'operators'
        Stack<Character> operators = new Stack<>();

        //Auxiliary variables
        double f = 0.0; //Frequency
        double c = 0.0; //Capacitance
        double l = 0.0; //Inductance
        final double pi = 3.14159265359;

        for (int i = 0; i < input.length; i++) {
            // Current token is a whitespace, skip it
            if (input[i] == ' ')
                continue;
            // Current token is a E, skip it
            if (input[i] == 'E')
                continue;

            //Current token is a number. Reading complex number:
            else if (input[i] == 'i' || input[i] == 'j' || input[i] == 'J' || input[i] == 'I') {
                /*------------------------------------------------------------------------*/
                //Imaginary part:
                for (int j = ++i; j < input.length; ) {

                    if (input[j] == ' ') {
                        j++;
                    } else if (condition(input[j])) {
                        imag.push(Double.parseDouble(evaluationRight(input, j)) * multiplier);
                        multiplier = 1.0;
                        multipliers = 0;
                        j = input.length;
                    } else
                        break;
                }
                i--; //Returning to input[i] == 'i'
                /*------------------------------------------------------------------------*/
                //Real part:
                for (int j = --i; j >= 0; ) {

                    if (input[j] == ' ') {
                        j--;
                    } else if (condition(input[j])) {
                        real.push(Double.parseDouble(evaluationLeft(input, j)) * multiplier);
                        multiplier = 1.0;
                        multipliers = 0;
                        j = -1;
                    } else
                        break;
                }
                /*------------------------------------------------------------------------*/
                ComplexTest.push(new ComplexTest(real.peek(), imag.peek())); //Adding complex read to stack
                real.pop();
                imag.pop();
                i++; //Returning to input[i] == 'i'
            }

            //Capacitor:
            else if (input[i] == 'C' || input[i] == 'c') {
                /*------------------------------------------------------------------------*/
                //Frequency:
                for (int j = ++i; j < input.length; ) {

                    if (input[j] == ' ') {
                        j++;
                    } else if (condition(input[j])) {
                        f = Double.parseDouble(evaluationRight(input, j)) * multiplier;
                        multiplier = 1.0;
                        multipliers = 0;
                        j = input.length;
                    } else
                        break;
                }
                i--; //Returning to input[i] == 'c'
                /*------------------------------------------------------------------------*/
                //Capacitance:
                for (int j = --i; j >= 0; ) {

                    if (input[j] == ' ') {
                        j--;
                    } else if (condition(input[j])) {
                        c = Double.parseDouble(evaluationLeft(input, j)) * multiplier;
                        multiplier = 1.0;
                        multipliers = 0;
                        j = -1;
                    } else
                        break;
                }
                /*------------------------------------------------------------------------*/
                //Calculating Xc:
                if (f <= 0.0 || c <= 0.0)
                    throw new RuntimeException();

                ComplexTest.push(new ComplexTest(0.0, (1.00 / (f * c * 2.00 * pi)) * (-1.00))); //Adding complex read to stack
                f = 0.0;
                c = 0.0;
                i++; //Returning to input[i] == 'c'
            }

            //Inductance:
            else if (input[i] == 'L' || input[i] == 'l') {
                /*------------------------------------------------------------------------*/
                //Frequency:
                for (int j = ++i; j < input.length; ) {

                    if (input[j] == ' ') {
                        j++;
                    } else if (condition(input[j])) {
                        f = Double.parseDouble(evaluationRight(input, j)) * multiplier;
                        multiplier = 1.0;
                        multipliers = 0;
                        j = input.length;
                    } else
                        break;
                }
                i--; //Returning to input[i] == 'l'
                /*------------------------------------------------------------------------*/
                //Inductance:
                for (int j = --i; j >= 0; ) {

                    if (input[j] == ' ') {
                        j--;
                    } else if (condition(input[j])) {
                        l = Double.parseDouble(evaluationLeft(input, j)) * multiplier;
                        multiplier = 1.0;
                        multipliers = 0;
                        j = -1;
                    } else
                        break;
                }
                /*------------------------------------------------------------------------*/
                //Calculating Xl:
                if (f <= 0.0 || l <= 0.0)
                    throw new RuntimeException();

                ComplexTest.push(new ComplexTest(0.0, (f * l * 2.00 * pi))); //Adding complex read to stack
                f = 0.0;
                c = 0.0;
                i++; //Returning to input[i] == 'l'
            }

            //Current token is an opening brace, push it to 'operators'
            else if (input[i] == '(')
                operators.push(input[i]);

                // Closing brace encountered, solve entire brace
            else if (input[i] == ')') {
                while (operators.peek() != '(')
                    ComplexTest.push(applyOp(operators.pop(), ComplexTest.pop(), ComplexTest.pop()));
                operators.pop();
            }

            // Current token is an operator.
            else if (input[i] == '+' || input[i] == '|') {
                if (input[++i] == '|')
                    input[i] = ' ';
                i--;

                //Push current token to 'operators'.
                operators.push(input[i]);
            }
        }

        //Entire expression has been parsed at this point, apply remaining operators to remaining complex
        while (!operators.empty())
            ComplexTest.push(applyOp(operators.pop(), ComplexTest.pop(), ComplexTest.pop()));

        //Top of 'complex' contains result, return it
        return ComplexTest.pop();
    }

    //Calculating result
    public static ComplexTest applyOp(char op, ComplexTest b, ComplexTest a) {
        return switch (op) {
            case '+' -> ComplexTest.plus(a, b);
            case '|' -> ComplexTest.divides(ComplexTest.times(a, b), ComplexTest.plus(a, b));
            default -> throw new ArithmeticException("Illegal expression");
        };
    }

    //Read expression on right
    public static String evaluationRight(char[] input, int j) {
        StringBuffer data = new StringBuffer();
        while (j < input.length && condition(input[j])) {
            switch (input[j]) {
                case 'm':
                    multiplier = 0.001;
                    multipliers++;
                    break;
                case 'u':
                    multiplier = 0.000001;
                    multipliers++;
                    break;
                case 'n':
                    multiplier = 0.000000001;
                    multipliers++;
                    break;
                case 'p':
                    multiplier = 0.000000000001;
                    multipliers++;
                    break;
                case 'k':
                    multiplier = 1000.0;
                    multipliers++;
                    break;
                case 'M':
                    multiplier = 1000000.0;
                    multipliers++;
                    break;
                case 'G':
                    multiplier = 1000000000.0;
                    multipliers++;
                    break;
                case 'E':
                    multiplier = exponentialRight(input, j);
                    multipliers++;
                    break;
                case ' ':
                    break;
                default:
                    data.append(input[j]);
            }
            if (multipliers > 1) {
                multipliers = 0;
                throw new RuntimeException();
            }
            j++;
        }
        return data.toString();
    }

    //Read expression on left
    public static String evaluationLeft(char[] input, int j) {
        StringBuffer data = new StringBuffer();
        multiplier = exponentialLeft(input, j);
        while (j >= 0 && condition(input[j])) {
            switch (input[j]) {
                case 'm':
                    multiplier = 0.001;
                    multipliers++;
                    break;
                case 'u':
                    multiplier = 0.000001;
                    multipliers++;
                    break;
                case 'n':
                    multiplier = 0.000000001;
                    multipliers++;
                    break;
                case 'p':
                    multiplier = 0.000000000001;
                    multipliers++;
                    break;
                case 'k':
                    multiplier = 1000.0;
                    multipliers++;
                    break;
                case 'M':
                    multiplier = 1000000.0;
                    multipliers++;
                    break;
                case 'G':
                    multiplier = 1000000000.0;
                    multipliers++;
                    break;
                case ' ':
                    break;
                default:
                    data.append(input[j]);
            }
            if (multipliers > 1) {
                multipliers = 0;
                throw new RuntimeException();
            }
            j--;
        }
        data.reverse();
        test2 = Double.parseDouble(data.toString());
        return data.toString();
    }

    //Auxiliary method
    public static boolean condition(char x) {
        return (x >= '0' && x <= '9') ||
                x == '.' ||
                x == '-' ||
                x == 'm' ||
                x == 'u' ||
                x == 'n' ||
                x == 'p' ||
                x == 'k' ||
                x == 'M' ||
                x == 'G' ||
                x == 'E' ||
                x == ' ';
    }

    //Auxiliary method
    public static double exponentialRight(char[] input, int j) {
        StringBuffer data = new StringBuffer();
        while (j < input.length && ((input[j] >= '0' && input[j] <= '9') || input[j] == '-' || input[j] == 'E')) {
            if(input[j] != 'E')
                data.append(input[j]);
            input[j] = ' ';
            j++;
        }
        multiplier = Math.pow(10, Double.parseDouble(data.toString()));
        test = multiplier;
        return multiplier;
    }

    //Auxiliary method
    public static double exponentialLeft(char[] input, int j) {
        StringBuffer data = new StringBuffer();
        for (; j >= 0; j--) {
            if(input[j] == 'E') {
                while (j < input.length && ((input[j] >= '0' && input[j] <= '9') || input[j] == '-' || input[j] == 'E')) {
                    if (input[j] != 'E')
                        data.append(input[j]);
                    input[j] = ' ';
                    j++;
                }
                multiplier = Math.pow(10, Double.parseDouble(data.toString()));
                break;
            }
            else
                multiplier = 1.0;
        }
        return multiplier;
    }
}