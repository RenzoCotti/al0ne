package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Engine.Physics.Behaviours.MaterialBehaviours.FlashlightBehaviour;

public class Flashlight extends LightItem{
    public Flashlight() {
        super("flashlight", "Flashlight", "A fairly bright flashlight. It uses AA batteries.",
                0.3, Size.VSMALL, Material.ALUMINIUM, 5, "AA battery",
                "You change the battery.");
        addProperty(new FlashlightBehaviour());
    }
}
