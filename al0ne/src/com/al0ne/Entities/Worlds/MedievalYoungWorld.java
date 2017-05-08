package com.al0ne.Entities.Worlds;

import com.al0ne.Behaviours.*;
import com.al0ne.Behaviours.Enums.Command;
import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Pairs.Subject;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Entities.Items.Behaviours.Wearable.BodyClothing;
import com.al0ne.Entities.Items.ConcreteItems.Coin.SilverCoin;
import com.al0ne.Entities.Items.ConcreteItems.DoorUnlocker;
import com.al0ne.Entities.Items.ConcreteItems.Food.SliceOfCake;
import com.al0ne.Entities.Items.ConcreteItems.Note;

import java.util.ArrayList;

/**
 * Created by BMW on 30/04/2017.
 */
public class MedievalYoungWorld extends World{
    public MedievalYoungWorld() {
        super("medievaly", "homeyourroom");

        Room yourRoom = new Room("homeyourroom", "Your bedroom", "You are in a fairly chaotic bedroom. " +
                "It's not like your mom doesn't tell you to tidy it up.");

        Prop bed = new Prop("bed", "Your bed, it needs tidying.",
                "a bed", "Your bed. Neatly tidied now.", Material.WOOD);
        bed.addCommand(Command.TIDY);
        yourRoom.addEntity(bed);

        yourRoom.addEntity(new Prop("window", "A window with a wooden " +
                "frame. You can see your neighbour's house from it.", "a window"));
        yourRoom.addEntity(new JunkItem("lucky stone",
                "Your lucky stone. You're confident it brings good luck.", 0.1, Size.SMALL));

        yourRoom.addExit("east", "homehallway");

//        Shopkeeper bob = new Shopkeeper("shopkeeper", "Bob", "a fairly chubby man with a glint in his eyes.", "a clever looking man", "Hi, I'm Bob, a shop keeper. Are you interested in some of my items?");
//        bob.simpleAddItem(new Knife(), 50);
//        bob.simpleAddItem(new Apple(), 2);
//        bob.simpleAddItem(new Scroll("mazesolution", "Parched scroll", "what seems like a fairly old scroll","Down, Right, Up, Right, Down", 0.1), 20);
//        yourRoom.addEntity(bob);
//        yourRoom.addEntity(new GoldCoin());
        yourRoom.visit();
        putRoom(yourRoom);


        Room hallway = new Room("homehallway", "Hallway", "A hallway. Connects your room and your " +
                "parents's to the rest of the house.");
        hallway.addExit("west", "homeyourroom");
        hallway.addExit("north", "home");
        hallway.addExit("east", "homeparentsroom");
        putRoom(hallway);

        Room parentsRoom = new Room("homeparentsroom", "Your parent's room",
                "Your parents's room. It's quite warm in here");
        parentsRoom.addExit("west", "homehallway");
        parentsRoom.addEntity(new Prop("flower pot",
                "A pot filled with water, containing a fairly dried up flower.",
                "a flower pot", Material.CLAY));
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
                true, new Pair(new SilverCoin(), 6), true, "geteggs"));
        mom.addSubject("eggs", new Subject("Yes, i need about six. " +
                "I'll give you a piece of cake when you come back"));
        mom.addSubject("sister", new Subject("She's in a better place now, honey. The wolf took her away."));
        mom.addReactionItem("eggs", new SliceOfCake());
        mainHouse.addEntity(mom);
        mainHouse.addExit("south", "homehallway");
        mainHouse.addExit("west", "neighbourhood");
        putRoom(mainHouse);

        Room neighbourhood = new Room("neighbourhood", "Neighbourhood",
                "There are several houses in this area, most of them are not terribly run down.");
        neighbourhood.addExit("east", "home");
        neighbourhood.addExit("north", "square");
        neighbourhood.addExit("south", "pathforest");
        neighbourhood.addExit("west", "neighbourporch");
        putRoom(neighbourhood);

        Room neighbourPorch = new Room("neighbourporch", "Neighbour's porch",
                "You are on your neighbour's porch. He doesn't seem to be at home right now.");
        neighbourPorch.addExit("east", "neighbourhood");
        neighbourPorch.addExit("west", "neighbourhouse");
        neighbourPorch.lockDirection("west", "neighbourkey");
//        DoorUnlocker doorBell = new DoorUnlocker("bell", "the door bell of your neighbour.",
//                "a door bell", "you rung the door bell.", Material.BRASS, "neighbourkey");
//        doorBell.addCommand(Command.PRESS);
//        neighbourPorch.addEntity(doorBell);
        putRoom(neighbourPorch);

        Room neighbourHouse = new Room("neighbourhouse", "Neighbour's house",
                "How did you even get in here?");
        neighbourHouse.addExit("east", "neighbourporch");
        neighbourHouse.addEntity(new Prop("Corpse", "The decomposed corpse of a child. " +
                "It seems it's holding something in its hand."));
        neighbourHouse.addEntity(new InvisibleProp("hand", "It's holding a white rose."));
        putRoom(neighbourHouse);

        Room square = new Room("square", "Square", "The village square, quite deserted at this time of day.");
        square.addCustomDirection("To the east you see your town's church, to the north the village hall, " +
                "to the west the market and to the south your neighbourhood.");
        square.addExit("east", "villagechurch");
//        square.addExit("west", "villagemarket");
        square.addExit("south", "neighbourhood");
        square.addExit("north", "villagehall");
        square.addEntity(new Prop("Fountain", "A fountain made of stone"));
        putRoom(square);

        Room villageChurch = new Room("villagechurch", "Village Church",
                "The village's church. It's a fairly large building made out of large blocks of stone, " +
                        "with a tall bell tower.");
        villageChurch.addEntity(new InvisibleProp("tower", "A tall tower, with a huge brass bell at the top"));
        villageChurch.addExit("east", "villagegraveyard");
        villageChurch.addExit("west", "square");
        putRoom(villageChurch);

        Room villageGraveyard = new Room("villagegraveyard", "Graveyard", "There are a lot of tombstones here.");
        villageGraveyard.addExit("west", "villagechurch");
        villageGraveyard.addEntity(new Prop("Family tomb",
                "The last entry listed on here is the one of your sister...",
                "your family tomb"));
        villageGraveyard.addEntity(new JunkItem("dried flower",
                "a dried white rose that was on your family tomb.", 0.05, Size.SMALL));
        putRoom(villageGraveyard);

        Room villageHall = new Room("villagehall", "Hall", "This place is strangely deserted. Weird.");
        villageHall.addExit("south", "square");
        villageHall.addEntity(new Prop("desk", "A large desk with some drawers, with some papers on top of it.",
                "a desk", Material.WOOD));
        InvisibleProp drawers = new InvisibleProp("drawers", "Wooden drawers. What else did you expect?");
        ArrayList<Interactable> drawersContent = new ArrayList<>();
        drawersContent.add(new JunkItem("paperweight", "A paperweight.",
                0.2, Size.VSMALL, Material.STONE));
        drawersContent.add(new JunkItem("quill", "A goose quill, pretty light.", 0, Size.VSMALL));
        drawers.setAddsItem("Opening the drawers reveals a pen and a paperweight", drawersContent);
        drawers.addCommand(Command.OPEN);
        villageHall.addEntity(drawers);
        villageHall.addEntity(new Note("vacation", "Hello, we are taking a day off " +
                "in preparation for the celebration of tomorrow."));
        putRoom(villageHall);



        //TODO
        Room pathForest = new Room("pathforest", "Path to the forest",
                "A path towards the village's forest. Dad forbids you from going there without his consent.");
        pathForest.addExit("north", "neighbourhood");
        putRoom(pathForest);


        Player p = new Player(true, 10, getStartingRoom(),
                "You're a boy, chestnut hair, brown eyes, and big dreams for the future." +
                        "You'd love to become a knight, one day. Or maybe a wizard, you haven't decided yet.");
        BodyClothing bc = new BodyClothing("roughclothes", "clothes",
                "Some rough clothes, they look a bit worn", 0.5, Size.NORMAL, Material.FIBRE);
        p.simpleAddItem(bc, 1);
        p.wear(bc);
        setPlayer(p);
    }
}
