package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Pairs.PairReactionItem;
import com.al0ne.Behaviours.Pairs.Subject;
import com.al0ne.Behaviours.Quests.Quest;
import com.al0ne.Behaviours.abstractEntities.WorldCharacter;

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
    //a reaction item is an item that, when given to the npc, produces a result
    protected HashMap<String, PairReactionItem> reactionItems;
    protected String intro;
    protected boolean knowName;


    protected boolean isShopkeeper=false;

    public NPC(String name, String description, String intro,
            int maxHealth, int attack, int dexterity, int armor, int damage) {
        super("npc"+(entityCounter++), name, description, name, maxHealth, attack, dexterity, armor, damage);
        this.subjects = new HashMap<>();
        this.reactionItems = new HashMap<>();
        this.intro=intro;
        this.type='n';
        this.knowName = false;
    }

    public NPC(String name, String description, String intro) {
        super("npc"+(entityCounter++), name, description, name, 20, 40, 40, 1, 2);
        this.subjects = new HashMap<>();
        this.reactionItems = new HashMap<>();
        this.intro=intro;
        this.type='n';
    }

    public NPC(String name, String description, String shortDescription, String intro) {
        super("npc"+(entityCounter++), name, description, shortDescription, 20, 40, 40, 1, 2);
        this.subjects = new HashMap<>();
        this.reactionItems = new HashMap<>();
        this.intro=intro;
        this.type='n';
    }

    //is the npc a shopkeeper?
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
                    player.addQuest(temp.getQuest());
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

    //need todo revise what's in this, npcs can receive items without there being a qeust related to it
    public boolean isGiven(Item item, Player player){

        boolean completed = false;
        for(Quest q: player.getQuests().values()){
            if(q.checkCompletion(player)){
                completed = true;
            }
        }
        if(completed){
            return true;
        }

        printToLog("\"Sorry, I don't need it.\"");
        return false;
//        if(reactionItems.get(item.getID()) != null){
//                Item currentItem = reactionItems.get(item.getID()).getReward();
//                printToLog("\"Thank you, i really needed a "+item.getName()+".\"");
//                printToLog("\"Here's a "+currentItem.getName()+" as a reward.\"");
//                //removes *all* items of that type
//                player.getInventory().remove(item.getID());
//                player.modifyWeight(-item.getWeight());
//                if (!player.simpleAddItem(currentItem, 1)){
//                    player.getCurrentRoom().addEntity(currentItem, 1);
//                }
////                if(reactionItems.get(item.getID()).getQuestID() != null){
//////                    System.out.println("completing: "+reactionItems.get(item.getID()).getQuestID());
////                    player.completeQuest(reactionItems.get(item.getID()).getQuestID());
////                }
//
//                reactionItems.remove(item.getID());
//                return true;
//        }
    }

    public String getIntro() {
        return intro;
    }

    @Override
    public String getName() {
        return name;
    }

    public void printIntro(){
        printToLog("\""+intro+"\"");
        this.knowName = true;
    }

}
