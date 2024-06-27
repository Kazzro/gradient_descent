package de.inmediasp.gradientdescent.optimizer;

import java.util.function.BiFunction;

public class Optimization {

    public static double function1(double x, double y) {
        return (x + 5) * (x + 1) * (x - 2) * (x + 4) * x * (y - 1) * (y + 2) * (y - 3) * (y + 5);
    }

    public static double function2(double x, double y) {
        return Math.sin(x * x + y) - Math.cos(y * y - x);
    }

    public static double fibonacciSearch(BiFunction<Double, Double, Double> function, double a, double b, double epsilon) {
        int iterations = 20;
        double[] fib = new double[iterations + 1];
        fib[0] = 0;
        fib[1] = 1;
        for (int i = 2; i <= iterations; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }

        double c = a + (fib[iterations - 2] / fib[iterations]) * (b - a);
        double d = a + (fib[iterations - 1] / fib[iterations]) * (b - a);
        double fc = function.apply(c, 0.0);
        double fd = function.apply(d, 0.0);

        for (int k = 1; k < iterations; k++) {
            if (fc > fd) {
                a = c;
                c = d;
                d = a + (fib[iterations - k - 1] / fib[iterations - k]) * (b - a);
                fc = fd;
                fd = function.apply(d, 0.0);
            } else {
                b = d;
                d = c;
                c = a + (fib[iterations - k - 2] / fib[iterations - k]) * (b - a);
                fd = fc;
                fc = function.apply(c, 0.0);
            }
        }

        return (a + b) / 2;
    }

    public static double[] gradientDescent(BiFunction<Double, Double, Double> func, double x0, double y0, double learningRate, double epsilon, int maxIterations) {
        double x = x0;
        double y = y0;

        for (int i = 0; i < maxIterations; i++) {
            double gradX = (func.apply(x + epsilon, y) - func.apply(x, y)) / epsilon;
            double gradY = (func.apply(x, y + epsilon) - func.apply(x, y)) / epsilon;

            double newX = x - learningRate * gradX;
            double newY = y - learningRate * gradY;

            if (Math.sqrt((newX - x) * (newX - x) + (newY - y) * (newY - y)) < epsilon) {
                break;
            }

            x = newX;
            y = newY;
        }

        return new double[]{x, y};
    }

    public static void main(String[] args) {

        double minFibonacci = fibonacciSearch(Optimization::function1, -2, 2, 0.001);
        System.out.println("Minimum entlang einer Suchgeraden mit Fibonacci Methode: " + minFibonacci);

        double[] minGradient = gradientDescent(Optimization::function2, 0, 0, 0.01, 0.0001, 10000);
        System.out.println("Minimum mit Gradientenabstiegsverfahren: x = " + minGradient[0] + ", y = " + minGradient[1]);
    }
}
