package com.al0ne.AbstractEntities.Player;

import com.al0ne.AbstractEntities.Enums.Command;
import com.al0ne.AbstractEntities.NPC;
import com.al0ne.AbstractEntities.Pairs.Pair;
import com.al0ne.AbstractEntities.Pairs.PotentialItems;
import com.al0ne.AbstractEntities.Pairs.SpellPair;
import com.al0ne.AbstractEntities.Prop;
import com.al0ne.AbstractEntities.Quests.Quest;
import com.al0ne.AbstractEntities.Quests.TravelQuest;
import com.al0ne.AbstractEntities.Room;
import com.al0ne.AbstractEntities.Abstract.*;
import com.al0ne.Engine.TextParsing.HandleCommands;
import com.al0ne.Engine.Utility.Utility;
import com.al0ne.ConcreteEntities.Items.Types.Container;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.RangedWeapon;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Weapon;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.Books.Spellbook;
import com.al0ne.ConcreteEntities.Spells.*;

import java.util.ArrayList;
import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 11/07/2017.
 */
public class PlayerActions {


    //we give an item to an NPC, if the player has it
    public static boolean give(Player player, NPC npc, Entity item){
        if(player.hasItemInInventory(item.getID())){
            Pair pair = player.getItemPair(item.getID());

            return npc.isGiven((Item) pair.getEntity(), player);
        } else{
            printToLog("You don't have it.");
            return true;
        }
    }

    public static boolean reload(Player player, Weapon weapon){
        if(weapon instanceof RangedWeapon){
            RangedWeapon rweapon = (RangedWeapon) weapon;
            if(rweapon.needsReloading()){
                if(player.hasItemInInventory(rweapon.getAmmoID())){
                    Pair ammo = player.getItemPair(rweapon.getAmmoID());
                    int toReload = rweapon.getMagazineSize()-rweapon.getInMagazine();
                    if(ammo.getCount() >= toReload){
                        rweapon.fullReload();
                        player.removeXItem((Item) ammo.getEntity(), toReload);
                        printToLog("You reload your "+rweapon.getName()+".");
                        return true;
                    } else {
                        rweapon.setInMagazine(ammo.getCount()+rweapon.getInMagazine());
                        player.removeXItem((Item) ammo.getEntity(), ammo.getCount());
                        printToLog("You partially reload your "+rweapon.getName()+".");
                        return true;
                    }
                } else {
                    printToLog("You have run out of ammunition for your "+rweapon.getName());
                    return true;
                }
            } else{
                printToLog("The "+rweapon.getName()+" doesn't need reloading.");
                return false;
            }
        } else{
            printToLog("You can't reload the "+weapon.getName()+".");
            return false;
        }
    }


    //this function handles attacking an entity
    //if it is an enemy, we roll 1d100, we sum attack,
    //and we check if its bigger than the dodge roll
    //if it is, we roll for damage, subtract the armor
    //and inflict the damage (if the enemy isn't resistant
    // to the type of damage).
    //at this point, we make the enemy attack, if its still alive
    //and we snooze him this turn (all aggrod enemies in the room attack at EOT)
    public static boolean attack(Player player, WorldCharacter enemy){
        Room currentRoom = player.getCurrentRoom();
        String type;
        int armorPen;
        int condition = 100;
        Weapon weapon = player.getWeapon();
        boolean ranged = false;

        if(weapon==null){
            type="fists";
            armorPen=0;
        } else if(weapon instanceof RangedWeapon){
            RangedWeapon rweapon = (RangedWeapon) weapon;
            int inMagazine = rweapon.getInMagazine();
            type=weapon.getDamageType();
            condition = weapon.getIntegrity();
            armorPen=weapon.getArmorPenetration();
            ranged = true;

            if(rweapon.needsReloading() && inMagazine == 0){
                if(player.hasItemInInventory(rweapon.getAmmoID())){
                    printToLog("You need to reload your "+rweapon.getName());
                } else{
                    printToLog("You're out of ammunition.");
                }
                return true;
            } else if (!rweapon.needsReloading() && !player.hasItemInInventory(rweapon.getAmmoID())){
                printToLog("You're out of ammunition.");
                return true;
            }
        } else{
            type=weapon.getDamageType();
            condition = weapon.getIntegrity();
            armorPen=weapon.getArmorPenetration();
        }


        int attackRoll = Utility.randomNumber(100)+player.getAttack();
        int dodgeRoll = Utility.randomNumber(100)+enemy.getDexterity();

        int inflictedDamage = player.getDamage()-Math.max(enemy.getArmorLevel()-armorPen, 0);

        String nameToUse;
        if(enemy instanceof NPC){
            nameToUse = enemy.getName();
        } else {
            nameToUse = "the "+enemy.getName().toLowerCase();
        }

//            System.out.println("ATK: "+attackRoll+" vs ENEMY DEX: "+dodgeRoll);
        if(attackRoll > dodgeRoll){

            if (enemy.isWeakAgainst(type) && type.equals("fists")) {
                return handToHand(player, enemy);

            } else if(enemy.isWeakAgainst(type) && ranged){
                if(Utility.randomGreaterThan(condition)){
                    //weapon jammed, no shooting
                    printToLog("Your weapon malfunctions and you aren't able to shoot.");
                } else {
                    printToLog("You shoot and hit "+nameToUse+".");
                    if(!enemy.modifyHealth(-(inflictedDamage))){
                        handleWeaponWearing(weapon, player);
                        enemy.handleLoot(player);
                        currentRoom.getEntities().remove(enemy.getID());
                        return true;
                    }
                }
            } else if(enemy.isWeakAgainst(type)){
                //melee attack
                int damageAfterIntegrity = Math.round((inflictedDamage*condition)/100);

                if(inflictedDamage <= 0){
                    printToLog("Your attack doesn't hurt "+nameToUse+".");
                } else if( damageAfterIntegrity <= 0 ){
                    printToLog("Your weapon is too worn to hurt "+nameToUse+".");
                } else {
                    printToLog("You attack and hit "+nameToUse+".");
                    if(!enemy.modifyHealth(-(damageAfterIntegrity))){
                        enemy.handleLoot(player);
                        handleWeaponWearing(weapon, player);
                        currentRoom.getEntities().remove(enemy.getID());
                        return true;
                    }
                }

            } else{
                printToLog("Your attack does not seem to affect "+nameToUse);
            }

            System.out.println(enemy.getName()+" HP: "+enemy.getCurrentHealth()+" damage: "+inflictedDamage);


        } else{
            if(weapon instanceof RangedWeapon){
                printToLog("You shoot, but "+nameToUse+"."+" dodges.");
                handleWeaponWearing(weapon, player);
            } else {
                printToLog("You attack, but "+nameToUse+"."+" dodges.");
            }
        }

        enemy.isAttacked(player, currentRoom);
        enemy.setSnooze(true);

        return true;
    }

    private static void handleWeaponWearing(Weapon weapon, Player player){
        if(weapon == null) return;

        boolean isWorn = Utility.randomGreaterThan(70);

        if( weapon instanceof RangedWeapon && ((RangedWeapon) weapon).needsReloading() ){
            ((RangedWeapon) weapon).shoot();
            if(isWorn){
                weapon.modifyIntegrity(-1);
            }
        } else if(weapon instanceof RangedWeapon && player.hasItemInInventory(((RangedWeapon) weapon).getAmmoID())){
            Item ammo = (Item) player.getItemPair(((RangedWeapon) weapon).getAmmoID()).getEntity();
            player.removeOneItem(ammo);
            if(isWorn){
                weapon.modifyIntegrity(-1);
            }
        } else {
            if(isWorn){
                weapon.modifyIntegrity(-2);
            }
        }
    }



    private static boolean handToHand(Player player, WorldCharacter enemy){
        Room currentRoom = player.getCurrentRoom();
        int inflictedDamage = player.getDamage() - Math.max(enemy.getArmorLevel(), 0);

        String nameToUse;
        if(enemy instanceof NPC){
            nameToUse = enemy.getName();
        } else {
            nameToUse = "the "+enemy.getName().toLowerCase();
        }

        System.out.println(enemy.getName()+" HP: "+enemy.getCurrentHealth()+" damage: "+inflictedDamage);
        if(inflictedDamage <= 0){
            printToLog("Your punch bounces against"+ nameToUse+"'s armor.");
            return false;
        } else{
            boolean attackResult = enemy.modifyHealth(-inflictedDamage);
            if(!attackResult){
                //enemy is dead
                enemy.handleLoot(player);
                currentRoom.getEntities().remove(enemy.getID());
                return true;
            } else {
                //enemy is alive
                printToLog("You punch and hit "+ nameToUse+".");
                return true;
            }
        }
    }



    //this function handles custom action entities
    //atm supports props and items
    public static boolean customAction(Player player, Command action, Entity entity){

        Room currentRoom = player.getCurrentRoom();
        if(entity.getType()=='i'){
            boolean inRoom = false;
            Pair pair = player.getInventory().get(entity.getID());
            if (pair == null){
                inRoom = true;
                pair = currentRoom.getEntityPair(entity.getID());
            }
            if (pair == null){
                return false;
            }
            Item item = (Item) pair.getEntity();

            if (item == null){
                return false;
            }

            for (Command command : entity.getRequiredCommand()){
                if (command.equals(action)){
                    boolean result = item.used(player);
                    if(result){
                        if (item.hasProperty("consumable")){

                            if(!inRoom && !player.getItemPair(entity.getID()).modifyCount(-1)){
                                player.getInventory().remove(entity.getID());
                                player.recalculateWeight();
                            } else {
                                if(!pair.modifyCount(-1)){
                                    currentRoom.getEntities().remove(item.getID());
                                }
                            }
                        }
                        return true;
                    }
                    return false;
                }
            }

        } else if (entity.getType() == 'p'){
            Prop prop = (Prop) entity;
            for (Command command : prop.getRequiredCommand()){
                if(command.equals(action)){
                    return prop.used(player);
                }
            }
        }
        return false;
    }


    //this function handles talking with an NPC
    public static boolean talkToNPC(Player player, String npcName, String subject){
        Room currentRoom = player.getCurrentRoom();
        NPC npc = currentRoom.getNPC(npcName);
        return npc != null && npc.talkAbout(subject, player);
    }

    //this function makes the player drop X amount of target, if it has it
    public static int drop(Player player, Pair target, Integer amt){
        Room currentRoom = player.getCurrentRoom();
        if (target != null){
            //trying to drop more than there are, abort
            if(target.getCount() < amt){
                printToLog("You have only "+target.getCount()+" of those.");
                return 2;
            }

            Pair roomItem = currentRoom.getEntityPair(target.getEntity().getID());
            //the item is undroppable
            if(!((Item) target.getEntity()).canDrop() ){
                printToLog("You can't drop it.");
                return 2;
            }

            //we handle adding items to the room
            if (roomItem != null){
                roomItem.setCount(roomItem.getCount()+amt);
            } else {
                currentRoom.addItem((Item) target.getEntity(), amt);
            }

            //we modify the player's weight accordingly, and remove the item if necessary
            player.modifyWeight(-(((Item) target.getEntity()).getWeight()*amt));
            target.modifyCount(-amt);
            if (target.getCount() == 0){
                player.getInventory().remove(target.getEntity().getID());
            }
            //we unequip the item if it was equipped
            if (target.getEntity().getType()=='w' &&  player.isWearingItem(target.getEntity().getID())){
                player.unequipItem(target.getEntity().getID());
            }
            return 1;
        } else {
            //the item is not in the inventory
            return 0;
        }
    }



    //this function tries to pick up an amount from Item
    public static int pickUpItem(Player player, Pair item, Integer amt){

        Room currentRoom = player.getCurrentRoom();

        if(player.hasItemInInventory(item.getEntity().getID()) && ((Item)item.getEntity()).isUnique()){
            printToLog("You can have just one with you.");
            //bypassed by taking chest with that in it
            return 0;
        }
        if(item.getCount() < amt){
            printToLog("There are just "+item.getCount()+" of those.");
            return 0;
        }

        if(!((Interactable)item.getEntity()).canTake()){
            printToLog("You can't take it.");
            return 0;
        }

        Item toAdd = (Item) item.getEntity();
        if (!player.addAmountItem(item, amt)){
            printToLog("Too heavy to carry.");
            return 0;
        } else {
            if (item.isEmpty()){
                currentRoom.getEntities().remove(toAdd.getID());
            }
        }
        return 1;
    }

    //this function tries to take an item from a container
    public static boolean takeFrom(Player player, Pair item, Container container, int amount){

        if(container.isLocked()){
            printToLog("It's locked.");
            return false;
        }

        Item toAdd = (Item) item.getEntity();
        int currentCounter = item.getCount();
        if (!player.addAmountItem(item, amount)){
            printToLog("Too heavy to carry.");
            return false;
        } else {
            currentCounter-=item.getCount();
            container.modifySize(-currentCounter*toAdd.getSize());
            if (item.isEmpty()){
                container.removeItem(item);
            }
        }
        return true;
    }

    //this function puts an x items into a container, if the space is enough

    public static boolean putIn(Player player, Pair item, Container container, int amount){

        if(container.isLocked()){
            printToLog("It's locked.");
            return false;
        }

        int count = item.getCount();
        if (!container.putIn(item, amount)){
            printToLog("Not enough space in it.");
            return false;
        } else {
            player.modifyWeight(-count*((Item)item.getEntity()).getWeight());
            if (item.isEmpty()){
                player.getInventory().remove(item.getEntity().getID());
            }
        }
        return true;
    }



    //this makes the player use an item (simple use, not with an item/prop)
    //returns 0 if the item can't be used
    //returns 1 if the item was used successfully
    //returns 2 if the item was used successfully and it doesn't need to print anything
    public static boolean simpleUse(Player player, Entity target){

        if (target.getType() == 'p'){
            Prop prop = (Prop) target;
            return prop.used(player);

        } else if (target.getType() == 'i'){
            Pair pair = player.getInventory().get(target.getID());
            Item item = (Item) pair.getEntity();
            boolean result = item.used(player);
            if(result){
                //if the item is consumable, we subtract 1 from the inventory
                if (item.hasProperty("consumable")){
                    if(!player.getItemPair(item.getID()).modifyCount(-1)){
                        player.getInventory().remove(item.getID());
                    }
                }
                return true;
            }
        }
        return false;
    }


    //this function makes the player use item on target, item is an inventory Item, target is a Prop/Item
    //one can't use an enemy or an NPC

    public static void interactOnWith(Player player, Entity target, Entity item){
        if (item.getType() == 'e'){
            return;
        } else if(target.getType() == 'n'){
            PlayerActions.give(player, (NPC) target, item);
            return;
        }
        if(target.getType() == 'p' && item.getType() == 'p'){
            printToLog("At least one of the items must be from your inventory.");
            return;
        }
        System.out.println("Using "+item.getName()+"(i) with "+ target.getName());


        ((Interactable) item).usedWith((Interactable) target, player);
    }

    //this function handles examining an entity
    public static void examine(Player player, Entity target){
        if(target != null){
            target.printLongDescription(player);
        }
    }



    //this function checks if the direction selected is accessible from the
    //currentRoom and if it's not locked by a door, if so it moves there
    public static boolean moveToRoom(Player player, String direction, HashMap<String, Room> rooms){

        Room currentRoom = player.getCurrentRoom();
        if(currentRoom.isLocked(direction)){
            printToLog("The way "+direction+" is blocked.");
            return false;
        }
        //iterate over all directions of currentRoom, eg. north
        for (String s : currentRoom.getExits().keySet()){
            //check them with the given direction
            if (s.equals(direction)){ //north == north
                printToLog("You move "+direction);

                //get the next room's ID
                String nextRoomId = currentRoom.getExits().get(s);

                //set next room
                player.setCurrentRoom(rooms.get(nextRoomId));

                for(Quest q : player.getQuests().values()){
                    if(q instanceof TravelQuest){
                        q.checkCompletion(player);
                    }
                }

                return true;
            }
        }
        printToLog("You can't figure out how to go " + direction);
        return false;
    }


    public static void castSpell(Player player, String spellName){
        Room currentRoom = player.getCurrentRoom();
        if (player.hasItemInInventory("spellbook")) {
            Spellbook spellbook = (Spellbook) player.getItemPair("spellbook").getEntity();

            ArrayList<Spell> potentialSpells = HandleCommands.getPotentialSpell(spellName, spellbook);

            if(potentialSpells.size() > 1){
                printToLog("Be more specific.");
                return;
            }else if (potentialSpells.size() == 0){
                printToLog("Your spellbook doesn't seem to have the spell "+spellName);
                return;
            }

            if (spellbook.hasSpell(potentialSpells.get(0).getID())) {
                SpellPair spell = spellbook.getSpell(potentialSpells.get(0).getID());


                if(spell.getCount() <= 0){
                    printToLog("You don't have any castings left.");
                    return;
                }
                Spell s = spell.getSpell();
                char castOn = s.getTarget();

                switch (castOn) {
                    case 's':
                        SelfSpell ss = (SelfSpell) s;
                        if(ss.isCasted(player)){
                            spell.modifyCount(-1);
                            return;
                        }
                        return;
                    case 'w':
                        WorldSpell ws = (WorldSpell) s;
                        if(ws.isCasted(player, currentRoom)){
                            spell.modifyCount(-1);
                        }
                }
                //TODO: TO REVISE this
            } else {
                printToLog("Your spellbook doesn't seem to have such a spell.");
            }
        } else{
            printToLog("You need a spellbook to cast spells.");
        }
    }

    public static void castAtTarget(Player player, String spellName, String target){

        if (player.hasItemInInventory("spellbook")) {

            Spellbook spellbook = (Spellbook) player.getItemPair("spellbook").getEntity();


            ArrayList<Spell> potentialSpells = HandleCommands.getPotentialSpell(spellName, spellbook);

            if(potentialSpells.size() > 1){
                printToLog("Be more specific.");
                return;
            } else if (potentialSpells.size() == 0){
                printToLog("Your spellbook doesn't seem to have such a spell.");
                return;
            }


            if (spellbook.hasSpell(potentialSpells.get(0).getID())) {

                SpellPair spell = spellbook.getSpell(potentialSpells.get(0).getID());

                if(spell.getCount() <= 0){
                    printToLog("You don't have any castings left.");
                    return;
                }
                Spell s = spell.getSpell();
                char castOn = s.getTarget();

                switch (castOn) {
                    case 'w':
                    case 'i':
                        PotentialItems inventoryItems = HandleCommands.getPotentialItem(target, player, 0);
                        ArrayList<Pair> possibleItemsFromInventory = inventoryItems.getItems();
                        PotentialItems items = HandleCommands.getPotentialItem(target, player, 1);
                        ArrayList<Pair> possibleItems = items.getItems();

                        if(possibleItems.size() + possibleItemsFromInventory.size() > 1){
                            printToLog("Be more specific.");
                            return;
                        } else if(possibleItems.size() != 0){
                            printToLog("You need to be holding that item.");
                            return;
                        } else if (possibleItemsFromInventory.size() == 0){
                            printToLog("You can't see a "+target);
                            return;
                        }

                        TargetSpell ts = (TargetSpell) s;

                        if (ts.isCasted(player, possibleItemsFromInventory.get(0).getEntity())) {
                            spell.modifyCount(-1);
                            return;
                        }
                        return;


                    case 'e':
                        DamagingSpell ds = (DamagingSpell) s;
                        ArrayList<Enemy> enemies = HandleCommands.getPotentialEnemy(target, player);
                        if(enemies.size() == 0){
                            printToLog("You can't see that enemy");
                        } else if(enemies.size()>1){
                            printToLog("Be more specific");
                        } else {
                            if (ds.isCasted(player, enemies.get(0))) {
                                spell.modifyCount(-1);
                            }
                        }
                }

            } else {
                printToLog("Your spellbook doesn't seem to have such a spell.");
            }
        } else{
            printToLog("You need a spellbook to cast spells.");
        }
    }


}
