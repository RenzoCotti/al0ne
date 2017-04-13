package com.al0ne.Entities.Statuses;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Status;

import static com.al0ne.Engine.Main.printToLog;



public class Thirst extends BasicNeed{
    public static final int THIRST_CLOCK = 20;
    public Thirst() {
        super("thirst", THIRST_CLOCK, "You'd like some water.", "dehydrated");
    }
}
