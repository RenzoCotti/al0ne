package com.al0ne.Entities.Worlds;

import com.al0ne.Behaviours.Area;
import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Entities.Items.Types.Wearable.BodyClothing;
import com.al0ne.Entities.Items.ConcreteItems.AAbattery;
import com.al0ne.Entities.Items.ConcreteItems.Coin.Credit;
import com.al0ne.Entities.Items.ConcreteItems.Flashlight;
import com.al0ne.Entities.Items.ConcreteItems.Weapons.MeleeWeapon.Knife;

public class DeltaBlock extends Area {
    public DeltaBlock() {
        super("cyberworld");

        Room alley = new Room("Dark alley",
                "The smell of urine permeates this shady alley. " +
                        "You can hear the rain ticking heavily on the roof above you.");

        putRoom(alley);





        Player player = new Player("You", "You don't remember anything about you.", true, alley,
                20, 40, 40, 0, 1, 20);
        player.simpleAddItem(new Credit(), 5);
        Knife serratedKnife = new Knife("Serrated knife", "A knife with a serrated edge and a plastic handle. " +
                "The blade is coated with a black paint that has started chipping off in several points.", Material.IRON);
        serratedKnife.setIntegrity(40);
        player.addOneItem(new Pair(serratedKnife, 1));
        player.wear(serratedKnife);
        BodyClothing jeansAndTShirt = new BodyClothing("Clothes",
                "This outfit consists of a pair of jeans and a ragged T-shirt.", 0.4, Material.FABRIC);
        jeansAndTShirt.setIntegrity(30);
        player.addOneItem(new Pair(jeansAndTShirt, 1));
        player.wear(jeansAndTShirt);
        player.simpleAddItem(new Flashlight(), 1);
        player.simpleAddItem(new AAbattery(), 2);
        setPlayer(player);
        setStartingRoom(alley);
    }
}
