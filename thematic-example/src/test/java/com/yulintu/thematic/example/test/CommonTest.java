package com.yulintu.thematic.example.test;

import org.junit.Test;

public class CommonTest {

    @Test
    public void testDescent() {

        int n = 4 * 365 * 24 * 6;
        double rate = .5;
        double total = 0;
        double base = 49.94292237442922;

        while (true) {

            total += base * n;
            base *= rate;

            System.out.println(total);
        }
    }

    @Test
    public void testDescent2() {

        double baseMin = 1;
        double baseMax = 100;
        double base = 0;
        double total = 21000000;

        while (true) {

            base = (baseMin + baseMax) / 2;
            double current = testDescent2(base);
            double dis = total - current;
            if (Math.abs(dis) < 0.000000000001)
                break;
            if (dis > 0)
                baseMin = base;
            else if (dis < 0)
                baseMax = base;
        }

        System.out.println(base);
    }

    public double testDescent2(double base) {

        int n = 4 * 365 * 24 * 6;
        double rate = .5;
        double total = 0;
        double lastTotal = 0;

        while (true) {

            lastTotal = total;
            total += base * n;
            base *= rate;

            if (Math.abs(total - lastTotal) < 0.00000000001)
                return total;
        }
    }
}
