package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Pairs.Subject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;

/**
 * An NPC has:
 * - dialogue options
 * - objects to react
 * - name
 * - longDescription
 * - life?
 * - inventory
 */
public class NPC extends Character{

    //maps subjects to answers
    protected HashMap<String, Subject> subjects;
    protected HashMap<String, Item> reactionItems;
    protected ArrayList<Item> inventory;
    protected String intro;


    protected boolean isShopkeeper=false;

    public NPC(String id, String name, String description, String shortDescription, String intro) {
        super(id, name, description, shortDescription);
        this.subjects = new HashMap<>();
        this.reactionItems = new HashMap<>();
        this.inventory = new ArrayList<>();
        this.intro=intro;
        this.type='n';
    }


    public boolean isShopkeeper() {
        return isShopkeeper;
    }


    public void addSubject(String subjectID, Subject subject){
        subjects.put(subjectID, subject);
    }

    public void addReactionItem(String itemid, Item item){
        reactionItems.put(itemid, item);
    }


    public boolean talkAbout(String subject, Player player){
        for (String s : subjects.keySet()){
            if (s.equals(subject)){
                Subject temp = subjects.get(s);
                if(temp.addsQuest()){
                    player.addQuest(temp.getQuestID());
                }
                if(temp.givesItem()){
                    if(!player.addAmountItem(temp.getItem(), temp.getItem().getCount())){
                        player.getCurrentRoom().addEntity(temp.getItem().getEntity(), temp.getItem().getCount());
                    }
                }
                printToLog("\""+temp.getAnswer()+"\"");
                return true;
            }
        }
        return false;
    }

    public boolean isGiven(Item item, Player player){
        for (String s : reactionItems.keySet()){

            if (item.getID().toLowerCase().equals(s)){
                printToLog("\"Thank you, i really needed a "+item.getName()+".\"");
                printToLog("\"Here's a "+reactionItems.get(s).getName()+" as a reward.\"");
                //removes *all* items of that type
                player.getInventory().remove(item.getID());
                player.modifyWeight(-item.getWeight());
                if (!player.simpleAddItem(reactionItems.get(s), 1)){
                    player.getCurrentRoom().addEntity(reactionItems.get(s), 1);
                }
                return true;
            }
        }
        printToLog("\"Sorry, I don't need it.\"");
        return false;
    }

    @Override
    public String getName() {
        return name;
    }

    public void printIntro(){
        printToLog("\""+intro+"\"");
    }

    public void printLongDescription(Player player, Room room){
        printToLog(longDescription);
    }

}
