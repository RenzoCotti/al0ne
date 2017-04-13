package com.al0ne.Engine;

import com.al0ne.Behaviours.Room;

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

    public static Size toSize(Integer size){
        if (toInt(MICRO) > size){
            return MICRO;
        } else if (toInt(MICRO) <= size && size < toInt(VSMALL)){
            return MICRO;
        } else if (toInt(VSMALL) <= size && size < toInt(SMALL)){
            return VSMALL;
        } else if (toInt(SMALL) <= size && size < toInt(NORMAL)){
            return SMALL;
        } else if (toInt(NORMAL) <= size && size < toInt(LARGE)){
            return NORMAL;
        } else if (toInt(LARGE) <= size && size < toInt(VLARGE)){
            return LARGE;
        } else if (toInt(VLARGE) <= size && size < toInt(HUGE)){
            return VLARGE;
        } else if (size >= toInt(HUGE)){
            return HUGE;
        } else{
            System.out.println("error in calculating size");
            return null;
        }
    }

    public static String toString(Integer size){
        Size s = toSize(size);

        switch(s) {
            case MICRO:
                return "tiny";
            case VSMALL:
                return "very small";
            case SMALL:
                return "small";
            case NORMAL:
                return "averagely sized";
            case LARGE:
                return "quite large";
            case VLARGE:
                return "very big";
            case HUGE:
                return "enornmous";
            default:
                return null;
        }

    }
}