package com.al0ne.Entities.Items.Props;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Prop;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Main;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 23/07/2017.
 */
public class TeleportProp extends Prop{
    protected String roomID;
    public TeleportProp(String name, String description, Room r) {
        super(name, description);
        this.roomID = r.getID();
    }

    @Override
    public int used(Room currentRoom, Player player) {
        if(Main.game.getCurrentWorld().getRooms().get(roomID) != null){
            Room newCurrentRoom = Main.game.getCurrentWorld().getRooms().get(roomID);
            player.setCurrentRoom(newCurrentRoom);
            printToLog("You see a blinding light, and suddenly you don't recognise this place anymore.");
            printToLog();
            newCurrentRoom.printRoom();
        } else{
            System.out.println("Tried to teleport to a non existing room.");
        }
        return 2;
    }
}
