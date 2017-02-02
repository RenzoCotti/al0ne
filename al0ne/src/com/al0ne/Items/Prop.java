package com.al0ne.Items;

import java.util.ArrayList;

/**
 * Created by BMW on 02/02/2017.
 */
public abstract class Prop {

    protected String name;
    protected String description;
    protected String after;
    protected String requiresItem;
    protected ArrayList<String> requiredType;
    protected boolean active;

    public Prop(String name, String description) {
        this.requiredType = new ArrayList<>();
        this.name = name;
        this.description = description;
        this.after = description;
        this.requiresItem="none";
        this.active=false;
    }

    public Prop(String name, String description, String after) {
        this.requiredType = new ArrayList<>();
        this.name = name;
        this.description = description;
        this.after = after;
        this.requiresItem="none";
        this.active=false;
    }

    public void addType(String type){
        requiredType.add(type);
    }

    public boolean usedWith(Item item){
        for (String s: requiredType){
            if (item.hasProperty(s)){
                active=(!active);
                return true;
            }
        }
        return false;
    }

    public boolean used(){
        if (requiresItem.equals("none")){
            active=(!active);
            return true;
        } else{
            return false;
        }
    }

    public void printDescription(){
        if(!active){
            System.out.println(description);
        } else {
            System.out.println(after);
        }
    }

    public String getName() {
        return name;
    }
}
