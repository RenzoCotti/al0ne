package com.al0ne.AbstractEntities.Player;

import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Pairs.Pair;
import com.al0ne.AbstractEntities.Quests.Quest;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Weapon;
import com.al0ne.Engine.Utility.Utility;

import static com.al0ne.Engine.Main.player;
import static com.al0ne.Engine.Main.printToLog;
import static com.al0ne.Engine.Main.printToSingleLine;

public class PrintPlayer {
    //this function prints the currently equipped weapon
    public static void printWielded(Player p){
        if(p.getWeapon() == null){
            printToLog("You're using your fists");
            return;
        }
        printToLog("You're using your "+p.getWeapon().getName());
    }

    //this function prints all currently equipped armor pieces
    public static void printArmor(Player p){

        boolean first=true;
        for (Item w : p.getWornItems().values()){
            if (w != null && !(w instanceof Weapon)){
                if(first){
                    printToSingleLine("You're wearing "+w.getShortDescription());
                    first = false;
                }else {
                    printToSingleLine(", "+w.getShortDescription());
                }
            }
        }
        if(first){
            printToLog("You're not wearing anything.");
        } else{
            printToLog();
        }
    }

    //this function prints a string corresponding to the current
    //health level
    public static void printHealthStatus(Player p){
        double percentage = ((double)p.getCurrentHealth()/(double)p.getMaxHealth())*100;

        if (percentage >= 80){
            printToLog("You're as healthy as ever.");
        } else if (percentage >= 60 && percentage < 80){
            printToLog("You're mostly fine.");
        } else if (percentage >= 40 && percentage < 60){
            printToLog("You need to medicate.");
        } else if (percentage >= 20 && percentage < 40){
            printToLog("You're bleeding heavily");
        } else {
            if (p.getCurrentHealth()<=0){
                return;
            }
            printToLog("You're alive by a miracle");
        }
    }

    //debug function, prints the current weight.
    public static void printWeight(Player p) {
        printToLog(p.getCurrentWeight()+"/"+p.getMaxWeight()+" kg.");
    }

    //prints the inventory
    public static void printInventory(Player p){
        if (p.getInventory().size()==0){
            printToLog("You have no items.");
        } else {
            printToLog("You have these items:");
            for (Pair pair : p.getInventory().values()) {
                Item currentItem = (Item) pair.getEntity();
                double weight = Utility.twoDecimals(currentItem.getWeight()*pair.getCount());
                printToLog("- "+pair.getCount()+"x " + currentItem.getName()+". "+weight+" kg.");
            }
            printToLog();
            printWeight(p);
        }
    }

    //prints the quests
    public static void printQuests(Player p){
        if (p.getQuests().size()==0){
            printToLog("You have no quests currently.");
        } else {

            boolean first = true;

            for (Quest q : p.getQuests().values()) {
                if(q.isVisibleToThePlayer() && first){
                    first = false;
                    printToLog("You have these quests:");
                    printToLog("- "+q.getQuestName());
                } else if(q.isVisibleToThePlayer()){
                    printToLog("- "+q.getQuestName());
                }
            }

            if(first){
                printToLog("You completed all your quests for now.");

            }

        }
    }
}
