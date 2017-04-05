package com.al0ne.Entities.Behaviours;

/**
 * Created by BMW on 05/04/2017.
 */
public abstract class Status {

    protected String name;
    protected Integer duration;

    public Status(String name, Integer duration){
        this.name = name;
        this.duration = duration;
    }

    public abstract boolean resolveStatus();

    public String getName(){
        return name;
    }

    public Integer getDuration(){
        return duration;
    }

}
