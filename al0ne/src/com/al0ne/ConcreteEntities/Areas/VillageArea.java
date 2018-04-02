package com.al0ne.ConcreteEntities.Areas;

import com.al0ne.AbstractEntities.*;
import com.al0ne.AbstractEntities.Enums.Command;
import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Pairs.Pair;
import com.al0ne.AbstractEntities.Pairs.Subject;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.AbstractEntities.Quests.FetchQuest;
import com.al0ne.AbstractEntities.Quests.KillQuest;
import com.al0ne.AbstractEntities.Quests.Quest;
import com.al0ne.AbstractEntities.Quests.TravelQuest;
import com.al0ne.AbstractEntities.Abstract.Interactable;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin.Coin;
import com.al0ne.Engine.Physics.InteractionResult.InteractionAdd;
import com.al0ne.ConcreteEntities.Enemies.Wolf;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.JunkItem;
import com.al0ne.ConcreteEntities.Items.Props.Types.InvisibleProp;
import com.al0ne.ConcreteEntities.Items.Types.Food;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Helmet;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Shield;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Books.Note;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Weapons.MeleeWeapon.Sword;
import com.al0ne.ConcreteEntities.NPCs.Shopkeeper;
import com.al0ne.Engine.Physics.InteractionResult.InteractionAddQuest;

import java.util.ArrayList;

/**
 * Created by BMW on 30/04/2017.
 */
public class VillageArea extends Area {
    public VillageArea(World w) {
        super("medievaly", w);

        Room yourRoom = new Room("Your bedroom", "You are in a fairly chaotic bedroom. " +
                "It's not like your mom doesn't tell you to tidy it up.");

        Prop bed = new Prop("bed", "Your bed, it needs tidying.",
                "a bed", "Your bed. Neatly tidied now.", Material.WOOD);
        bed.addCommand(Command.TIDY);
        yourRoom.addEntity(bed);

        yourRoom.addEntity(new Prop("window", "A window with a wooden " +
                "frame. You can see your neighbour's house from it."));
        yourRoom.addEntity(new JunkItem("lucky stone",
                "Your lucky stone. You're confident it brings good luck.", 0.1, Size.SMALL));


//        Shopkeeper bob = new Shopkeeper("shopkeeper", "Bob", "a fairly chubby man with a glint in his eyes.", "a clever looking man", "Hi, I'm Bob, a shop keeper. Are you interested in some of my items?");
//        bob.simpleAddItem(new Knife(), 50);
//        bob.simpleAddItem(new Apple(), 2);
//        bob.simpleAddItem(new Scroll("mazesolution", "Parched scroll", "what seems like a fairly old scroll","Down, Right, Up, Right, Down", 0.1), 20);
//        yourRoom.addEntity(bob);
//        yourRoom.addEntity(new GoldCoin());
        yourRoom.visit();
        addRoom(yourRoom);


        Room hallway = new Room("Hallway", "A hallway. Connects your room and your " +
                "parents's to the rest of the house.");


        addRoom(hallway);

        Room parentsRoom = new Room("Your parent's room",
                "Your parents's room. It's quite warm in here");

        parentsRoom.addEntity(new Prop("flower pot",
                "A pot filled with water, containing a fairly dried up flower.",
                "a flower pot", null, Material.CLAY));
        parentsRoom.addEntity(new Prop("bed", "Your parent's bed, it fits two people."));
        addRoom(parentsRoom);

        Room mainHouse = new Room("Living Room", "Your house's living room.");
        mainHouse.addEntity(new Prop("hearth", "A hearth made of stone blocks.",
                "a hearth", null, Material.STONE));
        mainHouse.addEntity(new Prop("table",
                "A wooden table; it's made of walnut, if you remember correctly.", "a table",
                null, Material.WOOD));
        mainHouse.addEntity(new Prop("chairs",
                "Three chairs: one for mom, one for dad, one for you.",
                "some chairs", null, null));
        NPC mom = new NPC("Mom", "mom", "Your mom. She looks a bit tired today.",
                "Hey sweetie, I'd need a favour.");
        NPC dad = new NPC("dad", "Your dad. He's a bit flushed, he must've just run.", "your dad",
                "Son, we have to talk about the woods");



//        mainHouse.setQuestRoom("geteggs", "At this very moment," +
//                " the door opens: your dad has come home", dad);

        Food eggs = new Food("eggs", "A pack of six fresh eggs.", 0.3, Size.SMALL, 3);

        Quest getEggs = new FetchQuest(eggs, 1);
        getEggs.addRewards(new InteractionAdd(dad, 1));

//        getEggs.addEntity(dad, 1);


        Subject favour = new Subject("Could you be so kind to go out and buy some eggs for me? " +
                "Here's some money for that. Thanks!");
        favour.addEffect(new InteractionAdd(new Coin(), 3));
        favour.addEffect(new InteractionAddQuest(getEggs));

        mom.addSubject("favour", favour);



        mom.addSubject("eggs", new Subject("Yes, i need about six. " +
                "I'll give you a piece of cake when you come back"));
        mom.addSubject("sister", new Subject("She's in a better place now, honey. The wolf took her away."));
//        mom.addReactionItem("eggs", "geteggs", );
        mainHouse.addEntity(mom);

        addRoom(mainHouse);

        Room neighbourhood = new Room("Neighbourhood",
                "There are several houses in this area, most of them are not terribly run down.");
        addRoom(neighbourhood);

        Room forest1 = new Room("Forest path", "This is a thin path through the woods.");
        forest1.addEntity(new Wolf());
        forest1.addEntity(new Helmet(Material.IRON));
        addRoom(forest1);

        Room forest2 = new Room("Clearing", "The woods open up in this area," +
                " the sun's light shines through the leaves.");
        forest2.addEntity(new Wolf());
        addRoom(forest2);

        Room forest3 = new Room("Temple ruins", "These are the ruins of an old temple.");
        forest3.addEntity(new Wolf());
        forest3.addEntity(new Shield(Material.WOOD));
        addRoom(forest3);


        Subject woodsSubject = new Subject("They are pretty nice huh? kill 3 wolves for me son.",
                new Sword(Material.IRON), 1);
        woodsSubject.addEffect(new InteractionAddQuest(new KillQuest(new Wolf(), 3)));

        //TODO CHECK WITH ADDING QUESTS, CURRENTLY WILL PROBABLY CRASH, SINCE NO QUEST WILL BE SEEN

        dad.addSubject("woods", woodsSubject);


        mom.addSubject("dad", new Subject("go to the temple", new TravelQuest(forest3)));
//                true, new KillQuest("killwolves", wolf, 3)));


        Room neighbourPorch = new Room("Neighbour's porch",
                "You are on your neighbour's porch. He doesn't seem to be at home right now.");
        neighbourPorch.lockDirection("west", "neighbourkey");
        neighbourPorch.addEntity(new InvisibleProp("door", "It's locked"));
//        DoorUnlocker doorBell = new DoorUnlocker("bell", "the door bell of your neighbour.",
//                "a door bell", "you rung the door bell.", Material.BRASS, "neighbourkey");
//        doorBell.addCommand(Command.PRESS);
//        neighbourPorch.addEntity(doorBell);
        addRoom(neighbourPorch);

        Room neighbourHouse = new Room("Neighbour's house",
                "How did you even get in here?");

        neighbourHouse.addEntity(new Prop("Corpse", "The decomposed corpse of a child. " +
                "It seems it's holding something in its hand."));
        neighbourHouse.addEntity(new InvisibleProp("hand", "It's holding a white rose."));
        addRoom(neighbourHouse);

        Room square = new Room("Square", "The village square, quite deserted at this time of day.");
        square.setCustomDirection("To the east you see your town's church, to the north the village hall, " +
                "to the west the market and to the south your neighbourhood.");

        square.addEntity(new Prop("Fountain", "A fountain made of stone"));
        addRoom(square);

        Room villageChurch = new Room("Village Church",
                "The village's church. It's a fairly large building made out of large blocks of stone, " +
                        "with a tall bell tower.");
        villageChurch.addEntity(new InvisibleProp("tower", "A tall tower, with a huge brass bell at the top"));

        addRoom(villageChurch);

        Room villageGraveyard = new Room("Graveyard", "There are a lot of tombstones here.");

        villageGraveyard.addEntity(new Prop("Family tomb",
                "The last entry listed on here is the one of your sister...",
                "your family tomb", null, Material.STONE));
        villageGraveyard.addEntity(new JunkItem("dried flower",
                "a dried white rose that was on your family tomb.", 0.05, Size.SMALL));
        addRoom(villageGraveyard);

        Room villageHall = new Room("Hall", "This place is strangely deserted. Weird.");

        villageHall.addEntity(new Prop("desk", "A large desk with some drawers, with some papers on top of it.",
                "a desk", null, Material.WOOD));
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
        addRoom(villageHall);

        Room  villageMarket = new Room( "Market", "This is the market. " +
                "There's a mixed smell of sweat and food lingering in the air ");

        Shopkeeper Varra = new Shopkeeper("Varra",
                "A fairly tall man, a bit on the chubby side.",
                "Hi, I'm Varra. Want to see the items i sell?");
        Varra.simpleAddItem(eggs, 3);
        villageMarket.addEntity(Varra);
        addRoom(villageMarket);




        forest1.addExit("north", neighbourhood);
        forest1.addExit("south", forest2);
        forest2.addExit("north", forest1);
        forest2.addExit("south", forest3);
        forest3.addExit("north", forest2);
        villageMarket.addExit("east", square);
        villageHall.addExit("south", square);
        villageGraveyard.addExit("west", villageChurch);
        villageChurch.addExit("east", villageGraveyard);
        villageChurch.addExit("west", square);
        square.addExit("east", villageChurch);
        square.addExit("west", villageMarket);
        square.addExit("south", neighbourhood);
        square.addExit("north", villageHall);
        neighbourHouse.addExit("east", neighbourPorch);
        neighbourPorch.addExit("east", neighbourhood);
        neighbourPorch.addExit("west", neighbourHouse);
        neighbourhood.addExit("east", mainHouse);
        neighbourhood.addExit("north", square);
        neighbourhood.addExit("south", forest1);
        neighbourhood.addExit("west", neighbourPorch);
        mainHouse.addExit("south", mainHouse);
        mainHouse.addExit("west", neighbourhood);
        parentsRoom.addExit("west", hallway);
        hallway.addExit("west", yourRoom);
        hallway.addExit("north", mainHouse);
        hallway.addExit("east", parentsRoom);
        yourRoom.addExit("east", hallway);



        setStartingRoom(yourRoom);


//        p.simpleAddItem(new FoodBehaviour("eggs", "asd", 0, Size.NORMAL, 0), 1);
    }
}
