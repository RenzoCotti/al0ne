package com.al0ne.Items.Items;

import com.al0ne.Entities.Player;
import com.al0ne.Items.Item;
import com.al0ne.Room;

/**
 * Created by BMW on 15/03/2017.
 */
public class Scroll extends Item{
    private String content;
    public Scroll(String id, String name, String description, String content, double weight) {
        super(id, name, description, weight);
        addCommand("read");
        this.content=content;
    }

    @Override
    public boolean used(Room currentRoom, Player player){
        System.out.println("\""+content+"\"");
        return true;
    }


}
