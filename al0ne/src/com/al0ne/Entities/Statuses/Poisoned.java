package com.al0ne.Entities.Statuses;

/**
 * Created by BMW on 06/04/2017.
 */
public class Poisoned extends HealthStatus{
    public Poisoned(Integer length, Integer damage) {
        super("poison", length, damage, "You are poisoned!", "You take damage from the poison", "The poison wears off");
    }
}
