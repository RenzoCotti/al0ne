package com.al0ne.Entities.Items.Behaviours;

/**
 * Created by BMW on 02/05/2017.
 */
public enum Material {
    IRON(2), BRASS(1), SILVER(2), GOLD(1), STEEL(3),

    LEATHER(1), WOOD(1), FUR(0), GLASS(0), PAPER(0),

    FIBRE(0), STONE(3),

    UNDEFINED(0);

    private int value;
    Material(int amt){
        this.value=amt;
    }

    public static String stringify(Material m){
        return m.toString().toLowerCase();
    }

    public int getValue() { return value; }
}
