package com.al0ne.AbstractEntities.Abstract;

import java.io.Serializable;
import java.util.ArrayList;

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
    protected ArrayList<Status> toApply;

    public Status(String name, Integer duration, String onApply, String tick, String resolve){
        this.name = name;
        this.duration = duration;
        this.maxDuration = duration;
        this.onApply=onApply;
        this.onResolve=resolve;
        this.onTick=tick;
        this.toApply = new ArrayList<>();
    }

    public Status(String name, Integer duration, String onApply, String resolve){
        this.name = name;
        this.duration = duration;
        this.onApply=onApply;
        this.onResolve=resolve;
        this.maxDuration = duration;
        this.toApply = new ArrayList<>();
    }

    public Status(String name, Integer duration, String onApply){
        this.name = name;
        this.duration = duration;
        this.onApply=onApply;
        this.maxDuration = duration;
        this.toApply = new ArrayList<>();
    }

    //returns true if it is to be removed
    public abstract boolean resolveStatus(WorldCharacter character);

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

    public Integer getMaxDuration() {
        return maxDuration;
    }

    public ArrayList<Status> getToApply() {
        return toApply;
    }
}
