package com.al0ne;

import com.al0ne.Items.Item;
import com.al0ne.Items.Pair;

import java.util.ArrayList;

/**
 * Created by BMW on 13/03/2017.
 */
public class Enemy {
    private String id;
    private String name;
    private String description;

    private int currentHealth;
    private int maxHealth;
    private int damage;

    private ArrayList<String> resistances;
    private ArrayList<Pair> loot;

    boolean alive = true;

    public Enemy(String id, String name, String description, int maxHealth, int damage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currentHealth = maxHealth;
        this.maxHealth = maxHealth;
        this.damage = damage;
        this.resistances = new ArrayList<>();
        this.loot = new ArrayList<>();
    }

    public String getID() {
        return id;
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

    public void printHealth() {
        System.out.println(currentHealth+"/"+maxHealth+" HP.");
    }

    public void modifyHealth(int health) {
        if (this.currentHealth+health <= maxHealth){
            this.currentHealth+=health;
        }
        int percentage = (currentHealth/maxHealth)*100;

        if (percentage >= 80){
            System.out.println("The "+name+" seems mostly fine.");
        } else if (percentage >= 60 && percentage < 80){
            System.out.println("The "+name+" has taken a good beating.");
        } else if (percentage >= 40 && percentage < 60){
            System.out.println("The "+name+" is bleeding.");
        } else if (percentage >= 20 && percentage < 40){
            System.out.println("The "+name+" is bleeding heavily");
        } else {
            if (this.currentHealth <= 0){
                alive=false;
            } else {
                System.out.println("The "+name+" seems almost dead.");
            }
        }
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public ArrayList<String> getResistances() {
        return resistances;
    }

    public void addResistance(String resistances) {
        this.resistances.add(resistances);
    }


    public ArrayList<Pair> getLoot() {
        return loot;
    }

    public void addItemLoot(Item item, Integer amount) {
        this.loot.add(new Pair(item, amount));
    }

    public void addItemLoot(Item item) {
        this.loot.add(new Pair(item, 1));
    }


    public void addLoot(Room room) {
        for (Pair pair : loot){
            boolean addedItem=false;
            Item currentItem = pair.getItem();
//            System.out.println(currentItem.getID());
            for (String id : room.getItems().keySet()){

                //case the item is already in the room
                if (currentItem.getID().equals(id)){
                    addedItem=true;
                    System.out.println("Adding"+currentItem.getName()+"x"+pair.getCount());

                    room.addItem(currentItem, pair.getCount()+room.getItems().get(id).getCount());
                } else{
                    addedItem=true;
                    room.addItem(currentItem, pair.getCount());
                    System.out.println("Adding"+currentItem.getName()+"x"+pair.getCount());
                }
            }

            if (!addedItem){
                room.addItem(currentItem, pair.getCount());
            }
        }
    }


    public boolean isAttacked(Player player, Room room){
        if(!alive){
            System.out.println("You defeated the "+ name);
            System.out.println("The "+name+" drops some items.");
            addLoot(room);
            return true;
        }
        player.modifyHealth(-damage);
        return false;
    }

    public boolean isWeakAgainst(String type){
        for (String s : resistances){
            if (s.equals(type)){
                return false;
            }
        }
        return true;
    }
}
