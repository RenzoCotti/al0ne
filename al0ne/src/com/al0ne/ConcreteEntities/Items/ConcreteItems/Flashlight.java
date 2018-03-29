package com.al0ne.ConcreteEntities.Items.ConcreteItems;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.Engine.Physics.Behaviours.MaterialBehaviours.RequiresBatteryBehaviour;

public class Flashlight extends LightItem{
    public Flashlight() {
        super("flashlight", "Flashlight", "A fairly bright flashlight. It uses AA batteries.",
                0.3, Size.VSMALL, Material.ALUMINIUM, 50);
        addBehaviour(new RequiresBatteryBehaviour("aabattery"));
        setRefillable("aabattery", "You change the battery.");
    }
}
