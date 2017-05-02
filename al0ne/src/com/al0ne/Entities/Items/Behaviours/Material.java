package com.al0ne.Entities.Items.Behaviours;

/**
 * Created by BMW on 02/05/2017.
 */
public enum Material {
    IRON, BRASS, SILVER, GOLD, STEEL,

    LEATHER, WOOD, FUR, GLASS, PAPER,

    FIBRE, STONE,

    UNDEFINED;

    public static String stringify(Material m){
        return m.toString().toLowerCase();
    }
}
