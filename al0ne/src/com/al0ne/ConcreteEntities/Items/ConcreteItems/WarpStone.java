package com.al0ne.ConcreteEntities.Items.ConcreteItems;

import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.World;
import com.al0ne.Engine.Utility.GameChanges;
import com.al0ne.Engine.Main;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.AbstractEntities.Enums.Material;

import javax.rmi.CORBA.Util;

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
    public boolean used(Player player) {

        if(Utility.randomGreaterThan(50)) {
            //then teleport
            int random = Utility.randomNumber(Main.game.getWorldCount()) - 1;
            GameChanges.changeWorld(random);
            return true;
        }
        printToLog("The stone fizzles, but does nothing.");
        return true;
    }
}
