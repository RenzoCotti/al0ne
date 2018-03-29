package com.al0ne.ConcreteEntities.Items.ConcreteItems;

import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.Engine.Utility.GameChanges;
import com.al0ne.Engine.Main;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.AbstractEntities.Enums.Material;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 24/04/2017.
 */
public class WarpStone extends Item{

    public WarpStone() {
        super("warpstone", "Warp stone",
                "A glowing opaque light-blue stone, whose edges are roughly shaped in a prism-shape.",
                0.3, Size.VSMALL, Material.STONE, 0);
        setShortDescription("a light-blue stone");
        setUnique();
        setUndroppable();
    }

    @Override
    public String used(Player player) {
        int chosen = Utility.randomNumber(Main.game.getWorldCount()*2);
        int i = 1;
        for (String s : Main.game.getWorlds().keySet()){
            if(i == chosen && !Main.game.getCurrentWorldName().equals(Main.game.getWorld(s).getAreaName())){
                GameChanges.changeWorld(s);
                printToLog("Your vision fades to black for a moment.\nWhen you can see again, you are someplace completely different.");
                return "";
            }
            i++;
        }
        printToLog("The stone fizzles, but does nothing.");
        return "";
    }
}
