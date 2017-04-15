package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.Spells.Fireball;
import com.al0ne.Behaviours.Spells.LightHeal;
import com.al0ne.Entities.Items.Behaviours.Container;
import com.al0ne.Entities.Items.Behaviours.Wearable.*;
import com.al0ne.Entities.Items.ConcreteItems.Spellbook;
import com.al0ne.Entities.Statuses.Hunger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;
import static com.al0ne.Engine.Main.printToSingleLine;

/**
 * Created by BMW on 28/01/2017.
 *
 * a Player is:
 * an inventory, storing itemID and ConcreteItems
 * a currentRoom, a Room
 * a maxWeight, double
 * a currentWeight, double
 *
 * TODO probably add currentHealth, belly
 */
public class Player implements Serializable{

    //Maps ItemID, Item
    private HashMap<String, Pair> inventory;
    private Room currentRoom;
    
    //Maximum carry weight of the player
    private static double maxWeight=10;
    //Current carry weight of the player
    private double currentWeight;

    private int currentHealth =10;
    private static int maxHealth=10;

    private int attack = 70;
    private int dexterity = 30;

    protected HashMap<String, Status> status;
    protected ArrayList<Status> toApply;

    private boolean alive = true;

    private boolean hasNeeds;


//    private Weapon wieldedWeapon;
    private HashMap<String, Wearable> wornItems;
    
    // TODO: 08/03/2017 add satiation, thirst level
    // add also money pouch?



    //creates a new Player, sets the current Room to currentRoom
    //inventory is empty, weight is 0
    public Player(Room currentRoom, boolean needs) {
        this.currentRoom = currentRoom;
        this.inventory = new HashMap<>();
        this.currentWeight=0;
        this.wornItems = new HashMap<>();
        this.status = new HashMap<>();
        this.toApply = new ArrayList<>();
        initialiseWorn();
        if(needs){
//            putStatus(new Thirst());
            putStatus(new Hunger());
        }
        this.hasNeeds = needs;

        Spellbook sb = new Spellbook();
        sb.addSpell(new Fireball(), 5);
        sb.addSpell(new LightHeal(), 3);
        addItem(new Pair(sb, 1));

    }

    public void initialiseWorn(){
        wornItems.put("main hand", null);
        wornItems.put("off hand", null);
        wornItems.put("armor", null);
        wornItems.put("helmet", null);
    }

    public boolean hasNeeds() {
        return hasNeeds;
    }

    public Weapon getWeapon(){
        return (Weapon) wornItems.get("main hand");
    }

    public Armor getArmor(){
        return (Armor) wornItems.get("armor");
    }

    public Helmet getHelmet(){
        return (Helmet) wornItems.get("helmet");
    }

    public Wearable getOffHand(){
        return wornItems.get("armor");
    }

    public void unequipItem(Wearable item){
        for (String part : wornItems.keySet()){
            Wearable currentItem = wornItems.get(part);

            if(currentItem != null && item.getID().equals(currentItem.getID())){
                wornItems.put(part, null);
            }
        }
    }

    public boolean isWearingItem(String id){
        for (String part : wornItems.keySet()){
            Wearable currentItem = wornItems.get(part);
            if(currentItem != null) {
                if(id.equals(currentItem.getID())){
                    return true;
                }
            }

        }
        return false;
    }

    public boolean wear(Item wearable){
        for (Pair pair : inventory.values()){
            Item currentItem = (Item) pair.getEntity();

            if (wearable.getID().equals(currentItem.getID()) && currentItem instanceof Wearable){
                String part = ((Wearable) currentItem).getPart();
                if (part.equals("main hand")){
                    wornItems.put(part, (Weapon) currentItem);
                    printToLog("You now wield the "+wearable.getName());
                } else if(part.equals("off hand")){
                    wornItems.put(part, (Shield) currentItem);
                    printToLog("You now wear the "+wearable.getName());
                } else if(part.equals("head")){
                    wornItems.put(part, (Helmet) currentItem);
                    printToLog("You now wear the "+wearable.getName());
                } else if(part.equals("armor")){
                    wornItems.put(part, (Armor) currentItem);
                    printToLog("You now wear the "+wearable.getName());
                }
                return true;
            }
        }
        return false;
    }

    public void printWielded(){
        if(getWeapon() == null){
            printToLog("You're using your fists");
            return;
        }
        printToLog("You're using your "+getWeapon().getName());
    }

    public void printArmor(){

        boolean first=true;

        for (Wearable w : wornItems.values()){
            if (w != null && !(w instanceof Weapon)){
                if(first){
                    printToSingleLine("You're wearing "+w.getShortDescription());
                    first = false;
                }else {
                    printToSingleLine(", "+w.getShortDescription());
                }
            }
        }

        if(first){
            printToLog("You're not wearing anything.");
        } else{
            printToLog();
        }
    }

    public int getArmorLevel(){
        Armor armor = getArmor();
        Helmet helmet = getHelmet();
        Wearable offHand = getOffHand();

        int armorLevel=0;
        if(armor != null){
            armorLevel= armor.getArmor();
        }
        if(helmet != null){
            armorLevel= helmet.getArmor();
        }
        if(offHand != null && offHand instanceof Shield){
            armorLevel= ((Shield) offHand).getArmor();
        }

        return armorLevel;
    }

    public int getAttack() {
        return attack;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void printHealth() {
        printToLog("You have "+ currentHealth +"/"+maxHealth+" HP.");
    }

    public void setHealth(int amount) {
        if(currentHealth+amount > maxHealth){
            currentHealth=maxHealth;
        } else if(currentHealth+amount < 0){
            currentHealth=0;
            alive=false;
        }
    }

    public void modifyHealth(int health) {
        if (this.currentHealth +health <= maxHealth){
            this.currentHealth +=health;

            if (this.currentHealth<=0){
                alive = false;
            }
        }
    }


    public void modifyHealthPrint(int health) {
        if (this.currentHealth +health <= maxHealth){
            this.currentHealth +=health;

            if (this.currentHealth<=0){
                alive = false;
            }
        }

        printHealthStatus();

    }

    public void printHealthStatus(){
        double percentage = ((double)currentHealth/(double)maxHealth)*100;

        if (percentage >= 80){
            printToLog("You're as healthy as ever.");
        } else if (percentage >= 60 && percentage < 80){
            printToLog("You're mostly fine.");
        } else if (percentage >= 40 && percentage < 60){
            printToLog("You need to medicate.");
        } else if (percentage >= 20 && percentage < 40){
            printToLog("You're bleeding heavily");
        } else {
            if (this.currentHealth==0){
                return;
            }
            printToLog("You're alive by a miracle");
        }
    }

    public void printWeight() {
        printToLog(currentWeight+"/"+maxWeight+" kg.");
    }

    public boolean modifyWeight(double weight) {
        if (this.currentWeight+weight <= maxWeight){
            this.currentWeight+=weight;
            this.currentWeight=Math.round(currentWeight*10.0)/10.0;

            if (currentWeight < 0){
                currentWeight=0;
            }
            return true;
        }

        return false;
    }

    public boolean isAlive(){
        return alive;
    }


    //returns the inventory hashmap
    public HashMap<String, Pair> getInventory() {
        return inventory;
    }

    //prints the inventory
    public void printInventory(){
        if (inventory.size()==0){
            printToLog("You have no items.");
        } else {
            printToLog("You have these items:");
            for (Pair pair : inventory.values()) {
                Item currentItem = (Item) pair.getEntity();
                printToLog("- "+pair.getCount()+"x " + currentItem.getName()+". "+currentItem.getWeight()*pair.getCount()+" kg.");
            }
            printToLog();
            printWeight();
        }
    }

    //this function adds an item to the inventory
    //returns false if item is not in inv or weight is nope
    public boolean addItem(Pair pair) {
        Item item = (Item) pair.getEntity();
        if (modifyWeight(item.getWeight())){
            if (hasItemInInventory(item.getID())){
                Pair fromInventory = inventory.get(item.getID());
                fromInventory.addCount();
                pair.subCount();
                return true;
            } else {
                inventory.put(item.getID(), new Pair(item, 1));
                pair.subCount();
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean addItem(Pair pair, Integer amount) {
        Item item = (Item) pair.getEntity();
        if (modifyWeight(item.getWeight() * amount)){
            if (hasItemInInventory(item.getID())){
                Pair fromInventory = inventory.get(item.getID());
                fromInventory.modifyCount(amount);
                pair.modifyCount(-amount);
                return true;
            } else {
                inventory.put(item.getID(), new Pair(item, amount));
                pair.modifyCount(-amount);
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean simpleAddItem(Item item, Integer amount) {
        if (modifyWeight(item.getWeight() * amount)){
            if (hasItemInInventory(item.getID())){
                Pair fromInventory = inventory.get(item.getID());
                fromInventory.modifyCount(amount);
                return true;
            } else {
                inventory.put(item.getID(), new Pair(item, amount));
                return true;
            }
        } else {
            return false;
        }
    }

    //this function checks if the player has an item in the inventory
    //if there is no item, it returns false
    public boolean hasItemInInventory(String item){
        for (String s : inventory.keySet()){
            if (s.equals(item)){
                return true;
            }
        }
        return false;
    }

    //this function tries to get an item from the inventory
    //if there is no such item, it returns null
    public Pair getItemPair(String itemID){
        if(hasItemInInventory(itemID)){
            return inventory.get(itemID);
        } else {
            printToLog("No such item in your inventory.");
            return null;
        }
    }

    //getter for currentRoom
    public Room getCurrentRoom() {
        return currentRoom;
    }

    //setter for currentRoom
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }


    //this function checks if the direction selected is accessible from the
    //currentRoom and if it's not locked by a door, if so it moves there
    public boolean moveToRoom(String direction, HashMap<String, Room> rooms){

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
                setCurrentRoom(rooms.get(nextRoomId));

                return true;
            }
        }
        printToLog("You can't figure out how to go " + direction);
        return false;
    }


    //this makes the player use an item
    public int simpleUse(Entity target){

        if (target.getType() == 'p'){
            Prop prop = (Prop) target;
            return prop.used(currentRoom, this);

        } else if (target.getType() == 'i'){
            Pair pair = inventory.get(target.getID());
            Item item = (Item) pair.getEntity();
            if(item.used(currentRoom, this) == 1){
                if (item.hasProperty("consumable")){
//                    printToLog("used :"+pair.getCount());

                    if(!getItemPair(item.getID()).modifyCount(-1)){
                        inventory.remove(item.getID());
                    }

                }

                return 1;
            } else if(item.used(currentRoom, this) == 2){
                if (item.hasProperty("consumable")){
//                    printToLog("used :"+pair.getCount());

                    if(!getItemPair(item.getID()).modifyCount(-1)){
                        inventory.remove(item.getID());
                    }

                }

                return 2;
            }
        }
        return 0;
    }


    //this function makes the player use item on target, item is an inventory Item, target is a Prop
    public boolean interactOnWith(Entity target, Entity item){

        if (target.getType() == 'n' || item.getType() == 'e'){
            return false;
        }

        if (target.getType() == 'p'){
            return ((Prop) target).usedWith((Item) item, currentRoom, this);
        } else if (target.getType() == 'i'){
            return ((Item) target).usedWith((Item) item, currentRoom, this);
        }

        return false;
    }

    //this function prints the longDescription of target, be it a prop or an Item
    public boolean examine(Entity target){

        if(target != null){
            target.printLongDescription(this, currentRoom);
            return true;
        }
        return false;

    }

    //this function makes the player drop target, if it has it
    public boolean drop(Pair target, boolean all){
        if (target != null){
            //case we drop 1 & target is in room
            Pair roomItem = currentRoom.getEntityPair(target.getEntity().getID());
            if (!all && roomItem != null){
                roomItem.addCount();
                //case we drop 1 & target is not in room
            } else if (!all){
                currentRoom.addItem((Item) target.getEntity());
                modifyWeight(-((Item) target.getEntity()).getWeight());
            }

            //case we drop all & target is in room
            if (all && roomItem != null){
                roomItem.setCount(roomItem.getCount()+target.getCount());
            } else if (all){
                currentRoom.addItem((Item) target.getEntity(), target.getCount());
            }

            //we dropped 1
            if(!all){
                modifyWeight(-((Item) target.getEntity()).getWeight());
                if (!target.subCount()){
                    inventory.remove(target.getEntity().getID());
                    if (target.getEntity().getType()=='w' && isWearingItem(target.getEntity().getID())){
                        unequipItem((Wearable) target.getEntity());
                    }
                }
                //we dropped all
            } else{
                modifyWeight(-(((Item) target.getEntity()).getWeight()*target.getCount()));
                inventory.remove(target.getEntity().getID());
                if (target.getEntity().getType()=='w' &&  isWearingItem(target.getEntity().getID())){
                    unequipItem((Wearable) target.getEntity());
                }
            }

            return true;

        } else {
            return false;
        }
    }


    //this function tries to pick up an item in the currentRoom:
    //it also checks if the player can carry the current item
    //if it didn't succeed, print an error message
    public boolean pickUpItem(Pair item, boolean all){

        if(hasItemInInventory(item.getEntity().getID()) && ((Item)item.getEntity()).isUnique()){
            printToLog("You can have just one with you.");
            //bypassed by taking chest with that in it
            return false;
        } else if(all && ((Item)item.getEntity()).isUnique() && item.getCount()>1){
            printToLog("You can have just one with you.");
            //bypassed by taking chest with that in it
            return false;
        }

        Item toAdd = (Item) item.getEntity();
        if (all){
            if (!addItem(item, item.getCount())){
                printToLog("Too heavy to carry.");
                return false;
            } else {
                if (item.isEmpty()){
                    currentRoom.getEntities().remove(toAdd.getID());
                }
            }
        } else {
            if (!addItem(item)){
                printToLog("Too heavy to carry.");
                return false;
            } else {
//                printToLog(item.getCount());
                if (item.isEmpty()){
                    currentRoom.getEntities().remove(toAdd.getID());
                }
            }
        }
        return true;
    }

    //this function tries to pick up an item in the currentRoom:
    //it also checks if the player can carry the current item
    //if it didn't succeed, print an error message
    public boolean takeFrom(Pair item, Container container, boolean all){

        Item toAdd = (Item) item.getEntity();
        if (all){
            int currentCounter = item.getCount();
            if (!addItem(item, item.getCount())){
                printToLog("Too heavy to carry.");
                return false;
            } else {
                currentCounter-=item.getCount();
                container.modifySize(-currentCounter*toAdd.getSize());
                if (item.isEmpty()){
                    container.removeItem(item);
                }
            }
        } else {
            if (!addItem(item)){
                printToLog("Too heavy to carry.");
                return false;
            } else {
                container.modifySize(-toAdd.getSize());
                if (item.isEmpty()){
                    container.removeItem(item);
                }
            }
        }
        return true;
    }

    public boolean putIn(Pair item, Container container, boolean all){

        if (all){
            int count = item.getCount();
            if (!container.putIn(item, count)){
                printToLog("Not enough space in it.");
                return false;
            } else {
                printToLog("count : "+count);
                currentWeight-=count*((Item)item.getEntity()).getWeight();
                if (item.isEmpty()){
                    inventory.remove(item.getEntity().getID());
                }
            }
        } else {
            if (!container.putIn(item)){
                printToLog("Not enough space in it.");
                return false;
            } else {
                currentWeight-=((Item)item.getEntity()).getWeight();
                if (item.isEmpty()){
                    inventory.remove(item.getEntity().getID());
                }
            }
        }
        return true;
    }


    public int customAction(String action, Entity entity){

        if(entity.getType()=='i'){
            boolean inRoom = false;
            Pair pair = inventory.get(entity.getID());
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

            for (String command : entity.getRequiredCommand()){
                if (command.equals(action)){
                    int result = item.used(currentRoom, this);
                    if(result == 1 || result == 2){
                        if (item.hasProperty("consumable")){

                            if(!inRoom && !getItemPair(entity.getID()).modifyCount(-1)){
                                inventory.remove(entity.getID());
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
            for (String command : prop.getRequiredCommand()){

                    prop.used(currentRoom, this);
                    return 1;
                }
            }
        return 0;
    }


    public boolean talkToNPC(String name, String subject){
        NPC npc = currentRoom.getNPC(name);
        if (npc != null && npc.talkAbout(subject)){
            return true;
        }
        return false;
    }

    public boolean attack(Entity name){
        Pair p = currentRoom.getEntityPair(name.getID());
        Entity entity;
        if (p == null) {
            printToLog("You can't see a "+name.getName());
            return false;
        } else {
            entity = p.getEntity();
        }
        if(entity.getType() == 'n'){
            printToLog("It's best not to attack "+ name.getName());
            return false;
        } else if (entity.getType() == 'e'){
            Enemy enemy = (Enemy) entity;
            String type;
            if(getWeapon()==null){
                type="fists";
            } else{
                type=getWeapon().getDamageType();
            }


            int attackRoll = (int)(Math.random() * (100 - 1) + 1)+attack;
            int dodgeRoll = (int)(Math.random() * (100 - 1) + 1)+enemy.getDexterity();
            System.out.println("ATK: "+attackRoll+" vs ENEMY DEX: "+dodgeRoll);
            if(attackRoll > dodgeRoll){

                if (enemy.isWeakAgainst(type) && type.equals("fists")) {
                    printToLog("You punch and hit the "+enemy.getName().toLowerCase()+".");
                    enemy.modifyHealth(-1);
                }else if(enemy.isWeakAgainst(type) ){

                    int inflictedDamage = getWeapon().getDamage()-enemy.getArmor();

                    if(inflictedDamage < 0){
                        printToLog("The "+enemy.getName().toLowerCase()+" seem not to be affected");
                    } else{
                        printToLog("You attack and hit the "+enemy.getName().toLowerCase()+".");
                        enemy.modifyHealth(-(inflictedDamage));
                    }
                } else{
                    printToLog("The "+enemy.getName().toLowerCase()+" seem not to be affected");
                }

                if (enemy.isAttacked(this, currentRoom)) {
                    currentRoom.getEntities().remove(enemy.getID());
                }
                return true;
            } else{
                printToLog("You attack, but the "+enemy.getName().toLowerCase()+" dodges.");
                if (enemy.isAttacked(this, currentRoom)) {
                    currentRoom.getEntities().remove(enemy.getID());
                }
                return true;
            }

        } else {
            printToLog("You can't seem to see an enemy.");
        }
        return false;
    }


    public boolean hasEnoughMoney(int amt){
        Pair pair = inventory.get("coin");
        if(pair != null){
            int amount = pair.getCount();
            return amount - amt >= 0;
        } else {
            return false;
        }
    }

    public boolean give(NPC npc, Entity item){
        if(hasItemInInventory(item.getID())){
            Pair pair = getItemPair(item.getID());

            if (npc.isGiven((Item) pair.getEntity(), this)){
                return true;
            } else{
                return false;
            }
        } else{
            printToLog("You don't have it.");
            return true;
        }
    }


    public HashMap<String, Status> getStatus() {
        return status;
    }

    public boolean putStatus(Status s) {
        for (Status st : status.values()){
            if(s.getName().equals(st.getName())){
                //refresh on reapply of status
                st.duration = s.duration;
                return false;
            }
        }
        status.put(s.getName(), s);
        return true;
    }

    public boolean removeStatus(String statusName) {

        if (status.get(statusName) != null){
            status.remove(statusName);
        }
        return true;
    }

    public boolean hasStatus(String s) {
        return status.get(s) != null;
    }

    public ArrayList<Status> getToApply() {
        return toApply;
    }

    public void queueStatus(Status st) {
        this.toApply.add(st);
    }
}