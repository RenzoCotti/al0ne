package com.al0ne.AbstractEntities;

import com.al0ne.AbstractEntities.Pairs.Subject;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Quests.Quest;
import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Abstract.WorldCharacter;
import com.al0ne.Engine.Physics.InteractionResult.InteractionBehaviour;
import com.al0ne.Engine.Physics.Physics;

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
    private HashMap<String, Subject> subjects;
    //a reaction item is an item that, when given to the npc, produces a result
    private HashMap<String, Subject> reactionItems;
    private String intro;


    protected boolean isShopkeeper=false;

    public NPC(String name, String description, String intro,
            int maxHealth, int attack, int dexterity, int armor, int damage) {
        super("npc"+(entityCounter++), name, description, name, maxHealth, attack, dexterity, armor, damage);
        this.subjects = new HashMap<>();
        this.reactionItems = new HashMap<>();
        this.intro=intro;
    }

    public NPC(String name, String description, String intro) {
        super("npc"+(entityCounter++), name, description, name, 20, 40, 40, 1, 2);
        this.subjects = new HashMap<>();
        this.reactionItems = new HashMap<>();
        this.intro=intro;
    }

    public NPC(String name, String description, String shortDescription, String intro) {
        super("npc"+(entityCounter++), name, description, shortDescription, 20, 40, 40, 1, 2);
        this.subjects = new HashMap<>();
        this.reactionItems = new HashMap<>();
        this.intro=intro;
    }

    //is the npc a shopkeeper?
    public boolean isShopkeeper() {
        return isShopkeeper;
    }


    public void addSubject(String keyword, Subject subject){
        subjects.put(keyword, subject);
    }

    public void addReactionItem(String itemID, Subject s){
        reactionItems.put(itemID, s);
    }

    public boolean talkAbout(String subject, Player player){
        for (String current : subjects.keySet()){
            if (current.equals(subject)){
                Subject s = subjects.get(current);

                printToLog("\""+s.getAnswer()+"\"");

                for(InteractionBehaviour ib : s.getEffects()){
                    ib.interactionEffect(player);
                }

                return true;
            }
        }
        return false;
    }

    public boolean isGiven(Item item, Player player){

        for(String id : reactionItems.keySet()){
            if (item.getID().equals(id)){

                printToLog("\""+reactionItems.get(id).getAnswer()+"\"");


                for(InteractionBehaviour ib : reactionItems.get(id).getEffects()){
                    ib.interactionEffect(player);
                }

            }
        }

        printToLog("\"Sorry, I don't need it.\"");
        return false;
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
    }

}
