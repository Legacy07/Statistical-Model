package statistical.model;

public class Calculations {

    public static double probabilityDensity(double x, double mean, double standardD) {
        //probability density function's formula breakdown
        double answer = 1 / (standardD * (Math.sqrt(2 * Math.PI)));
        //calculate the first exponent
        double exponent = Math.pow((x - mean), 2.0);
        //calculate the second exponent
        double exponent2 = 2 * Math.pow(standardD, 2.0);
        double exponent3 = Math.pow(Math.E, (-(exponent / exponent2)));
        answer = answer * exponent3;
        return answer;
    }

    public static double rectangularRule(double first, double last, double strips, double mean, double standardD) {
        //value of delta
        double delta = (last - first) / strips;
        double answer = 0.0;
        //use probability density funtion to get the values and add to answer. 
        for (double i = 1; i < strips; i++) {
            answer += probabilityDensity((first + (i - 1) * delta), mean, standardD);
        }
        //final answer, having the values within the loop
        answer = delta * answer;

        return answer;
    }

    public static double trapezodialRule(double first, double last, double strips, double mean, double standardD) {
        //value of delta
        double delta = (last - first) / strips;
        //store the calculated first and last , to be added in answer
        double first1 = probabilityDensity(first, mean, standardD);
        double last1 = probabilityDensity(last, mean, standardD);
        //first answer, to add first and last and half it 
        double answer = 0.5 * (first1 + last1);
        //create the virtual table to perform calculations of the given values. 
        //use probability density funtion to get the values and add to answer. 
        for (double i = 1; i < strips; i++) {
            answer = answer + probabilityDensity((first + (i * delta)), mean, standardD);
        }
        //final answer, using the rule = delta / 2 (first1 + values in loop + last1)
        answer = delta * answer;

        return answer;
    }

    public static double simpsonsRule(double first, double last, double strips, double mean, double standardD) {
        //value of delta
        double delta = (last - first) / strips;
        //getting the value for first and last intervals
        double first1 = probabilityDensity(first, mean, standardD);
        double last1 = probabilityDensity(last, mean, standardD);
        double answer = first1 + last1;

        double even = 0.0;
        double odd = 0.0;

        //odd numbers
        for (int i = 1; i < strips; i += 2) {
            odd += 4.0 * probabilityDensity((first + (i * delta)), mean, standardD);
        }

        // even numbers
        for (int i = 2; i < strips - 1; i += 2) {
            even += 2.0 * probabilityDensity((first + (i * delta)), mean, standardD);
        }
        //calculating the formula 
        answer = (delta / 3.0) * (answer + even + odd);
        return answer;
    }

    /////////////////////////////////////4 cases using Rectangular Rule/////////////////////////////////////////////////////////////////
    public static double aboveRectangular(double x, double strips, double mean, double standardD) {
        double y = mean - 4.1 * standardD;
        double answer = rectangularRule(y, x, strips, mean, standardD);
        return 1 - answer;
    }

    public static double belowRectangular(double x, double strips, double mean, double standardD) {
        double y = mean + 4.1 * standardD;
        double answer = rectangularRule(y, x, strips, mean, standardD);
        return 1 + answer;
    }

    public static double outsideRectangular(double y, double x, double strips, double mean, double standardD) {
        double answer = 1 + rectangularRule(y, x, strips, mean, standardD);
        return answer;
    }

    public static double betweenRectangular(double y, double x, double strips, double mean, double standardD) {
        double answer = rectangularRule(y, x, strips, mean, standardD);
        return answer;
    }

    ///////////////////////////////////////4 Cases using Trap rule//////////////////////////////////////////////////////////////////////
    public static double aboveTrap(double x, double strips, double mean, double standardD) {
        double y = mean - 4.1 * standardD;
        double answer = trapezodialRule(y, x, strips, mean, standardD);
        return 1 - answer;
    }

    public static double belowTrap(double x, double strips, double mean, double standardD) {
        double y = mean + 4.1 * standardD;
        double answer = trapezodialRule(y, x, strips, mean, standardD);
        return 1 + answer;
    }

    public static double outsideTrap(double y, double x, double strips, double mean, double standardD) {
        double answer = 1 + trapezodialRule(y, x, strips, mean, standardD);
        return answer;
    }

    public static double betweenTrap(double y, double x, double strips, double mean, double standardD) {
        double answer = trapezodialRule(y, x, strips, mean, standardD);
        return answer;
    }

    ///////////////////////////////////////4 cases using Simpsons Rule/////////////////////////////////////////////////////////////
    public static double aboveSimpsons(double x, double strips, double mean, double standardD) {
        double y = mean - 4.1 * standardD;
        double answer = simpsonsRule(y, x, strips, mean, standardD);
        return 1 - answer;
    }

    public static double belowSimpsons(double x, double strips, double mean, double standardD) {
        double y = mean + 4.1 * standardD;
        double answer = simpsonsRule(y, x, strips, mean, standardD);
        return 1 + answer;
    }

    public static double outsideSimpsons(double y, double x, double strips, double mean, double standardD) {
        double answer = 1 + simpsonsRule(y, x, strips, mean, standardD);
        return answer;
    }

    public static double betweenSimpsons(double y, double x, double strips, double mean, double standardD) {
        double answer = simpsonsRule(y, x, strips, mean, standardD);
        return answer;
    }

}
