package com.al0ne.ConcreteEntities.Statuses.ConcreteStatuses;
import com.al0ne.ConcreteEntities.Statuses.TimerHealth;

/**
 * Created by BMW on 17/04/2017.
 */
public class NaturalHealing extends TimerHealth {
    public NaturalHealing() {
        super("naturalhealing", 10, 1, "Your body heals naturally.", "Your body heals a bit.", "Your body stopped healing.");
    }
}
