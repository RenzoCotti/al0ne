package com.al0ne.Entities.Items.Props;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Item;
import com.al0ne.Entities.Items.ConcreteItems.Coin.SilverCoin;
import com.al0ne.Behaviours.Prop;
import com.al0ne.Behaviours.Room;

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

    @Override
    public int used(Room currentRoom, Player player){
        return 0;
    }

}
