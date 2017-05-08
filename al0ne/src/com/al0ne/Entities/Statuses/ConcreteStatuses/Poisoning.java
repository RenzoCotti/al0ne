package com.al0ne.Entities.Statuses.ConcreteStatuses;

import com.al0ne.Entities.Statuses.DamagingHealth;
import com.al0ne.Entities.Statuses.HealthStatus;

/**
 * Created by BMW on 06/04/2017.
 */
public class Poisoning extends DamagingHealth {
    public Poisoning(Integer length, Integer damage) {
        super("poisoning", length, damage, "You are poisoned!", "You take damage from the poison", "The poison wears off");
    }
}
