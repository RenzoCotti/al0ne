package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Behaviours.Wearable.*;
import com.al0ne.Entities.Items.ConcreteItems.Armor.*;
import com.al0ne.Entities.Items.ConcreteItems.Helmet.Barbute;
import com.al0ne.Entities.Items.ConcreteItems.Helmet.GreatHelm;
import com.al0ne.Entities.Items.ConcreteItems.Helmet.Sallet;
import com.al0ne.Entities.Items.ConcreteItems.Weapons.MeleeWeapon.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by BMW on 13/05/2017.
 */
public class LootTable implements Serializable{
    private ArrayList<Item> loot;

    public LootTable() {
        this.loot = new ArrayList<>();
        for (Material m : Material.getMetals()){
            loot.add(new Sallet(m));
            loot.add(new GreatHelm(m));
            loot.add(new Barbute(m));
            loot.add(new ChainMail(m));
            loot.add(new PlateArmor(m));
            loot.add(new ScaleArmor(m));
        }

        for (Material m : Material.getMaterials(0)){
            loot.add(new Armor(m));
        }

        for (Material m : Material.getMaterials(2)){
            loot.add(new Shield(m));
        }

        for (Material m : Material.getMaterials(3)){
            loot.add(new Helmet(m));
        }

        for (Material m : Material.getMaterials(1)){
            loot.add(new Sword(m));
            loot.add(new Mace(m));
            loot.add(new Spear(m));
            loot.add(new Knife(m));
            loot.add(new Axe(m));
        }

        long seed = System.nanoTime();
        Collections.shuffle(loot, new Random(seed));
    }

    public ArrayList<Pair> getLoot(int value){
        int total = 0;

        ArrayList<Pair> lootDropped = new ArrayList<>();


        int maxItems = 0;
        for (int begin = Utility.randomNumber(loot.size()); begin < loot.size(); begin++){
            Item currentItem = loot.get(begin);

            if(total+currentItem.getPrice() <= value){
                lootDropped.add(new Pair(currentItem, 1));
                total+= currentItem.getPrice();
                maxItems++;

                int chanceToContinue = (int)((((double)currentItem.getPrice())
                        /(double)value)*100);
//                System.out.println("must roll above: "+chanceToContinue);

                int random = Utility.randomNumber(currentItem.getPrice());
//                System.out.println("rolled: "+random);
                if(random > 100 - chanceToContinue || maxItems == 2){
                    break;
                }
            }
        }
        for(Pair p : Utility.toCoins(value-total)){
            if(p.getCount() > 0){
                lootDropped.add(p);
            }
        }

        long seed = System.nanoTime();
        Collections.shuffle(loot, new Random(seed));

        return lootDropped;
    }

}
