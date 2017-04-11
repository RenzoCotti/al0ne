package com.al0ne.Behaviours;

/**
 * placeholder class for attackable npcs
 */
public abstract class Character extends Entity{

    public Character(String id, String name, String description, String shortDescription) {
        super(id, name, description, shortDescription);
    }

    @Override
    public boolean used(Room currentRoom, Player player){
        return false;
    };

}
