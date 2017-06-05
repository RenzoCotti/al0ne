package com.al0ne.Engine;

import com.al0ne.Behaviours.*;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Behaviours.abstractEntities.Enemy;
import com.al0ne.Behaviours.abstractEntities.Entity;
import com.al0ne.Engine.Editing.EditorInfo;
import com.al0ne.Engine.UI.SimpleItem;
import com.al0ne.Entities.Items.Behaviours.Protective;
import com.al0ne.Entities.Items.Behaviours.Wearable.Weapon;
import com.al0ne.Entities.Items.ConcreteItems.WarpStone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

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
            Main.currentRoom = loadedGame.getRoom();
            Main.notes.setText(loadedGame.getNotes());

            printToLog("Game loaded successfully.");
            printToLog();
            Main.currentRoom.printRoom();
            Main.currentRoom.printName();
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
        World oldWorld = Main.game.getWorld(Main.game.getCurrentWorld());
        oldWorld.setPlayer(Main.player);

        if (Main.game instanceof WarpGame && !((WarpGame) Main.game).hasWarpstone()){
            ((WarpGame) Main.game).setWarpstone();

            for (World w : Main.game.getWorlds().values()){
                Player p = w.getPlayer();

                if(!p.hasItemInInventory("warpstone")){
                    p.addOneItem(new Pair(new WarpStone(), 1));
                }
            }
        }

        switch (s){
            case "alpha":
            case "medievaly":
            case "cave":
                Main.game.setCurrentWorld(s);
                Main.player = Main.game.getPlayer();
                Main.currentRoom = Main.player.getCurrentRoom();
                Main.clearScreen();
                Main.currentRoom.visit();
                return true;
            default:
                printToLog("404: world not found\nAvailable worlds:");
                for(World w : Main.game.getWorlds().values()){
                    printToLog("- "+w.getWorldName());
                }
                return false;
        }
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

    public static void attackIfAggro(Player player, Room currentRoom){
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
        Main.currentRoom = Main.game.getRoom();
        Main.log.setText("");
        printToLog("Game restarted.");
        printToLog();
        Main.player.getCurrentRoom().printName();
        Main.player.getCurrentRoom().printRoom();
    }


    public static Game copyGame(Game g){

        save("temp123", null, g, true);
        Game game = loadAndGetGame("temp123.save", null);
        Utility.removeFile("temp123.save");

        return game;

//        ObjectInputStream ois = null;
//        ObjectOutputStream oos = null;
//
//        Game copy = null;
//
//        try {
//
//            ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
//            ByteArrayInputStream arrayIn = new ByteArrayInputStream(arrayOut.toByteArray());
//            ois = new ObjectInputStream(arrayIn);
//
//
//            //we write into an array of bytes
//            oos = new ObjectOutputStream(arrayOut);
//
//
//            oos.writeObject(g);
//            try{
//                copy = (Game) ois.readObject();
//            } catch (Exception ex){
//                copy = null;
//            }
//
//            oos.flush();
//            oos.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//
//            if (oos != null) {
//                try {
//                    oos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (ois != null) {
//                try {
//                    ois.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            return copy;
//        }

//        Game tempGame = new Game(g.getGameName());
//        for(String worldName : g.getWorlds().keySet()){
//            World w = g.getWorlds().get(worldName);
//            World tempWorld = new World(w.getWorldName());
//            for(String roomName : w.getRooms().keySet()){
//                Room r = w.getRooms().get(roomName);
//                Room tempRoom = new Room(r.getName(), r.getLongDescription());
//                for(String entityName: r.getEntities().keySet()){
//                    Pair p = r.getEntities().get(entityName);
//                    Pair tempPair = new Pair(p.getEntity().getClone(), p.getCount());
//                    if(tempPair.getEntity() != null){
//                        tempRoom.addEntity(tempPair.getEntity(), p.getCount());
//                    }
//                }
//                for(String exit : r.getExits().keySet()){
//                    //problem
//                }
//
//                tempWorld.putRoom(tempRoom);
//                tempWorld.setStartingRoom(tempWorld.getRooms().get(w.getStartingRoom().getID()));
//                Player p = w.getPlayer();
//                Player tempPlayer = new Player(p.getName(), p.getStory(), p.hasNeeds(), w.getStartingRoom(),
//                        p.getMaxHealth(), p.getAttack(), p.getDexterity(), p.getArmorLevel(),
//                        p.getDamage(), p.getMaxWeight());
//                tempWorld.setPlayer(tempPlayer);
//            }
//        }
//
//        return tempGame;
    }

}
