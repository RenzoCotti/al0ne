package com.al0ne.Behaviours.Pairs;

import com.al0ne.Behaviours.abstractEntities.Interactable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class InteractionResult implements Serializable {
    protected HashMap<Integer, Object> result;
    protected Interactable first;
    protected Interactable second;
    protected ArrayList<Pair> toAdd;

    public InteractionResult(HashMap<Integer, Object> result, Interactable first,
                             Interactable second, ArrayList<Pair> toAdd) {
        this.result = result;
        this.first = first;
        this.second = second;
        this.toAdd = toAdd;
    }

    public HashMap<Integer, Object> getResult() {
        return result;
    }

    public Interactable getFirst() {
        return first;
    }

    public Interactable getSecond() {
        return second;
    }

    public ArrayList<Pair> getToAdd() {
        return toAdd;
    }
}
