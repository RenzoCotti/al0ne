package com.al0ne.AbstractEntities.Pairs;

import com.al0ne.ConcreteEntities.Spells.Spell;

import java.io.Serializable;

/**
 * Created by BMW on 14/04/2017.
 */
public class SpellPair implements Serializable {
    private Spell spell;
    private int count;

    public SpellPair(Spell spell, int count) {
        this.spell = spell;
        this.count = count;
    }

    public Spell getSpell() {
        return spell;
    }

    public int getCount() {
        return count;
    }

    public void addCount() {
        count++;
    }

    public void setCount(Integer amount) {
        count=amount;
    }

    public boolean modifyCount(Integer amount) {
        count+=amount;
        if(count <= 0){
            return false;
        }
        return true;
    }

    public boolean subCount() {
        count--;
        if (count <= 0){
            return false;
        }
        return true;
    }

    public boolean isEmpty(){
        return count <= 0;
    }
}
