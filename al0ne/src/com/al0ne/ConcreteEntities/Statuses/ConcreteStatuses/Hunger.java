package com.al0ne.ConcreteEntities.Statuses.ConcreteStatuses;

import com.al0ne.ConcreteEntities.Statuses.BasicNeed;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 13/04/2017.
 */
public class Hunger extends BasicNeed {
    public static final int HUNGER_CLOCK = 150;
    public Hunger() {
        super("hunger", HUNGER_CLOCK, "You'd like some food.", "starving");
    }
}
