package com.al0ne.Entities.Worlds;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Pairs.Subject;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Enemies.Snake;
import com.al0ne.Entities.Items.Behaviours.Container;
import com.al0ne.Entities.Items.Behaviours.Wearable.Armor;
import com.al0ne.Entities.Items.Behaviours.Wearable.Helmet;
import com.al0ne.Entities.Items.Behaviours.Wearable.Shield;
import com.al0ne.Entities.Items.ConcreteItems.Coin.SilverCoin;
import com.al0ne.Entities.Spells.ConcreteSpells.Fireball;
import com.al0ne.Entities.Spells.ConcreteSpells.LightHeal;
import com.al0ne.Entities.Enemies.Demon;
import com.al0ne.Entities.Items.ConcreteItems.*;
import com.al0ne.Entities.Items.ConcreteItems.Armor.IronHelmet;
import com.al0ne.Entities.Items.ConcreteItems.Armor.LeatherArmour;
import com.al0ne.Entities.Items.ConcreteItems.Food.Apple;
import com.al0ne.Entities.Items.ConcreteItems.Food.Mushroom;
import com.al0ne.Entities.Items.ConcreteItems.Weapon.HolySword;
import com.al0ne.Entities.Items.ConcreteItems.Weapon.Knife;
import com.al0ne.Behaviours.Prop;
import com.al0ne.Behaviours.NPC;
import com.al0ne.Entities.Items.Props.HolyFountain;
import com.al0ne.Entities.Items.Props.LockedDoor;
import com.al0ne.Behaviours.Room;
import com.al0ne.Entities.NPCs.Shopkeeper;
import com.al0ne.Entities.Enemies.Wolf;
import com.al0ne.Behaviours.World;
import com.al0ne.Entities.Spells.MidasSpell;

/**
 * Created by BMW on 14/03/2017.
 */
public class AlphaWorld extends World{

    public AlphaWorld() {
        super("alpha", "startroom");

        Room startRoom = new Room("startroom", "Generic Room", "You are in a pretty generic-looking cave. It feels pretty damp.");
        startRoom.addExit("north","daggerroom");
        startRoom.addExit("south","mushroomroom");
        startRoom.addExit("west","ladderroom");
//        Container chest = new Chest();
//        chest.addItem(new SilverCoin(), 100);
//        chest.addItem(new Knife(), 1);
//        startRoom.addEntity(chest);
//        startRoom.addEntity(new WarpStone());
//        startRoom.addEntity(new SilverCoin(), 100);
        startRoom.addEntity(new Snake());
//        for(Material m : Material.getMaterials(true)){
//            startRoom.addEntity(new Helmet(m));
//        }
//
//        for(Material m : Material.getMaterials(true)){
//            startRoom.addEntity(new Armor(m));
//        }
//
//        for(Material m : Material.getMaterials(true)){
//            startRoom.addEntity(new Shield(m));
//        }
        startRoom.visit();
        putRoom(startRoom);

        Room ladderRoom = new Room("ladderroom", "Dusty Room", "It's very dusty in here.");
        ladderRoom.addEntity(new Prop("Ladder", "a wooden ladder heading in the ceiling", "a wooden ladder"));
        ladderRoom.addCustomDirection("You can see a ladder going up. You see an opening to the east.");
        ladderRoom.addExit("up", "emonroom");
        ladderRoom.addExit("east", "startroom");
        putRoom(ladderRoom);

        Room daggerRoom = new Room("daggerroom", "Empty room", "The room is very barren.");
        daggerRoom.addEntity(new Knife());
        daggerRoom.addExit("south", "startroom");
        daggerRoom.addExit("east", "wolfroom");
        daggerRoom.addEntity(new IronHelmet());
        putRoom(daggerRoom);

        Room emonRoom = new Room("emonroom", "Attic", "You're in a wooden attic.");
        NPC emon = new NPC("emon", "Emon", "A handy man. Probably fixes small keys.","handy man","Hi, I'm Emon. My job is fixing small keys. Just give me one and I'll fix it.");
        emonRoom.addExit("down", "ladderroom");
        emon.addSubject("keys", new Subject("Yup, I fix small keys."));
        emon.addSubject("beer", new Subject("I love beer!"));
        emon.addReactionItem("brokenkey", new Key("bosskey", "Big key", "A key, the biggest, let me tell you."));
        emonRoom.addEntity(emon);
        putRoom(emonRoom);

        Room mushRoom = new Room("mushroomroom", "Mushy Room", "The air is very damp.");
        mushRoom.addItem(new Mushroom());
        mushRoom.addExit("north", "startroom");
        mushRoom.addExit("east", "cavernroom");
        putRoom(mushRoom);

        Room wolfRoom = new Room("wolfroom", "Wolf Room", "You see some bones scattered on the ground.");
        wolfRoom.addEntity(new Prop("bones", "upon further examination, those seem to be animal bones, probably rats and rabbit's.", "a massive pile of bones"));
        wolfRoom.addEntity(new Wolf());
        wolfRoom.addEntity(new Snake());
        wolfRoom.addEntity(new Apple());
        wolfRoom.addExit("west", "daggerroom");
        wolfRoom.addExit("north", "shoproom");
        wolfRoom.addExit("down", "sanctuary");
        putRoom(wolfRoom);

        Room shopRoom = new Room("shoproom", "Shop", "You see several items neatly disposed on a table");
        shopRoom.addEntity(new Prop("table", "You can see a knife and an apple on the table", "a wooden table"));
        shopRoom.addEntity(new Prop("knife", "A knife. Probably better not to take it.", "a knife"));
        shopRoom.addEntity(new Prop("apple", "A red apple.Probably better not to take it.", "an apple"));
        Shopkeeper bob = new Shopkeeper("shopkeeper", "Bob", "a fairly chubby man with a glint in his eyes.", "a clever looking man", "Hi, I'm Bob, a shop keeper. Are you interested in some of my items?");
        bob.addToInventory(new Knife(), 5);
        bob.addToInventory(new Apple(), 2);
        bob.addToInventory(new Scroll("mazesolution", "Parched scroll", "what seems like a fairly old scroll","Down, Right, Up, Right, Down", 0.1), 20);
        shopRoom.addEntity(bob);
        shopRoom.addExit("south", "wolfroom");
        putRoom(shopRoom);

        Room sanctuary = new Room("sanctuary", "Sanctuary", "There is a holy aura permeating this place.");
        NPC priest = new NPC("priest", "Asdolfo", "A holy man, hood up.", "hooded man", "Greetings, child. I can bless items for you. Should you be wounded, you can use this fountain to strengthen your spirits.");
        HolySword sword = new HolySword();
        sword.setType("holy");
        sword.setDamage(8);
        priest.addReactionItem("holysword", sword);
        sanctuary.addEntity(priest);
        sanctuary.addExit("up", "wolfroom");
        sanctuary.addEntity(new HolyFountain());
        putRoom(sanctuary);

        Room cavernRoom = new Room("cavernroom", "Cavernous opening", "The tunnel suddenly opens up in this place.");
        cavernRoom.addExit("west", "mushroomroom");
        cavernRoom.addExit("north", "minibossroom");
        cavernRoom.addExit("south", "mazemain");
        cavernRoom.addExit("east", "brokenkeyroom");
        putRoom(cavernRoom);

        Room mazeMain = new Room("mazemain", "Maze", "These walls all look the same, you feel very disorientated");
        mazeMain.addExit("north", "cavernroom");
        mazeMain.addExit("south", "maze1");
        mazeMain.addExit("east", "mazemain");
        mazeMain.addExit("west", "mazemain");
        putRoom(mazeMain);

        Room maze1 = new Room("maze1", "Maze", "These walls all look the same, you feel very disorientated");
        maze1.addExit("north", "mazemain");
        maze1.addExit("south", "mazemain");
        maze1.addExit("east", "maze2");
        maze1.addExit("west", "mazemain");
        putRoom(maze1);

        Room maze2 = new Room("maze2", "Maze", "These walls all look the same, you feel very disorientated");
        maze2.addExit("north", "maze3");
        maze2.addExit("south", "mazemain");
        maze2.addExit("east", "mazemain");
        maze2.addExit("west", "mazemain");
        putRoom(maze2);

        Room maze3 = new Room("maze3", "Maze", "These walls all look the same, you feel very disorientated");
        maze3.addExit("east", "maze4");
        maze3.addExit("south", "mazemain");
        maze3.addExit("north", "mazemain");
        maze3.addExit("west", "mazemain");

        //add corrosive slime
        putRoom(maze3);

        Room maze4 = new Room("maze4", "Maze", "These walls all look the same, you feel very disorientated");
        maze4.addExit("east", "mazemain");
        maze4.addExit("south", "swordroom");
        maze4.addExit("north", "mazemain");
        maze4.addExit("west", "mazemain");
        putRoom(maze4);

        Room swordRoom = new Room("swordroom", "Lake in the mountain", "You suddenly find yourself at the coast of a lake. A little path leads you to a circle of stones, in which you see an exquisitely crafted sword.");
        swordRoom.addItem(new HolySword());
        swordRoom.addEntity(new Prop("lake", "A stunningly beautiful lake, very calming", "a calm lake"));
        swordRoom.addEntity(new Prop("Circle of stones", "a circle made with roundish stones, around 5 m wide", "a circle of stones"));
        swordRoom.addExit("north", "mazemain");
        putRoom(swordRoom);

        Room miniBossRoom = new Room("minibossroom", "Skeleton Room", "Everything in this room is of a very white colour. Upon further examination, you realise it's because everything is made of bones. Human ones.");
        //add miniboss
        miniBossRoom.addExit("south", "cavernroom");
        Spellbook sb = new Spellbook();
        sb.addSpell(new Fireball(), 5);
        sb.addSpell(new LightHeal(), 3);
        sb.addSpell(new MidasSpell(), 5);
        miniBossRoom.addEntity(sb, 1);
        putRoom(miniBossRoom);

        Room brokenKeyRoom = new Room("brokenkeyroom", "Hearth room", "This room is quite warm.");
        brokenKeyRoom.addExit("west", "cavernroom");
        brokenKeyRoom.addExit("east", "gateroom");
        brokenKeyRoom.addEntity(new Prop("hearth", "the hearth is alit. somebody has been here recently", "a stone hearth"));
        brokenKeyRoom.addEntity(new Prop("table", "a wooden table. It has a broken key on top.", "a wooden table"));
        brokenKeyRoom.addItem(new Key("brokenkey", "Broken key", "It seems this key has been broken clean in two."));
        putRoom(brokenKeyRoom);

        Room gateRoom = new Room("gateroom", "Hellish Gate", "The main feature of this room is a huge gate with even a bigger lock on it.");
        gateRoom.addExit("east", "bossroom");
        gateRoom.addExit("west", "brokenkeyroom");
        gateRoom.addEntity(new LockedDoor("bossgate", "Huge gate", "This gate has a huge lock on it.", "huge gate", "The gate is open now.","bosskey"));
        gateRoom.lockDirection("east", "bossgate");
        putRoom(gateRoom);

        Room bossRoom = new Room("bossroom", "Hell", "As soon as you enter this room, you're stunned by the amount of heat there is in this room. It feels as if the floor could melt.");
        bossRoom.addCustomDirection("You sense a magical barrier east.");
        bossRoom.addExit("west", "gateroom");
        bossRoom.addExit("east", "princessroom");
        bossRoom.addEntity(new LeatherArmour());
        bossRoom.lockDirection("east", "boss");
        bossRoom.addEntity(new Demon());
        putRoom(bossRoom);

        Room princessRoom = new Room("princessroom", "Princess room", "a royal room, full of decorations.");
        NPC peach = new NPC("princess", "Peach", "A princess in a pink dress is here", "pink princess", "Congratulations, you saved me!");
        peach.addSubject("mario", new Subject("Thank you Mario! but your princess is in another castle!"));
        //maybe exit
        princessRoom.addEntity(peach);
        putRoom(princessRoom);
    }
}
