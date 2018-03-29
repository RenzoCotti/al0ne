package com.al0ne.ConcreteEntities.Items.Props;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Prop;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 09/03/2017.
 */
public class MoneyTree extends Prop{
    private String usedMessage;
    public MoneyTree() {
        super("Money tree",
                "A tree with money instead of leaves is in the middle of the room",
                "a tree", "The tree has no leaves anymore...", Material.WOOD);
        this.usedMessage="You cut the leaves from the money tree.";
    }


//    @Override
//    public int usedWith(Item item, Room currentRoom, Player player) {
//
//        if(item.hasProperty("sharp")){
//            printToLog(usedMessage);
//            currentRoom.addItem(new SilverCoin(), 100);
//            return 1;
//        }
//        return 0;
//    }

}
