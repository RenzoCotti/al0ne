package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Size;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 15/03/2017.
 */
public class Scroll extends Item{
    private String content;
    public Scroll(String id, String name, String description, String shortDescription, String content, double weight) {
        super(id, name, description, shortDescription, weight, Size.SMALL);
        addCommand("read");
        this.content=content;
    }

    @Override
    public boolean used(Room currentRoom, Player player){
        printToLog("\""+content+"\"");
        return true;
    }


}
