package com.al0ne.ConcreteEntities.Areas;

import com.al0ne.AbstractEntities.*;
import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.AbstractEntities.Enums.TechLevel;
import com.al0ne.AbstractEntities.Events.CompleteQuestEvent;
import com.al0ne.AbstractEntities.Events.HealthEvent;
import com.al0ne.AbstractEntities.Events.PrintEvent;
import com.al0ne.AbstractEntities.Pairs.Subject;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Quests.TravelQuest;
import com.al0ne.AbstractEntities.Abstract.Interactable;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin.Credit;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin.CreditOr;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Weapons.MeleeWeapon.Mace;
import com.al0ne.ConcreteEntities.Items.Props.Types.VendingMachine;
import com.al0ne.ConcreteEntities.Items.Types.Container;
import com.al0ne.ConcreteEntities.Items.Types.Drinkable;
import com.al0ne.ConcreteEntities.Items.Types.Food;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Armor;
import com.al0ne.Engine.Physics.Behaviours.WaterBehaviour;
import com.al0ne.Engine.Physics.InteractionResult.InteractionBehaviour;
import com.al0ne.Engine.Physics.InteractionResult.InteractionPrint;
import com.al0ne.Engine.Physics.InteractionResult.InteractionUnlock;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Books.Book;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.JunkItem;
import com.al0ne.ConcreteEntities.Items.Props.*;
import com.al0ne.ConcreteEntities.Items.Props.Types.HideItem;
import com.al0ne.ConcreteEntities.Items.Props.Types.InvisibleProp;
import com.al0ne.ConcreteEntities.Items.Props.Types.LockedDoor;
import com.al0ne.ConcreteEntities.Items.Props.Types.MoveProps.RoomSwitchProp;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.BodyClothing;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Weapons.MeleeWeapon.Knife;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Weapon;
import com.al0ne.ConcreteEntities.NPCs.ManGenerator;
import com.al0ne.ConcreteEntities.NPCs.Shopkeeper;

import java.util.ArrayList;

public class DeltaBlock extends Area {
    public DeltaBlock(World w) {
        super("deltablock", w);

        ManGenerator mg = new ManGenerator(getTechLevel());

        Room residential1 = new Room("Residential district 1",
                "The smell of urine permeates this shady alley. " +
                        "You can hear the rain ticking heavily on the roof above you.");

        addRoom(residential1);

        Room startbath = new Room("Dark bathroom", "Very dark, humid and smelling of blood.");
//        startbath.setLit(false);
        Prop bath = new Prop("Bath", "It's the cold bath you woke into. " +
                "You can see that the water is still bloody.");
        bath.addBehaviour(new WaterBehaviour());
        startbath.addEntity(bath);
        startbath.addItem(new Credit(), 1000);

        Knife serratedKnife = new Knife("Serrated knife", "A knife with a serrated edge " +
                "and a plastic handle. " +
                "The blade is coated with a black paint that has started chipping off in several points.",
                Material.IRON);
        serratedKnife.setIntegrity(40);
        BodyClothing jeansAndTShirt = new BodyClothing("Clothes",
                "This outfit consists of a pair of jeans and a ragged T-shirt.",
                0.4, Material.FABRIC);
        jeansAndTShirt.setIntegrity(30);

        startbath.addEntity(serratedKnife);
        startbath.addEntity(jeansAndTShirt);
        startbath.addEntity(new CreditOr());
//        startbath.addEntity(new Flashlight());
//        startbath.addEntity(new Battery("AA"));
        startbath.addEntity(new Lightswitch("light switch", "A light switch. " +
                "Should do something if pressed."));
        startbath.addEntity(new Prop("broken mirror",
                "The moment you see your fragmented reflection through the broken mirror, " +
                        "you are quite shocked." +
                        "You look like a ghost, extremely pale, red eyes... Better find medical aid " +
                        "as soon as possible.",
                Material.GLASS));

        addRoom(startbath);
        setStartingRoom(startbath);



        Room shadyBuilding = new Room("Shady building", "You do not recognise this building, " +
                "although you can figure out it's a residential one.");
        shadyBuilding.addEntity(new LockedDoor(Material.IRON, shadyBuilding, "west"));
        shadyBuilding.addEntity(new Prop("Torn rug",
                "A stained and very ruined rug. It's beige.", Material.FABRIC));
        shadyBuilding.addEntity(new Prop("Trash bin", "A small plastic trash bin. " +
                "It's overfull, and garbage has spilled on the floor.", Material.PLASTIC));
        InvisibleProp garbage = new InvisibleProp("Garbage", "You can see an empty " +
                "soda can and a banana peel.");

        garbage.setAddsOnExamine(new ArrayList<Interactable>(){
            {add(new JunkItem("empty soda can", "An empty can of a soft drink.",
                    0.1, Material.ALUMINIUM));
            add(new JunkItem("banana peel", "A yellow-blackish banana peel. Yuck.",
                    0.1, Size.SMALL));}});
        shadyBuilding.addEntity(garbage);
        addRoom(shadyBuilding);


        Room beggarMaze = new Room("Fire barrel", "");
        NPC beggar1 = new NPC("a beggar",
                "A filthy man, extremely this. Probably hasn't eaten in months.",
                "Could you give me some credits mate?");
        beggar1.addReactionItem("credit", new Subject("Thank you so much mate!"));
        beggar1.setShortDescription("a really scruffy man");
        beggarMaze.addEntity(beggar1);
        beggarMaze.addEntity(new Prop("Fire barrel",
                "A barrel full of gasoline and combustible stuff. The fire is roaring."));
        addRoom(beggarMaze);

        Room trashMaze = new Room("Alley full of trash",
                "This alley has been used as a giant trash bin. Pretty much the standard " +
                        "in Delta block these days.");
        trashMaze.addEntity(new Prop("Trash container", "It's yellow, although the " +
                "paint has mostly chipped off," +
                " revealing a very rusty structure underneath"));
        trashMaze.addEntity(new Prop("trash", "Some junk on the ground, namely cans, " +
                "broken glass bottles and similar.",
                "some trash on the ground"));
        addRoom(trashMaze);

        Room mazeSewers = new Room("Manhole access", "");
        mazeSewers.addEntity(new Prop("Manhole", "You see a locked manhole, " +
                "probably leading to the sewers."));
        addRoom(mazeSewers);

        Room mazeEntrance = new Room("Dimly lit back street", "This place looks familiar...");
        Room maze1 = new Room("Dimly lit back street", "This place looks familiar...");
        Room maze2 = new Room("Dimly lit back street", "This place looks familiar...");

        addRoom(mazeEntrance);
        addRoom(maze1);
        addRoom(maze2);

        Room unconsciousMan = new Room("Unconscious man", "This place smells of urine, " +
                "probably because of the unconscious guy sleeping in his own pee");
        unconsciousMan.addEntity(new InvisibleProp("unconscious man",
                "Not very reactive at all. " +
                "The empty bottles of vodka near him might have something to do with it."));
        unconsciousMan.addEntity(new JunkItem("Empty bottle of vodka",
                "A bottle of vodka of a well known brand. Cheap shit.",
                0.3, Material.GLASS), 2);
        unconsciousMan.addEntity(new Weapon("brokenbottle","broken bottle of vodka",
                "This bottle of vodka is broken, the sharpness might come in handy.",
                "sharp",
                0, 1,0.3, Size.SMALL, Material.GLASS), 1);
        addRoom(unconsciousMan);


        Room graffittiMaze = new Room("Graffiti on the wall",
                "On the brick wall on your left you see some graffiti.");
        graffittiMaze.addEntity(new InvisibleProp("graffiti", "A black and white graffiti " +
                "of a guy wearing a baseball cap and a jacket that partly covers his face. " +
                "He seems to be about to throw a " +
                "roll of toilet paper. Certainly odd."));
        graffittiMaze.addEntity(new InvisibleProp("brick wall", "looks very " +
                "rough and quickly made, " +
                "the bricks have started breaking in some points."));
        addRoom(graffittiMaze);

        Room drugMaze = new Room("Dark Alley", "It is really dark in here");
        Shopkeeper drugDealer1 = new Shopkeeper("Shady", "lul",
                "You're looking for some... special... items?");
        drugDealer1.simpleAddItem(new JunkItem("weed", "Some pot you bought from " +
                "a guy on the street. " +
                "Hopefully it's not just dried grass.", 0.05, Size.VSMALL), 20);
        drugDealer1.simpleAddItem(new JunkItem("Ecstasy",
                "Happiness in pills. Well, until the effect wears out, that is.",
                0.01, Size.MICRO), 50);
        drugMaze.addEntity(drugDealer1);
        addRoom(drugMaze);







        shadyBuilding.connectRoom("south", startbath);
        shadyBuilding.connectRoom("north", beggarMaze);
        beggarMaze.connectRoom("east", trashMaze);
        beggarMaze.connectRoom("west", mazeEntrance);
        mazeEntrance.connectRoom("north", mazeSewers);
        mazeEntrance.connectRoom("south", maze1);
        mazeEntrance.addExit("west", mazeEntrance);
        mazeSewers.addExit("south", mazeEntrance);
        maze1.addExit("east", mazeEntrance);
        maze1.addExit("south", mazeEntrance);
        maze1.connectRoom("west", maze2);
        maze2.connectRoom("north", unconsciousMan);
        maze2.addExit("east", maze1);
        maze2.addExit("south", mazeEntrance);
        maze2.addExit("west", mazeEntrance);
        unconsciousMan.connectRoom("west", graffittiMaze);
        graffittiMaze.connectRoom("west", drugMaze);






        Room hospitalReception = new Room("Hospital reception", "");
        hospitalReception.addEntity(new Prop("Desk",
                "A white desk, with some papers and a computer on top.", Material.IRON));
        hospitalReception.addEntity(new Prop("Plant", "A decorative fake plant, " +
                "it's about 1m tall."));
        NPC hospitalReceptionist = new NPC("a receptionist", "A receptionist in her 40s. " +
                "She looks tired and a bit flushed because of the stress, " +
                "the white apron she has to wear just makes it more obvious.", "Oh my God. " +
                "You need a doctor, now.");
        hospitalReceptionist.addSubject("doctor",
                new Subject("Just go in the waiting room, a doctor will be there shortly."));
        hospitalReceptionist.addSubject("hospital",
                new Subject("It certainly has seen better days, but it does its job."));
        hospitalReception.addEntity(hospitalReceptionist);

        TravelQuest reception = new TravelQuest(hospitalReception, true);
        getParentWorld().addQuest(reception);


        addRoom(hospitalReception);


        Room hospitalWR = new Room("Smelly room", "This looks like the hospital waiting room.");
        hospitalWR.addEntity(new Prop("Chairs", "Some metal chairs. " +
                "They look uncomfortable to sit on, " +
                "and you can see some rust on their legs.", Material.IRON));
        hospitalWR.addEntity(new Prop("Painting", "It's a print of a famous " +
                "painting representing a" +
                "storm over a city full of neon lights."));
        hospitalWR.addEntity(new Prop("Magazines", "Fairly generic magazines. Gossip, a few " +
                "newspapers and... oh. " +
                "What's that, an adult magazine?"));
        NPC wrNPC1 = new NPC("a bloody person", "This person's face is bloody, and he has a " +
                "few bruises as well. " +
                "Looks that he was beaten up pretty badly.", "Mind your own fucking business.");
        NPC wrNPC2 = new NPC("a very pale person", "This person's skin is so pale he looks " +
                "as if he were a ghost. " +
                "His eyes are watery, and he looks as if he hasn't slept in ages.", "Please, just let me be. " +
                "Been waiting for the last 6h here.");
        hospitalWR.addEntity(wrNPC1);
        hospitalWR.addEntity(wrNPC2);

        addRoom(hospitalWR);

        Room ER1 = new Room("Emergency Room 1", "This room has a very bright " +
                "light, almost blinding.");
        NPC surgeon = new NPC("a surgeon", "A woman wearing a surgical mask, a green hairnet, " +
                "some latex gloves and a white apron. She looks concerned.", "Please wait for your turn.");
        ER1.addEntity(surgeon);
        ER1.addEntity(new Prop("Surgery table", "A table covered in green plastic." +
                " It's quite large."));
        //TODO add medicines in here
        ER1.addEntity(new Prop("Locked cupboard", "You can see through the glass what " +
                "probably are medicines, painkillers and such."));
        addRoom(ER1);

        Room ER2 = new Room("Emergency Room 2", "The walls of this room are disturbingly " +
                "white, they look even too sterile.");
        ER2.addEntity(new Prop("Surgery table", "A table covered in green plastic. " +
                "It's quite large."));
        ER2.addEntity(new Prop("Locked cupboard", "You can see through the glass what " +
                "probably are medicines, painkillers and such."));
        addRoom(ER2);

        Room hospitalUpper = new Room("Upper floor",
                "This is the floor where in-patients recover.");
        hospitalUpper.addEntity(new RoomSwitchProp("Elevator",
                "An elevator made out of metal", hospitalReception));
        hospitalReception.addEntity(new RoomSwitchProp("Elevator",
                "An elevator made out of metal", hospitalUpper));
        addRoom(hospitalUpper);

        Room patientRoom1 = new Room("Patient rooms 1", "An oddly empty room.");
        patientRoom1.addEntity(new Prop("Beds", "Clean beds, looks like nobody is " +
                "staying here for the time being."));
        patientRoom1.addEntity(new Prop("TV", "A news presenter is rambling " +
                "about a recent homicide."));
        addRoom(patientRoom1);

        Room patientRoom2 = new Room("Patient rooms 2", "As soon as you enter, " +
                "you hear a very distinct sound of snoring.");
        patientRoom2.addEntity(new Prop("Beds", "Two of the 5 beds are occupied. " +
                "In one, a person is sleeping."));
        patientRoom2.addEntity(new Prop("Broken TV", "The screen somehow cracked, " +
                "even though the TV is quite high up."));
        NPC pat1 = new NPC("a patient", "His leg is pretty messed up.", "Don't mind my leg, " +
                "it's still recovering from the surgery.");
        pat1.addSubject("leg", new Subject("It got pretty messed up in a car accident, " +
                "luckily the surgery looks promising."));
        pat1.addSubject("car accident", new Subject("A fucker ran me over and didn't even stop. " +
                "I had passed out, and luckily somebody called an ambulance."));
        pat1.addSubject("surgery", new Subject("It was last week, and made by " +
                "this really cute female surgeon."));
        patientRoom2.addEntity(pat1);
        patientRoom2.addEntity(new Prop("sleeping patient", "He's sleeping quite deeply. " +
                "Better not disturb him."));
        addRoom(patientRoom2);

        hospitalReception.connectRoom("west", ER1);
        hospitalReception.connectRoom("east", ER2);
        hospitalReception.connectRoom("up", hospitalUpper);
        hospitalReception.connectRoom("north", hospitalWR);
        hospitalUpper.connectRoom("west", patientRoom1);
        hospitalUpper.connectRoom("east", patientRoom2);


        Room checkpointDelta = new Room("Checkpoint", "You approach a  military " +
                "checkpoint separating " +
                "the delta block from the epsilon area.");
        checkpointDelta.lockDirection("west", "needdocs");
        NPC checkpointGuard = new NPC("a checkpoint guard", "A fairly large man, " +
                "with a stern look in his eyes. " +
                "He's wearing a bulletproof vest.", "Hey, show me your documents");
        checkpointGuard.addReactionItem("yourdocuments", new Subject("Neat, you can pass",
                new InteractionUnlock("needdocs")));
        checkpointGuard.addSubject("documents", new Subject("Yes, your personal " +
                "documents stating you're from delta block. Now."));
        checkpointGuard.addSubject("epsilon area", new Subject("You need some documents " +
                "in order to go to epsilon."));
        checkpointDelta.addEntity(checkpointGuard);
        NPC checkpointArmedGuard = new NPC("an armed guard", "Tough looking, wears a bulletproof " +
                "vest and wields an assault rifle. " +
                "He's currently aiming at you.", "Give your documents to the other guy.");
        checkpointDelta.addEntity(checkpointArmedGuard);
        checkpointDelta.addEntity(new Prop("blockade", "you see a barricade blocking " +
                "access to the west. " +
                "The only way through is through a guarded door."));



        Room checkpointHouse = new Room("Checkpoint barracks", "This place is where the guards " +
                "relax while they're not on duty.");
        NPC barracksGuard = new NPC("a bored guard", "A man in his 50s, " +
                "sporting a moustache. " +
                "He's wearing a bulletproof vest.", "You shouldn't be here.");
        checkpointHouse.addEntity(barracksGuard);
        checkpointHouse.addEntity(new Prop("bunks", "the place in which the guards sleep in.",
                "some bunks"));
        checkpointHouse.addEntity(new HideItem("table", "a rusty metal table",
                "a table with a journal on top", Material.IRON, new Book("human traffic",
                "It's countless logs about people getting in and getting out of the Delta block.")));

        Container locker = new Container("locker", "a metal locker", Size.LARGE, Material.IRON);
        locker.addItem(new Mace("Nightstick", "A hard plastic nightstick.", Material.PLASTIC));
        locker.addItem(new Armor("Bulletproof vest",
                "A vest which protects against low calibre bullets.", Material.KEVLAR));
        locker.lock();

        checkpointHouse.addEntity(locker);



        Room nearCheck = new Room("Nearby military checkpoint", "You are on a street and " +
                "see in the distance what seems to be a checkpoint. Better not approach it if one has no documents.");


        Room hospitalPark = new Room("Hospital park", "You see on your right the parking lot for " +
                "visitors of the hospital, and further away the hospital itself.");
        hospitalPark.addEntity(new Prop("a car", "A blue car, parked over two parking spots. " +
                "How irritating."));
        hospitalPark.addEntity(new Prop("a motorcycle", "A naked motorcycle, black in colour."));
        hospitalPark.addEntity(new Prop("a clamped car", "The driver is likely to have parked there " +
                "too long."));

        Room highwayEntrance = new Room("Highway entrance", "You end up near the highway entrance." +
                "You can't go in without a vehicle, it would be suicide.");

        addRoom(checkpointDelta);
        addRoom(checkpointHouse);
        addRoom(nearCheck);
        addRoom(hospitalPark);
        addRoom(highwayEntrance);

        highwayEntrance.connectRoom("west", hospitalPark);
        hospitalPark.connectRoom("west", nearCheck);
        hospitalPark.connectRoom("north", hospitalReception);
        checkpointDelta.connectRoom("south", checkpointHouse);
        nearCheck.connectRoom("west", checkpointDelta);

        Room industrialZone = new Room("Industrial zone", "Down this road you can see several " +
                "run down factories. It's not that they were abandoned, they still are quite active; it's just that" +
                "their external appearance is quite neglected.");

        Room SFEntrance = new Room("Silicon Factory Entrance", "This is Delta-block's" +
                " famous silicon factory. It's where they take rock, collected through Omega zone slaves's death," +
                " and refine it into silicon, a very useful commodity nowadays.");
        SFEntrance.addEntity(new Prop("sign", "It reads: \"Silicatum\""));

        Room SFReception = new Room("Reception", "This is the reception of the factory. Oddly" +
                "enough, nobody sems to be here.");

        Room SFBreak = new Room("Break room", "This room smells of smoke and sweat.");
//        SFBreak.addEntity(new Prop("pile of clothes", "You can see some stained clothes, still" +
//                "humid you'd bet"));
        SFBreak.addEntity(new JunkItem("erotic calendar", "A calendar with pictures" +
                " of pretty yet little dressed ladies.", 0.2, Size.SMALL));
        SFBreak.addEntity(new Container("locker", "A locker", Size.LARGE, Material.IRON, true));

        Room SFWork1 = new Room("Machine room", "A hall with lots of machines to refine" +
                " the ore into silicon");
        SFWork1.addEntity(new InvisibleProp("machines", "You have absolutely no idea what they do"));

        Room SFWork2 = new Room("Furnace room", "A huge furnace dominates this room.");
        SFWork2.addEntity(new InvisibleProp("furnace", "It's what they use to purify the silica"));
        SFWork2.addEntity(mg.generate("a worker", "Sorry, I'm very busy"));


        Room SFWarehouse = new Room("Silicon warehouse",
                "Full of boxes and spare parts made of silicon.");
        Container boxes = new Container("boxes", "a few stacked boxes.",
                Size.LARGE, Material.CARDBOARD);
        boxes.addItem(new Mace("wrench", "a metal wrench", Material.IRON));
        boxes.addItem(new JunkItem("spare parts", "Some random silica made parts.",
                1, Material.SILICA));
        SFWarehouse.addEntity(boxes);



        addRoom(industrialZone);
        addRoom(SFEntrance);
        addRoom(SFReception);
        addRoom(SFBreak);
        addRoom(SFWarehouse);
        addRoom(SFWork1);
        addRoom(SFWork2);

        hospitalPark.connectRoom("south", industrialZone);
        industrialZone.connectRoom("west", SFEntrance);
        SFEntrance.connectRoom("west", SFReception);
        SFEntrance.connectRoom("north", SFWarehouse);
        SFReception.connectRoom("west", SFBreak);
        SFBreak.connectRoom("west", SFWork1);
        SFWork1.connectRoom("west", SFWork2);

        Room polyEntrance = new Room("Polymer Factory entrance","This is the " +
                "headquarters of Polymore, delta block's little" +
                " gem. Ever since polymers became a substitute for wood, the firm has been on a continuous " +
                "growth");
        polyEntrance.addEntity(new Prop("Sign", "It states \"Polymore\""));

        Room polyWH = new Room("Polymore Warehouse", "There is a distinct " +
                "smell of palstic of different kinds in here");
        polyWH.addEntity(new Prop("boxes", "a pile of empty boxes"));

        Room polyReception = new Room("Polymore reception", "This looks like the " +
                "reception of this factory.");
        polyReception.addEntity(mg.generate("a cute receptionist", "Hi! Welcome to" +
                " polymore.", true));
        polyReception.addEntity(new Prop("warning sign", "It says not to go" +
                "to the room to the west without a gas mask."));

        Room chemBaths = new Room("Chemical baths", "The smell here is terrible.");
        chemBaths.addEvent(new HealthEvent(100,
                "The toxic fumes destroy your lungs!", -5));
        //there's a strong smell of chemicals, you can smell it even through the mask
        chemBaths.addEntity(new Prop("barrels full of chemicals",
                "They're of various colours, some green, some blue, and most are filled to the" +
                        " brim.",Material.PLASTIC));
        NPC hazmatGuy = new NPC("guy in a hazmat suit", "He's wearing a red hazmat suit and " +
                "looks quite busy", "MMMPH MMFPT MMMPH!");
        chemBaths.addEntity(hazmatGuy);

        Room dryRoom = new Room("Drying room", "The noise from the fans drying the chemical " +
                "compounds is deafening.");
        dryRoom.addEntity(new Prop("fans", "They are quite large, they look about 5 meters " +
                "wide.", Material.STEEL));
        dryRoom.addEvent(new HealthEvent(20, "The noise hurts your ears", -1));
        NPC earmuffsGuy = new NPC("guy wearing earmuffs", "He wears a pair of orange earmuffs. " +
                "You call out to him but he doesn't hear you.", "...");
        dryRoom.addEntity(earmuffsGuy);

        Room modernBR = new Room("Modern looking room", "This looks like a break room");
        modernBR.addEntity(new Prop("vending machine", "You can buy a soda or some chips"));
        modernBR.addEntity(mg.generate("guy drinking tea", "Hi, can I help you?"));
        modernBR.addEntity(new Prop("modern painting", "It looks like somebody vomited" +
                " on a canvas, to be honest."));





        Room textWH = new Room("Styratex Warehouse", "You can see some rolls of " +
                "synthetic weave covered in plastic.");
        textWH.addEntity(new Prop("some opened rolls", "The material is soft and cold" +
                " to the touch. Oddly enough, this isn't the famous Styratex."));

        Room styraEntrance = new Room("Styratex Factory entrance", "This is the entrance of " +
                " StyraTex factory. They started as a" +
                "synthetic fabric factory, Weavestill, and when they developed the famous fabric StyraTex " +
                "they changed name.");
        styraEntrance.addEntity(new Prop("closed gate", "The factory seems to be closed now."));


        Room polyStyraRoom = new Room("Inner Industrial district",
                "To the west you can see the entrance to Polymore, " +
                        "whereas to the south you see the entrance to StyraTex,");

        addRoom(polyStyraRoom);
        addRoom(polyEntrance);
        addRoom(polyWH);
        addRoom(polyReception);
        addRoom(chemBaths);
        addRoom(dryRoom);
        addRoom(modernBR);

        industrialZone.connectRoom("south", polyStyraRoom);
        polyStyraRoom.connectRoom("west", polyEntrance);
        polyStyraRoom.connectRoom("south", styraEntrance);
        polyEntrance.connectRoom("north", polyWH);
        polyEntrance.connectRoom("west", polyReception);
        polyReception.connectRoom("west", chemBaths);
        chemBaths.connectRoom("south",dryRoom);
        chemBaths.connectRoom("west", modernBR);


        Room steelFactEntrance = new Room("Steel Factory entrance", "McFalryo Steel & co.'s" +
                " factory lies here. The guys don't have a good reputation, but the profit they make" +
                " manages to shut up questioning mouths.");
        steelFactEntrance.addEntity(new Prop("sign", "It states: \"Mc Falroy Steel & co.\""));

        Room mcFalWH = new Room("McFalroy Warehouse", "There is a distinct smell of " +
                "oil, probably used to preserve the products.");
        mcFalWH.addEntity(new Prop("Steel blocks", "They are about 30x30 cm", Material.STEEL));
        mcFalWH.addEntity(new JunkItem("Steel bar", "A bar of about 50 cm, with a width of 2 cm.",
                1, Size.NORMAL, Material.STEEL));

        Room mcFalReception = new Room("Reception", "A very neat and clean reception");
        mcFalReception.addEntity(new Prop("pictures", "They represent historical moments" +
                " for this firm, such as the founding."));
        mcFalReception.addEntity(new Prop("certificates", "Those are certificates of " +
                "excellence of their materials"));
        mcFalReception.addEntity(mg.generate("a receptionist", "Hello, how can I help you today?"));

        Room artRoom = new Room("Art Exposition", "You can see a lot of paintings here." +
                "Wow the guys are classy");

        Room meltRoom = new Room("Melting room", "This is where the ore gets melted and" +
                " turned into a river of very hot steel.");
        meltRoom.addEntity(new Prop("cauldron", "The ore goes in there, melts and" +
                "drips out."));

        Room shapingRoom = new Room("Moulding station", "This is where the steel gets shaped into " +
                "roughly what it's supposed to be.");
        shapingRoom.addEntity(new Prop("moulds", "Those are full of molten steel, and " +
                "once it will have cooled off, they will be removed to reveal the hardened steel inside"));
        shapingRoom.addEntity(new Prop("Oven", "Once the steel has cooled, it gets put" +
                " in here to temper it and make it stronger."));

        Room forgingRoom = new Room("Forge", "Here the steel gets refined into the final shape");
        forgingRoom.addEntity(new Prop("hydraulic hammer", "this strikes the steel and shapes it."));


        addRoom(steelFactEntrance);
        addRoom(mcFalReception);
        addRoom(mcFalWH);
        addRoom(meltRoom);
        addRoom(artRoom);
        addRoom(shapingRoom);
        addRoom(forgingRoom);

        industrialZone.connectRoom("east", steelFactEntrance);
        steelFactEntrance.connectRoom("east", mcFalReception);
        steelFactEntrance.connectRoom("north", mcFalWH);
        mcFalReception.connectRoom("east", meltRoom);
        mcFalReception.connectRoom("south", artRoom);
        meltRoom.connectRoom("east", shapingRoom);
        shapingRoom.connectRoom("east", forgingRoom);

        Room canteen = new Room("Canteen", "A huge hall, full of tables. " +
                "There is a distinct smell of food here. Broccoli maybe?");
        VendingMachine canteenvm = new VendingMachine("Food dispenser", "a machine that sells meals.",
                Material.IRON);
        canteenvm.addItem(new Food("meal", "a prepackaged instant meal, ready to be consumed. " +
                "Chicken flavoured.", 0.7, Size.NORMAL, 30), 10);
        canteen.addEntity(canteenvm);

        Room southEntranceFD = new Room("South entrance of the Factory District",
                "This place reeks of oil and rust.");
        southEntranceFD.setCustomDirection("To the west you can see the industrial district.");

        Room openedManhole = new Room("Opened manhole",
                "You can see a man working in a manhole, probably fixing some tubing.");
        openedManhole.setCustomDirection("To the east you see an overpass");
        openedManhole.addEntity(mg.generate("worker", "Sorry mate, I'm busy"));
        openedManhole.addEntity(new Prop("warning sign", "A red and white rusted" +
                "warning sign.", Material.IRON));

        Room overpass = new Room("Overpass",
                "You're on an overpass, on top of the highway.");
        overpass.addEvent(new PrintEvent(20, "A car zooms very fast under you!"));
        overpass.addEntity(new JunkItem("crumpled can", "A tin soda can that was squashed",
                0.1, Material.ALUMINIUM));

        Room kiosk = new Room("Battered vending machine", "");
        VendingMachine kioskvm = new VendingMachine();
        kioskvm.addItem(new Food("snack", "An energy bar of 150 kcal",
                0.05, Size.VSMALL, 5), 5);
        kioskvm.addItem(new Drinkable("Energy drink", "An energetic drink containing " +
                "loads of caffeine and sugar.", 0.25, Size.SMALL), 3);
        kioskvm.addItem(new JunkItem("newstab", "broken, unfortunately.",
                0.2, Size.SMALL), 1);
        kiosk.addEntity(kioskvm);

        Room rundownShack = new Room("Shack", "A moldy, wooden shack " +
                "that got run down by the elements.");
        Shopkeeper ddealer = new Shopkeeper("hooded figure",
                "doesn't look very much trustworthy", "What are you buying?");
        ddealer.simpleAddItem(new JunkItem("meth", "don't do drugs, dummy.",
                0.05, Size.VSMALL), 3);
        ddealer.simpleAddItem(new JunkItem("coke", "drugs are bad mate.",
                0.05, Size.VSMALL), 5);
        //TODO add stimulants, what they do is offer you a free turn every x turns, by returning false
        //to check if it interferes with enemies attacking
        rundownShack.addEntity(ddealer);

        Room bloodyAlley = new Room("Bloody alley", "You can see a large crimson " +
                "stain on the wall");
        bloodyAlley.addEntity(new InvisibleProp("stain", "Looks like fresh blood."));
        bloodyAlley.addItem(new JunkItem("shards of glass", "they are a bit bloody",
                0.1, Material.GLASS ));

        Room aPuddle = new Room("A puddle", "It's fairly dark in here, your feet seem " +
                "to be in a puddle. There's something on the ground.");
        aPuddle.setLit(false);
        //if lit -> disfigured corpse, puddle of blood and water

        Room muddyCorner = new Room("Muddy corner", "The water has made the " +
                "ground quite muddy.");
        muddyCorner.addEntity(new Prop("footprints", "They are larger than yours," +
                " they are set quite deep in the mud, as if the person was carrying something very heavy."));


        addRoom(canteen);
        addRoom(southEntranceFD);
        addRoom(openedManhole);
        addRoom(overpass);
        addRoom(bloodyAlley);
        addRoom(aPuddle);
        addRoom(kiosk);
        addRoom(rundownShack);
        addRoom(muddyCorner);

        canteen.connectRoom("west", polyStyraRoom);
        canteen.connectRoom("east", southEntranceFD);
        southEntranceFD.connectRoom("east", openedManhole);
        southEntranceFD.connectRoom("east", overpass);
        southEntranceFD.connectRoom("north", bloodyAlley);
        bloodyAlley.connectRoom("north", aPuddle);
        aPuddle.connectRoom("west", kiosk);
        aPuddle.connectRoom("north", rundownShack);
        aPuddle.connectRoom("east", muddyCorner);
        muddyCorner.connectRoom("north", graffittiMaze);


    }
}
