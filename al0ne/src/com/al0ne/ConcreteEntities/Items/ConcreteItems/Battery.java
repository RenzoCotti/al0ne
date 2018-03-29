package com.al0ne.ConcreteEntities.Items.ConcreteItems;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.Engine.Physics.Behaviours.BatteryBehaviour;

public class Battery extends Item{

    public Battery(String type) {
        super(type, "Battery", "Hopefully it's charged",
                0.1, Size.MICRO, Material.IRON, null);
        switch (type){
            case "AA":
                setName("AA battery");
                setLongDescription("An AA battery. "+longDescription);
                setWeight(0.05);
                addBehaviour(new BatteryBehaviour("aabattery"));
                break;
            case "AAA":
                setName("AAA battery");
                setLongDescription("An AAA battery. "+longDescription);
                setWeight(0.02);
                addBehaviour(new BatteryBehaviour("aaabattery"));
                break;

        }
        setSize(Size.toInt(Size.MICRO));

    }

}
