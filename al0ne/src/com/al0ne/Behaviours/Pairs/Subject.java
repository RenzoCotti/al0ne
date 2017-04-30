package com.al0ne.Behaviours.Pairs;

import com.al0ne.Behaviours.Item;

import java.io.Serializable;

/**
 * Created by BMW on 30/04/2017.
 */
public class Subject implements Serializable{
    private String answer;
    private boolean givesItem;
    private Pair item;
    private boolean addsQuest;
    private String questID;

    public Subject(String answer, boolean givesItem, Pair item, boolean addsQuest, String questID) {
        this.answer = answer;
        this.givesItem = givesItem;
        this.item = item;
        this.addsQuest = addsQuest;
        this.questID = questID;
    }

    public Subject(String answer) {
        this.answer = answer;
        this.givesItem = false;
        this.item = null;
        this.addsQuest = false;
        this.questID = null;
    }

    public Subject(String answer, boolean givesItem, Pair item) {
        this.answer = answer;
        this.givesItem = givesItem;
        this.item = item;
        this.addsQuest = false;
        this.questID = null;
    }

    public Subject(String answer, boolean addsQuest, String questID) {
        this.answer = answer;
        this.givesItem = false;
        this.item = null;
        this.addsQuest = addsQuest;
        this.questID = questID;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean givesItem() {
        return givesItem;
    }

    public Pair getItem() {
        return item;
    }

    public boolean addsQuest() {
        return addsQuest;
    }

    public String getQuestID() {
        return questID;
    }
}
