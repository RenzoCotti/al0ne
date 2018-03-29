package com.al0ne.ConcreteEntities.Items.Types.Wearable;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.AbstractEntities.Player.Player;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 22/07/2017.
 */
public class RangedWeapon extends Weapon{
    protected String ammoID;
    protected boolean needsReloading;
    protected int magazineSize;
    protected int inMagazine;
    public RangedWeapon(String id, String name, String description, String damageType, int armorPen, int damage, double weight, Size size, Material material, String ammoID) {
        super(id, name, description, damageType, armorPen, damage, weight, size, material);
        this.ammoID = ammoID;
        this.needsReloading = false;
        this.magazineSize = 1;
        this.inMagazine = 1;
    }

    public RangedWeapon(String id, String name, String description, String damageType, int armorPen, int damage,
                        double weight, Size size, Material material, String ammoID, int magazineSize) {
        super(id, name, description, damageType, armorPen, damage, weight, size, material);
        this.ammoID = ammoID;
        this.needsReloading = true;
        this.magazineSize = magazineSize;
        this.inMagazine = 0;
    }

    public String getAmmoID() {
        return ammoID;
    }

    public boolean needsReloading() {
        return needsReloading;
    }

    public int getMagazineSize() {
        return magazineSize;
    }

    public int getInMagazine() {
        return inMagazine;
    }

    public void setInMagazine(int inMagazine) {
        this.inMagazine = inMagazine;
    }

    public void fullReload() {
        this.inMagazine = magazineSize;
    }

    public void shoot(){
        this.inMagazine--;
    }

    @Override
    public void printLongDescription(Player player) {
        super.printLongDescription(player);
        if(needsReloading()){
            printToLog("Its magazine holds: "+inMagazine+"/"+magazineSize+" rounds.");
        }
    }
}
