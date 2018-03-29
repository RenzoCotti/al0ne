package com.al0ne.ConcreteEntities.Items.Types;

import com.al0ne.AbstractEntities.Enums.Command;
import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Enums.Size;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 14/04/2017.
 */
public class Readable extends Item {
    protected String content;
    public Readable(String id, String name, String description, Size size, String content, double weight) {
        super(id, name, description, weight, size, Material.PAPER, null);
        addCommand(Command.READ);
        this.content=content;
    }

    @Override
    public boolean used(Player player){
        printToLog("\""+content+"\"");
        return true;
    }


}
