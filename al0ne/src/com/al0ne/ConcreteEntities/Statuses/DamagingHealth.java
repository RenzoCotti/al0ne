package com.al0ne.ConcreteEntities.Statuses;

/**
 * Created by BMW on 24/04/2017.
 */
public class DamagingHealth extends HealthStatus{
    public DamagingHealth(String name, Integer duration, Integer healthModifier, String onApply, String tick, String resolve) {
        super(name, duration, -healthModifier, onApply, tick, resolve);
        if(healthModifier < 0){
            System.out.println(name+" HEALS, fix it!");
        }
    }
}
