package com.Apothic0n.BiosphericalExpansion.core;

public class BioxMath {
    public static int mid(int x, int y) {
        return x/2 + y/2 + (x%2 + y%2)/2;
    }

    public static int booleanToInt(boolean bool) {
        if (bool == true) {
            return 1;
        } else {
            return 0;
        }
    }

    public static double progressBetweenInts(int min, int max, int value) {
        return (double) value / (max + min);
    }
}
