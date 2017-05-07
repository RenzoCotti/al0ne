package com.al0ne.Engine;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.World;
import com.al0ne.Entities.Worlds.AlphaWorld;
import com.al0ne.Entities.Worlds.CaveWorld;
import com.al0ne.Entities.Worlds.MedievalYoungWorld;

/**
 * Created by BMW on 24/04/2017.
 */
public class WarpGame extends Game{
    private boolean warpstone;

    public WarpGame(boolean needs) {
        super();
        this.warpstone = false;

//        World startingWorld = new AlphaWorld();
//        World alphaWorld = new MedievalYoungWorld();

        World startingWorld = new MedievalYoungWorld();
        World alphaWorld = new AlphaWorld();


        World caveWorld = new CaveWorld();
        addWorld(startingWorld, new Player(needs, 10, startingWorld.getStartingRoom(),
                    "You're a boy, chestnut hair, brown eyes, and big dreams for the future." +
                            "You'd love to become a knight, one day. Or maybe a wizard, you haven't decided yet."));
        addWorld(caveWorld, new Player(needs, 20, caveWorld.getStartingRoom(), "You are a caveman.") );
        addWorld(alphaWorld, new Player(needs, 20, alphaWorld.getStartingRoom(), "You are alpha. And omega. Maybe."));

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
