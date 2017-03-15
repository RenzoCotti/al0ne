package com.al0ne;

import com.al0ne.Entities.Enemy;
import com.al0ne.Entities.NPC;
import com.al0ne.Items.Item;
import com.al0ne.Items.Pair;
import com.al0ne.Items.Prop;
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
public class Room {


    //room description
    private String description;
    //room name
    private String name;
    //room ID
    private String id;
    //maps direction - room ID
    private HashMap<String, String> exits;
    //maps door ID - direction
    private HashMap<String, String> lockedDirections;
    //maps propName - Prop
    private HashMap<String, Prop> props;
    //maps itemID - (Item, count)
    private HashMap<String, Pair> items;

    private HashMap<String, NPC> npcList;

    private HashMap<String, Enemy> enemyList;

    private String customDirections;



    public Room(String id, String name, String description) {
        this.id=id;
        this.description = description;
        this.name = name;
        this.props=new HashMap<>();
        this.items=new HashMap<>();
        this.exits=new HashMap<>();
        this.npcList=new HashMap<>();
        this.lockedDirections =new HashMap<>();
        this.enemyList = new HashMap<>();
        customDirections = null;
    }

    public HashMap<String, Enemy> getEnemyList() {
        return enemyList;
    }

    public void addEnemy(Enemy enemy) {
        this.enemyList.put(enemy.getID(), enemy);
    }

    public Enemy getEnemy(String name) {
        for (Enemy e : enemyList.values()){
            if (e.getName().toLowerCase().contains(name)){
                return e;
            }
        }
        return null;
    }

    public void printEnemy() {
        for (Enemy enemy : enemyList.values()){
            System.out.println("You can see "+enemy.getName()+" here.");
        }
    }


    public HashMap<String, NPC> getNPCList() {
        return npcList;
    }

    public void addNPC(NPC npc) {
        this.npcList.put(npc.getID(), npc);
    }

    public NPC getNPC(String name) {
        for (NPC n : npcList.values()){
            if (n.getName().toLowerCase().equals(name)){
                return n;
            }
        }
        return null;
    }

    public void printNPCs() {
        for (NPC npc : npcList.values()){
            System.out.println("You can see "+npc.getName()+" here.");
        }
    }


    public String getName() {
        return name;
    }

    public HashMap<String, Prop> getProps() {
        return props;
    }

    public HashMap<String, Pair> getItems() {
        return items;
    }

    public HashMap<String, String> getExits() {
        return exits;
    }

    public String getID(){
        return id;
    }

//    //prints props in the room
//    private void printProps(){
//        if (props.size()!=0){
//            props.values().forEach(Prop::printDescription);
//        }
//    }

    //prints items in the room
    private void printItems(){
        if (items.size()!=0){
            System.out.println("You can see:");
            for (Pair item : items.values()) {
                Item currentItem = item.getItem();
                System.out.println("- " +item.getCount()+"x "+ currentItem.getName());
            }
        }
    }

    public void printName(){
        System.out.println(name);
    }

    private void printDescription(){
        System.out.println(description);
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
                System.out.println("The way "+currentDirection+" is blocked by "+props.get(door).getDescription().toLowerCase());
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

    public void addProp(Prop prop) {
        this.props.put(prop.getID(), prop);
    }

    public void addItem(Item item) {
        if (hasItem(item.getID())){
            Pair currentPair=items.get(item.getID());
            currentPair.addCount();
        } else{
            this.items.put(item.getID(), new Pair(item, 1));
        }
    }

    public void addItem(Item item, Integer amount) {
        if (hasItem(item.getID())){
            Pair currentPair=items.get(item.getID());
            currentPair.setCount(amount);
        } else{
            this.items.put(item.getID(), new Pair(item, amount));
        }
    }

    public boolean hasItem(String id) {
        return items.get(id) != null;
    }

    public Pair getPair(String id) {

        if (hasItem(id)){
            return items.get(id);
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