package com.al0ne.Engine;

import java.util.HashMap;

/**
 * Created by BMW on 30/04/2017.
 */
public class CommandMap {
    private static HashMap<String, Command> commandMap;

    public CommandMap(){
        this.commandMap = new HashMap<>();

        commandMap.put("help", Command.HELP);
        commandMap.put("exit", Command.QUIT);
        commandMap.put("quit", Command.QUIT);
        commandMap.put("load", Command.LOAD);
        commandMap.put("save", Command.SAVE);
        commandMap.put("weight", Command.WEIGHT);
        commandMap.put("health", Command.HEALTH);
        commandMap.put("time", Command.TIME);
        commandMap.put("use", Command.USE);
        commandMap.put("examine", Command.EXAMINE);
        commandMap.put("x", Command.EXAMINE);
        commandMap.put("drink", Command.DRINK);
        commandMap.put("eat", Command.EAT);
        commandMap.put("move", Command.MOVE);
        commandMap.put("n", Command.NORTH);
        commandMap.put("north", Command.NORTH);
        commandMap.put("south", Command.SOUTH);
        commandMap.put("s", Command.SOUTH);
        commandMap.put("east", Command.EAST);
        commandMap.put("e", Command.EAST);
        commandMap.put("w", Command.WEST);
        commandMap.put("west", Command.WEST);
        commandMap.put("u", Command.UP);
        commandMap.put("up", Command.UP);
        commandMap.put("d", Command.DOWN);
        commandMap.put("down", Command.DOWN);
        commandMap.put("i", Command.INVENTORY);
        commandMap.put("inventory", Command.INVENTORY);
        commandMap.put("talk", Command.TALK);
        commandMap.put("buy", Command.BUY);
        commandMap.put("give", Command.GIVE);
        commandMap.put("read", Command.READ);
        commandMap.put("l", Command.LOOK);
        commandMap.put("look", Command.LOOK);
        commandMap.put("wear", Command.EQUIP);
        commandMap.put("wield", Command.EQUIP);
        commandMap.put("equip", Command.EQUIP);
        commandMap.put("attack", Command.ATTACK);
        commandMap.put("kill", Command.ATTACK);
        commandMap.put("g", Command.AGAIN);
        commandMap.put("again", Command.AGAIN);
        commandMap.put("drop", Command.DROP);
        commandMap.put("worn", Command.DROP);
        commandMap.put("equipment", Command.DROP);
        commandMap.put("story", Command.DROP);
        commandMap.put("warp", Command.DROP);
        commandMap.put("death", Command.DROP);
        commandMap.put("commands", Command.DROP);
        commandMap.put("ilikecheese", Command.DROP);
        commandMap.put("execute", Command.DROP);
    }

    public void addCommand(String s, Command c){
        commandMap.put(s, c);
    }

    public String stringify(Command command){
        for(String s: commandMap.keySet()){
            if(commandMap.get(s).equals(command)){
                return s;
            }
        }
        return null;
    }

    public Command toCommand(String s){
        return commandMap.get(s);
    }
}
