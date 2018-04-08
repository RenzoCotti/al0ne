package com.al0ne.AbstractEntities.Enums;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by BMW on 02/05/2017.
 */
public enum Material {

    //materials are of the kind (DMG)/TOUGHNESS/WEIGHT/PRICE

    //Base Tech materials
    CLAY(0, 2, 0), STONE(1, 1, 4, 0), WOOD(1, 2, 3, 0), LEATHER(1, 1, 4), FUR(1, 2, 2),
    FABRIC(0.5, 0),


    //Low Tech materials
    IRON(3, 3, 3, 2), BRASS(0, 0, 2, 1), SILVER(2, 2, 1, 6), GOLD(1, 1, 4, 12),
    GLASS(0.5, 1), PAPER(1, 1),

    //Mid Tech materials
    PLASTIC(0, 0, 0.5, 0), KEVLAR(6, 2, 10), STEEL(4, 4, 3, 2), COTTON(1, 1),
    ALUMINIUM(1, 1, 1, 2), CARDBOARD (1, 1, 1),

    //High Tech materials
    NANITE(10, 1, 20), TITANIUM(5, 5, 1, 10), SILICA(1, 0, 1, 4), STYRATEX(5, 2, 7),

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
        if (m == null) return "";
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

    //0: armor
    //1: weapon
    //2: shield
    //3: helmet
    public static ArrayList<Material> getMaterials(int number){
        ArrayList<Material> materials = new ArrayList<>();
        materials.add(IRON);
        materials.add(WOOD);
        materials.add(GOLD);
        materials.add(STEEL);
        materials.add(BRASS);
        materials.add(SILVER);
        if(number == 0){
            materials.add(LEATHER);
        } else if(number == 1){
            materials.add(STONE);
        } else if(number == 2){

        } else if(number == 3){

        }
        return materials;
    }

    public static ArrayList<Material> getAllMaterials(){
        ArrayList<Material> materials = new ArrayList<>();
        materials.addAll(Arrays.asList(Material.class.getEnumConstants()));
        return materials;
    }

    public static ArrayList<String> getAllMaterialString(){
        ArrayList<Material> materials = getAllMaterials();
        ArrayList<String> temp = new ArrayList<>();

        for(Material m : materials){
            String current;
            if(m.toString().equals("UNDEFINED")){
                current = "Not specified";
            } else {
                current = m.toString().toLowerCase();
                current = current.substring(0, 1).toUpperCase()+current.substring(1, current.length());
            }
            temp.add(current);
        }
        return temp;
    }

    public static Material strToMaterial(String s){
        ArrayList<Material> materials = getAllMaterials();
        for (Material m: materials){
            if(m.toString().toLowerCase().equals(s.toLowerCase())){
                return m;
            }
        }
        return UNDEFINED;
    }

    public static String materialToString(Material m){
        ArrayList<Material> materials = getAllMaterials();
        for (Material material: materials){
            if(material.toString().toLowerCase().equals(m.toString().toLowerCase())){
                String temp = material.toString().toLowerCase();
                temp = temp.substring(0, 1).toUpperCase()+temp.substring(1, temp.length());
                return temp;
            }
        }
        return "Not specified";
    }



    public static ArrayList<Material> getMetals(){
        ArrayList<Material> materials = new ArrayList<>();
        materials.add(IRON);
        materials.add(GOLD);
        materials.add(STEEL);
        materials.add(BRASS);
        materials.add(SILVER);
        materials.add(TITANIUM);
        materials.add(ALUMINIUM);
        return materials;
    }

    public static ArrayList<Material> getBaseTechMaterials(){
        ArrayList<Material> materials = new ArrayList<>();
        materials.add(CLAY);
        materials.add(STONE);
        materials.add(WOOD);
        materials.add(LEATHER);
        materials.add(FUR);
        materials.add(FABRIC);
        return materials;
    }

    public static ArrayList<Material> getLowTechMaterials(){
        ArrayList<Material> materials = getBaseTechMaterials();
        materials.add(IRON);
        materials.add(BRASS);
        materials.add(SILVER);
        materials.add(GOLD);
        materials.add(GLASS);
        materials.add(PAPER);
        return materials;
    }


    public static ArrayList<Material> getMidTechMaterials(){
        ArrayList<Material> materials = getLowTechMaterials();
        materials.add(PLASTIC);
        materials.add(KEVLAR);
        materials.add(STEEL);
        materials.add(COTTON);
        materials.add(ALUMINIUM);
        materials.add(CARDBOARD);
        return materials;
    }

    public static ArrayList<Material> getHighTechMaterials(){
        ArrayList<Material> materials = getMidTechMaterials();
        materials.add(NANITE);
        materials.add(TITANIUM);
        materials.add(SILICA);
        return materials;
    }

}
