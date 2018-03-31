package com.al0ne.ConcreteEntities.Items.Props.Types.MoveProps;

import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Prop;
import com.al0ne.AbstractEntities.Room;
import com.al0ne.Engine.Main;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 23/07/2017.
 * This teleports the player within the same area
 */
public class RoomSwitchProp extends SwitchProp{
    private Room room;
    public RoomSwitchProp(String name, String description, Room r) {
        super(name, description);
        this.room = r;
    }

    public RoomSwitchProp(String name, String description, Room r, String s) {
        super(name, description, s);
        this.room = r;
    }

    @Override
    public boolean teleport(Player player) {

        if(player.getCurrentArea().getRooms() != null){
            Room newCurrentRoom = player.getCurrentArea().getRooms().get(room.getID());
            player.setCurrentRoom(newCurrentRoom);
            printToLog(teleportMessage);
            printToLog();
            newCurrentRoom.printRoom();
        } else{
            System.out.println("Tried to teleport to a non existing room.");
        }
        return true;
    }
}
