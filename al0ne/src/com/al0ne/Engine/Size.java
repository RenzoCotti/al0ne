package com.al0ne.Engine;

/**
 * Created by BMW on 11/04/2017.
 */

public enum Size {
    MICRO, VSMALL, SMALL, NORMAL, LARGE, VLARGE, HUGE;


    public static int toInt(Size s) {
        switch(s) {
            case MICRO:
                return (int) Math.pow(2, 0);
            case VSMALL:
                return (int) Math.pow(2, 2);
            case SMALL:
                return (int) Math.pow(2, 5);
            case NORMAL:
                return (int) Math.pow(2, 8);
            case LARGE:
                return (int) Math.pow(2, 11);
            case VLARGE:
                return (int) Math.pow(2, 14);
            case HUGE:
                return (int) Math.pow(2, 17);
        }
        return -1;
    }
}