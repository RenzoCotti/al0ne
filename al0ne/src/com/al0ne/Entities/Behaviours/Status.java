package com.al0ne.Entities.Behaviours;

/**
 * Created by BMW on 05/04/2017.
 */
public abstract class Status {

    protected String name;
    protected Integer duration;
    protected String onApply;
    protected String onResolve;
    protected String onTick;

    public Status(String name, Integer duration, String onApply, String tick, String resolve){
        this.name = name;
        this.duration = duration;
        this.onApply=onApply;
        this.onResolve=resolve;
        this.onTick=tick;
    }

    public abstract boolean resolveStatus(Player player);

    public String getName(){
        return name;
    }

    public Integer getDuration(){
        return duration;
    }

    public String getOnApply(){
        return onApply;
    }

}
