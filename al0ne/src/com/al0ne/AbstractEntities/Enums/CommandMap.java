package com.al0ne.AbstractEntities.Enums;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by BMW on 30/04/2017.
 */
public class CommandMap implements Serializable{
    private HashMap<String, Command> commands;
    private HashMap<String, Command> aliases;

    public CommandMap(){
        this.commands = new HashMap<>();
        this.aliases = new HashMap<>();

        commands.put("help", Command.HELP);
        commands.put("exit", Command.QUIT);
        commands.put("quit", Command.QUIT);
        commands.put("load", Command.LOAD);
        commands.put("save", Command.SAVE);
        commands.put("weight", Command.WEIGHT);
        commands.put("health", Command.HEALTH);
        commands.put("time", Command.TIME);
        commands.put("use", Command.USE);
        commands.put("examine", Command.EXAMINE);
        commands.put("drink", Command.DRINK);
        commands.put("tidy", Command.TIDY);
        commands.put("eat", Command.EAT);
        commands.put("move", Command.MOVE);
        commands.put("north", Command.NORTH);
        commands.put("south", Command.SOUTH);
        commands.put("east", Command.EAST);
        commands.put("west", Command.WEST);
        commands.put("northeast", Command.NORTHEAST);
        commands.put("southwest", Command.SOUTHWEST);
        commands.put("northwest", Command.NORTHWEST);
        commands.put("southeast", Command.SOUTHEAST);
        commands.put("up", Command.UP);
        commands.put("down", Command.DOWN);
        commands.put("inventory", Command.INVENTORY);
        commands.put("talk", Command.TALK);
        commands.put("take", Command.TAKE);
        commands.put("buy", Command.BUY);
        commands.put("qqq", Command.QQQ);
        commands.put("cast", Command.CAST);
        commands.put("status", Command.STATUS);
        commands.put("open", Command.OPEN);
        commands.put("put", Command.PUT);
        commands.put("give", Command.GIVE);
        commands.put("read", Command.READ);
        commands.put("look", Command.LOOK);
        commands.put("equip", Command.EQUIP);
        commands.put("attack", Command.ATTACK);
        commands.put("again", Command.AGAIN);
        commands.put("drop", Command.DROP);
        commands.put("equipment", Command.EQUIPMENT);
        commands.put("story", Command.STORY);
        commands.put("warp", Command.WARP);
        commands.put("death", Command.DEATH);
        commands.put("commands", Command.COMMANDS);
        commands.put("asd", Command.DEBUG);
        commands.put("execute", Command.EXECUTE);
        commands.put("wait", Command.WAIT);
        commands.put("press", Command.PRESS);
        commands.put("ls", Command.LINUX);
        commands.put("shoot", Command.SHOOT);
        commands.put("reload", Command.RELOAD);
        commands.put("quests", Command.QUEST);
        commands.put("light", Command.LIGHT);




        aliases.put("x", Command.EXAMINE);
        aliases.put("z", Command.WAIT);
        aliases.put("i", Command.INVENTORY);
        aliases.put("n", Command.NORTH);
        aliases.put("sw", Command.SOUTHWEST);
        aliases.put("se", Command.SOUTHEAST);
        aliases.put("nw", Command.NORTHWEST);
        aliases.put("ne", Command.NORTHEAST);
        aliases.put("d", Command.DOWN);
        aliases.put("u", Command.UP);
        aliases.put("e", Command.EAST);
        aliases.put("w", Command.WEST);
        aliases.put("s", Command.SOUTH);
        aliases.put("worn", Command.EQUIPMENT);
        aliases.put("kill", Command.ATTACK);
        aliases.put("g", Command.AGAIN);
        aliases.put("wear", Command.EQUIP);
        aliases.put("wield", Command.EQUIP);
        aliases.put("l", Command.LOOK);
        aliases.put("go north", Command.NORTH);
        aliases.put("go south", Command.SOUTH);
        aliases.put("go east", Command.EAST);
        aliases.put("go west", Command.WEST);
        aliases.put("go up", Command.UP);
        aliases.put("go down", Command.DOWN);
        aliases.put("go northeast", Command.NORTHEAST);
        aliases.put("go northwest", Command.NORTHWEST);
        aliases.put("go southeast", Command.SOUTHEAST);
        aliases.put("go southwest", Command.SOUTHWEST);
        aliases.put("mv", Command.LINUX);
        aliases.put("rm", Command.LINUX);
        aliases.put("cd", Command.LINUX);
        aliases.put("?", Command.HELP);
        aliases.put("fire", Command.SHOOT);
        aliases.put("load", Command.RELOAD);
        aliases.put("journal", Command.QUEST);
        aliases.put("ask", Command.TALK);




    }

    public String stringify(Command command){
        for(String s: commands.keySet()){
            if(commands.get(s).equals(command)){
                return s;
            }
        }
        return null;
    }

    public Command toCommand(String s){
        Command c = commands.get(s);
        if(c == null){
            return aliases.get(s);
        }
        return c;
    }
}
