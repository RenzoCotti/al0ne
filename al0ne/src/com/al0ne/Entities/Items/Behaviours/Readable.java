package com.al0ne.Entities.Items.Behaviours;

import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Size;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 14/04/2017.
 */
public abstract class Readable extends Item {
    protected String content;
    public Readable(String id, String name, String description, String shortDescription, Size size, String content, double weight) {
        super(id, name, description, shortDescription, weight, size);
        addCommand("read");
        this.content=content;
    }

    @Override
    public int used(Room currentRoom, Player player){
        printToLog("\""+content+"\"");
        return 2;
    }


}
