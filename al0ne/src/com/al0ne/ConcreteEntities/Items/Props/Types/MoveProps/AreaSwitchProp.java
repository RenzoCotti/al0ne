package com.al0ne.ConcreteEntities.Items.Props.Types.MoveProps;

import com.al0ne.AbstractEntities.Area;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Prop;
import com.al0ne.AbstractEntities.Room;
import com.al0ne.Engine.Main;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 23/07/2017.
 * This teleports the player within the same area
 */
public class AreaSwitchProp extends SwitchProp{
    private String area;

    public AreaSwitchProp(String name, String description, String a) {
        super(name, description);
        this.area = a;
    }

    public AreaSwitchProp(String name, String description, String a, String s) {
        super(name, description, s);
        this.area = a;
    }

    @Override
    public boolean teleport(Player player) {

        for(Area a : Main.game.getCurrentWorld().getAreas()){
            if(a.getAreaName().equals(area)) {
                player.setCurrentArea(a);
                player.setCurrentRoom(a.getStartingRoom());
                printToLog(teleportMessage);
                printToLog();
                a.getStartingRoom().printRoom();
                return true;
            }
        }
        System.out.println("Tried to teleport to a non existing area.");
        return false;
    }
}