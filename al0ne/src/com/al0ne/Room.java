package com.al0ne;

import com.al0ne.Entities.Enemy;
import com.al0ne.Entities.NPC;
import com.al0ne.Entities.Entity;
import com.al0ne.Entities.Player;
import com.al0ne.Items.Item;
import com.al0ne.Items.Pair;
import com.al0ne.Items.Prop;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by BMW on 28/01/2017.
 *
 * a Room is:
 * a props, all props in the room
 * an items, all objects you can pickup
 * a description of the room
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
        super(id, name, description);
        this.exits=new HashMap<>();
        this.lockedDirections =new HashMap<>();
        customDirections = null;
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
        if(enemyList.size()==0){
            return null;
        }
        return enemyList;
    }

    public HashMap<String, Pair> getEntities() {
        return entities;
    }

    public void addEntity(Entity entity) {
        this.entities.put(entity.getID(), new Pair(entity, 1));
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

    public void printEnemy() {
        ArrayList<Enemy> enemies = getEnemyList();
        for (Enemy enemy : enemies){
            System.out.println("You can see a "+enemy.getName()+" here.");
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
        if(npcList.size()==0){
            return null;
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
        for (NPC enemy : npcs){
            System.out.println("You can see "+enemy.getName()+" here.");
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
        if(propList.size()==0){
            return null;
        }
        return propList;
    }

    public ArrayList<Pair> getItemList() {
        ArrayList<Pair> itemList = new ArrayList<>();
        for (Pair p : entities.values()){
            Entity e = p.getEntity();
            if (e.getType()=='i'){
                itemList.add(p);
            }
        }
        if(itemList.size()==0){
            return null;
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
            System.out.println("You can see:");
            for (Pair p : items) {
                Item currentItem = (Item) p.getEntity();
                System.out.println("- " +p.getCount()+"x "+ currentItem.getName());
            }
        }
    }

    public void printName(){
        System.out.println(name);
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
                //// TODO: 15/03/2017
//                System.out.println("The way "+currentDirection+" is blocked by "+props.get(door).getDescription().toLowerCase());
            } catch (NullPointerException ex){
//                System.out.println("Shhhh c:");
            }
        }

        if(customDirections != null){
            System.out.println(customDirections);
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
                System.out.print(toPrint);
                if (first){
                    toPrint="You can go "+exit;
                    first=false;
                } else{
                    toPrint=", "+exit;
                }
            }
        }
        System.out.print(toPrint);
    }

    //this function prints every time a room is discovered
    public void printRoom(){
        printDescription();
//        printProps();
        printItems();
        printNPCs();
        printEnemy();
        printDirections();
        System.out.println();
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