package com.al0ne.Interactables;

/**
 * Created by BMW on 26/06/2016.
 */
public abstract class Interactable {
    protected boolean toggled;
    protected String result;
    protected String error;


    protected void isToggled(){
        if (toggled){
            System.out.println(result);
        }
        else{
            System.out.println(error);
        }
    }

    public void setToggled(boolean toggled){
        this.toggled=toggled;
        isToggled();

    }

    public boolean getToggled(){
        return toggled;
    }

}
