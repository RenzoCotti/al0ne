package com.al0ne.Entities.Worlds;

import com.al0ne.Behaviours.JunkItem;
import com.al0ne.Behaviours.Prop;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.World;
import com.al0ne.Engine.Size;

/**
 * Created by BMW on 30/04/2017.
 */
public class MedievalYoungWorld extends World{
    public MedievalYoungWorld() {
        super("medievalyoung", "yourroom");

        Room yourRoom = new Room("yourroom", "Your bedroom", "You are in a fairly chaotic bedroom. " +
                "It's not like your mom doesn't tell you to tidy it up.");

        Prop bed = new Prop("bed", "bed", "Your bed, it fits one person. Needs tidying.",
                "a bed", "Your bed, it fits one person. Neatly tidied now.");
        bed.addCommand("tidy");
        yourRoom.addEntity(bed);

        yourRoom.addEntity(new Prop("window", "A window with a wooden " +
                "frame. You can see your neighbour's house from it.", "a window"));
        yourRoom.addEntity(new JunkItem("lucky stone",
                "Your lucky stone. You're confident it brings good luck.",
                "a small stone", 0.1, Size.SMALL));

        yourRoom.visit();
        putRoom(yourRoom);
    }
}
