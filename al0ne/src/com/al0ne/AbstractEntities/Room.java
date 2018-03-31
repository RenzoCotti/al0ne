package com.al0ne.AbstractEntities;

import com.al0ne.AbstractEntities.Events.Event;
import com.al0ne.AbstractEntities.Pairs.Pair;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Abstract.Enemy;
import com.al0ne.AbstractEntities.Abstract.Entity;
import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.ConcreteEntities.Items.ConcreteItems.LightItem;
import com.al0ne.ConcreteEntities.Items.Types.Container;
import com.al0ne.Engine.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;

import static com.al0ne.Engine.Main.player;
import static com.al0ne.Engine.Main.printToLog;
import static com.al0ne.Engine.Main.printToSingleLine;

/**
 * Created by BMW on 28/01/2017.
 *
 * a Room is:
 * a props, all props in the room
 * an items, all objects you can pickup
 * a longDescription of the room
 * a name of the room
 * an exits, HashMap of direction - roomid
 * an lockedDirection, HashMap of doorName - direction
 *
 */
public class Room extends Entity {

    //maps direction - room ID
    private HashMap<String, String> exits;
    //maps door ID - direction
    private HashMap<String, String> lockedDirections;

    //maps entityID - Pair(Entity, amt)
    private HashMap<String, Pair> entities;

    private String customDirections;

    private boolean firstVisit;

    private boolean isLit;

    private ArrayList<Event> events;


    public Room(String name, String description) {
        super("room"+(entityCounter++), name, description, name);
        this.exits=new HashMap<>();
        this.lockedDirections = new HashMap<>();
        this.customDirections = null;
        this.entities = new HashMap<>();
        this.firstVisit = true;
        this.isLit = true;
        this.events = new ArrayList<>();
    }

    public boolean isFirstVisit(){
        return firstVisit;
    }

    public void visit(){
        firstVisit = false;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

    public void triggerEvents(Player p){
        for (Event e: events){
            if(Utility.randomGreaterThan(e.getProbability())){
                e.resolveEvent(p);
            }
        }
    }


    public ArrayList<Enemy> getEnemyList() {
        ArrayList<Enemy> enemyList = new ArrayList<>();
        for (Pair p : entities.values()){
            Entity e = p.getEntity();
            if (e.getType()=='e'){
                Enemy enemy = (Enemy) e;
                enemyList.add(enemy);
            }
        }
        return enemyList;
    }

    public ArrayList<Container> getContainers() {
        ArrayList<Container> containers = new ArrayList<>();
        for (Pair p : entities.values()){
            Entity c = p.getEntity();
            if (c.getType()=='C'){
                Container container = (Container) c;
                containers.add(container);
            }
        }
        return containers;
    }

    public HashMap<String, Pair> getEntities() {
        return entities;
    }

    public void addEntity(Entity entity) {

        //avoiding a room containing itself
        if(entity.equals(this)){
            return;
        }

        if(entities.get(entity.getID()) != null){
            entities.get(entity.getID()).addCount();
            return;
        }
        this.entities.put(entity.getID(), new Pair(entity, 1));
    }

    public void addEntity(Entity entity, int qty) {

        //avoiding a room containing itself
        if(entity.equals(this)){
            return;
        }

        if(entities.get(entity.getID()) != null){
            entities.get(entity.getID()).modifyCount(qty);
            return;
        }
        this.entities.put(entity.getID(), new Pair(entity, qty));
    }

    public Entity getEntity(String name) {
        for (Pair p : entities.values()){
            Entity currentEntity = p.getEntity();
            //TODO, FIX THIS WITH PROPER SEEKING
            if (currentEntity.getName().toLowerCase().contains(name)){
                return currentEntity;
            }
        }
        return null;
    }

    public void printEnemy() {
        ArrayList enemies = getEnemyList();
        printArrayInRoom(enemies, "You can see ");
    }

    public void printArrayInRoom(ArrayList entities, String begin){
        if(entities.size() > 0){
            printToSingleLine(begin);
            for (int i=0; i<entities.size(); i++) {
                Entity currentEntity = ((Entity)entities.get(i));
                if(currentEntity instanceof NPC){
                    printToSingleLine(currentEntity.getName());
                } else{
                    printToSingleLine(currentEntity.getShortDescription().toLowerCase());
                }
                if(i==entities.size()-2){
                    printToSingleLine(" and ");
                } else if(i!=entities.size()-1){
                    printToSingleLine(", ");
                } else{
                    printToSingleLine(" here.\n");
                }
            }
        }
    }


    public ArrayList<NPC> getNPCList() {
        ArrayList<NPC> npcList = new ArrayList<>();
        for (Pair p : entities.values()){
            Entity e = p.getEntity();
            if (e instanceof NPC){
                NPC npc = (NPC) e;
                npcList.add(npc);
            }
        }
        return npcList;
    }

    public NPC getNPC(String name) {
        ArrayList<NPC> npcs = getNPCList();
        for (Entity e : npcs){
            if (e.getName().toLowerCase().contains(name)){
                return (NPC) e;
            }
        }
        return null;
    }

    public void printNPCs() {
        ArrayList<NPC> npcs = getNPCList();
        printArrayInRoom(npcs, "There is ");
    }


    public ArrayList<Prop> getPropList() {
        ArrayList<Prop> propList = new ArrayList<>();
        for (Pair p : entities.values()){
            Entity e = p.getEntity();
            if (e.getType()=='p'){
                Prop prop = (Prop) e;
                propList.add(prop);
            }
        }
        return propList;
    }

    public ArrayList<Pair> getItemList() {
        ArrayList<Pair> itemList = new ArrayList<>();
        for (Pair p : entities.values()){
            Entity e = p.getEntity();
            if (e.getType()=='i' || e.getType()=='w' || e.getType()=='C'){
//                printToLog(e.getID());
                itemList.add(p);
            }
        }
        return itemList;
    }

    public HashMap<String, String> getExits() {
        return exits;
    }

    private void printItems(){
        ArrayList<Pair> items = getItemList();
        if (items.size()!=0){
            printToSingleLine("You can see ");
            for (int i=0; i<items.size(); i++) {
                Item currentItem = (Item) items.get(i).getEntity();
                int count = items.get(i).getCount();
                if(count == 1){
                    printToSingleLine(currentItem.getShortDescription());
                } else{
                    printToSingleLine(count + " " + currentItem.getName());
                }
                if(i==items.size()-2){
                    printToSingleLine(" and ");
                } else if(i!=items.size()-1){
                    printToSingleLine(", ");
                } else{
                    printToSingleLine(" here.");
                    printToLog();
                }
            }
        }
    }

    private void printProps(){
        ArrayList<Prop> props = getPropList();
        ArrayList<Prop> toRemove = new ArrayList<>();

        for(Prop p : props){
            if (p.isInvisible()){
                toRemove.add(p);
            }
        }
        props.removeAll(toRemove);

        if(props.size() == 0) return;

        printArrayInRoom(props, "You can see ");
    }


    public void printName(){
        printToLog(name);
    }

    public void setCustomDirection(String s){
        customDirections=s;
    }

    //prints available travel directions that are not locked
    public void printDirections(){

        boolean first=true;
        String toPrint="";

        for (String door : lockedDirections.keySet()){
            String currentDirection = lockedDirections.get(door);
            try{
                printToLog("The way "+currentDirection+" is blocked by "+entities.get(door).getEntity().getLongDescription().toLowerCase());
            } catch (NullPointerException ex){
                System.out.println("Nothing more to see here >_>");
            }
        }

        if(customDirections != null){
            printToLog(customDirections);
            return;
        }

        for (String exit : exits.keySet()){
            boolean free = true;
            for (String direction : lockedDirections.values()){
                if (direction.equals(exit)) {
                    free = false;
                }
            }

            if(free){
                printToSingleLine(toPrint);
                if (first){
                    toPrint="You can go "+exit;
                    first=false;
                } else{
                    toPrint=", "+exit;
                }
            }
        }
        if(exits.values().size() > 0){
            printToSingleLine(toPrint+".");
        } else{
            printToLog("This room has no exits that you can see.");
        }
    }

    //this function prints every time a room is discovered
    public void printRoom(){
        Item item = player.getOffHand();
        if(isLit || (item != null && item instanceof LightItem && ((LightItem) item).isLit())){
            printLongDescription(null);
            printItems();
            printProps();
            printNPCs();
            printEnemy();
            printDirections();
            printToLog();
        } else {
            printToLog("Too dark to see anything.");
            printToLog();
        }
    }


    public void addItem(Item item) {
        if (hasItem(item.getID())){
            Pair currentPair=entities.get(item.getID());
            currentPair.addCount();
        } else{
            entities.put(item.getID(), new Pair(item, 1));
        }
    }

    public void addItem(Item item, Integer amount) {
        if (hasItem(item.getID())){
            Pair currentPair=entities.get(item.getID());
            currentPair.modifyCount(amount);
        } else{
            entities.put(item.getID(), new Pair(item, amount));
        }
    }

    public boolean hasItem(String id) {
        ArrayList<Pair> items = getItemList();
        if (items == null){
            return false;
        }
        for (Pair p : items){
            Item currentItem = (Item) p.getEntity();
            if (currentItem.getID().equals(id)){
                return true;
            }
        }
        return false;
    }

    public boolean hasEntity(String id) {
        for (Pair p : entities.values()){
            if (p.getEntity().getID().equals(id)){
                return true;
            }
        }
        return false;
    }

    public Pair getEntityPair(String id) {

        if (hasEntity(id)){
            return entities.get(id);
        }
        else return null;
    }

    public void addExit(String exit, Room room) {
        this.exits.put(exit, room.getID());
    }

    //this connects the two rooms in a symmetric fashion, eg R1 --W--> R2 and R2 --E--> R1
    public void connectRoom(String exit, Room room){
        String other;

        switch (exit){
            case "north":
                other="south";
                break;
            case "south":
                other="north";
                break;
            case "east":
                other="west";
                break;
            case "west":
                other="east";
                break;
            case "southeast":
                other="northwest";
                break;
            case "southwest":
                other="northeast";
                break;
            case "northeast":
                other="southwest";
                break;
            case "northwest":
                other="southeast";
                break;
            case "up":
                other="down";
                break;
            case "down":
                other="up";
                break;
            default:
                System.out.println("Connecting room failed. "+room.getName()+" and "+this.getName());
                return;
        }

        this.exits.put(exit, room.getID());
        room.exits.put(other, this.getID());
    }

    //this function locks direction behind doorID
    public void lockDirection(String direction, String idDoor){
        lockedDirections.put(idDoor, direction);
    }

    public void unlockDirection(String idDoor){
        lockedDirections.remove(idDoor);
    }

    //checks if the current direction is locked
    public boolean isLocked(String direction){
        for (String s : lockedDirections.values()){
            if (s.equals(direction)){
                return true;
            }
        }
        return false;
    }

    public boolean hasEnemies(){
        return getEnemyList().size() > 0;
    }


    public String getCustomDirections() {
        return customDirections;
    }


    public boolean isLit() {
        return isLit;
    }

    public void setLit(boolean lit) {
        isLit = lit;
    }
}