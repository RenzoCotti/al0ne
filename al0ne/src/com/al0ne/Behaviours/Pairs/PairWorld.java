package com.al0ne.Behaviours.Pairs;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.World;

import java.io.Serializable;

/**
 * Created by BMW on 17/04/2017.
 */
public class PairWorld implements Serializable {
    private Player player;
    private World world;

    public PairWorld(Player player, World world) {
        this.player = player;
        this.world = world;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
