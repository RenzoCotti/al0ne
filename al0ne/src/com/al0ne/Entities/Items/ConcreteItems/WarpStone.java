package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Pairs.PairWorld;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.GameChanges;
import com.al0ne.Engine.Main;
import com.al0ne.Engine.Size;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Behaviours.Material;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 24/04/2017.
 */
public class WarpStone extends Item{

    public WarpStone() {
        super("warpstone", "Warp stone",
                "A glowing opaque light-blue stone, whose edges are roughly shaped in a prism-shape.",
                0.3, Size.VSMALL, Material.STONE);
        setShortDescription("a light-blue stone");
        setUnique();
        setUndroppable();
    }

    @Override
    public int used(Room currentRoom, Player player) {
        int chosen = Utility.randomNumber(Main.game.getWorldCount()*2);
        int i = 1;
        for (String s : Main.game.getWorlds().keySet()){
            if(i == chosen && !Main.game.getCurrentWorld().equals(Main.game.getWorld(s).getWorld().getWorldName())){
                GameChanges.changeWorld(s);
                printToLog("Your vision fades to black for a moment.\nWhen you can see again, you are someplace completely different.");
                return 2;
            }
            i++;
        }
        printToLog("The stone fizzles, but does nothing.");
        return 2;
    }
}
