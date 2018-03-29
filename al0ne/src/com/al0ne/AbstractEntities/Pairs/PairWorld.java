package com.al0ne.AbstractEntities.Pairs;

import com.al0ne.AbstractEntities.Area;
import com.al0ne.AbstractEntities.Player.Player;

import java.io.Serializable;

/**
 * Created by BMW on 17/04/2017.
 */
public class PairWorld implements Serializable {
    private Player player;
    private Area area;

    public PairWorld(Player player, Area area) {
        this.player = player;
        this.area = area;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
