package com.al0ne.Entities.Statuses.ConcreteStatuses;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Status;
import com.al0ne.Entities.Statuses.BasicNeed;

import static com.al0ne.Engine.Main.printToLog;



public class Thirst extends BasicNeed {
    public static final int THIRST_CLOCK = 50;
    public Thirst() {
        super("thirst", THIRST_CLOCK, "You'd like some water.", "dehydrated");
    }
}
