package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Player;
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
