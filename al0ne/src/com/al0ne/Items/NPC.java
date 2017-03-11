package com.al0ne.Items;

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
    protected ArrayList<String> reactionItems;
    protected ArrayList<Item> inventory;

    public NPC(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.subjects = new HashMap<>();
        this.reactionItems = new ArrayList<>();
        this.inventory = new ArrayList<>();
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

    public boolean talkAbout(String subject){
        for (String s : subjects.keySet()){
            if (s.equals(subject)){
                System.out.println(subjects.get(s));
                return true;
            }
        }
        return false;
    }
}
