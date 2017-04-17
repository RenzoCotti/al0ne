package com.al0ne.Behaviours;

import java.io.Serializable;

/**
 * Created by BMW on 05/04/2017.
 */
public abstract class Status implements Serializable{

    protected String name;
    protected Integer duration;
    protected Integer maxDuration;
    protected String onApply;
    protected String onResolve;
    protected String onTick;

    public Status(String name, Integer duration, String onApply, String tick, String resolve){
        this.name = name;
        this.duration = duration;
        this.maxDuration = duration;
        this.onApply=onApply;
        this.onResolve=resolve;
        this.onTick=tick;
    }

    public Status(String name, Integer duration, String onApply, String resolve){
        this.name = name;
        this.duration = duration;
        this.onApply=onApply;
        this.onResolve=resolve;
        this.maxDuration = duration;
    }

    public Status(String name, Integer duration, String onApply){
        this.name = name;
        this.duration = duration;
        this.onApply=onApply;
        this.maxDuration = duration;
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

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void modifyDuration(Integer amount){
        if(duration+amount >= maxDuration){
            this.duration = maxDuration;
        } else if(duration+amount <= 0){
            this.duration = 0;
        } else{
            this.duration += amount;
        }
    }
}
