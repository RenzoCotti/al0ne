package com.al0ne.Interactables;

/**
 * Created by BMW on 26/06/2016.
 */
public class Door extends Interactable {

    public Door(String doorText, String error){
        result=doorText;
        this.error=error;
        toggled=false;
    }

}
