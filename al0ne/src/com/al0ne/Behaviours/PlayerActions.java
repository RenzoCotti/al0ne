package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Enums.Command;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.abstractEntities.Enemy;
import com.al0ne.Behaviours.abstractEntities.Entity;
import com.al0ne.Behaviours.abstractEntities.Interactable;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Behaviours.Container;
import javafx.application.Platform;
import sun.jvm.hotspot.utilities.RobustOopDeterminator;

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

            if (npc.isGiven((Item) pair.getEntity(), player)){
                return true;
            } else{
                return false;
            }
        } else{
            printToLog("You don't have it.");
            return true;
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
    public static boolean attack(Player player, Entity name){
        Room currentRoom = player.getCurrentRoom();
        Pair p = currentRoom.getEntityPair(name.getID());
        Entity entity;
        if (p == null) {
            printToLog("You can't see "+name.getName()+".");
            return false;
        } else {
            entity = p.getEntity();
        }
        if(entity.getType() == 'n'){
            printToLog("It's best not to attack "+ name.getName()+".");
            return false;
        } else if (entity.getType() == 'e'){
            Enemy enemy = (Enemy) entity;
            String type;
            int armorPen;
            if(player.getWeapon()==null){
                type="fists";
                armorPen=0;
            } else{
                type=player.getWeapon().getDamageType();
                armorPen=player.getWeapon().getArmorPenetration();
            }


            int attackRoll = Utility.randomNumber(100)+player.getAttack();
            int dodgeRoll = Utility.randomNumber(100)+enemy.getDexterity();
//            System.out.println("ATK: "+attackRoll+" vs ENEMY DEX: "+dodgeRoll);
            if(attackRoll > dodgeRoll){

                if (enemy.isWeakAgainst(type) && type.equals("fists")) {
                    int inflictedDamage = player.getDamage() - Math.max(enemy.getArmorLevel()-armorPen, 0);
                    System.out.println(enemy.getName()+" HP: "+enemy.getCurrentHealth()+" damage: "+inflictedDamage);
                    if(inflictedDamage <= 0){
                        printToLog("Your punch bounces against the "+enemy.getName().toLowerCase()+"'s armor.");
                    } else{
                        boolean attackResult = enemy.modifyHealth(-inflictedDamage);
                        if(!attackResult){
                            enemy.handleLoot(currentRoom);
                            currentRoom.getEntities().remove(enemy.getID());
                            return true;
                        } else {
                            printToLog("You punch and hit the "+enemy.getName().toLowerCase()+".");
                        }
                    }

                } else if(enemy.isWeakAgainst(type) ){

                    int inflictedDamage = player.getDamage()-Math.max(enemy.getArmorLevel()-armorPen, 0);

                    if(inflictedDamage <= 0){
                        printToLog("Your attack doesn't hurt the "+enemy.getName().toLowerCase()+".");
                    } else{
                        printToLog("You attack and hit the "+enemy.getName().toLowerCase()+".");
                        System.out.println(enemy.getName()+" HP: "+enemy.getCurrentHealth()+" damage: "+inflictedDamage);
                        if(!enemy.modifyHealth(-(inflictedDamage))){
                            enemy.handleLoot(currentRoom);
                            currentRoom.getEntities().remove(enemy.getID());
                            return true;
                        }
                    }
                } else{
                    printToLog("The "+enemy.getName().toLowerCase()+" seem not to be affected by your attack.");
                }

                enemy.isAttacked(player, currentRoom);

                enemy.setSnooze(true);
                return true;
            } else{
                printToLog("You attack, but the "+enemy.getName().toLowerCase()+" dodges.");
                enemy.isAttacked(player, currentRoom);
                enemy.setSnooze(true);
                return true;
            }

        } else {
            printToLog("The "+p.getEntity().getName().toLowerCase() +" isn't threatening.");
        }
        return false;
    }



    //this function handles custom action entities
    //atm supports props and items
    public static int customAction(Player player, Command action, Entity entity){

        Room currentRoom = player.getCurrentRoom();
        if(entity.getType()=='i'){
            boolean inRoom = false;
            Pair pair = player.getInventory().get(entity.getID());
            if (pair == null){
                inRoom = true;
                pair = currentRoom.getEntityPair(entity.getID());
            }
            if (pair == null){
                return 0;
            }
            Item item = (Item) pair.getEntity();

            if (item == null){
                return 0;
            }

            for (Command command : entity.getRequiredCommand()){
                if (command.equals(action)){
                    int result = item.used(currentRoom, player);
                    if(result == 1 || result == 2){
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
                        return result;
                    }
                    return 0;
                }
            }

        } else if (entity.getType() == 'p'){
            Prop prop = (Prop) entity;
            for (Command command : prop.getRequiredCommand()){
                if(command.equals(action)){
                    return prop.used(currentRoom, player);
                }
            }
        }
        return 0;
    }


    //this function handles talking with an NPC
    public static boolean talkToNPC(Player player, String name, String subject){
        Room currentRoom = player.getCurrentRoom();
        NPC npc = currentRoom.getNPC(name);
        if (npc != null && npc.talkAbout(subject, player)){
            return true;
        }
        return false;
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
    public static int simpleUse(Player player, Entity target){

        Room currentRoom = player.getCurrentRoom();
        if (target.getType() == 'p'){
            Prop prop = (Prop) target;
            return prop.used(currentRoom, player);

        } else if (target.getType() == 'i'){
            Pair pair = player.getInventory().get(target.getID());
            Item item = (Item) pair.getEntity();
            int result = item.used(currentRoom, player);
            if(result == 1 || result == 2){
                //if the item is consumable, we subtract 1 from the inventory
                if (item.hasProperty("consumable")){
                    if(!player.getItemPair(item.getID()).modifyCount(-1)){
                        player.getInventory().remove(item.getID());
                    }
                }
                return result;
            }
        }
        return 0;
    }


    //this function makes the player use item on target, item is an inventory Item, target is a Prop/Item
    //one can't use an enemy or an NPC

    public static int interactOnWith(Player player, Entity target, Entity item){
        Room currentRoom = player.getCurrentRoom();
        System.out.println("Using "+target.getName()+" with "+ item.getName());
        if (target.getType() == 'n' || item.getType() == 'e'){
            return 0;
        }
        if(target.getType() == 'p' && item.getType() == 'p'){
            printToLog("At least one of the items must be from your inventory.");
            return 0;
        }
        System.out.println("1");

        if (item instanceof Item){
            System.out.println("2");
            ((Interactable) target).usedWith((Item) item, currentRoom, player);
        } else if (target instanceof Item){
            System.out.println("3");
            ((Interactable) item).usedWith((Item) target, currentRoom, player);
        }
//        else if ((target.getType() == 'i' || target.getType() == 'w')&&(item.getType() == 'i')){
//            System.out.println("3");
//            ((Item) target).usedWith((Item) item, currentRoom, this);
//        } else if((item.getType() == 'i' || item.getType() == 'w')&&(target.getType() == 'i')){
//            System.out.println("4");
//            ((Item) item).usedWith((Item) target, currentRoom, this);
//        }
        return 0;
    }

    //this function handles examining an entity
    public static boolean examine(Player player, Entity target){
        Room currentRoom = player.getCurrentRoom();
        if(target != null){
            target.printLongDescription(player, currentRoom);
            return true;
        }
        return false;
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

                return true;
            }
        }
        printToLog("You can't figure out how to go " + direction);
        return false;
    }


}
