package com.al0ne.Items.Props;

import com.al0ne.Entities.Player;
import com.al0ne.Items.Item;
import com.al0ne.Items.Items.Coin;
import com.al0ne.Items.Prop;
import com.al0ne.Room;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 09/03/2017.
 */
public class MoneyTree extends Prop{
    private String usedMessage;
    public MoneyTree() {
        super("moneytree", "Coin tree", "A tree with money instead of leaves is in the middle of the room", "a tree", "The tree has no leaves anymore...");
        this.usedMessage="You cut the leaves from the money tree.";
    }

    public MoneyTree(String name, String description, String after, String usedMessage) {
        super("moneytree", name, description, after);
        this.usedMessage=usedMessage;
    }


    @Override
    public boolean usedWith(Item item, Room currentRoom) {

        if(item.hasProperty("sharp")){
            printToLog(usedMessage);
            currentRoom.addItem(new Coin(), 100);
            return true;
        }
        return false;
    }

    @Override
    public boolean used(Room currentRoom, Player player){
        return false;
    }

}
