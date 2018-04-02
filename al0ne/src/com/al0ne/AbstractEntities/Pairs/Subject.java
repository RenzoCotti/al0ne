package com.al0ne.AbstractEntities.Pairs;

import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Quests.Quest;
import com.al0ne.Engine.Physics.InteractionResult.InteractionAdd;
import com.al0ne.Engine.Physics.InteractionResult.InteractionBehaviour;
import com.al0ne.Engine.Physics.InteractionResult.InteractionCompleteQuest;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by BMW on 30/04/2017.
 */
public class Subject implements Serializable{
    private String answer;
    private ArrayList<InteractionBehaviour> effects = new ArrayList<>();

    public Subject(String answer, Item i, int qty) {
        this.answer = answer;
        this.effects.add(new InteractionAdd(i, qty));
    }

    public Subject(String answer) {
        this.answer = answer;
    }

    public Subject(String answer, Quest q) {
        this.answer = answer;
        this.effects.add(new InteractionCompleteQuest(q.getQuestID()));
    }

    public Subject(String answer, InteractionBehaviour effect) {
        this.answer = answer;
        this.effects.add(effect);
    }

    public Subject(String answer, ArrayList<InteractionBehaviour> effects) {
        this.answer = answer;
        this.effects = effects;
    }

    public String getAnswer() {
        return answer;
    }

    public ArrayList<InteractionBehaviour> getEffects() {
        return effects;
    }

    public void addEffect(InteractionBehaviour effect) {
        this.effects.add(effect);
    }
}
