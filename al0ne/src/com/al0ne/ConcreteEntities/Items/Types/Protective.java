package com.al0ne.ConcreteEntities.Items.Types;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Wearable;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 23/03/2017.
 */
public abstract class Protective extends Wearable {
    protected int armor;
    protected int encumberment;

    public Protective(String id, String name, String description,
                      double weight, int armor, int encumberment, Size size, Material material) {
        super(id, name, description, weight, size, material);
        this.armor=armor;
        this.encumberment=encumberment;
    }

    public int getArmor() {
        return armor;
    }

    public int getEncumberment() {
        return encumberment;
    }

    @Override
    public void printLongDescription(Player player) {
        super.printLongDescription(player);
        if(armor > 0){
            printToLog("It protects for "+ armor+".");
        }
    }
}
