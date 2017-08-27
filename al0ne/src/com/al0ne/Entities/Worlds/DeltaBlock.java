package com.al0ne.Entities.Worlds;

import com.al0ne.Behaviours.*;
import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Quests.TravelQuest;
import com.al0ne.Behaviours.abstractEntities.Interactable;
import com.al0ne.Engine.Physics.Behaviours.WaterBehaviour;
import com.al0ne.Engine.Physics.InteractionResult.InteractionBehaviour;
import com.al0ne.Engine.Physics.InteractionResult.InteractionPrint;
import com.al0ne.Entities.Items.ConcreteItems.Battery;
import com.al0ne.Entities.Items.Props.Lightswitch;
import com.al0ne.Entities.Items.Props.LockedDoor;
import com.al0ne.Entities.Items.Types.Wearable.BodyClothing;
import com.al0ne.Entities.Items.ConcreteItems.Flashlight;
import com.al0ne.Entities.Items.ConcreteItems.Weapons.MeleeWeapon.Knife;
import com.al0ne.Entities.Items.Types.Wearable.Weapon;
import com.al0ne.Entities.Statuses.DeathStatus;

import java.util.ArrayList;

public class DeltaBlock extends Area {
    public DeltaBlock() {
        super("deltablock");

        Room alley = new Room("Dark alley",
                "The smell of urine permeates this shady alley. " +
                        "You can hear the rain ticking heavily on the roof above you.");

        addRoom(alley);

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
















        shadyBuilding.addExit("south", startbath);
        shadyBuilding.addExit("north", beggarMaze);
        startbath.addExit("north", shadyBuilding);
        beggarMaze.addExit("east", trashMaze);
        beggarMaze.addExit("south", shadyBuilding);
        beggarMaze.addExit("west", mazeEntrance);
        trashMaze.addExit("west", beggarMaze);
        mazeEntrance.addExit("north", mazeSewers);
        mazeEntrance.addExit("east", beggarMaze);
        mazeEntrance.addExit("south", maze1);
        mazeEntrance.addExit("west", mazeEntrance);
        mazeSewers.addExit("south", mazeEntrance);
        maze1.addExit("north", mazeEntrance);
        maze1.addExit("east", mazeEntrance);
        maze1.addExit("south", mazeEntrance);
        maze1.addExit("west", maze2);
        maze2.addExit("north", unconsciousMan);
        maze2.addExit("east", maze1);
        maze2.addExit("south", mazeEntrance);
        maze2.addExit("west", mazeEntrance);
        unconsciousMan.addExit("south", maze2);







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
