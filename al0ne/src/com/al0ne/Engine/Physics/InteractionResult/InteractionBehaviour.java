package com.al0ne.Engine.Physics.InteractionResult;

import com.al0ne.Behaviours.Player;

import java.io.Serializable;

public abstract class InteractionBehaviour implements Serializable{
    public abstract void interactionEffect(Player p);
}
