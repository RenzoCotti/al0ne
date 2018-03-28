package com.al0ne.Entities.Items.Props;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Prop;
import com.al0ne.Behaviours.Room;

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
    public String used(Player player){
        Room currentRoom = player.getCurrentRoom();
        currentRoom.addItem(hidden);
        return "";
    }
}
