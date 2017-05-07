package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Enums.Command;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Engine.Main;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Behaviours.Container;
import com.al0ne.Entities.Items.Behaviours.Wearable.*;
import com.al0ne.Entities.Items.ConcreteItems.Coin.BrassCoin;
import com.al0ne.Entities.Items.ConcreteItems.Coin.GoldCoin;
import com.al0ne.Entities.Items.ConcreteItems.Coin.SilverCoin;
import com.al0ne.Entities.Statuses.ConcreteStatuses.Hunger;
import com.al0ne.Entities.Statuses.ConcreteStatuses.NaturalHealing;
import com.al0ne.Entities.Statuses.ConcreteStatuses.Thirst;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;
import static com.al0ne.Engine.Main.printToSingleLine;

/**
 * a Player is:
 * an inventory, storing itemID and ConcreteItems
 * a currentRoom, a Room the player is currently in
 * a maxWeight, double, representing the max carry weight
 * a currentWeight, double
 * a maxHealth, a double representing the max health
 * a curretnHealth
 * an attack, int, representing how likely the player is to hit
 * a dexterity, int, representing the player's dodging chance
 * a status, a HashMap <StatusID, Status>
 * a toApply, a queue of status that will be applied at the end of the turn
 * an alive, boolean. Represents if the player is alive or not
 * a hasNeeds, boolean: represents if the current Player will become hungry/thirsty
 * a story, String containing a brief introduction about the player
 * a quests, HashMap<questID, Boolean>, representing all due quests
 * a wornItems, HashMap<BodyPart, Item>: all equipped items
 */
public class Player extends WorldCharacter{

    //stores current room the player is in
    private Room currentRoom;
    
    //Maximum carry weight of the player
    private double maxWeight;
    //Current carry weight of the player
    private double currentWeight;

    //various
    private boolean hasNeeds;
    private String story;

    //maps questID to boolean
    private HashMap<String, Boolean> quests;

    //maps BodyPart to Item
    private HashMap<String, Wearable> wornItems;
    
    // add also money pouch?


    //creates a new Player, sets the current Room to currentRoom
    //inventory is empty, weight is 0
    //add thirst and hunger if needs is true
    public Player(boolean needs, int maxWeight, Room currentRoom, String story) {
        super("alpha", "player", "nix", "da",
        10, 70, 30, 0, 1);
        this.currentRoom = currentRoom;
        this.maxWeight = maxWeight;
        this.currentWeight=0;
        this.wornItems = new HashMap<>();
        this.story = story;
        this.quests = new HashMap<>();
        initialiseWorn();
        if(needs){
            putStatus(new Thirst());
            putStatus(new Hunger());
        }
        this.hasNeeds = needs;
        putStatus(new NaturalHealing());
    }

    //this function initialises the HashMap with all the body parts
    public void initialiseWorn(){
        wornItems.put("main hand", null);
        wornItems.put("off hand", null);
        wornItems.put("body", null);
        wornItems.put("head", null);
    }

    //returns true if the player has basic needs
    public boolean hasNeeds() {
        return hasNeeds;
    }

    //debug function to kill the player
    public void killPlayer(){
        this.alive = false;
    }

    //returns the currently equipped X or null
    public Weapon getWeapon(){
        return (Weapon) wornItems.get("main hand");
    }
    public Armor getArmor(){
        return (Armor) wornItems.get("body");
    }
    public Helmet getHelmet(){
        return (Helmet) wornItems.get("head");
    }
    public Wearable getOffHand(){
        return wornItems.get("off hand");
    }


    //returns true if the player is wearing the current item
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

    //this function unequips the given item, if it's equipped
    public void unequipItem(String id){
        for (String part : wornItems.keySet()){
            Wearable currentItem = wornItems.get(part);
            if(currentItem != null) {
                if(id.equals(currentItem.getID())){
                    wornItems.put(part, null);
                }
            }

        }
    }

    //this function sets currentWeight to the given weight
    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    //this function equips an item to the correct slot, if it's a wearable
    public boolean wear(Item wearable){
        for (Pair pair : inventory.values()){
            Item currentItem = (Item) pair.getEntity();

            if (wearable.getID().equals(currentItem.getID()) && currentItem.getType() == 'w'){
                String part = ((Wearable) currentItem).getPart();
                if (part.equals("main hand")){
                    wornItems.put(part, (Weapon) currentItem);
                    printToLog("You now wield the "+wearable.getName());
                } else if(part.equals("off hand")){
                    wornItems.put(part, (Wearable) currentItem);
                    printToLog("You now wear the "+wearable.getName());
                } else if(part.equals("head")){
                    wornItems.put(part, (Helmet) currentItem);
                    printToLog("You now wear the "+wearable.getName());
                } else if(part.equals("body")){
                    wornItems.put(part, (Armor) currentItem);
                    printToLog("You now wear the "+wearable.getName());
                }
                return true;
            }
        }
        return false;
    }

    //this function prints the currently equipped weapon
    public void printWielded(){
        if(getWeapon() == null){
            printToLog("You're using your fists");
            return;
        }
        printToLog("You're using your "+getWeapon().getName());
    }

    //this function prints all currently equipped armor pieces
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

    //this function computes the total level of protection given by armor
    @Override
    public int getArmorLevel(){
        Armor armor = getArmor();
        Helmet helmet = getHelmet();
        Wearable offHand = getOffHand();

        int armorLevel=this.armor;
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

    @Override
    public int getDamage(){
        int damage = this.damage;
        Wearable weapon = getWeapon();
        if(weapon != null){
            damage+=((Weapon) weapon).getDamage();
        }
        return damage;
    }


    //debug printing
//    public void printHealth() {
//        printToLog("You have "+ currentHealth +"/"+maxHealth+" HP.");
//    }


    //this function modifies the health as above but also prints the health status
//    public void modifyHealthPrint(int health) {
//        if (this.currentHealth +health <= maxHealth){
//            this.currentHealth +=health;
//
//            if (this.currentHealth<=0){
//                this.currentHealth=0;
//                alive = false;
//            }
//        }
//        printHealthStatus();
//    }

    //this function prints a string corresponding to the current
    //health level
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
            if (this.currentHealth<=0){
                return;
            }
            printToLog("You're alive by a miracle");
        }
    }


    //gets the requested resource
    public double getMaxWeight() {
        return maxWeight;
    }
    public double getCurrentWeight() {
        return currentWeight;
    }

    //debug function, prints the current weight.
    public void printWeight() {
        printToLog(currentWeight+"/"+maxWeight+" kg.");
    }

    //this function modifies the current weight
    //it rounds off the result correctly
    //the weight is bound by maxWeight and 0
    public boolean modifyWeight(double weight) {
        if (this.currentWeight+weight <= maxWeight){
            this.currentWeight+=weight;
            this.currentWeight= Utility.twoDecimals(currentWeight);

            if (currentWeight < 0){
                currentWeight=0;
            }
            return true;
        }

        return false;
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
                double weight = Utility.twoDecimals(currentItem.getWeight()*pair.getCount());
                printToLog("- "+pair.getCount()+"x " + currentItem.getName()+". "+weight+" kg.");
            }
            printToLog();
            printWeight();
        }
    }

    //this function adds 1 item from pair to the inventory
    //returns true if it's successful, else the player can't carry it
    public boolean addOneItem(Pair pair) {
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

    //this function adds X items from pair to the inventory
    //returns true if it's successful, else the player can't carry them
    public boolean addAmountItem(Pair pair, Integer amount) {
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

    //this function adds all items from pair to the inventory
    //returns true if it's successful, else the player can't carry them
    public boolean addAllItem(Pair pair) {
        Item item = (Item) pair.getEntity();
        if (modifyWeight(item.getWeight() * pair.getCount())){
            if (hasItemInInventory(item.getID())){
                Pair fromInventory = inventory.get(item.getID());
                fromInventory.modifyCount(pair.getCount());
                pair.modifyCount(-pair.getCount());
                return true;
            } else {
                inventory.put(item.getID(), new Pair(item, pair.getCount()));
                pair.modifyCount(-pair.getCount());
                return true;
            }
        } else {
            return false;
        }
    }

    //this function adds an item, amount times
    //returns true if it's successful, else the player can't carry it
    @Override
    public boolean simpleAddItem(Item item, Integer amount) {
        if (modifyWeight(item.getWeight() * amount)){
            return super.simpleAddItem(item, amount);
        } else {
            return false;
        }
    }



    //this function tries to get an item from the inventory
    //if there is no such item, it returns null
    public ArrayList<Pair> getByType(char type){
        ArrayList<Pair> listOfItems = new ArrayList<>();
        for(Pair p: inventory.values()){
            Item currentItem = (Item) p.getEntity();
            if(currentItem.getType() == type){
                listOfItems.add(p);
            }
        }
        return listOfItems;
    }

    //getter for currentRoom
    public Room getCurrentRoom() {
        return currentRoom;
    }

    //setter for currentRoom
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
        Main.currentRoom = currentRoom;
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


    //this makes the player use an item (simple use, not with an item/prop)
    //returns 0 if the item can't be used
    //returns 1 if the item was used successfully
    //returns 2 if the item was used successfully and it doesn't need to print anything
    public int simpleUse(Entity target){

        if (target.getType() == 'p'){
            Prop prop = (Prop) target;
            return prop.used(currentRoom, this);

        } else if (target.getType() == 'i'){
            Pair pair = inventory.get(target.getID());
            Item item = (Item) pair.getEntity();
            int result = item.used(currentRoom, this);
            if(result == 1 || result == 2){
                //if the item is consumable, we subtract 1 from the inventory
                if (item.hasProperty("consumable")){
                    if(!getItemPair(item.getID()).modifyCount(-1)){
                        inventory.remove(item.getID());
                    }
                }
                return result;
            }
        }
        return 0;
    }


    //this function makes the player use item on target, item is an inventory Item, target is a Prop/Item
    //one can't use an enemy or an NPC

    public boolean interactOnWith(Entity target, Entity item){
        if (target.getType() == 'n' || item.getType() == 'e'){
            return false;
        }
        if (target.getType() == 'p'){
            return ((Prop) target).usedWith((Item) item, currentRoom, this);
        } else if (target.getType() == 'i' || target.getType() == 'w'){
            return ((Item) target).usedWith((Item) item, currentRoom, this);
        }
        return false;
    }

    //this function handles examining an entity
    public boolean examine(Entity target){
        if(target != null){
            target.printLongDescription(this, currentRoom);
            return true;
        }
        return false;
    }

    //this function makes the player drop X amount of target, if it has it
    public int drop(Pair target, Integer amt){
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
            modifyWeight(-(((Item) target.getEntity()).getWeight()*amt));
            target.modifyCount(-amt);
            if (target.getCount() == 0){
                inventory.remove(target.getEntity().getID());
            }
            //we unequip the item if it was equipped
            if (target.getEntity().getType()=='w' &&  isWearingItem(target.getEntity().getID())){
                unequipItem(target.getEntity().getID());
            }
            return 1;
        } else {
            //the item is not in the inventory
            return 0;
        }
    }



    //this function tries to pick up an amount from Item
    public int pickUpItem(Pair item, Integer amt){

        if(hasItemInInventory(item.getEntity().getID()) && ((Item)item.getEntity()).isUnique()){
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
        if (!addAmountItem(item, amt)){
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
    public boolean takeFrom(Pair item, Container container, int amount){

        Item toAdd = (Item) item.getEntity();
            int currentCounter = item.getCount();
            if (!addAmountItem(item, amount)){
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

    public boolean putIn(Pair item, Container container, int amount){

            int count = item.getCount();
            if (!container.putIn(item, amount)){
                printToLog("Not enough space in it.");
                return false;
            } else {
                modifyWeight(-count*((Item)item.getEntity()).getWeight());
                if (item.isEmpty()){
                    inventory.remove(item.getEntity().getID());
                }
            }
        return true;
    }


    //this function handles custom action entities
    //atm supports props and items
    public int customAction(Command action, Entity entity){

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

            for (Command command : entity.getRequiredCommand()){
                if (command.equals(action)){
                    int result = item.used(currentRoom, this);
                    if(result == 1 || result == 2){
                        if (item.hasProperty("consumable")){

                            if(!inRoom && !getItemPair(entity.getID()).modifyCount(-1)){
                                inventory.remove(entity.getID());
                                this.recalculateWeight();
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
                        prop.used(currentRoom, this);
                        return 1;
                    }
                }
            }
        return 0;
    }


    //this function handles talking with an NPC
    public boolean talkToNPC(String name, String subject){
        NPC npc = currentRoom.getNPC(name);
        if (npc != null && npc.talkAbout(subject, this)){
            return true;
        }
        return false;
    }

    //this function handles attacking an entity
    //if it is an enemy, we roll 1d100, we sum attack,
    //and we check if its bigger than the dodge roll
    //if it is, we roll for damage, subtract the armor
    //and inflict the damage (if the enemy isn't resistant
    // to the type of damage).
    //at this point, we make the enemy attack, if its still alive
    //and we snooze him this turn (all aggrod enemies in the room attack at EOT)
    public boolean attack(Entity name){
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
            if(getWeapon()==null){
                type="fists";
            } else{
                type=getWeapon().getDamageType();
            }


            int attackRoll = Utility.randomNumber(100)+attack;
            int dodgeRoll = Utility.randomNumber(100)+enemy.getDexterity();
//            System.out.println("ATK: "+attackRoll+" vs ENEMY DEX: "+dodgeRoll);
            if(attackRoll > dodgeRoll){

                if (enemy.isWeakAgainst(type) && type.equals("fists")) {
                    int inflictedDamage = getDamage()-enemy.getArmorLevel();
                    System.out.println(enemy.getName()+" HP: "+enemy.currentHealth+" damage: "+inflictedDamage);
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

                }else if(enemy.isWeakAgainst(type) ){

                    int inflictedDamage = getDamage()-enemy.getArmorLevel();

                    if(inflictedDamage <= 0){
                        printToLog("Your attack doesn't hurt the "+enemy.getName().toLowerCase()+".");
                    } else{
                        printToLog("You attack and hit the "+enemy.getName().toLowerCase()+".");
                        System.out.println(enemy.getName()+" HP: "+enemy.currentHealth+" damage: "+inflictedDamage);
                        if(!enemy.modifyHealth(-(inflictedDamage))){
                            enemy.handleLoot(currentRoom);
                            currentRoom.getEntities().remove(enemy.getID());
                            return true;
                        }
                    }
                } else{
                    printToLog("The "+enemy.getName().toLowerCase()+" seem not to be affected by your attack.");
                }

                enemy.isAttacked(this, currentRoom);

                enemy.setSnooze(true);
                return true;
            } else{
                printToLog("You attack, but the "+enemy.getName().toLowerCase()+" dodges.");
                enemy.isAttacked(this, currentRoom);
                enemy.setSnooze(true);
                return true;
            }

        } else {
            printToLog("The "+p.getEntity().getName().toLowerCase() +" isn't threatening.");
        }
        return false;
    }


    //we check if the player has at least amt money
    public boolean hasEnoughMoney(int amt){
        return getMoney() >= amt;
    }

    //we return the money of the player
    public int getMoney(){
        Pair gold = inventory.get("gcoin");
        Pair silver = inventory.get("scoin");
        Pair brass = inventory.get("bcoin");

        int value=0;
        if(gold != null){
            value += gold.getCount()*100;
        }
        if(silver != null){
            value += silver.getCount()*10;
        }
        if(brass != null){
            value += brass.getCount();
        }
        return value;
    }

    //we remove amt money from the player
    public boolean removeAmountMoney(int amt){
        Pair gold = inventory.get("gcoin");
        Pair silver = inventory.get("scoin");
        Pair brass = inventory.get("bcoin");

        int toRemove = amt;
        int totalMoney = getMoney();
        if(totalMoney >= toRemove){

            System.out.println("total due: "+toRemove+" current money: "+totalMoney);
            totalMoney-=toRemove;

            Pair tempGold = new Pair(new GoldCoin(), 0);
            Pair tempSilver = new Pair(new SilverCoin(), 0);
            Pair tempBrass = new Pair(new BrassCoin(), 0);
            ArrayList<Pair> values = new ArrayList<>();
            values.add(tempGold);
            values.add(tempSilver);
            values.add(tempBrass);

            while (totalMoney != 0){
                if(!(totalMoney % 10 == 0)){
                    //we subtract brass
                    values.get(2).addCount();
                    totalMoney--;
                } else if(!(totalMoney % 100 == 0)){
                    //we subtract silver
                    values.get(1).addCount();
                    totalMoney-=10;
                } else{
                    //we subtract gold
                    values.get(0).addCount();
                    totalMoney-=100;
                }
            }

            int i = 0;
            for (Pair p : values){
                Pair money;
                if(i==0){
                    money=gold;
                } else if(i==1){
                    money=silver;
                } else{
                    money=brass;
                }

                if(money != null){
                    if(money.setCount(p.getCount())){
                        inventory.remove(money.getEntity().getID());
                    }
                } else if(p.getCount() > 0){
                    addAllItem(p);
                }
                i++;
            }

            return true;

        }else {
            return false;
        }
    }

    //we give an item to an NPC, if the player has it
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


    //this recalculates weight, run at the end of every turn
    public void recalculateWeight(){
        double amt = 0;
        for(Pair p : inventory.values()){
            amt+=((Item)p.getEntity()).getWeight();
        }
        currentWeight = Utility.twoDecimals(amt);
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }



    //returns the story of the player
    public String getStory() {
        return story;
    }

    //returns the quests
    public HashMap<String, Boolean> getQuests() {
        return quests;
    }

    //this adds a quest to the quest log
    public void addQuest(String s) {
        this.quests.put(s, false);
    }

    //returns true if the player has finished the quest
    public boolean hasDoneQuest(String s) {
        return this.quests.get(s) != null;
    }

}