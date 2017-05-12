package com.al0ne.Behaviours.Enums;

import java.util.ArrayList;

/**
 * Created by BMW on 02/05/2017.
 */
public enum Material {
    IRON(3, 3, 3, 2), BRASS(0, 0, 1.5, 1), SILVER(2, 2, 1, 6), GOLD(1, 1, 4, 12), STEEL(4, 4, 3, 2),

    WOOD(1, 2, 3, 0), STONE(1, 1, 4, 0), LEATHER(1, 0.5, 4),

    GLASS(0.5, 1), PAPER(1, 1), FIBRE(0.5, 0), CLAY(0, 0.5, 0),

    UNDEFINED(0, 0);

    private int toughness;
    private int damage;
    private double weight;
    private int price;

    Material(int damage, int toughness, double weight, int price){
        this.damage=damage;
        this.toughness=toughness;
        this.weight=weight;
        this.price = price;
    }

    Material(int toughness, double weight, int price){
        this.toughness=toughness;
        this.weight=weight;
        this.price = price;
    }

    Material(double weight, int price){
        this.weight=weight;
        this.toughness=0;
        this.damage=0;
        this.price = price;
    }

    public static String stringify(Material m){
        return m.toString().toLowerCase();
    }

    public int getToughness(){
        return toughness;
    }

    public int getDamage(){
        return damage;
    }

    public double getWeight(){
        return weight;
    }

    public int getPrice(){
        return price;
    }

    public static ArrayList<Material> getMaterials(boolean armor){
        ArrayList<Material> materials = new ArrayList<>();
        materials.add(IRON);
        materials.add(WOOD);
        materials.add(GOLD);
        materials.add(STEEL);
        materials.add(BRASS);
        materials.add(SILVER);
        if(armor){
            materials.add(LEATHER);
        } else{
            materials.add(STONE);
        }
        return materials;
    }

    public static ArrayList<Material> getMetals(){
        ArrayList<Material> materials = new ArrayList<>();
        materials.add(IRON);
        materials.add(GOLD);
        materials.add(STEEL);
        materials.add(BRASS);
        materials.add(SILVER);
        return materials;
    }

}
