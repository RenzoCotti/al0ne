package com.al0ne.Entities.Items.Props;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Prop;
import com.al0ne.Behaviours.Room;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 09/02/2017.
 */
public class CuttableRope extends Prop{
//    private static int counter=0;
    public CuttableRope() {
        super("rope", "Rope", "A tightened rope holds the Graken still.", "a tightened rope", "The rope has been cut, and the Graken has disappeared.");
        addType("sharp");
    }

    @Override
    public boolean usedWith(Item item, Room currentRoom, Player player) {
        if(( item.hasProperty("sharp"))){
            printToLog("You cut the rope, freeing the Graken");
            active = true;
            return true;
        }
        else{
            printToLog("You get the feeling a sharp item would be more effective.");
            return false;
        }
    }
}