package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Pairs.Prop;
import com.al0ne.Entities.Items.Behaviours.Container;

import java.util.ArrayList;
import java.util.HashMap;

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
public class Room extends Entity{

    //maps direction - room ID
    private HashMap<String, String> exits;
    //maps door ID - direction
    private HashMap<String, String> lockedDirections;

    //maps entityID - Pair(Entity, amt)
    private HashMap<String, Pair> entities;


    private String customDirections;



    public Room(String id, String name, String description) {
        super(id, name, description, name);
        this.exits=new HashMap<>();
        this.lockedDirections =new HashMap<>();
        customDirections = null;
        entities = new HashMap<>();
    }

    @Override
    public boolean used(Room currentRoom, Player player) {
        return false;
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
        this.entities.put(entity.getID(),
                new Pair(entity, 1));
    }

    public void addEntity(Entity entity, int qty) {
        this.entities.put(entity.getID(), new Pair(entity, qty));
    }

    public Enemy getEnemy(String name) {
        ArrayList<Enemy> enemies = getEnemyList();
        for (Entity e : enemies){
            if (e.getName().toLowerCase().contains(name)){
                return (Enemy) e;
            }
        }
        return null;
    }

    public Entity getEntity(String name) {
        for (Pair p : entities.values()){
            Entity currentEntity = p.getEntity();
            if (currentEntity.getName().toLowerCase().contains(name)){
                return currentEntity;
            }
        }
        return null;
    }

    public void printEnemy() {
        ArrayList<Enemy> enemies = getEnemyList();
        for (Enemy enemy : enemies){
            printToLog("You can see "+enemy.getShortDescription()+" here.");
        }
    }


    public ArrayList<NPC> getNPCList() {
        ArrayList<NPC> npcList = new ArrayList<>();
        for (Pair p : entities.values()){
            Entity e = p.getEntity();
            if (e.getType()=='n'){
                NPC npc = (NPC) e;
                npcList.add(npc);
            }
        }
        return npcList;
    }

    public NPC getNPC(String name) {
        ArrayList<NPC> npcs = getNPCList();
        for (Entity e : npcs){
            if (e.getName().toLowerCase().equals(name)){
                return (NPC) e;
            }
        }
        return null;
    }

    public void printNPCs() {
        ArrayList<NPC> npcs = getNPCList();
        for (NPC npc : npcs){
            printToLog("You can see "+npc.getName()+" here.");
        }
    }

    //prints containers in the room
    private void printContainers(){
        ArrayList<Container> containers = getContainers();
        if (containers.size()!=0){
            printToSingleLine("You see ");
            for (int i=0; i<containers.size(); i++) {
                printToSingleLine(containers.get(i).getShortDescription());
                if(i==containers.size()-2){
                    printToSingleLine(" and ");
                } else if(i!=containers.size()-1){
                    printToSingleLine(", ");
                } else{
                    printToSingleLine(" here.");
                    printToLog();
                }
            }
        }
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
            if (e.getType()=='i' || e.getType()=='w'){
//                printToLog(e.getID());
                itemList.add(p);
            }
        }
        return itemList;
    }

    public HashMap<String, String> getExits() {
        return exits;
    }


    //prints items in the room
    private void printItems(){
        ArrayList<Pair> items = getItemList();
        if (items.size()!=0){
            printToLog("You can see:");
            for (Pair p : items) {
                Item currentItem = (Item) p.getEntity();
                printToLog("- " +p.getCount()+"x "+ currentItem.getName());
            }
        }
    }

    //prints props in the room
    private void printProps(){
        ArrayList<Prop> items = getPropList();
        if (items.size()!=0){
            printToSingleLine("There is ");
            for (int i=0; i<items.size(); i++) {
                printToSingleLine(items.get(i).getShortDescription());
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

    public void printName(){
        printToLog(name);
    }

    public void addCustomDirection(String s){
        customDirections=s;
    }

    //prints available travel directions that are not locked
    private void printDirections(){

        boolean first=true;
        String toPrint="";

        for (String door : lockedDirections.keySet()){
            String currentDirection = lockedDirections.get(door);
            try{
                printToLog("The way "+currentDirection+" is blocked by "+entities.get(door).getEntity().getLongDescription().toLowerCase());
            } catch (NullPointerException ex){
                printToLog("Nothing more to see here >_>");
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
        printToSingleLine(toPrint);
    }

    //this function prints every time a room is discovered
    public void printRoom(){
        printLongDescription(null, null);
        printItems();
        printProps();
        printContainers();
        printNPCs();
        printEnemy();
        printDirections();
        printToLog();
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

    public void addExit(String exit, String roomid) {
        this.exits.put(exit, roomid);
    }

    //this function locks direction behind doorID
    public void lockDirection(String direction, String idDoor){
        lockedDirections.put(idDoor, direction);
    }

    public void unlockDirection(String nameDoor){
        lockedDirections.remove(nameDoor);
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

}