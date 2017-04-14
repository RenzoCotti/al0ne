package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Pairs.SpellPair;
import com.al0ne.Behaviours.Spell;
import com.al0ne.Engine.Size;
import com.al0ne.Entities.Items.Behaviours.Readable;

import java.util.HashMap;

/**
 * Created by BMW on 14/04/2017.
 */
public class Spellbook extends Readable{
    private HashMap<String, SpellPair> spells;
    public Spellbook() {
        super("spellbook", "Spellbook", "A tome containing all the spells you can cast.", "a dusty tome", Size.NORMAL, "Plenty of magic formulae here.", 2);
        this.spells = new HashMap<>();
        setUnique();
    }

    public void addSpell(Spell spell, int charges){
        SpellPair pair = spells.get(spell.getID());
        if (pair != null){
            pair.modifyCount(charges);
        } else{
            spells.put(spell.getID(), new SpellPair(spell, charges));
        }
    }

    public boolean hasSpell(String id){
        for(String spellID : spells.keySet()){
            if(spellID.equals(id) && spells.get(id).getCount() > 0){
                return true;
            }
        }
        return false;
    }

    public SpellPair getSpell(String id){
        for(String spellID : spells.keySet()){
            if(spellID.equals(id) && spells.get(id).getCount() > 0){
                return spells.get(spellID);
            }
        }
        return null;
    }
}
