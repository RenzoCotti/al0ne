package com.al0ne.Engine.Physics;

/**
 * Created by BMW on 09/07/2017.
 */
public class FoodBehaviour extends Behaviour{
    public FoodBehaviour() {
        super("food");
    }

    @Override
    public int isInteractedWith(Behaviour b) {
        return 0;
    }
}
