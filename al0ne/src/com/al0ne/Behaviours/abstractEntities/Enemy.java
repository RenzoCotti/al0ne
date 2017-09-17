package com.al0ne.Behaviours.abstractEntities;

import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Pairs.PairDrop;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Quests.KillQuest;
import com.al0ne.Behaviours.Quests.Quest;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.Status;
import com.al0ne.Engine.Utility;

import java.util.ArrayList;
import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;


/**
 * Created by BMW on 13/03/2017.
 */
public class Enemy extends WorldCharacter {

    static final int CHANCE_OF_SPECIAL = 20;

    protected boolean elite;




    //special: 0 means not at all, 1 random, 2 assured

    public Enemy(String name, String description, String shortDescription,
                 int maxHealth, int attack, int dexterity, int armor, int damage) {
        super(name, name, description, shortDescription,maxHealth, attack, dexterity, armor, damage);
        this.type='e';
        this.elite=false;

//        if(special == 0){
//            return;
//        } else if(special == 1){
//
//            int chance = Utility.randomNumber(100);
//
//            if(chance < 100 - CHANCE_OF_SPECIAL){
//                return;
//            }
//
//            initialisePrefix();
//        } else if(special == 2){
//            initialisePrefix();
//        }

    }


    //testing purposes
    @Override
    public void printLongDescription(Player player) {
        System.out.println("HP: "+currentHealth+"/"+maxHealth+" DMG: "+damage+" ATK: "+attack+" DEX: "+dexterity+" ARM: "+armor);
        printToLog(longDescription);
        printHealthDescription();
    }

    public void setElite(){


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

        elite=true;
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


    public boolean isElite() {
        return elite;
    }

}
