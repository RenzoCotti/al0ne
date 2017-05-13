package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Pairs.PairReactionItem;
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
public class NPC extends WorldCharacter {

    //maps subjects to answers
    protected HashMap<String, Subject> subjects;
    protected HashMap<String, PairReactionItem> reactionItems;
    protected String intro;


    protected boolean isShopkeeper=false;

    public NPC(String id, String name, String description, String shortDescription, String intro,
            int maxHealth, int attack, int dexterity, int armor, int damage) {
        super(id, name, description, shortDescription, maxHealth, attack, dexterity, armor, damage);
        this.subjects = new HashMap<>();
        this.reactionItems = new HashMap<>();
        this.intro=intro;
        this.type='n';
    }

    public NPC(String id, String name, String description, String intro) {
        super(id, name, description, name, 20, 40, 40, 1, 2);
        this.subjects = new HashMap<>();
        this.reactionItems = new HashMap<>();
        this.intro=intro;
        this.type='n';
    }

    public NPC(String id, String name, String description, String shortDescription, String intro) {
        super(id, name, description, shortDescription, 20, 40, 40, 1, 2);
        this.subjects = new HashMap<>();
        this.reactionItems = new HashMap<>();
        this.intro=intro;
        this.type='n';
    }


    public boolean isShopkeeper() {
        return isShopkeeper;
    }


    public void addSubject(String subjectID, Subject subject){
        subjects.put(subjectID, subject);
    }

    public void addReactionItem(String itemID, String quest, Item item){
        reactionItems.put(itemID, new PairReactionItem(quest, item));
    }

    public void addReactionItem(String itemID, Item item){
        reactionItems.put(itemID, new PairReactionItem(null, item));
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
        if(reactionItems.get(item.getID()) != null){
                Item currentItem = reactionItems.get(item.getID()).getReward();
                printToLog("\"Thank you, i really needed a "+item.getName()+".\"");
                printToLog("\"Here's a "+currentItem.getName()+" as a reward.\"");
                //removes *all* items of that type
                player.getInventory().remove(item.getID());
                player.modifyWeight(-item.getWeight());
                if (!player.simpleAddItem(currentItem, 1)){
                    player.getCurrentRoom().addEntity(currentItem, 1);
                }
                if(reactionItems.get(item.getID()).getQuestID() != null){
//                    System.out.println("completing: "+reactionItems.get(item.getID()).getQuestID());
                    player.completeQuest(reactionItems.get(item.getID()).getQuestID());
                }

                reactionItems.remove(item.getID());
                return true;
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

}
