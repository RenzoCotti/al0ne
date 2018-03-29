package com.al0ne.ConcreteEntities.Items.ConcreteItems.Books;

import com.al0ne.AbstractEntities.Pairs.SpellPair;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.ConcreteEntities.Spells.Spell;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.ConcreteEntities.Items.Types.Readable;

import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 14/04/2017.
 */
public class Spellbook extends Readable{
    private HashMap<String, SpellPair> spells;
    public Spellbook() {
        super("spellbook", "Spellbook", "A tome containing all the spells you can cast.",
                Size.NORMAL, "Plenty of magic formulae here.", 2);
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
            if(spellID.equals(id)){
                return true;
            }
        }
        return false;
    }

    public SpellPair getSpell(String id){
        for(String spellID : spells.keySet()){
            if(spellID.equals(id)){
                return spells.get(spellID);
            }
        }
        return null;
    }

    @Override
    public boolean used(Player player) {
        printToLog("Your spellbook contains these spells:");
        for(SpellPair sp : spells.values()){
            Spell s = sp.getSpell();
            printToLog("- "+s.getName()+": "+sp.getCount()+" castings.");
        }
        return true;
    }

    public HashMap<String, SpellPair> getSpells() {
        return spells;
    }
}
