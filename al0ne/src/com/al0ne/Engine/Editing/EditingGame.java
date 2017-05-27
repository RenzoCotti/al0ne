package com.al0ne.Engine.Editing;

import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.NPC;
import com.al0ne.Behaviours.Prop;
import com.al0ne.Behaviours.World;
import com.al0ne.Behaviours.abstractEntities.Enemy;
import com.al0ne.Engine.Game;

import java.util.HashMap;

/**
 * Created by BMW on 27/05/2017.
 */
public class EditingGame {

    private Game currentEdit;

    private World currentWorld;

    private HashMap<String, Item> items;

    private HashMap<String, Prop> props;

    private HashMap<String, NPC> npcs;

    private HashMap<String, Enemy> enemies;


    public EditingGame(String s) {
        this.currentEdit = new Game(s);
        this.items = new HashMap<>();
        this.props = new HashMap<>();
        this.npcs = new HashMap<>();
        this.enemies = new HashMap<>();
    }

    public Game getCurrentEdit() {
        return currentEdit;
    }

    public void setCurrentEdit(Game currentEdit) {
        this.currentEdit = currentEdit;
    }

    public World getCurrentWorld() {
        return currentWorld;
    }

    public void setCurrentWorld(World currentWorld) {
        this.currentWorld = currentWorld;
    }

    public HashMap<String, Item> getItems() {
        return items;
    }

    public void addItem(Item i) {
        this.items.put(i.getID(), i);
    }

    public HashMap<String, Prop> getProps() {
        return props;
    }

    public void addProp(Prop p) {
        this.props.put(p.getID(), p);
    }


    public HashMap<String, NPC> getNpcs() {
        return npcs;
    }

    public void addNPC(NPC n) {
        this.npcs.put(n.getID(), n);
    }


    public HashMap<String, Enemy> getEnemies() {
        return enemies;
    }

    public void addEnemy(Enemy e) {
        this.enemies.put(e.getID(), e);
    }
}
