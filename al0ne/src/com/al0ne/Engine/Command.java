package com.al0ne.Engine;

/**
 * This is a list of all possible commands
 */
public enum Command {
    HELP, QUIT, SAVE, LOAD, DEBUG, COMMANDS,


    //debug commands
    WEIGHT, TIME, WARP, DEATH, QQQ, EXECUTE,
    STATUS,

    //custom actions
    DRINK, EAT, OPEN, MOVE, READ, AGAIN,

    //item related
    USE, EXAMINE, TAKE, EQUIP, PUT, DROP,

    //movement
    NORTH, SOUTH, EAST, WEST, DOWN, UP,

    //player related
    LOOK, HEALTH, INVENTORY,  EQUIPMENT, STORY, CAST,

    //npc
    TALK, ATTACK, BUY, GIVE

}



