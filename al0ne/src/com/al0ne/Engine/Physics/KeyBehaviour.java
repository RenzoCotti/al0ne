package com.al0ne.Engine.Physics;

/**
 * Created by BMW on 09/07/2017.
 */
public class KeyBehaviour extends Behaviour{
    public KeyBehaviour() {
        super("key");
    }

    @Override
    public int isInteractedWith(Behaviour b) {
        return 0;
    }
}
