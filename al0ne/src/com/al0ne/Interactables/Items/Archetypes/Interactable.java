package com.al0ne.Interactables.Items.Archetypes;

/**
 * Created by BMW on 26/06/2016.
 */
public abstract class Interactable {

    protected String name;
    protected String description;
    protected boolean toggled;

    public Interactable(String name, String description) {
        this.name = name;
        this.description = description;
        toggled=false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void printDescription(){
        System.out.println(description);
    }

    public boolean isToggled() {
        return toggled;
    }

    public void isInteractedWith(Pickable object){};

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }
}
