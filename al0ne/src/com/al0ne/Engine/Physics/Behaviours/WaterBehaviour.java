package com.al0ne.Engine.Physics.Behaviours;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Engine.Physics.AddEntity;
import com.al0ne.Engine.Physics.Behaviour;
import com.al0ne.Entities.Items.ConcreteItems.Food.Ration;
import com.al0ne.Entities.Items.ConcreteItems.Weapon.Dagger;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 09/07/2017.
 */
public class WaterBehaviour extends Behaviour {


    public WaterBehaviour() {
        super("water", new AddEntity(new Dagger(Material.BRASS), 1));
    }

    @Override
    public String isInteractedWith(Behaviour b) {

        if(b.getName().equals("iron")){
            printToLog("The iron turns into food!");
            return "3";
        }
        return "0";
    }
}
