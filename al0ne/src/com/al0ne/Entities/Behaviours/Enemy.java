package com.al0ne.Entities.Behaviours;

import com.al0ne.Entities.Items.Item;
import com.al0ne.Entities.Items.PairDrop;

import java.util.ArrayList;
import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;


/**
 * Created by BMW on 13/03/2017.
 */
public abstract class Enemy extends Character {

    static final int CHANCE_OF_SPECIAL = 20;




    protected boolean alive;

    protected int maxHealth;
    protected int currentHealth;
    protected int damage;
    protected int attack;
    protected int armor;
    protected int dexterity;

    protected boolean special;

    protected ArrayList<String> resistances;
    protected ArrayList<PairDrop> loot;
    //maps status to percentage of applying
    protected HashMap<Status, Integer> inflictStatuses;


    public Enemy(String id, String name, String description, String shortDescription) {
        super(id, name, description, shortDescription);
        this.resistances = new ArrayList<>();
        this.loot = new ArrayList<>();
        this.inflictStatuses = new HashMap<>();
        this.alive = true;
        this.type='e';
        this.special=false;

        this.attack = 0;
        this.armor = 0;
        this.dexterity = 0;
        this.maxHealth=0;
        this.currentHealth=0;
        this.damage = 0;
    }

    public void setStats( int maxHealth, int damage, int attack, int armor, int dexterity){
        this.attack = attack;
        this.armor = armor;
        this.dexterity = dexterity;
        this.maxHealth=maxHealth;
        this.currentHealth=maxHealth;
        this.damage = damage;

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

        int chance = (int)(Math.random() * (100 - 1) + 1);

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
//                System.out.println("health");
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
//                System.out.println("strong");
                break;
            //case resilient
            case 3:
                this.armor=armor+armor/2;
//                System.out.println("resilient");
                break;
            //case fast
            case 4:
                this.dexterity=dexterity+dexterity/3;
//                System.out.println("fast");
                break;
            //case fierce
            case 5:
                this.attack=attack+attack/3;
//                System.out.println("fierce");
                break;
        }

        special=true;
    }


    public int getDamage() {
        return damage;
    }

    public int getAttack() {
        return attack;
    }

    public int getArmor() {
        return armor;
    }

    public int getDexterity() {
        return dexterity;
    }


    public void printHealth() {
        printToLog(currentHealth+"/"+maxHealth+" HP.");
    }


    public void modifyHealth(int health) {
        if (this.currentHealth+health <= maxHealth){
            this.currentHealth+=health;
        }

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


    public boolean isAttacked(Player player, Room room){
        if(!alive){
            printToLog("You defeated the "+ name.toLowerCase());
            if(addLoot(room)){
                printToLog("The "+name.toLowerCase()+" drops some items.");
            }
            room.getEntities().remove(ID);
            return true;
        }
        int attackRoll = (int)(Math.random() * (100 - 1) + 1)+attack;
        int dodgeRoll = (int)(Math.random() * (100 - 1) + 1)+player.getDexterity();
        System.out.println("ENEMY ATK: "+attackRoll+" vs DEX: "+dodgeRoll);
        if(attackRoll > dodgeRoll){
            printToLog("The "+name.toLowerCase()+" attacks and hits you.");
            int inflictedDamage = damage-player.getArmorLevel();
            if (inflictedDamage>0){
                for (Status s : inflictStatuses.keySet()){
                    //possibly resistance from player?
                    int inflictProbability = 100-inflictStatuses.get(s);
                    int inflictStatus = (int)(Math.random() * (100 - 1) + 1);
                    if(inflictStatus > inflictProbability){
                        if (player.putStatus(s)){
                            printToLog(s.getOnApply());
                        }
                    }
                }
                player.modifyHealth(-inflictedDamage);
//                player.printHealth();
            } else{
                printToLog("Your armor absorbs the damage.");
            }
        } else{
            printToLog("The "+name.toLowerCase()+" attacks, but you manage to dodge.");
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
