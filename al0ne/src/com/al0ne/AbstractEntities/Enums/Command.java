package com.al0ne.AbstractEntities.Enums;

/**
 * This is a list of all possible commands
 */
public enum Command {
    HELP, QUIT, SAVE, LOAD, DEBUG, COMMANDS,
    AGAIN, NONE, WAIT,


    //debug commands
    WEIGHT, TIME, WARP, DEATH, QQQ, EXECUTE,
    STATUS,

    //custom actions
    DRINK, EAT, OPEN, MOVE, READ, TIDY, PRESS, LIGHT,

    //item related
    USE, EXAMINE, TAKE, EQUIP, PUT, DROP,

    //movement
    NORTH, SOUTH, EAST, WEST, DOWN, UP,
    NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST,

    //player related
    LOOK, HEALTH, INVENTORY,  EQUIPMENT, STORY, CAST,
    QUEST,

    //npc
    TALK, ATTACK, BUY, GIVE, SHOOT, RELOAD,

    //extra
    LINUX

}



