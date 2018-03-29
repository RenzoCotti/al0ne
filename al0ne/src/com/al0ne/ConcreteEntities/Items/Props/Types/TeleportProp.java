package com.al0ne.ConcreteEntities.Items.Props.Types;

import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Prop;
import com.al0ne.AbstractEntities.Room;
import com.al0ne.Engine.Main;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 23/07/2017.
 */
public class TeleportProp extends Prop{
    protected String roomID;
    protected String teleportMessage;
    public TeleportProp(String name, String description, Room r) {
        super(name, description);
        this.roomID = r.getID();
        teleportMessage = "You are now in another room";
    }
    public TeleportProp(String name, String description, Room r, String s) {
        super(name, description);
        this.roomID = r.getID();
        teleportMessage = s;
    }

    public void setTeleportMessage(String teleportMessage) {
        this.teleportMessage = teleportMessage;
    }

    @Override
    public boolean used(Player player) {
        if(Main.game.getCurrentWorld().getRooms().get(roomID) != null){
            Room newCurrentRoom = Main.game.getCurrentWorld().getRooms().get(roomID);
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
