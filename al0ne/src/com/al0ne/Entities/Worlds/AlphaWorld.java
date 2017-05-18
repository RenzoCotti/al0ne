package com.al0ne.Entities.Worlds;

import com.al0ne.Behaviours.*;
import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Pairs.Subject;
import com.al0ne.Entities.Enemies.Snake;
import com.al0ne.Entities.Items.Behaviours.Container;
import com.al0ne.Entities.Items.Behaviours.Wearable.*;
import com.al0ne.Entities.Items.ConcreteItems.Armor.*;
import com.al0ne.Entities.Items.ConcreteItems.Books.Scroll;
import com.al0ne.Entities.Items.ConcreteItems.Books.Spellbook;
import com.al0ne.Entities.Items.ConcreteItems.Coin.SilverCoin;
import com.al0ne.Entities.Items.ConcreteItems.Helmet.Barbute;
import com.al0ne.Entities.Items.ConcreteItems.Helmet.GreatHelm;
import com.al0ne.Entities.Items.ConcreteItems.Helmet.IronHelmet;
import com.al0ne.Entities.Items.ConcreteItems.Helmet.Sallet;
import com.al0ne.Entities.Items.ConcreteItems.Shield.*;
import com.al0ne.Entities.Items.ConcreteItems.Weapon.*;
import com.al0ne.Entities.Spells.ConcreteSpells.Fireball;
import com.al0ne.Entities.Spells.ConcreteSpells.LightHeal;
import com.al0ne.Entities.Enemies.Demon;
import com.al0ne.Entities.Items.ConcreteItems.*;
import com.al0ne.Entities.Items.ConcreteItems.Food.Apple;
import com.al0ne.Entities.Items.ConcreteItems.Food.Mushroom;
import com.al0ne.Entities.Items.Props.HolyFountain;
import com.al0ne.Entities.Items.Props.LockedDoor;
import com.al0ne.Entities.NPCs.Shopkeeper;
import com.al0ne.Entities.Enemies.Wolf;
import com.al0ne.Entities.Spells.MidasSpell;

/**
 * Created by BMW on 14/03/2017.
 */
public class AlphaWorld extends World{

    public AlphaWorld() {
        super("alpha");

        Room startRoom = new Room("Generic Room", "You are in a pretty generic-looking cave. It feels pretty damp.");

        Container chest = new Chest();
        chest.addItem(new SilverCoin(), 100);
        chest.addItem(new Dagger(Material.IRON), 1);
        startRoom.addEntity(chest);
        startRoom.addEntity(new Snake());
        putRoom(startRoom);

        Room lootRoom = new Room("Looty room", "A room full of loot! yay!");

        for(Pair p: lootTable.getLoot(123)){
            lootRoom.addEntity(p.getEntity(), p.getCount());
        }
        putRoom(lootRoom);


        Room shieldRoom = new Room("Shieldery", "A room full of shields");



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







        putRoom(shieldRoom);

        Room armorRoom = new Room("Armory", "A room full of armor");



        for(Material m : Material.getMaterials(0)){
            armorRoom.addEntity(new Armor(m));
        }

        for(Material m : Material.getMetals()){
            armorRoom.addEntity(new ChainMail(m));
            armorRoom.addEntity(new PlateArmor(m));
            armorRoom.addEntity(new ScaleArmor(m));
        }






        putRoom(armorRoom);

        Room helmetRoom = new Room("Helmetry", "A room full of helmets");

        for(Material m : Material.getMetals()){
            helmetRoom.addEntity(new Sallet(m));
            helmetRoom.addEntity(new Barbute(m));
            helmetRoom.addEntity(new GreatHelm(m));
        }

        for(Material m : Material.getMaterials(3)){
            helmetRoom.addEntity(new Helmet(m));
        }



        putRoom(helmetRoom);



        Room weaponRoom = new Room("Weaponry", "A room full of weapons");
        for(Material m : Material.getMaterials(1)){
            weaponRoom.addEntity(new Dagger(m));
            weaponRoom.addEntity(new Spear(m));
            weaponRoom.addEntity(new Sword(m));
            weaponRoom.addEntity(new Mace(m));
            weaponRoom.addEntity(new Axe(m));
        }


        putRoom(weaponRoom);



        Room ladderRoom = new Room( "Dusty Room", "It's very dusty in here.");
        ladderRoom.addEntity(new Prop("Ladder", "a wooden ladder heading in the ceiling", "a wooden ladder"));
        ladderRoom.addCustomDirection("You can see a ladder going up. You see an opening to the east.");

        ladderRoom.addEntity(new WarpStone());
        putRoom(ladderRoom);

        Room daggerRoom = new Room("Empty room", "The room is very barren.");
        daggerRoom.addEntity(new Dagger(Material.IRON));
        daggerRoom.addEntity(new IronHelmet());
        putRoom(daggerRoom);

        Room emonRoom = new Room("Attic", "You're in a wooden attic.");
        NPC emon = new NPC("Emon", "A handy man. Probably fixes small keys.",
                "handy man","Hi, I'm Emon. My job is fixing small keys. Just give me one and I'll fix it.");
        emon.addSubject("keys", new Subject("Yup, I fix small keys."));
        emon.addSubject("beer", new Subject("I love beer!"));
        emon.addReactionItem("brokenkey", new Key("bosskey", "Big key", "A key, the biggest, let me tell you."));
        emonRoom.addEntity(emon);
        putRoom(emonRoom);

        Room mushRoom = new Room("Mushy Room", "The air is very damp.");
        mushRoom.addItem(new Mushroom());

        putRoom(mushRoom);

        Room wolfRoom = new Room("Wolf Room", "You see some bones scattered on the ground.");
        wolfRoom.addEntity(new Prop("bones", "upon further examination, those seem to be animal bones, probably rats and rabbit's.", "a massive pile of bones"));
        wolfRoom.addEntity(new Wolf());
        wolfRoom.addEntity(new Snake());
        wolfRoom.addEntity(new Apple());

        putRoom(wolfRoom);

        Room shopRoom = new Room("Shop", "You see several items neatly disposed on a table");
        shopRoom.addEntity(new Prop("table", "You can see a knife and an apple on the table", "a wooden table"));
        shopRoom.addEntity(new Prop("knife", "A knife. Probably better not to take it.", "a knife"));
        shopRoom.addEntity(new Prop("apple", "A red apple.Probably better not to take it.", "an apple"));
        Shopkeeper bob = new Shopkeeper("Bob", "a fairly chubby man with a glint in his eyes.", "a clever looking man", "Hi, I'm Bob, a shop keeper. Are you interested in some of my items?");
        bob.simpleAddItem(new Dagger(Material.IRON), 5);
        bob.simpleAddItem(new Apple(), 2);
        bob.simpleAddItem(new Scroll("mazesolution", "Parched scroll", "what seems like a fairly old scroll","Down, Right, Up, Right, Down", 0.1), 20);
        shopRoom.addEntity(bob);
        putRoom(shopRoom);

        Room sanctuary = new Room("Sanctuary", "There is a holy aura permeating this place.");
        NPC priest = new NPC("Asdolfo", "A holy man, hood up.", "hooded man", "Greetings, child. I can bless items for you. Should you be wounded, you can use this fountain to strengthen your spirits.");
        HolySword sword = new HolySword();
        sword.setType("holy");
        sword.setDamage(8);
        priest.addReactionItem("holysword", sword);
        sanctuary.addEntity(priest);
        sanctuary.addEntity(new HolyFountain());
        putRoom(sanctuary);

        Room cavernRoom = new Room( "Cavernous opening", "The tunnel suddenly opens up in this place.");

        putRoom(cavernRoom);

        Room mazeMain = new Room("Maze", "These walls all look the same, you feel very disorientated");
        putRoom(mazeMain);

        Room maze1 = new Room("Maze", "These walls all look the same, you feel very disorientated");
        putRoom(maze1);

        Room maze2 = new Room( "Maze", "These walls all look the same, you feel very disorientated");

        putRoom(maze2);

        Room maze3 = new Room( "Maze", "These walls all look the same, you feel very disorientated");
        //add corrosive slime
        putRoom(maze3);

        Room maze4 = new Room("Maze", "These walls all look the same, you feel very disorientated");

        putRoom(maze4);

        Room swordRoom = new Room("Lake in the mountain", "You suddenly find yourself at the coast of a lake. A little path leads you to a circle of stones, in which you see an exquisitely crafted sword.");
        swordRoom.addItem(new HolySword());
        swordRoom.addEntity(new Prop("lake", "A stunningly beautiful lake, very calming", "a calm lake"));
        swordRoom.addEntity(new Prop("Circle of stones", "a circle made with roundish stones, around 5 m wide", "a circle of stones"));
        putRoom(swordRoom);

        Room miniBossRoom = new Room( "Skeleton Room", "Everything in this room is of a very white colour. Upon further examination, you realise it's because everything is made of bones. Human ones.");
        //add miniboss
        Spellbook sb = new Spellbook();
        sb.addSpell(new Fireball(), 5);
        sb.addSpell(new LightHeal(), 3);
        sb.addSpell(new MidasSpell(), 5);
        miniBossRoom.addEntity(sb, 1);
        putRoom(miniBossRoom);

        Room brokenKeyRoom = new Room("Hearth room", "This room is quite warm.");
        brokenKeyRoom.addEntity(new Prop("hearth", "the hearth is alit. somebody has been here recently", "a stone hearth"));
        brokenKeyRoom.addEntity(new Prop("table", "a wooden table. It has a broken key on top.", "a wooden table"));
        brokenKeyRoom.addItem(new Key("brokenkey", "Broken key", "It seems this key has been broken clean in two."));
        putRoom(brokenKeyRoom);

        Room gateRoom = new Room("Hellish Gate", "The main feature of this room is a huge gate with even a bigger lock on it.");
        gateRoom.addEntity(new LockedDoor("bossgate", "Huge gate", "This gate has a huge lock on it.", "huge gate", Material.IRON,"bosskey"));
        gateRoom.lockDirection("east", "bossgate");
        putRoom(gateRoom);

        Room bossRoom = new Room("Hell", "As soon as you enter this room, you're stunned by the amount of heat there is in this room. It feels as if the floor could melt.");
        bossRoom.addCustomDirection("You sense a magical barrier east.");
        bossRoom.addEntity(new LeatherArmour());
        bossRoom.lockDirection("east", "boss");
        bossRoom.addEntity(new Demon());
        putRoom(bossRoom);

        Room princessRoom = new Room("Princess room", "a royal room, full of decorations.");
        NPC peach = new NPC("Peach", "A princess in a pink dress is here", "pink princess", "Congratulations, you saved me!");
        peach.addSubject("mario", new Subject("Thank you Mario! but your princess is in another castle!"));
        //maybe exit
        princessRoom.addEntity(peach);
        putRoom(princessRoom);


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

        Player p = new Player(true, 20, getStartingRoom(), "You are alpha. And omega. Maybe.");

        setPlayer(p);
    }
}
