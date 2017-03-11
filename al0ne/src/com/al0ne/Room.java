package com.al0ne;

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

    public Room(String id, String name, String description) {
        this.id=id;
        this.description = description;
        this.name = name;
        this.props=new HashMap<>();
        this.items=new HashMap<>();
        this.exits=new HashMap<>();
        this.lockedDirections =new HashMap<>();
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

    protected HashMap<String, String> getExits() {
        return exits;
    }

    public String getId(){
        return id;
    }

    //prints props in the room
    private void printProps(){
        if (props.size()!=0){
            props.values().forEach(Prop::printDescription);
        }
    }

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

    private void printDescription(){
        System.out.println(description);
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
                System.out.println("Shhhh c:");
            }
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
    //// TODO: 08/03/2017 maybe shortened version? once visited once
    public void printRoom(){
//        System.out.println(name);
        printDescription();
        printProps();
        printItems();
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

    protected boolean hasItem(String id) {
        return items.get(id) != null;
    }

    protected Pair getPair(String id) {

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