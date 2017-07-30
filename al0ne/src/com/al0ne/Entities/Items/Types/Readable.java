package com.al0ne.Entities.Items.Types;

import com.al0ne.Behaviours.Enums.Command;
import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Enums.Size;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 14/04/2017.
 */
public abstract class Readable extends Item {
    protected String content;
    public Readable(String id, String name, String description, Size size, String content, double weight) {
        super(id, name, description, weight, size, Material.PAPER, null);
        addCommand(Command.READ);
        this.content=content;
    }

    @Override
    public String used(Player player){
        printToLog("\""+content+"\"");
        return "";
    }


}
