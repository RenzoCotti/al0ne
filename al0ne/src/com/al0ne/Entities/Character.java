package com.al0ne.Entities;

import com.al0ne.Items.Entity;
import com.al0ne.Items.Item;
import com.al0ne.Items.Pair;
import com.al0ne.Room;

import java.util.ArrayList;

/**
 * placeholder class for attackable npcs
 */
public abstract class Character extends Entity{

    public Character(String id, String name, String description) {
        super(id, name, description);
    }

}
