package com.al0ne.Items;
import com.al0ne.Items.Behaviours.Consumable;
import com.al0ne.Items.Behaviours.Cuttable;
import com.al0ne.Items.Behaviours.Sharp;
import com.al0ne.Items.Behaviours.Usable;
import com.al0ne.Items.ConcreteBehaviours.NoBehaviours.NoConsume;
import com.al0ne.Items.ConcreteBehaviours.NoBehaviours.NoCut;
import com.al0ne.Items.ConcreteBehaviours.NoBehaviours.NoUse;
import com.al0ne.Items.ConcreteBehaviours.NoBehaviours.NotSharp;
import com.al0ne.Items.ConcreteBehaviours.YesBehaviours.CanConsume;
import com.al0ne.Items.ConcreteBehaviours.YesBehaviours.CanCut;
import com.al0ne.Items.ConcreteBehaviours.YesBehaviours.CanUse;
import com.al0ne.Items.ConcreteBehaviours.YesBehaviours.IsSharp;

/**
 * Created by BMW on 02/02/2017.
 *
 * Item interface
 */
public abstract class Item {

    protected String name;
    protected String description;
    protected double weight;

    protected Cuttable cuttable;
    protected Consumable consumable;
    protected Usable usable;
    protected Sharp sharp;


    public Item(String name, String description, double weight) {
        this.name = name;
        this.weight = weight;
        this.description = description;
        this.cuttable = new NoCut();
        this.consumable = new NoConsume();
        this.usable = new NoUse();
        this.sharp = new NotSharp();
    }

    public void addProperty(String behaviour, int value){
        switch(behaviour){
            case "cuttable":
                this.cuttable=new CanCut();
                break;
            case "consumable":
                this.consumable=new CanConsume(value);
                break;
            case "usable":
                this.usable=new CanUse();
                break;
            case "sharp":
                this.sharp = new IsSharp(value);
                break;
            default:
                System.out.println("error in addProperty");
                System.out.println(behaviour);
        }
    }

    public void applyToItem(String command){
        if (command.equals("use")){
            if(usable.isUsed()){
                usable.isUsed();
            } else{
                consumable.isConsumed();
            }
        }
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void printDescription() {
        System.out.println(description);
    }

    public double getWeight() {
        return weight;
    }
}
