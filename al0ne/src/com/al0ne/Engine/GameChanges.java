package com.al0ne.Engine;

import com.al0ne.Behaviours.*;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.abstractEntities.Enemy;
import com.al0ne.Behaviours.abstractEntities.Interactable;
import com.al0ne.Engine.Editing.EditorInfo;
import com.al0ne.Engine.UI.SimpleItem;
import com.al0ne.Entities.Items.Behaviours.ChargeItem;
import com.al0ne.Entities.Items.Behaviours.Protective;
import com.al0ne.Entities.Items.Behaviours.Wearable.Weapon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

import static com.al0ne.Engine.Main.player;
import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 13/04/2017.
 */
public class GameChanges {

    public static void save(String s, String path, Object g, boolean game){
        FileOutputStream fop = null;
        ObjectOutputStream oos = null;
        File file;

        if(g instanceof Game){
            ((Game) g).setNotes(Main.notes.getText());
        }

        try {
            if (path != null && game){
                file = new File(path+".save");
            } else if (game) {
                file = new File("./"+s+".save");
            } else if(path != null){
                file = new File(path+".edtr");
            } else{
                file = new File("./"+s+".edtr");
            }
            fop = new FileOutputStream(file);

            ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();

            oos = new ObjectOutputStream(arrayOut);


            oos.writeObject(g);
            fop.write(Base64.getEncoder().encode(arrayOut.toByteArray()));

            // if file doesnt exists, then create it
            if (!file.exists()) {
                printToLog("File already exists. Specify a non existing file name.");
                return;
            }

            oos.flush();
            oos.close();

            printToLog("Saving successful");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fop != null) {
                try {
                    fop.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean load(String s, String path, boolean game){
        Object loaded = deserializeGame(s, path);
        if (loaded == null){
            printToLog("Failed to load the game.");
            return false;
        } else if(loaded instanceof Game && game){
            Game loadedGame = (Game) loaded;
            Main.game = loadedGame;
            Main.player = loadedGame.getPlayer();
            Main.turnCounter = loadedGame.getTurnCount();
            Main.notes.setText(loadedGame.getNotes());

            printToLog("Game loaded successfully.");
            printToLog();
            Room currentRoom = player.getCurrentRoom();
            currentRoom.printRoom();
            currentRoom.printName();
            return true;
        } else if(loaded instanceof EditorInfo && !game){
            Main.edit = (EditorInfo) loaded;
            return true;
        } else {
            System.out.println("error: file loaded not recognised");
            return false;
        }
    }

    public static Game loadAndGetGame(String s, String path){
        Object loaded = deserializeGame(s, path);
        if (loaded == null){
            return null;
        } else if(loaded instanceof Game){
            return (Game) loaded;
        } else {
            return null;
        }
    }

    private static Object deserializeGame(String filename, String path) {

        Object read;

        FileInputStream fin = null;
        ObjectInputStream ois = null;

        try {
            String toUse;
            if (path != null){
                fin = new FileInputStream(path);
                toUse = path;
            } else{
                fin = new FileInputStream(filename);
                toUse = filename;
            }


            ByteArrayInputStream arrayIn = new ByteArrayInputStream(Base64.getDecoder().decode(
                    Files.readAllBytes(Paths.get(toUse))));

            ois = new ObjectInputStream(arrayIn);
            read = ois.readObject();


        } catch (Exception ex) {
            printToLog("File not found");
            return null;
        } finally {

            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        if(read instanceof Game || read instanceof EditorInfo){
            return read;
        } else{
            System.out.println("error loading file");
            return null;
        }

    }

    public static boolean changeWorld(String s){
        //save old state
        Area oldArea = Main.game.getWorld(Main.game.getCurrentWorldName());
        oldArea.setPlayer(Main.player);

        for(Area w : Main.game.getWorlds().values()){
            if(s.equals(w.getAreaName())){
                Main.game.setCurrentWorld(s);
                Main.player = Main.game.getPlayer();
                Main.clearScreen();
                printToLog("Your see black and feel very cold for a moment, and suddenly you are somewhere else.");
                printToLog();
                player.getCurrentRoom().printRoom();
                player.getCurrentRoom().visit();
                return true;
            }
        }

        System.out.println("404: world not found\nAvailable worlds:");
        for(Area w : Main.game.getWorlds().values()){
            printToLog("- "+w.getAreaName());
        }
        return false;

    }

    public static ObservableList<SimpleItem> getInventoryData(){

        ObservableList<SimpleItem> data = FXCollections.observableArrayList();

        if (Main.player.getInventory().size()==0){}
        else {
            for (Pair pair : Main.player.getInventory().values()) {
                Item currentItem = (Item) pair.getEntity();
                double weight = Utility.twoDecimals(currentItem.getWeight()*pair.getCount());
                String name = currentItem.getName();
                int damage = 0;
                int defense = 0;
//                if(Main.game.isInDebugMode()){
                    if (currentItem instanceof Protective){
                        defense = ((Protective) currentItem).getArmor();
                        damage = ((Protective) currentItem).getEncumberment();
//                        name=" ("+((Protective)currentItem).getArmor()+" DEF) "+"$"+currentItem.getPrice()+" "+name;
                    } else if (currentItem instanceof Weapon){
                        damage = ((Weapon) currentItem).getDamage();
                        defense = ((Weapon) currentItem).getArmorPenetration();
//                        name=" ("+((Weapon)currentItem).getDamage()+"DMG/ "+
//                                ((Weapon)currentItem).getArmorPenetration()+"AP) "+"$"+currentItem.getPrice()+" "+name;
                    }
//                }

                SimpleItem s = new SimpleItem(name, pair.getCount(), weight, currentItem.getPrice(),defense, damage);
                data.add(s);
            }
        }
        return data;
    }

    public static void attackIfAggro(Player player){
        Room currentRoom = player.getCurrentRoom();
        if(currentRoom.hasEnemies()){
            for (Enemy e : currentRoom.getEnemyList()){
                if(e.isAggro() && !e.isSnooze()){
                    System.out.println(e.getName()+" attacks");
                    e.isAttacked(player, currentRoom);
                } else{
                    if (e.isSnooze()){
                        e.setSnooze(false);
                    }
                }
            }
        }
    }

    public static void restartGame(){
        GameChanges.changeWorld(Main.game.getStartingWorld());
        Main.input.setDisable(false);
        Main.game = new WarpGame();
        Main.player = Main.game.getPlayer();
//        Main.currentRoom = Main.game.getRoom();
        Main.log.setText("");
        printToLog("Game restarted.");
        printToLog();
        Main.player.getCurrentRoom().printName();
        Main.player.getCurrentRoom().printRoom();
    }


    public static Game copyGame(Game g){

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        ByteArrayOutputStream bos;
        ByteArrayInputStream bis;


        Game copy = null;

        try {


            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);

            oos.writeObject(g);

            byte temp[] = bos.toByteArray();
            bos.close();
            oos.flush();
            oos.close();


            bis = new ByteArrayInputStream(temp);
            ois = new ObjectInputStream(bis);

            try{
                copy = (Game) ois.readObject();
            } catch (Exception ex){
                System.out.println("CopyGame: object is not a game");
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return copy;
        }

//        save("temp123", null, g, true);
//        Game game = loadAndGetGame("temp123.save", null);
//        Utility.removeFile("temp123.save");
//
//        return game;

    }


    public static void useResult(HashMap<Integer, Object> result, Player player, ArrayList<Pair> toAdd,
                                 Interactable obj, Interactable subj){



        Room currentRoom = player.getCurrentRoom();
        for(Integer i : result.keySet()){
            switch (i){

                case 1:
                    //success, no need to print
                    break;
                case 3:
                    //tries to add to inventory, if can't add to room
                    for (Pair p: toAdd){
                        currentRoom.addEntity(p.getEntity(), p.getCount());
                    }
                    break;

//                case 2:
//                    //add to room
//                    Pair pair1 = interacted.getEntity().getPair();
//                    Entity entity = pair1.getEntity();
//                    int count = pair1.getCount();
//                    currentRoom.addEntity(entity, count);
//                    break;
                case 4:
                    //remove this
                    if(player.hasItemInInventory(subj.getID())){
                        player.removeOneItem((Item) subj);
                    } else{
                        currentRoom.getEntities().remove(subj.getID());
                    }
                    break;
                case 5:
                    if(obj == null){
                        System.out.println("probably a quest tried to remove an item ");
                        break;
                    }
                    //remove other
                    if(player.hasItemInInventory(obj.getID())){
                        player.removeOneItem((Item) obj);
                    } else{
                        currentRoom.getEntities().remove(obj.getID());
                    }
                    break;
                case 6:
                    currentRoom.unlockDirection((String)result.get(i));
                    break;
                case 7:
                    if(obj == null || subj == null){
                        System.out.println("probably a quest tried to refill an object");
                        break;
                    }
                    //refill
                    ((ChargeItem) subj).refill(player, obj);
                    break;
                case 8:
                    //modify health
                    player.modifyHealth((Integer)result.get(i));
                    break;
                case 9:
                    if(subj == null){
                        System.out.println("probably a quest tried to change an object's integrity");
                        break;
                    }
                    //modify integrity
                    subj.modifyIntegrity((Integer) result.get(i));
                    break;

                default:
                    System.out.println("ERROR: no behaviour code found");
                    break;

            }
        }
    }


}
