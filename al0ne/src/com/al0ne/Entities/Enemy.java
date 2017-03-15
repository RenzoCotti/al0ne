package com.al0ne.Entities;

import com.al0ne.Items.Entity;
import com.al0ne.Items.Item;
import com.al0ne.Items.Pair;
import com.al0ne.Room;

import java.util.ArrayList;

/**
 * Created by BMW on 13/03/2017.
 */
public class Enemy extends Character{

    protected boolean alive;

    protected int maxHealth;
    protected int currentHealth;
    protected int damage;

    protected ArrayList<String> resistances;
    protected ArrayList<Pair> loot;

    public Enemy(String id, String name, String description, int maxHealth, int damage) {
        super(id, name, description);
        this.maxHealth=maxHealth;
        this.damage = damage;
        this.currentHealth=maxHealth;
        this.resistances = new ArrayList<>();
        this.loot = new ArrayList<>();
        this.alive = true;
        this.type='e';
    }

    public void printHealth() {
        System.out.println(currentHealth+"/"+maxHealth+" HP.");
    }


    public void modifyHealth(int health) {
        if (this.currentHealth+health <= maxHealth){
            this.currentHealth+=health;
        }
        double percentage = ((double)currentHealth/(double)maxHealth)*100;

        if (percentage >= 80){
            System.out.println("The "+name+" seems mostly fine.");
        } else if (percentage >= 60 && percentage < 80){
            System.out.println("The "+name+" doesn't look its best");
        } else if (percentage >= 40 && percentage < 60){
            System.out.println("The "+name+" is staggering.");
        } else if (percentage >= 20 && percentage < 40){
            System.out.println("The "+name+" falls, then gets up again.");
        } else {
            if (this.currentHealth <= 0){
                alive=false;
            } else {
                System.out.println("The "+name+" seems almost dead.");
            }
        }
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
            Item currentLoot = (Item) pair.getEntity();
            room.addItem(currentLoot, pair.getCount());
        }
    }


    public boolean isAttacked(Player player, Room room){
        if(!alive){
            System.out.println("You defeated the "+ name);
            System.out.println("The "+name+" drops some items.");
            addLoot(room);
            return true;
        }
        System.out.println("The "+name+" attacks and hits you.");
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
