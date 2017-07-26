package com.al0ne.Behaviours.Pairs;

import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Quests.Quest;

import java.io.Serializable;

/**
 * Created by BMW on 30/04/2017.
 */
public class Subject implements Serializable{
    private String answer;
    private boolean givesItem;
    private Pair item;
    private boolean addsQuest;
    private Quest quest;

    public Subject(String answer, boolean givesItem, Pair item, boolean addsQuest, Quest quest) {
        this.answer = answer;
        this.givesItem = givesItem;
        this.item = item;
        this.addsQuest = addsQuest;
        this.quest = quest;
    }

    public Subject(String answer) {
        this.answer = answer;
        this.givesItem = false;
        this.item = null;
        this.addsQuest = false;
        this.quest = null;
    }

    public Subject(String answer, boolean givesItem, Pair item) {
        this.answer = answer;
        this.givesItem = givesItem;
        this.item = item;
        this.addsQuest = false;
        this.quest = null;
    }

    public Subject(String answer, boolean addsQuest, Quest quest) {
        this.answer = answer;
        this.givesItem = false;
        this.item = null;
        this.addsQuest = addsQuest;
        this.quest = quest;
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

    public Quest getQuest() {
        return quest;
    }
}
