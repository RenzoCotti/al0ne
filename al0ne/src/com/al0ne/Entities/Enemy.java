package com.al0ne.Entities;

import com.al0ne.Items.Item;
import com.al0ne.Items.Pair;
import com.al0ne.Items.PairDrop;
import com.al0ne.Room;

import java.util.ArrayList;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 13/03/2017.
 */
public abstract class Enemy extends Character{

    protected boolean alive;

    protected int maxHealth;
    protected int currentHealth;
    protected int damage;
    protected int attack;
    protected int armor;
    protected int dexterity;

    protected ArrayList<String> resistances;
    protected ArrayList<PairDrop> loot;


    public Enemy(String id, String name, String description, String shortDescription, int maxHealth, int damage, int attack, int armor, int dexterity) {
        super(id, name, description, shortDescription);
        this.maxHealth=maxHealth;
        this.damage = damage;
        this.currentHealth=maxHealth;
        this.resistances = new ArrayList<>();
        this.loot = new ArrayList<>();
        this.alive = true;
        this.type='e';
        this.attack = attack;
        this.armor = armor;
        this.dexterity = dexterity;

        initialisePrefix();

    }

    private void initialisePrefix(){

        ArrayList<String> prefixes = new ArrayList<>();

        prefixes.add("tough");
        prefixes.add("fiery");
        prefixes.add("strong");
        prefixes.add("resilient");
        prefixes.add("fast");
        prefixes.add("fierce");


    }

    public void printHealth() {
        printToLog(currentHealth+"/"+maxHealth+" HP.");
    }


    public void modifyHealth(int health) {
        if (this.currentHealth+health <= maxHealth){
            this.currentHealth+=health;
        }
        double percentage = ((double)currentHealth/(double)maxHealth)*100;

        if (percentage >= 80){
            printToLog("The "+name+" seems mostly fine.");
        } else if (percentage >= 60 && percentage < 80){
            printToLog("The "+name+" doesn't look its best");
        } else if (percentage >= 40 && percentage < 60){
            printToLog("The "+name+" is staggering.");
        } else if (percentage >= 20 && percentage < 40){
            printToLog("The "+name+" falls, then gets up again.");
        } else {
            if (this.currentHealth <= 0){
                alive=false;
            } else {
                printToLog("The "+name+" seems almost dead.");
            }
        }
    }


    public ArrayList<String> getResistances() {
        return resistances;
    }

    public void addResistance(String resistances) {
        this.resistances.add(resistances);
    }


    public ArrayList<PairDrop> getLoot() {
        return loot;
    }

    public void addItemLoot(Item item, Integer amount, Integer probability) {
        this.loot.add(new PairDrop(item, amount, probability));
    }

    public void addItemLoot(Item item) {
        this.loot.add(new PairDrop(item, 1, 100));
    }


    public boolean addLoot(Room room) {
        boolean dropped = false;
        for (PairDrop pair : loot){
            int rolled = (int)(Math.random() * (100 - 1) + 1);
            if((100 - pair.getProbability()) - rolled <= 0){
                Item currentLoot = (Item) pair.getEntity();

                if(pair.getCount() > 5){
                    int randomAmount = (int)(Math.random() * (2*pair.getCount() - pair.getCount()/2) + pair.getCount()/2);
                    room.addItem(currentLoot, randomAmount);
                } else{
                    room.addItem(currentLoot, pair.getCount());
                }
                dropped = true;
            }
            printToLog("rolled "+rolled+"; expected "+pair.getProbability());
        }
        return dropped;
    }


    public boolean isAttacked(Player player, Room room){
        if(!alive){
            printToLog("You defeated the "+ name);
            if(addLoot(room)){
                printToLog("The "+name+" drops some items.");
            }
            room.getEntities().remove(ID);
            return true;
        }
        printToLog("The "+name+" attacks and hits you.");
        int inflictedDamage = damage-player.getArmorLevel();
        if (inflictedDamage>0){
            player.modifyHealthPrint(-inflictedDamage);
        } else{
            printToLog("Your armor absorbs the damage.");
        }
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
