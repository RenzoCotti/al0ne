package com.al0ne.Entities.Worlds;

import com.al0ne.Behaviours.*;
import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.abstractEntities.Interactable;
import com.al0ne.Engine.Physics.Behaviours.WaterBehaviour;
import com.al0ne.Entities.Items.Props.Lightswitch;
import com.al0ne.Entities.Items.Props.LockedDoor;
import com.al0ne.Entities.Items.Types.Wearable.BodyClothing;
import com.al0ne.Entities.Items.ConcreteItems.Flashlight;
import com.al0ne.Entities.Items.ConcreteItems.Weapons.MeleeWeapon.Knife;
import com.al0ne.Entities.Statuses.DeathStatus;

import java.util.ArrayList;

public class DeltaBlock extends Area {
    public DeltaBlock() {
        super("cyberworld");

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












        shadyBuilding.addExit("south", startbath);
        startbath.addExit("north", shadyBuilding);







        Player player = new Player("You", "You don't remember anything about you.", true, startbath,
                20, 40, 40, 0, 1, 20);

        player.addStatus(new DeathStatus("Kidney failure", 60, "You feel dizzy.",
                "Your kidney hurts like hell.", "You died of kidney failure."));

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
