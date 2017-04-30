package com.al0ne.Entities.Worlds;

import com.al0ne.Behaviours.*;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Pairs.Subject;
import com.al0ne.Engine.Size;
import com.al0ne.Entities.Items.ConcreteItems.Coin;
import com.al0ne.Entities.Items.ConcreteItems.Food.SliceOfCake;

/**
 * Created by BMW on 30/04/2017.
 */
public class MedievalYoungWorld extends World{
    public MedievalYoungWorld() {
        super("medievalyoung", "homeyourroom");

        Room yourRoom = new Room("homeyourroom", "Your bedroom", "You are in a fairly chaotic bedroom. " +
                "It's not like your mom doesn't tell you to tidy it up.");

        Prop bed = new Prop("homebed", "bed", "Your bed, it needs tidying.",
                "a bed", "Your bed. Neatly tidied now.");
        bed.addCommand("tidy");
        yourRoom.addEntity(bed);

        yourRoom.addEntity(new Prop("window", "A window with a wooden " +
                "frame. You can see your neighbour's house from it.", "a window"));
        yourRoom.addEntity(new JunkItem("lucky stone",
                "Your lucky stone. You're confident it brings good luck.",
                "a small stone", 0.1, Size.SMALL));

        yourRoom.addExit("east", "homehallway");

        yourRoom.visit();
        putRoom(yourRoom);


        Room hallway = new Room("homehallway", "Hallway", "A hallway. Connects your room and your " +
                "parents's to the rest of the house.");
        hallway.addExit("west", "homeyourrrom");
        hallway.addExit("north", "home");
        hallway.addExit("east", "homeparentsroom");
        putRoom(hallway);

        Room parentsRoom = new Room("homeparentsroom", "Your parent's room",
                "Your parents's room. It's quite warm in here");
        parentsRoom.addExit("west", "homehallway");
        parentsRoom.addEntity(new Prop("flower pot",
                "A pot filled with water, containing a fairly dried up flower.", "a flower pot"));
        parentsRoom.addEntity(new Prop("parentsbed", "Your parent's bed, it fits two people.",
                "a large bed"));
        putRoom(parentsRoom);

        Room mainHouse = new Room("home", "Living Room", "Your house's living room.");
        mainHouse.addEntity(new Prop("hearth", "A hearth made of stone blocks.", "a hearth"));
        mainHouse.addEntity(new Prop("table",
                "A wooden table; it's made of walnut, if you remember correctly.", "a table"));
        mainHouse.addEntity(new Prop("chairs",
                "Three chairs: one for mom, one for dad, one for you.", "some chairs"));
        NPC mom = new NPC("mom", "mom", "Your mom. She looks a bit tired today.",
                "mom","Hey sweetie, I'd need a favour.");
        mom.addSubject("favour", new Subject("Could you be so kind to go out and buy some eggs for me? " +
                "Here's some money for that. Thanks!",
                true, new Pair(new Coin(), 6), true, "geteggs"));
        mom.addSubject("eggs", new Subject("Yes, i need about six. " +
                "I'll give you a piece of cake when you come back"));
        mom.addReactionItem("eggs", new SliceOfCake());
        mainHouse.addEntity(mom);
        mainHouse.addExit("south", "homehallway");
        putRoom(mainHouse);

    }
}
