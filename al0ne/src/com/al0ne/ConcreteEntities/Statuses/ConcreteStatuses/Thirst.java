package com.al0ne.ConcreteEntities.Statuses.ConcreteStatuses;

import com.al0ne.ConcreteEntities.Statuses.BasicNeed;

import static com.al0ne.Engine.Main.printToLog;



public class Thirst extends BasicNeed {
    public static final int THIRST_CLOCK = 50;
    public Thirst() {
        super("thirst", THIRST_CLOCK, "You'd like some water.", "dehydration");
    }
}
