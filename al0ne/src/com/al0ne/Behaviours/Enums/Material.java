package com.al0ne.Behaviours.Enums;

import java.util.ArrayList;

/**
 * Created by BMW on 02/05/2017.
 */
public enum Material {
    IRON(2, 2, 1), BRASS(0, 0, 0.5), SILVER(1, 1, 1), GOLD(1, 1, 1.5), STEEL(3, 3, 1),

    LEATHER(1, 0.5), WOOD(0, 1, 1.5), FUR(1, 0.5), GLASS(0.5), PAPER(1),

    FIBRE(0.5), STONE(1, 1, 2),

    UNDEFINED(0);

    private int toughness;
    private int damage;
    private double weight;

    Material(int damage, int toughness, double weight){
        this.damage=damage;
        this.toughness=toughness;
        this.weight=weight;
    }

    Material(int toughness, double weight){
        this.toughness=toughness;
        this.weight=weight;
    }

    Material(double weight){
        this.weight=weight;
        this.toughness=0;
        this.damage=0;
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

    public static ArrayList<Material> getMaterials(boolean armor){
        ArrayList<Material> materials = new ArrayList<>();
        materials.add(IRON);
        materials.add(WOOD);
        materials.add(LEATHER);
        materials.add(GOLD);
        materials.add(STEEL);
        if(armor){
            materials.add(FUR);
        } else{
            materials.add(STONE);
        }
        return materials;
    }

}
