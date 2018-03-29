package com.al0ne.ConcreteEntities.Items.Props.Types;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Prop;
import com.al0ne.AbstractEntities.Room;

public class HideItem extends Prop{
    //    private String useMessage;
    private Item hidden;

    public HideItem(String name, String description, String shortDescription, String after, Material m, Item hidden) {
        super(name, description, shortDescription, after, m);
        this.hidden=hidden;
    }
    public HideItem(String name, String description, String shortDescription, Material m, Item hidden) {
        super(name, description, shortDescription, description, m);
        this.hidden=hidden;
    }

    //this object when used adds the item it's hiding in the room
    @Override
    public boolean used(Player player){
        Room currentRoom = player.getCurrentRoom();
        currentRoom.addItem(hidden);
        return true;
    }
}
