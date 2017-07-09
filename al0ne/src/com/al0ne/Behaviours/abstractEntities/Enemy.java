package com.al0ne.Behaviours.abstractEntities;

import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Pairs.PairDrop;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.Status;
import com.al0ne.Behaviours.abstractEntities.WorldCharacter;
import com.al0ne.Engine.Utility;

import java.util.ArrayList;
import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;


/**
 * Created by BMW on 13/03/2017.
 */
public abstract class Enemy extends WorldCharacter {

    static final int CHANCE_OF_SPECIAL = 20;

    protected boolean special;
    protected boolean aggro;
    protected boolean snooze;

    protected ArrayList<String> resistances;
    //maps status to percentage of applying
    protected HashMap<Status, Integer> inflictStatuses;


    public Enemy(String name, String description, String shortDescription,
                 int maxHealth, int attack, int dexterity, int armor, int damage) {
        super("enemy"+(entityCounter++), name, description, shortDescription,maxHealth, attack, dexterity, armor, damage);
        this.resistances = new ArrayList<>();
        this.inflictStatuses = new HashMap<>();
        this.alive = true;
        this.type='e';
        this.special=false;
        this.aggro = false;
        this.snooze = false;

        initialisePrefix();
    }

    public void addInflictedStatus(Status status, Integer chanceToApply){
        inflictStatuses.put(status, chanceToApply);
    }

    //testing purposes
    @Override
    public void printLongDescription(Player player, Room room) {
        System.out.println("HP: "+currentHealth+"/"+maxHealth+" DMG: "+damage+" ATK: "+attack+" DEX: "+dexterity+" ARM: "+armor);
        printToLog(longDescription);
        printHealthDescription();
    }

    private void initialisePrefix(){

        int chance = Utility.randomNumber(100);

        if(chance < 100 - CHANCE_OF_SPECIAL){
            return;
        }


        ArrayList<String> prefixes = new ArrayList<>();

        prefixes.add("tough");
        prefixes.add("fiery");
        prefixes.add("strong");
        prefixes.add("resilient");
        prefixes.add("fast");
        prefixes.add("fierce");

        int rolled = (int)(Math.random() * 6 );

        name = prefixes.get(rolled).toLowerCase()+" "+name.toLowerCase();
        longDescription = longDescription+" It looks quite "+prefixes.get(rolled).toLowerCase()+".";


        String[] temp = shortDescription.split(" ");
        String newDescription = temp[0]+" "+prefixes.get(rolled);

        for(int i=1; i<temp.length; i++){
            newDescription+=" "+temp[i];
        }
        shortDescription=newDescription;



        switch (rolled){
            //case tough
            case 0:
                this.maxHealth=maxHealth+maxHealth/2;
                this.currentHealth=maxHealth;
                break;
            //case fiery
            case 1:
                this.damage=damage+damage/2;
                this.attack=attack+attack/3;
                break;
            //case strong
            case 2:
                this.damage=damage+damage;
                this.attack=-10;
                break;
            //case resilient
            case 3:
                this.armor=armor+armor/2;
                break;
            //case fast
            case 4:
                this.dexterity=dexterity+dexterity/3;
                break;
            //case fierce
            case 5:
                this.attack=attack+attack/3;
                break;
        }

        special=true;
    }

    public void printHealthDescription(){
        double percentage = ((double)currentHealth/(double)maxHealth)*100;

        if (percentage >= 80){
            printToLog("The "+name.toLowerCase()+" seems mostly fine.");
        } else if (percentage >= 60 && percentage < 80){
            printToLog("The "+name.toLowerCase()+" doesn't look its best");
        } else if (percentage >= 40 && percentage < 60){
            printToLog("The "+name.toLowerCase()+" is staggering.");
        } else if (percentage >= 20 && percentage < 40){
            printToLog("The "+name.toLowerCase()+" falls, then gets up again.");
        } else {
            if (this.currentHealth <= 0){
                alive=false;
            } else {
                printToLog("The "+name.toLowerCase()+" seems almost dead.");
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
        ArrayList<PairDrop> loot = new ArrayList<>();
        for(Pair p : inventory.values()){
            loot.add((PairDrop)p);
        }
        return loot;
    }

    public void addItemLoot(Item item, Integer amount, Integer probability) {
        this.inventory.put(item.getID(), new PairDrop(item, amount, probability));
    }

    public void addItemLoot(Item item) {
        this.inventory.put(item.getID(), new PairDrop(item, 1, 100));
    }


    public boolean addLoot(Room room) {
        boolean dropped = false;
        for (PairDrop pair : getLoot()){
            int rolled = Utility.randomNumber(100);
            if(((100 - pair.getProbability()) - rolled <= 0) || special){
                Item currentLoot = (Item) pair.getEntity();

                if(pair.getCount() > 5){
                    int randomAmount = (int)(Math.random() * (2*pair.getCount() - pair.getCount()/2) + pair.getCount()/2);
                    room.addItem(currentLoot, randomAmount);
                } else{
                    room.addItem(currentLoot, pair.getCount());
                }
                dropped = true;
            }
            System.out.println("Loot: rolled "+rolled+"; expected "+pair.getProbability());
        }
        return dropped;
    }


    public void isAttacked(Player player, Room room){

        aggro = true;

        int attackRoll = Utility.randomNumber(100)+attack;
        int dodgeRoll = Utility.randomNumber(100)+player.getDexterity();
//        System.out.println("ENEMY ATK: "+attackRoll+" vs DEX: "+dodgeRoll);
        if(attackRoll > dodgeRoll){
            printToLog("The "+name.toLowerCase()+" attacks and hits you.");
            int inflictedDamage = damage-player.getArmorLevel();
            if (inflictedDamage>0){
                for (Status s : inflictStatuses.keySet()){
                    //possibly resistance from player?
                    int inflictProbability = 100-inflictStatuses.get(s);
                    int inflictStatus = Utility.randomNumber(100);
                    if(inflictStatus > inflictProbability){
                        if (player.putStatus(s)){
                            printToLog(s.getOnApply());
                        }
                    }
                }
                if(player.modifyHealth(-inflictedDamage)) {
                    player.setCauseOfDeath(shortDescription);
                }
            } else{
                printToLog("Your armor absorbs the damage.");
            }
        } else{
            printToLog("The "+name.toLowerCase()+" attacks, but you manage to dodge.");
        }
    }

    public boolean isWeakAgainst(String type){
        for (String s : resistances){
            if (s.equals(type)){
                return false;
            }
        }
        return true;
    }

    public boolean isAggro() {
        return aggro;
    }

    public void setAggro(boolean aggro) {
        this.aggro = aggro;
    }

    public boolean isSnooze() {
        return snooze;
    }

    public void setSnooze(boolean snooze) {
        this.snooze = snooze;
    }

    public boolean handleLoot(Room room){
        if(!alive){
            printToLog("You defeated the "+ name.toLowerCase());
            if(addLoot(room)){
                printToLog("The "+name.toLowerCase()+" drops some items.");
            }
            room.getEntities().remove(ID);
            return true;
        }
        return false;
    }
}
