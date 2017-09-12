package com.al0ne.Entities.Worlds;

import com.al0ne.Behaviours.*;
import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Pairs.Subject;
import com.al0ne.Behaviours.Quests.TravelQuest;
import com.al0ne.Behaviours.abstractEntities.Interactable;
import com.al0ne.Engine.Physics.Behaviours.WaterBehaviour;
import com.al0ne.Engine.Physics.InteractionResult.InteractionBehaviour;
import com.al0ne.Engine.Physics.InteractionResult.InteractionPrint;
import com.al0ne.Entities.Items.ConcreteItems.Battery;
import com.al0ne.Entities.Items.Props.Lightswitch;
import com.al0ne.Entities.Items.Props.LockedDoor;
import com.al0ne.Entities.Items.Props.TeleportProp;
import com.al0ne.Entities.Items.Types.Wearable.BodyClothing;
import com.al0ne.Entities.Items.ConcreteItems.Flashlight;
import com.al0ne.Entities.Items.ConcreteItems.Weapons.MeleeWeapon.Knife;
import com.al0ne.Entities.Items.Types.Wearable.Weapon;
import com.al0ne.Entities.NPCs.Shopkeeper;
import com.al0ne.Entities.Statuses.DeathStatus;

import java.util.ArrayList;

public class DeltaBlock extends Area {
    public DeltaBlock() {
        super("deltablock");

        Room residential1 = new Room("Residential district 1",
                "The smell of urine permeates this shady alley. " +
                        "You can hear the rain ticking heavily on the roof above you.");

        addRoom(residential1);

        Room startbath = new Room("Dark bathroom", "Very dark, humid and smelling of blood.");
        startbath.setLit(false);
        Prop bath = new Prop("Bath", "It's the cold bath you woke into. " +
                "You can see that the water is still bloody.");
        bath.addBehaviour(new WaterBehaviour());
        startbath.addEntity(bath);

        Knife serratedKnife = new Knife("Serrated knife", "A knife with a serrated edge and a plastic handle. " +
                "The blade is coated with a black paint that has started chipping off in several points.", Material.IRON);
        serratedKnife.setIntegrity(40);
        BodyClothing jeansAndTShirt = new BodyClothing("Clothes",
                "This outfit consists of a pair of jeans and a ragged T-shirt.", 0.4, Material.FABRIC);
        jeansAndTShirt.setIntegrity(30);

        startbath.addEntity(serratedKnife);
        startbath.addEntity(jeansAndTShirt);
        startbath.addEntity(new Flashlight());
        startbath.addEntity(new Battery("AA"));
        startbath.addEntity(new Lightswitch("light switch", "A light switch. " +
                "Should do something if pressed."));
        startbath.addEntity(new Prop("broken mirror",
                "The moment you see your fragmented reflection through the broken mirror, you are quite shocked." +
                        "You look like a ghost, extremely pale, red eyes... Better find medical aid as soon as possible.",
                Material.GLASS));

        addRoom(startbath);


        Room shadyBuilding = new Room("Shady building", "You do not recognise this building, " +
                "although you can figure out it's a residential one.");
        shadyBuilding.addEntity(new LockedDoor(Material.IRON, shadyBuilding, "west"));
        shadyBuilding.addEntity(new Prop("Torn rug",
                "A stained and very ruined rug. It's beige.", Material.FABRIC));
        shadyBuilding.addEntity(new Prop("Trash bin", "A small plastic trash bin. " +
                "It's overfull, and garbage has spilled on the floor.", Material.PLASTIC));
        InvisibleProp garbage = new InvisibleProp("Garbage", "You can see an empty soda can and a banana peel.");

        garbage.setAddsOnExamine(new ArrayList<Interactable>(){
            {add(new JunkItem("empty soda can", "An empty can of a soft drink.", 0.1, Material.ALUMINIUM));
            add(new JunkItem("banana peel", "A yellow-blackish banana peel. Yuck.", 0.1, Size.SMALL));}});
        shadyBuilding.addEntity(garbage);
        addRoom(shadyBuilding);


        Room beggarMaze = new Room("Fire barrel", "");
        NPC beggar1 = new NPC("a beggar",
                "A filthy man, extremely this. Probably hasn't eaten in months.",
                "Could you give me some credits mate?");
        beggar1.addReactionItem("credit", new InteractionPrint("Thank you so much! I really owe you one"));
        beggar1.setShortDescription("a really scruffy man");
        beggarMaze.addEntity(beggar1);
        beggarMaze.addEntity(new Prop("Fire barrel",
                "A barrel full of gasoline and combustible stuff. The fire is roaring."));
        addRoom(beggarMaze);

        Room trashMaze = new Room("Alley full of trash",
                "This alley has been used as a giant trash bin. Pretty much the standard in Delta block these days.");
        trashMaze.addEntity(new Prop("Trash container", "It's yellow, although the paint has mostly chipped off," +
                " revealing a very rusty structure underneath"));
        trashMaze.addEntity(new Prop("trash", "Some junk on the ground, namely cans, broken glass bottles and similar.",
                "some trash on the ground"));
        addRoom(trashMaze);

        Room mazeSewers = new Room("Manhole access", "");
        mazeSewers.addEntity(new Prop("Manhole", "You see a locked manhole, probably leading to the sewers."));
        addRoom(mazeSewers);

        Room mazeEntrance = new Room("Dimly lit back street", "This place looks familiar...");
        Room maze1 = new Room("Dimly lit back street", "This place looks familiar...");
        Room maze2 = new Room("Dimly lit back street", "This place looks familiar...");

        addRoom(mazeEntrance);
        addRoom(maze1);
        addRoom(maze2);

        Room unconsciousMan = new Room("Unconscious man", "This place smells of urine, " +
                "probably because of the unconscious guy sleeping in his own pee");
        unconsciousMan.addEntity(new InvisibleProp("unconscious man", "Not very reactive at all. " +
                "The empty bottles of vodka near him might have something to do with it."));
        unconsciousMan.addEntity(new JunkItem("Empty bottle of vodka",
                "A bottle of vodka of a well known brand. Cheap shit.", 0.3, Material.GLASS), 2);
        unconsciousMan.addEntity(new Weapon("brokenbottle","broken bottle of vodka",
                "This bottle of vodka is broken, the sharpness might come in handy.", "sharp",
                0, 1,0.3, Size.SMALL, Material.GLASS), 1);
        addRoom(unconsciousMan);


        Room graffittiMaze = new Room("Graffiti on the wall",
                "On the brick wall on your left you see some graffiti.");
        graffittiMaze.addEntity(new InvisibleProp("graffiti", "A black and white graffiti " +
                "of a guy wearing a baseball cap and a jacket that partly covers his face. He seems to be about to throw a " +
                "roll of toilet paper. Certainly odd."));
        graffittiMaze.addEntity(new InvisibleProp("brick wall", "looks very rough and quickly made, " +
                "the bricks have started breaking in some points."));
        addRoom(graffittiMaze);

        Room drugMaze = new Room("Dark Alley", "It is really dark in here");
        Shopkeeper drugDealer1 = new Shopkeeper("Shady", "lul",
                "You're looking for some... special... items?");
        drugDealer1.simpleAddItem(new JunkItem("weed", "Some pot you bought from a guy on the street. " +
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
        hospitalReception.addEntity(new Prop("Plant", "A decorative fake plant, it's about 1m tall."));
        NPC hospitalReceptionist = new NPC("a receptionist", "A receptionist in her 40s. " +
                "She looks tired and a bit flushed because of the stress, " +
                "the white apron she has to wear just makes it more obvious.", "Oh my God. You need a doctor, now.");
        hospitalReceptionist.addSubject("doctor",
                new Subject("Just go in the waiting room, a doctor will be there shortly."));
        hospitalReceptionist.addSubject("hospital",
                new Subject("It certainly has seen better days, but it does its job."));
        hospitalReception.addEntity(hospitalReceptionist);

        addRoom(hospitalReception);


        Room hospitalWR = new Room("Smelly room", "This looks like the hospital waiting room.");
        hospitalWR.addEntity(new Prop("Chairs", "Some metal chairs. They look uncomfortable to sit on, " +
                "and you can see some rust on their legs.", Material.IRON));
        hospitalWR.addEntity(new Prop("Painting", "It's a print of a famous painting representing a" +
                "storm over a city full of neon lights."));
        hospitalWR.addEntity(new Prop("Magazines", "Fairly generic magazines. Gossip, a few newspapers and... oh. " +
                "What's that, an adult magazine?"));
        NPC wrNPC1 = new NPC("a bloody person", "This person's face is bloody, and he has a few bruises as well. " +
                "Looks that he was beaten up pretty badly.", "Mind your own fucking business.");
        NPC wrNPC2 = new NPC("a very pale person", "This person's skin is so pale he looks as if he were a ghost. " +
                "His eyes are watery, and he looks as if he hasn't slept in ages.", "Please, just let me be. Been waiting for the last 6h here.");
        hospitalWR.addEntity(wrNPC1);
        hospitalWR.addEntity(wrNPC2);

        addRoom(hospitalWR);

        Room ER1 = new Room("Emergency Room 1", "This room has a very bright light, almost blinding.");
        NPC surgeon = new NPC("a surgeon", "A woman wearing a surgical mask, a green hairnet, " +
                "some latex gloves and a white apron. She looks concerned.", "Please wait for your turn.");
        ER1.addEntity(surgeon);
        ER1.addEntity(new Prop("Surgery table", "A table covered in green plastic. It's quite large."));
        //TODO add medicines in here
        ER1.addEntity(new Prop("Locked cupboard", "You can see through the glass what " +
                "probably are medicines, painkillers and such."));
        addRoom(ER1);

        Room ER2 = new Room("Emergency Room 2", "The walls of this room are disturbingly " +
                "white, they look even too sterile.");
        ER2.addEntity(new Prop("Surgery table", "A table covered in green plastic. It's quite large."));
        ER2.addEntity(new Prop("Locked cupboard", "You can see through the glass what " +
                "probably are medicines, painkillers and such."));
        addRoom(ER2);

        Room hospitalUpper = new Room("Upper floor", "This is the floor where in-patients recover.");
        hospitalUpper.addEntity(new TeleportProp("Elevator", "An elevator made out of metal", hospitalReception));
        hospitalReception.addEntity(new TeleportProp("Elevator", "An elevator made out of metal", hospitalUpper));
        addRoom(hospitalUpper);

        Room patientRoom1 = new Room("Patient rooms 1", "An oddly empty room.");
        patientRoom1.addEntity(new Prop("Beds", "Clean beds, looks like nobody is " +
                "staying here for the time being."));
        patientRoom1.addEntity(new Prop("TV", "A news presenter is rambling about a recent homicide."));
        addRoom(patientRoom1);

        Room patientRoom2 = new Room("Patient rooms 2", "As soon as you enter, " +
                "you hear a very distinct sound of snoring.");
        patientRoom2.addEntity(new Prop("Beds", "Two of the 5 beds are occupied. In one, a person is sleeping."));
        patientRoom2.addEntity(new Prop("Broken TV", "The screen somehow cracked, even though the TV is quite high up."));
        NPC pat1 = new NPC("a patient", "His leg is pretty messed up.", "Don't mind my leg, " +
                "it's still recovering from the surgery.");
        pat1.addSubject("leg", new Subject("It got pretty messed up in a car accident, luckily the surgery looks promising."));
        pat1.addSubject("car accident", new Subject("A fucker ran me over and didn't even stop. " +
                "I had passed out, and luckily somebody called an ambulance."));
        pat1.addSubject("surgery", new Subject("It was last week, and made by this really cute female surgeon."));
        patientRoom2.addEntity(pat1);
        patientRoom2.addEntity(new Prop("sleeping patient", "He's sleeping quite deeply. Better not disturb him."));
        addRoom(patientRoom2);

        hospitalReception.connectRoom("west", ER1);
        hospitalReception.connectRoom("east", ER2);
        hospitalReception.connectRoom("up", hospitalUpper);
        hospitalReception.connectRoom("north", hospitalWR);
        hospitalUpper.connectRoom("west", patientRoom1);
        hospitalUpper.connectRoom("east", patientRoom2);





        Player player = new Player("You", "You don't remember anything about you.", true, startbath,
                20, 40, 40, 0, 1, 20);

        player.addStatus(new DeathStatus("Kidney failure", 60, "You feel dizzy.",
                "Your kidney hurts like hell.", "You died of kidney failure."));
//        player.addQuest(new TravelQuest());

//        player.simpleAddItem(new Credit(), 5);
//
//        player.addOneItem(new Pair(serratedKnife, 1));
//
//        player.addOneItem(new Pair(jeansAndTShirt, 1));
//        player.simpleAddItem(new Flashlight(), 1);
        //        player.wear(serratedKnife);
//        player.wear(jeansAndTShirt);
//        player.simpleAddItem(new Battery("aabattery"), 2);
        setPlayer(player);
    }
}
