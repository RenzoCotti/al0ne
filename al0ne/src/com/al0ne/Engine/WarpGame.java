package com.al0ne.Engine;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.World;
import com.al0ne.Entities.Worlds.CaveWorld;
import com.al0ne.Entities.Worlds.MedievalYoungWorld;

/**
 * Created by BMW on 24/04/2017.
 */
public class WarpGame extends Game{
    private boolean warpstone;

    public WarpGame(boolean needs) {
        super(needs);
        this.warpstone = false;

        World startingWorld = new MedievalYoungWorld();
        World caveWorld = new CaveWorld();
        if (needs) {
            addWorld(startingWorld, new Player(startingWorld.getStartingRoom(), true,
                    "You a boy, chestnut hair, brown eyes, and big dreams for the future." +
                            "You'd love to become a knight, one day. Or maybe a wizard, you haven't decided yet."));
            addWorld(caveWorld, new Player(caveWorld.getStartingRoom(), true, "You are a caveman.") );
        } else{
            addWorld(startingWorld, new Player(startingWorld.getStartingRoom(), false,
                    "You a boy, chestnut hair, brown eyes, and big dreams for the future." +
                    "You'd love to become a knight, one day. Or maybe a wizard, you haven't decided yet."));
            addWorld(caveWorld, new Player(caveWorld.getStartingRoom(), false, "You are a caveman."));
        }

        this.currentWorld = startingWorld.getWorldName();
        this.startingWorld = startingWorld.getWorldName();
    }

    public boolean hasWarpstone() {
        return warpstone;
    }

    public void setWarpstone() {
        this.warpstone = true;
    }
}
