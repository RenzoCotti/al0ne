package com.al0ne.ConcreteEntities.Areas;

import com.al0ne.AbstractEntities.*;
import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.TechLevel;
import com.al0ne.AbstractEntities.Events.HealthEvent;
import com.al0ne.AbstractEntities.Events.PrintEvent;
import com.al0ne.AbstractEntities.Pairs.Subject;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Coin.Coin;
import com.al0ne.Engine.Physics.Behaviours.MaterialBehaviours.IronBehaviour;
import com.al0ne.Engine.Physics.InteractionResult.InteractionAdd;
import com.al0ne.ConcreteEntities.Enemies.Snake;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.*;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Weapons.Ammunition.Arrow;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Armor.*;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Books.Scroll;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Books.Spellbook;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Helmet.Barbute;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Helmet.GreatHelm;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Helmet.IronHelmet;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Helmet.Sallet;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Shield.*;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Weapons.MeleeWeapon.*;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Weapons.RangedWeapon.LongBow;
import com.al0ne.ConcreteEntities.Items.Props.Types.MoveProps.RoomSwitchProp;
import com.al0ne.ConcreteEntities.Items.Props.Types.MoveProps.WorldSwitch;
import com.al0ne.ConcreteEntities.NPCs.ManGenerator;
import com.al0ne.ConcreteEntities.Spells.ConcreteSpells.Fireball;
import com.al0ne.ConcreteEntities.Spells.ConcreteSpells.LightHeal;
import com.al0ne.ConcreteEntities.Enemies.Demon;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.*;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Food.Apple;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Food.Mushroom;
import com.al0ne.ConcreteEntities.Items.Props.HolyFountain;
import com.al0ne.ConcreteEntities.Items.Props.Types.LockedDoor;
import com.al0ne.ConcreteEntities.NPCs.Shopkeeper;
import com.al0ne.ConcreteEntities.Enemies.Wolf;
import com.al0ne.ConcreteEntities.Spells.ConcreteSpells.TeleportSpell;
import com.al0ne.ConcreteEntities.Spells.MidasSpell;

/**
 * Created by BMW on 14/03/2017.
 */
public class TestingArea extends Area {

    public TestingArea(World w) {
        super("test", w);

        ManGenerator mg = new ManGenerator(getTechLevel());



        Room startRoom = new Room("Generic Room", "You are in a pretty generic-looking cave. It feels pretty damp.");
        startRoom.addEvent(new PrintEvent(5, "You fart."));
//        startRoom.addEvent(new HealthEvent(10,"you painfully stab your foot on a sharp rock", -1));
        startRoom.addItem(new Mushroom());
        startRoom.addItem(new Coin(), 1000);
//        for (int i = 0; i < 10; i++){
//            if(i > 7){
//                startRoom.addEntity(mg.generate("wamen"+i, "hello there general kenobesse.", true));
//            } else {
//                startRoom.addEntity(mg.generate("man"+i, "hello there general kenobi."));
//            }
//        }
//        Container chest = new Chest();
//        chest.addItem(new SilverCoin(), 100);
//        chest.addItem(new Knife(Material.IRON), 1);
//        startRoom.addEntity(chest);
//        startRoom.addEntity(new Snake());
//        startRoom.addEntity(new Pistol(5, "9mm", 7));
//        startRoom.addEntity(new Bullet9mm(), 10);

        LockedDoor ld = new LockedDoor("wooden door", "a wooden door", "an opened wooden door", Material.WOOD, startRoom, "west");
        startRoom.addEntity(ld.generateKey("generic key"));
        startRoom.addEntity(ld);

        addRoom(startRoom);


        Room attic = new Room("Attic", "An empty attic.");
        Spellbook sb = new Spellbook();
        sb.addSpell(new Fireball(), 10);
        sb.addSpell(new LightHeal(), 5);
        sb.addSpell(new TeleportSpell(), 10);
        attic.addEntity(new WorldSwitch("suspicious rock", "Very suspicios, let me tell you.", 1));


        attic.addEntity(new LongBow());
        attic.addEntity(new Arrow(Material.IRON), 3);
        attic.addEntity(sb);

        addRoom(attic);

        Room lootRoom = new Room("Looty room", "A room full of loot! yay!");

//        for(Pair p: lootTable.getLoot(123)){
//            lootRoom.addEntity(p.getEntity(), p.getCount());
//        }
        addRoom(lootRoom);


        Room shieldRoom = new Room("Shieldery", "A room full of shields");


        startRoom.addEntity(new RoomSwitchProp("rock", "another suspicious rock. ha!", shieldRoom));
        shieldRoom.addEntity(new RoomSwitchProp("rock", "another suspicious rock. ha!", startRoom));


        for(Material m : Material.getMetals()){
            shieldRoom.addEntity(new Buckler(m));
            shieldRoom.addEntity(new TowerShield(m));
            shieldRoom.addEntity(new RoundShield(m));
            shieldRoom.addEntity(new HeaterShield(m));
            shieldRoom.addEntity(new KiteShield(m));
        }

        for(Material m : Material.getMaterials(2)){
            shieldRoom.addEntity(new Shield(m));
        }







        addRoom(shieldRoom);

        Room armorRoom = new Room("Armory", "A room full of armor");



        for(Material m : Material.getMaterials(0)){
            armorRoom.addEntity(new Armor(m));
        }

        for(Material m : Material.getMetals()){
            armorRoom.addEntity(new ChainMail(m));
            armorRoom.addEntity(new PlateArmor(m));
            armorRoom.addEntity(new ScaleArmor(m));
        }






        addRoom(armorRoom);

        Room helmetRoom = new Room("Helmetry", "A room full of helmets");

        for(Material m : Material.getMetals()){
            helmetRoom.addEntity(new Sallet(m));
            helmetRoom.addEntity(new Barbute(m));
            helmetRoom.addEntity(new GreatHelm(m));
        }

        for(Material m : Material.getMaterials(3)){
            helmetRoom.addEntity(new Helmet(m));
        }



        addRoom(helmetRoom);



        Room weaponRoom = new Room("Weaponry", "A room full of weapons");
        for(Material m : Material.getMaterials(1)){
            weaponRoom.addEntity(new Knife(m));
            weaponRoom.addEntity(new Spear(m));
            weaponRoom.addEntity(new Sword(m));
            weaponRoom.addEntity(new Mace(m));
            weaponRoom.addEntity(new Axe(m));
        }


        addRoom(weaponRoom);



        Room ladderRoom = new Room( "Dusty Room", "It's very dusty in here.");
        ladderRoom.addEntity(new Prop("Ladder", "a wooden ladder heading in the ceiling",
                "a wooden ladder", null, Material.WOOD));
        ladderRoom.setCustomDirection("You can see a ladder going up. You see an opening to the east.");

        ladderRoom.addEntity(new WarpStone());
        addRoom(ladderRoom);

        Room daggerRoom = new Room("Empty room", "The room is very barren.");
        Knife d = new Knife(Material.IRON);
        d.addBehaviour(new IronBehaviour());
        daggerRoom.addEntity(d);
        daggerRoom.addEntity(new IronHelmet());
        addRoom(daggerRoom);

        Room emonRoom = new Room("Attic", "You're in a wooden attic.");
        NPC emon = new NPC("Emon", "A handy man. Probably fixes small keys.",
                "handy man","Hi, I'm Emon. My job is fixing small keys. Just give me one and I'll fix it.");
        emon.addSubject("keys", new Subject("Yup, I fix small keys."));
        emon.addSubject("beer", new Subject("I love beer!"));
        emonRoom.addEntity(emon);
        addRoom(emonRoom);

        Room mushRoom = new Room("Mushy Room", "The air is very damp.");
        mushRoom.addItem(new Mushroom());

        addRoom(mushRoom);

        Room wolfRoom = new Room("Wolf Room", "You see some bones scattered on the ground.");
        wolfRoom.addEntity(new Prop("pile of bones", "upon further examination, those seem to be animal bones, probably rats and rabbit's."));
        wolfRoom.addEntity(new Wolf());
        wolfRoom.addEntity(new Snake());
        wolfRoom.addEntity(new Apple());

        addRoom(wolfRoom);

        Room shopRoom = new Room("Shop", "You see several items neatly disposed on a table");
        shopRoom.addEntity(new Prop("table", "You can see a knife and an apple on the table",
                null, null, Material.WOOD));
        shopRoom.addEntity(new Prop("knife", "A knife. Probably better not to take it."));
        shopRoom.addEntity(new Prop("apple", "A red apple. Probably better not to take it."));
        Shopkeeper bob = new Shopkeeper("Bob", "a fairly chubby man with a glint in his eyes.", "Hi, I'm Bob, a shop keeper. Are you interested in some of my items?");
        bob.simpleAddItem(new Knife(Material.IRON), 5);
        bob.simpleAddItem(new Apple(), 2);
        bob.simpleAddItem(new Scroll("mazesolution", "Parched scroll", "what seems like a fairly old scroll","Down, Right, Up, Right, Down", 0.1), 20);
        shopRoom.addEntity(bob);
        addRoom(shopRoom);

        Room sanctuary = new Room("Sanctuary", "There is a holy aura permeating this place.");
        NPC priest = new NPC("Asdolfo", "A holy man, hood up.", "hooded man", "Greetings, child. I can bless items for you. Should you be wounded, you can use this fountain to strengthen your spirits.");
        HolySword sword = new HolySword();
        sword.setType("holy");
        sword.setDamage(8);
        priest.addReactionItem("holysword", new Subject("Here you go, a magic sword!", sword, 1));
        sanctuary.addEntity(priest);
        sanctuary.addEntity(new HolyFountain());
        addRoom(sanctuary);

        Room cavernRoom = new Room( "Cavernous opening", "The tunnel suddenly opens up in this place.");

        addRoom(cavernRoom);

        Room mazeMain = new Room("Maze", "These walls all look the same, you feel very disorientated");
        addRoom(mazeMain);

        Room maze1 = new Room("Maze", "These walls all look the same, you feel very disorientated");
        addRoom(maze1);

        Room maze2 = new Room( "Maze", "These walls all look the same, you feel very disorientated");

        addRoom(maze2);

        Room maze3 = new Room( "Maze", "These walls all look the same, you feel very disorientated");
        //add corrosive slime
        addRoom(maze3);

        Room maze4 = new Room("Maze", "These walls all look the same, you feel very disorientated");

        addRoom(maze4);

        Room swordRoom = new Room("Lake in the mountain", "You suddenly find yourself at the coast of a lake. A little path leads you to a circle of stones, in which you see an exquisitely crafted sword.");
        swordRoom.addItem(new HolySword());
        swordRoom.addEntity(new Prop("lake", "A stunningly beautiful lake, very calming",
                "a calm lake", null, null));
        swordRoom.addEntity(new Prop("Circle of stones", "a circle made with roundish stones, around 5 m wide",
                "a circle of stones", null, Material.STONE));
        addRoom(swordRoom);

        Room miniBossRoom = new Room( "Skeleton Room", "Everything in this room is of a very white colour. Upon further examination, you realise it's because everything is made of bones. Human ones.");
        //add miniboss
        Spellbook spellbook = new Spellbook();
        spellbook.addSpell(new Fireball(), 5);
        spellbook.addSpell(new LightHeal(), 3);
        spellbook.addSpell(new MidasSpell(), 5);
        miniBossRoom.addEntity(sb, 1);
        addRoom(miniBossRoom);

        Room brokenKeyRoom = new Room("Hearth room", "This room is quite warm.");
        brokenKeyRoom.addEntity(new Prop("hearth", "the hearth is alit. somebody has been here recently", null, null, Material.STONE));
        brokenKeyRoom.addEntity(new Prop("table", "a fairly used wooden table.", null, null, Material.WOOD));
        brokenKeyRoom.addItem(new Key("brokenkey", "Broken key", "It seems this key has been broken clean in two."));
        addRoom(brokenKeyRoom);

        Room gateRoom = new Room("Hellish Gate", "The main feature of this room is a huge gate with even a bigger lock on it.");
        LockedDoor bossgate = new LockedDoor("Huge gate", "This gate has a huge lock on it.", "huge gate", Material.IRON, gateRoom, "east");
        Key bosskey = bossgate.generateKey("Shiny key");
        emon.addReactionItem("brokenkey", new Subject("Here's the fixed key for you", bosskey, 1));

        gateRoom.addEntity(bossgate);
        //        gateRoom.lockDirection("east", "bossgate");
        addRoom(gateRoom);

        Room bossRoom = new Room("Hell", "As soon as you enter this room, you're stunned by the amount of heat there is in this room. It feels as if the floor could melt.");
        bossRoom.setCustomDirection("You sense a magical barrier east.");
        bossRoom.lockDirection("east", "boss");
        bossRoom.addEntity(new Demon());
        addRoom(bossRoom);

        Room princessRoom = new Room("Princess room", "a royal room, full of decorations.");
        NPC peach = new NPC("Peach", "A princess in a pink dress is here", "pink princess", "Congratulations, you saved me!");
        peach.addSubject("mario", new Subject("Thank you Mario! but your princess is in another castle!"));
        //maybe exit
        princessRoom.addEntity(peach);
        addRoom(princessRoom);


        maze1.addExit("north", mazeMain);
        maze1.addExit("south", mazeMain);
        maze1.addExit("east", maze2);
        maze1.addExit("west", mazeMain);
        mazeMain.addExit("north", cavernRoom);
        mazeMain.addExit("south", maze1);
        mazeMain.addExit("east", mazeMain);
        mazeMain.addExit("west", mazeMain);
        cavernRoom.addExit("west", mushRoom);
        cavernRoom.addExit("north", miniBossRoom);
        cavernRoom.addExit("south", mazeMain);
        cavernRoom.addExit("east", brokenKeyRoom);
        sanctuary.addExit("up", wolfRoom);
        shopRoom.addExit("south", wolfRoom);
        wolfRoom.addExit("west", daggerRoom);
        wolfRoom.addExit("north", shopRoom);
        wolfRoom.addExit("down", sanctuary);
        mushRoom.addExit("north", startRoom);
        mushRoom.addExit("east", cavernRoom);
        emonRoom.addExit("down", ladderRoom);
        daggerRoom.addExit("south", startRoom);
        daggerRoom.addExit("east", wolfRoom);
        ladderRoom.addExit("up", emonRoom);
        ladderRoom.addExit("east", startRoom);
        weaponRoom.addExit("south", helmetRoom);
        weaponRoom.addExit("north", startRoom);
        helmetRoom.addExit("south", armorRoom);
        helmetRoom.addExit("north", weaponRoom);
        armorRoom.addExit("south", shieldRoom);
        armorRoom.addExit("north", helmetRoom);
        shieldRoom.addExit("south", startRoom);
        shieldRoom.addExit("north", armorRoom);
        lootRoom.addExit("southwest", startRoom);
        startRoom.addExit("north",daggerRoom);
        startRoom.addExit("south",mushRoom);
        startRoom.addExit("west",ladderRoom);
        startRoom.addExit("northwest", shieldRoom);
        startRoom.addExit("northeast", lootRoom);
        startRoom.addExit("up", attic);
        attic.addExit("down", startRoom);
        bossRoom.addExit("west", gateRoom);
        bossRoom.addExit("east", princessRoom);
        gateRoom.addExit("east", bossRoom);
        gateRoom.addExit("west", brokenKeyRoom);
        brokenKeyRoom.addExit("west", cavernRoom);
        brokenKeyRoom.addExit("east", gateRoom);
        miniBossRoom.addExit("south", cavernRoom);
        swordRoom.addExit("north", mazeMain);
        maze3.addExit("east", maze4);
        maze3.addExit("south", mazeMain);
        maze3.addExit("north", mazeMain);
        maze3.addExit("west", mazeMain);
        maze4.addExit("east", mazeMain);
        maze4.addExit("south", swordRoom);
        maze4.addExit("north", mazeMain);
        maze4.addExit("west", mazeMain);
        maze2.addExit("north", maze3);
        maze2.addExit("south", mazeMain);
        maze2.addExit("east", mazeMain);
        maze2.addExit("west", mazeMain);
        
        setStartingRoom(startRoom);

    }
}
