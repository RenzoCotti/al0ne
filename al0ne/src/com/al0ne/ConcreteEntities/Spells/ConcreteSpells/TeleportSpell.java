package com.al0ne.ConcreteEntities.Spells.ConcreteSpells;

import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Room;
import com.al0ne.Engine.Main;
import com.al0ne.ConcreteEntities.Spells.SelfSpell;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 23/07/2017.
 */
public class TeleportSpell extends SelfSpell{
    public String roomID;
    public TeleportSpell() {
        super("teleport", "Teleport", "A spell to teleport to places not too far away.");
        this.roomID = null;
    }

    @Override
    public boolean isCasted(Player player) {
        if(this.roomID == null){
            this.roomID = player.getCurrentRoom().getID();
            printToLog("You feel the spell binding to this place.");
            return true;
        } else {
            Room newCurrentRoom = player.getCurrentArea().getRooms().get(roomID);

            player.setCurrentRoom(newCurrentRoom);
            this.roomID = null;

            printToLog("The spell opens a tear in reality and sucks you in!");
            printToLog("When you can see again, you somewhere different.");
            printToLog();
            newCurrentRoom.printRoom();
            return true;
        }
    }
}
