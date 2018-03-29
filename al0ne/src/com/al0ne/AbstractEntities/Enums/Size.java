package com.al0ne.AbstractEntities.Enums;

import java.util.ArrayList;

/**
 * Created by BMW on 11/04/2017.
 */

public enum Size {
    UNDEFINED, MICRO, VSMALL, SMALL, NORMAL, LARGE, VLARGE, HUGE;


    public static int toInt(Size s) {
        switch(s) {
            case UNDEFINED:
                return -1;
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

    public static Size intToSize(Integer size){
        if(size == -1){
            return UNDEFINED;
        } else if (toInt(MICRO) > size){
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

    public static Size stringToSize(String s){
        if (s.equals("tiny")){
            return MICRO;
        } else if (s.equals("very small")){
            return VSMALL;
        } else if (s.equals("small")){
            return SMALL;
        } else if (s.equals("averagely sized")){
            return NORMAL;
        } else if (s.equals("quite large")){
            return LARGE;
        } else if (s.equals("very big")){
            return VLARGE;
        } else if (s.equals("enormous")){
            return HUGE;
        } else{
            System.out.println("error in converting size");
            return null;
        }
    }

    public static String intToString(Integer size){
        Size s = intToSize(size);

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
                return "enormous";
            default:
                return null;
        }

    }

    public static ArrayList<Size> getSizes(){
        ArrayList<Size> temp = new ArrayList<>();
        temp.add(MICRO);
        temp.add(VSMALL);
        temp.add(SMALL);
        temp.add(NORMAL);
        temp.add(LARGE);
        temp.add(VLARGE);
        temp.add(HUGE);
        return temp;
    }

    public static ArrayList<String> getSizeStrings(){
        ArrayList<Size> sizes = getSizes();
        ArrayList<String> temp = new ArrayList<>();

        for(Size s : sizes){
            String currentSize = intToString(toInt(s));
            currentSize = currentSize.substring(0, 1).toUpperCase()+currentSize.substring(1, currentSize.length());
            temp.add(currentSize);
        }
        return temp;
    }
}