package com.al0ne.Entities;

import com.al0ne.Items.Item;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An NPC has:
 * - dialogue options
 * - objects to react
 * - name
 * - description
 * - life?
 * - inventory
 */
public class NPC {


    protected String id;
    protected String name;
    protected String description;
    //maps subjects to answers
    protected HashMap<String, String> subjects;
    protected HashMap<String, Item> reactionItems;
    protected ArrayList<Item> inventory;


    protected boolean isShopkeeper=false;

    public NPC(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.subjects = new HashMap<>();
        this.reactionItems = new HashMap<>();
        this.inventory = new ArrayList<>();
    }


    public boolean isShopkeeper() {
        return isShopkeeper;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addSubject(String subject, String answer){
        subjects.put(subject, answer);
    }

    public void addReactionItem(String itemid, Item item){
        reactionItems.put(itemid, item);
    }


    public boolean talkAbout(String subject){
        for (String s : subjects.keySet()){
            if (s.equals(subject)){
                System.out.println("\""+subjects.get(s)+"\"");
                return true;
            }
        }
        return false;
    }

    public boolean isGiven(Item item, Player player){
        for (String s : reactionItems.keySet()){
//            System.out.println(s);

            if (item.getID().toLowerCase().equals(s)){
                System.out.println("\"Thank you, i really needed a "+item.getName()+".\"");
                System.out.println("\"Here's a "+reactionItems.get(s).getName()+" as a reward.\"");
                //removes *all* items
                player.getInventory().remove(item.getID());
                player.addItem(reactionItems.get(s));
                return true;
            }
        }
        System.out.println("\"Sorry, I don't need it.\"");
        return false;
    }
}
