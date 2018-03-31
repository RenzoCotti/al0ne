package com.al0ne.ConcreteEntities.Areas;

import com.al0ne.AbstractEntities.*;
import com.al0ne.AbstractEntities.Enums.Command;
import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.TechLevel;
import com.al0ne.AbstractEntities.Pairs.Subject;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Weapons.MeleeWeapon.Mace;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Food.Apple;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Food.Beer;
import com.al0ne.ConcreteEntities.Enemies.Wolf;
import com.al0ne.ConcreteEntities.Items.Props.Types.Door;
import com.al0ne.ConcreteEntities.Items.Props.Types.HideProp;

public class FirstAlphaArea extends Area {


    public FirstAlphaArea(World w) {

        super("cave", w);



        Room cave1 = new Room("Cave 1", "You are in a pretty generic-looking cave. It feels pretty damp. You can see passageway east.");
//        cave1.addOneItem(new Potion());
//        cave1.addOneItem(new Key("cave1key","Ordinary Key"));
//        cave1.addOneItem(new Key("cave2key","Next Room Key"));
//        cave1.addProp(new LockedDoor("cave1door", "Generic Door","A sturdy wooden door blocks the passage to the east.","A sturdy wooden door lies open to the east.","cave1key"));
//        cave1.addEntity(new Door("Door", Material.WOOD));
//        cave1.addEntity(new GiantRat());
//        cave1.addEntity(new Chest());
//        Prop river = new Prop("River", "a watery river");
//        river.addBehaviour(new WaterBehaviour());
//        cave1.addEntity(river);
//        cave1.addEntity(new Canteen());
//        cave1.lockDirection("east", "cave1door");
        NPC emon1 = new NPC("Emon", "A handy man. Probably fixes small keys.", "handy man", "Hi, i'm eamon and i fix small keys");

        cave1.addEntity(emon1);
//        cave1.addEntity(new MoneyTree());

        addRoom(cave1);


        Room cave2 = new Room( "Cave 2", "The rocks are crumbly here.");
        cave2.addItem(new Apple(), 2);

        addRoom(cave2);

        Room cave3 = new Room("Cave 3", "Nothing worth of notice here.");
        Door trapdoor1 = new Door("Trapdoor","You can see a trapdoor on the floor.","a wooden trapdoor", Material.WOOD);
        HideProp rug = new HideProp("Rug", "A ragged rug. Ruggity rug.", "a dusty rug",
                "The rug is now out of the way.", Material.FABRIC, trapdoor1);
        rug.addCommand(Command.MOVE);
        cave3.addEntity(rug);
        cave3.lockDirection("down", "trapdoor");

        addRoom(cave3);

        Room cellar = new Room("Cellar", "Very damp and filled to the brim with bottles of beer! :D");
        cellar.addItem(new Beer());
        cellar.addItem(new Beer());
        cellar.addItem(new Beer());
        cellar.addItem(new Beer());

        addRoom(cellar);

        Room cave4 = new Room( "Shop Room", "Lots of items are in this room, all with a price tag on.");
        NPC emon = new NPC("Emon", "A handy man. Probably fixes small keys.", "handy man", "Hi, i'm eamon and i fix small keys");
        emon.addSubject("keys", new Subject("Yup, I fix small keys."));
        emon.addSubject("beer", new Subject("I love beer!"));
        cave4.addEntity(emon);

        addRoom(cave4);

        Room bossRoom = new Room("Boss Room", "Lots of bones cover the ground. You shiver.");
        Wolf boss = new Wolf();
        bossRoom.addEntity(boss);

        addRoom(bossRoom);


        bossRoom.addExit("west",cave2);
        cave4.addExit("south", cave2);
        cellar.addExit("up", cave3);
        cave3.addExit("north", cave2);
        cave3.addExit("down", cellar);
        cave2.addExit("west",cave1);
        cave2.addExit("north",cave4);
        cave2.addExit("south",cave3);
        cave2.addExit("east",bossRoom);
        cave1.addExit("east",cave2);


        setStartingRoom(cave1);
    }
}
